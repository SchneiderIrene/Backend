package de.leafgrow.leafgrow_project.security.sec_service;

import de.leafgrow.leafgrow_project.domain.entity.User;
import de.leafgrow.leafgrow_project.security.AuthInfo;
import de.leafgrow.leafgrow_project.security.sec_dto.TokenResponseDto;
import de.leafgrow.leafgrow_project.service.interfaces.UserService;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private UserService userService;
    private TokenService tokenService;
    private Map<String, String> refreshStorage;
    private BCryptPasswordEncoder encoder;

    public AuthService(UserService userService, TokenService tokenService,
                       Map<String, String> refreshStorage, BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.refreshStorage =new HashMap<>();
        this.encoder = encoder;
    }

    public TokenResponseDto login(@NonNull User inboundUser) throws AuthException {
        String email = inboundUser.getEmail();
        User foundUser = (User) userService.loadUserByEmail(email);

        if(encoder.matches(inboundUser.getPassword(), foundUser.getPassword())){
            String accessToken = tokenService.generateAccessToken(foundUser);
            String refreshToken = tokenService.generateRefreshToken(foundUser);
            refreshStorage.put(email, refreshToken);
            return new TokenResponseDto(accessToken, refreshToken);
        } else{
            throw new AuthException("Password is incorrect");
        }
    }

    public TokenResponseDto getAccessToken(@NonNull String inboundRefreshToken){
        Claims refreshClaims = tokenService.getRefreshClaims(inboundRefreshToken);
        String email = refreshClaims.getSubject();
        String savedRefreshToken = refreshStorage.get(email);

        if(inboundRefreshToken.equals(savedRefreshToken)){
            User user = (User) userService.loadUserByEmail(email);
            String accessToken = tokenService.generateAccessToken(user);
            return new TokenResponseDto(accessToken, null);
        }
        return new TokenResponseDto(null, null);
    }

    public AuthInfo getAuthInfo(){
        return (AuthInfo) SecurityContextHolder.getContext().getAuthentication();
    }

}
