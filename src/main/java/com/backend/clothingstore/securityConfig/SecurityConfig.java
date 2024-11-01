package com.backend.clothingstore.securityConfig;


import com.backend.clothingstore.model.User;
import com.backend.clothingstore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .cors(cors->cors.disable())    // cereri din frontend disable
                .cors(Customizer.withDefaults()) // Activează CORS

                .csrf(csrf -> csrf.disable()) // Deactivate CSRF
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/user/register", "/user/login").permitAll() // Allow these endpoints without authentication
                                .requestMatchers("/user/admin/**").hasRole("ADMIN")

                        .requestMatchers("/user/delete/**").hasRole("ADMIN")
                        .requestMatchers("/product/create").hasRole("ADMIN")
                        .requestMatchers("/product/update/**").hasRole("ADMIN")
                        .requestMatchers("/product/delete/**").hasRole("ADMIN")
                        .requestMatchers("/order/get-all").hasRole("ADMIN")
                        .requestMatchers("/product/get-all").permitAll()
                        .requestMatchers("/product/view/**").permitAll()

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Autowired
    UserRepository userRepository;


    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                        AuthorityUtils.createAuthorityList("ROLE_" + (user.getRole() != null ? user.getRole() : "USER")));
            }
        };
    }


    // cereri din partea de frontend
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // URL-ul frontend-ului
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setAllowCredentials(true); // Permite credențialele (cookies, headers, etc.)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
