package com.first.carRepairShop.configurationClasses;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth    //✅Allow anyone to call /register, /login, /Role, and /permission without a token
                        .requestMatchers(HttpMethod.POST, "/v1/auth/register/user").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/auth/**").permitAll()
                        .requestMatchers("/role/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/permission/**").permitAll()
                        .requestMatchers("/customer/**").permitAll()
                        .requestMatchers("/workLog/**").authenticated()
                        .requestMatchers("/repairOrder/**").permitAll()
                        .requestMatchers("/mechanic/**").permitAll()
                        .requestMatchers("/car/**").permitAll()
                        .requestMatchers("/item/**").permitAll()
                        .requestMatchers("/service/**").permitAll()
                        .requestMatchers("/assignment/**").permitAll()
                        .requestMatchers("/user/**").permitAll()
                        .anyRequest().authenticated()   //🔐But every other request must include a valid JWT token

                ).csrf(csrf -> csrf.disable())
                //CSRF protection is for browser-based apps.
                //You’re building an API (not a browser app), so it’s safe and common to disable it.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //Every request must have a JWT — no cookies or sessions used.
                //💡This is the key part for permission-based auth using @PreAuthorize.
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    //🔹 You’re creating a Spring bean to customize how Spring Security reads authorities (permissions) from your JWT.
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        //🔍 Step 1: Customize How Spring Reads Permissions from JWT
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        //🛠 Step 2: Tell it where the permissions are inside the token
        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
        //🧼 Step 3: Disable default “ROLE_” prefix
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        //🧱 Step 4: Set the converter
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return converter;
        //🔹 You create a final JwtAuthenticationConverter, and give it your customized authority converter.
    }

    @Bean
    public JwtDecoder jwtDecoder(@Value("${jwt.secret}") String secretKey) {
        byte[] keyByte = secretKey.getBytes();
        SecretKey key = new SecretKeySpec(keyByte, "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(key).build();
    }


}
