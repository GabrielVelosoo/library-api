package com.github.gabrielvelosoo.libraryapi.configurations;

import com.github.gabrielvelosoo.libraryapi.security.JwtCustomAuthenticationFilter;
import com.github.gabrielvelosoo.libraryapi.security.SocialLoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class ResourceServiceConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            SocialLoginSuccessHandler successHandler,
            JwtCustomAuthenticationFilter jwtCustomAuthenticationFilter
    ) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(configurer -> configurer.loginPage("/login").permitAll())
                .authorizeHttpRequests( authorize -> {
                    authorize.requestMatchers("/login/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/users/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/clients/**").permitAll();
                    authorize.anyRequest().authenticated();
                } )
                .oauth2Login(oauth2 -> oauth2.loginPage("/login").successHandler(successHandler))
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(Customizer.withDefaults()))
                .addFilterAfter(jwtCustomAuthenticationFilter, BearerTokenAuthenticationFilter.class)
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                  "/v2/api-docs/**",
                  "/v3/api-docs/**",
                  "/swagger-resources/**",
                  "/swagger-ui.html",
                  "/swagger-ui/**",
                  "/webjars/**",
                  "/actuator/**"
          );
    }

    @Bean
    public GrantedAuthorityDefaults configureRolePrefix() {
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public JwtAuthenticationConverter configureJwtScopePrefix() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return converter;
    }
}
