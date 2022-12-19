package com.example.backend221.config;

import com.example.backend221.component.JwtAuthenticationEntryPoint;
import com.example.backend221.component.JwtRequestFilter;
import com.example.backend221.services.Argon2PasswordEncoder;
import com.example.backend221.services.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST","PATCH","PUT","DELETE"));
//        configuration.setAllowedHeaders(Arrays.asList("*"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
    @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception{
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type", "IsRefreshToken"));
        httpSecurity.csrf().disable().cors().configurationSource(request -> corsConfiguration.applyPermitDefaultValues()).and()
                    .authorizeRequests().antMatchers("/api/jwt/login").permitAll()
                .and().authorizeRequests().antMatchers("/api/jwt/refresh").permitAll()
                .and().authorizeRequests().antMatchers("/api/eventCategories").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.POST,"/api/users").permitAll()
                //adding every role
//                .and().authorizeRequests().antMatchers("/api/events/adding").hasAnyAuthority("admin","student",null,"")
                .and().authorizeRequests().antMatchers("/api/events/adding").permitAll()
                .and().authorizeRequests().antMatchers("/api/jwt/loginms").permitAll()
                //event get all 3 roles
                .and().authorizeRequests().antMatchers("/api/events/all").hasAnyAuthority("admin","student","lecturer")
                //admin can get users
                .and().authorizeRequests().antMatchers("/api/users","/api/match","/api/events").hasAnyAuthority("admin")
//                .and().authorizeRequests().antMatchers(HttpMethod.PUT,"/api/events").hasAnyAuthority("admin")
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

