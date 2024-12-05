package com.example.demo.Regestration.Security;

import com.example.demo.Regestration.Model.MyAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration//важные насройки конфигурации
//@EnableWebSecurity//активирует веб секьюрити
//public class SecurityConfig {
//
//    private final MyAppUserService appUserService;
//    @Autowired
//    public SecurityConfig(MyAppUserService appUserService) {
//        this.appUserService = appUserService;
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(){
//        return this.appUserService;
//    }
//    @Bean
//    public AuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(appUserService);
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
//        return httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .formLogin(httpForm -> {
//                    httpForm.loginPage("/req/login").permitAll();
//                    httpForm.defaultSuccessUrl("/index");
//                        })
//
//                .authorizeHttpRequests(registry ->{
//                    registry.requestMatchers("/req/signup", "/css/**", "/js/**").permitAll();
//                    registry.anyRequest().authenticated();
//                        })
//
//                .build();
//    }
//}
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//    @Autowired
//    private final MyAppUserService appUserService;
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return this.appUserService;
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(this.appUserService);
//        provider.setPasswordEncoder(this.passwordEncoder());
//        return provider;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return (SecurityFilterChain)httpSecurity.csrf(AbstractHttpConfigurer::disable).formLogin((httpForm) -> {
//            httpForm.loginPage("/req/login").permitAll();
//            httpForm.defaultSuccessUrl("/index");
//        }).authorizeHttpRequests((registry) -> {
//            ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)registry.requestMatchers(new String[]{"/req/signup", "/css/**", "/js/**"})).permitAll();
//            ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)registry.anyRequest()).authenticated();
//        }).build();
//    }
//
//    public SecurityConfig(final MyAppUserService appUserService) {
//        this.appUserService = appUserService;
//    }
//}


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private final MyAppUserService appUserService;

    @Bean
    public UserDetailsService userDetailsService() {
        return this.appUserService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.appUserService);
        provider.setPasswordEncoder(this.passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return (SecurityFilterChain)httpSecurity.csrf(AbstractHttpConfigurer::disable).formLogin((httpForm) -> {
            httpForm.loginPage("/req/login").permitAll();
            httpForm.defaultSuccessUrl("/index");
        }).authorizeHttpRequests((registry) -> {
            ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)registry.requestMatchers(new String[]{"/req/signup", "/css/**", "/js/**", "/image/**"})).permitAll();
            ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)registry.anyRequest()).authenticated();
        }).build();
    }

    public SecurityConfig(final MyAppUserService appUserService) {
        this.appUserService = appUserService;
    }
}
