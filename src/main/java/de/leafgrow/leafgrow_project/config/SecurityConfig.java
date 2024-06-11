package de.leafgrow.leafgrow_project.config;

import de.leafgrow.leafgrow_project.security.sec_filter.TokenFilter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private TokenFilter filter;

    public SecurityConfig(TokenFilter filter) {
        this.filter = filter;
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(x -> x
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(x -> x
                        .requestMatchers(HttpMethod.POST, "/api/auth/login", "/api/auth/access").permitAll()//with token
                        .requestMatchers(HttpMethod.POST, "/api/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/register/confirm").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/register/resent").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/auth/profile").authenticated() // Эндпоинт /api/auth/profile доступен для прошедших проверку подлинности пользователей
                        .requestMatchers(HttpMethod.PATCH, "/api/auth/profile/change-password").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/auth/profile/delete-user").authenticated()
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/instructions/{day}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/pots/{potId}/instruction").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/pots/{potId}/refresh").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/pots/{potId}/activate").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/pots/create").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/pots/{potId}/skip-day").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/my").authenticated()

                        .anyRequest().authenticated())
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info().title("JWT demo app")
                        .description("Demo application for JSON web tokens")
                        .version("1.0.0")
                        .contact(new Contact().name("LeafGrow")
                                .email("leafgrow.project@gmail.com")
                                .url("https://github.com/LeafGrow"))
                        .license(new License().name("@LeafGrow")
                                .url("https://github.com/LeafGrow")));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}
