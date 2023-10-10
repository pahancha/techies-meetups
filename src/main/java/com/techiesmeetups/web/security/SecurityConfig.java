package com.techiesmeetups.web.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.techiesmeetups.web.service.UserService;
import com.techiesmeetups.web.utils.RSAKeyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
//    private UserService userDetailsService;

    private CustomUserDetailsService userDetailsService;

    private final RSAKeyProperties keys;
    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService, RSAKeyProperties keys) {
        this.userDetailsService  = userDetailsService;
        this.keys = keys;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(CustomUserDetailsService userDetailsService) {
//        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
//        daoProvider.setUserDetailsService(userDetailsService);
//        return new ProviderManager(daoProvider);
//    }

    @Bean
    public AuthenticationManager authenticationManager(CustomUserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(userDetailsService);
        daoProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoProvider);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests( auth -> {
                    auth.requestMatchers(HttpMethod.GET,"/api/clubs", "/api/events","/api/events/{eventId}","/api/clubs/{clubId}")
                            .permitAll();
                    auth.requestMatchers(HttpMethod.GET,"/api/admin/info")
                                    .hasRole("ADMIN");
//                    auth.requestMatchers(HttpMethod.GET,"/api/user/info")
//                            .hasRole("USER");
                    auth.requestMatchers(HttpMethod.GET,"/api/user/info")
                            .authenticated();

                    auth.requestMatchers(HttpMethod.PUT,"/clubs/{clubId}")
                            .authenticated();
                    auth.requestMatchers(HttpMethod.POST,"/api/login","/api/register","/clubs/{clubId}")
                            .permitAll();
                })
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
//                .formLogin((form -> form.disable()))
//                .httpBasic(Customizer.withDefaults())

//                .logout(logout -> logout.disable());

                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return http.build();
    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        return new CustomCors
//    }


    // ongoing validation via Oauth2

    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder(keys.getPublicKey()).privateKey(keys.getPrivateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtConverter;
    }



}
