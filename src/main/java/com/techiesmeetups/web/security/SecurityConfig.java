package com.techiesmeetups.web.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
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


@Configuration
@EnableWebSecurity
public class SecurityConfig {
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

                    //GET: clubs list, meetups(events) list, individual club and meetup information -> PUBLIC
                    auth.requestMatchers(HttpMethod.GET,"/api/clubs", "/api/events","/api/events/{eventId}","/api/clubs/{clubId}")
                            .permitAll();

                    //POST: logging and registering -> PUBLIC
                    auth.requestMatchers(HttpMethod.POST,"/api/login","/api/register")
                            .permitAll();

                    //GET: admin information -> ADMIN only
                    auth.requestMatchers(HttpMethod.GET,"/api/admin/info")
                                    .hasRole("ADMIN");

                    //GET: user information -> USER only
                    auth.requestMatchers(HttpMethod.GET,"/api/user/info/{userID}")
                            .hasRole("USER");

                    //PUT: updating club information and meetup (event) -> USER only
                    auth.requestMatchers(HttpMethod.PUT,"api/clubs/{clubId}","api/events/{eventID}")
                            .hasRole("USER");

                    //POST: create new club; create new meetup (event) -> USER only
                    auth.requestMatchers(HttpMethod.POST,"api/clubs/new","api/{clubID}/events")
                                    .hasRole("USER");

                    // DELETE: delete club and meetup (events) -> USER and ADMIN
                    auth.requestMatchers(HttpMethod.DELETE,"api/events/{eventID}","api/clubs/{clubID}")
                            .authenticated();

                })
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return http.build();
    }


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
