package com.example.backend221.config;

import com.example.backend221.component.JwtAuthenticationEntryPoint;
import com.example.backend221.component.JwtRequestFilter;
import com.example.backend221.services.Argon2PasswordEncoder;
import com.example.backend221.services.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
    public  class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;


    @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception{
            auth.userDetailsService(jwtUserDetailsService).passwordEncoder(argon2PasswordEncoder());
        }

    private PasswordEncoder argon2PasswordEncoder() {
            return new Argon2PasswordEncoder(16,29,1,16,2);
    }

    @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception{
//            httpSecurity
//                    .cors().and()
//                    .csrf().disable()
//                    .anonymous().and()

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type", "IsRefreshToken"));
        httpSecurity.csrf().disable().cors().configurationSource(request -> corsConfiguration).and()
                    .authorizeRequests()
                    .antMatchers("/api/events","/api/events/*").hasAnyAuthority("admin","student")
                    .antMatchers("/api/jwt/login").permitAll()
                    .antMatchers("/api/eventCategories","/api/eventCategories/*").permitAll()
                    .antMatchers("/api/users","/api/users/*","/api/match").hasAuthority("admin")
//                    .antMatchers("/api/users").authenticated()
//                    .anyRequest("/users").authenticated()
                    .anyRequest().authenticated()
                    .and()


                    .exceptionHandling()
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .and().sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        }
//        @Bean
//        public Argon2PasswordEncoder argon2PasswordEncoder(){
//            return new Argon2PasswordEncoder(int16,29,1,16,2);
//        }

    }

