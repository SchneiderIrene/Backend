package de.leafgrow.leafgrow_project.security.sec_dto;

import java.util.Objects;

public class TokenResponseDto {
    private String accessToken;
    private String refreshToken;

    public TokenResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TokenResponseDto that = (TokenResponseDto) o;

        if (!Objects.equals(accessToken, that.accessToken)) return false;
        return Objects.equals(refreshToken, that.refreshToken);
    }

    @Override
    public int hashCode() {
        int result = accessToken != null ? accessToken.hashCode() : 0;
        result = 31 * result + (refreshToken != null ? refreshToken.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("TokenResponseDto: access token - %s, refresh token - %s",
                accessToken, refreshToken);
    }
}
