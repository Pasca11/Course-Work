package ru.amir.library.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("In config");
        http
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers("/admin/*", "/admin").hasRole("ADMIN")
                                .requestMatchers("/librarian").hasRole("MODERATOR")
                                .requestMatchers("/auth/login", "/auth/registration", "/error").permitAll()
                                .requestMatchers("/test").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(login ->
                        login.loginPage("/auth/login")
                                .loginProcessingUrl("/process_login")
                                .defaultSuccessUrl("/", true)
                                .failureUrl("/auth/login?error")
                )
                .logout(logout ->
                        logout.logoutUrl("/logout")
                                .logoutSuccessUrl("/auth/login")
                )
                .exceptionHandling(exp ->
                        exp.accessDeniedPage("/accessDenied")
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
