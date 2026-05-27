package com.itoshi_m_dev.schedulingapi.config;

import com.itoshi_m_dev.schedulingapi.security.CustomAuthenticationProvider;
import com.itoshi_m_dev.schedulingapi.security.CustomOAuth2Service;
import com.itoshi_m_dev.schedulingapi.security.JwtConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   CustomAuthenticationProvider customAuthenticationProvider,
                                                   CustomOAuth2Service customOAuth2Service)
            throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {

                    auth.requestMatchers(
                            "/swagger-ui/**",
                            "/v3/api-docs/**"
                    ).permitAll();

                    auth.requestMatchers(HttpMethod.POST, "/api/users").permitAll();

                    auth.requestMatchers(HttpMethod.GET,
                            "/api/establishments/**",
                            "/api/services/**",
                            "/api/professionals/**",
                            "/api/availability/**",
                            "/api/users/**"
                    ).permitAll();

                    auth.anyRequest().authenticated();

                })
                .oauth2Login(oauth -> oauth
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(customOAuth2Service)
                        )
                )
                .formLogin(form -> form.loginPage("/login").permitAll())
                .authenticationProvider(customAuthenticationProvider)
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(Customizer.withDefaults()))
                .build();


    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
}



