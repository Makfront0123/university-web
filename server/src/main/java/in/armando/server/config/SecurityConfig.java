package in.armando.server.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import in.armando.server.filter.JwtRequestFilter;
import in.armando.server.service.impl.UserDetailService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final UserDetailService userService;
        private final JwtRequestFilter jwtRequestFilter;

        @Bean
        @Order(1)
        public SecurityFilterChain publicFilterChain(HttpSecurity http) throws Exception {
                http
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                                .csrf(AbstractHttpConfigurer::disable)
                                .securityMatcher(
                                                "/login", "/encode", "/register", "/verify",
                                                "/resend-otp", "/forgot", "/verifyForgot",
                                                "/reset-password", "/test-auth", "/debug-user", "/error")
                                .authorizeHttpRequests(auth -> auth
                                                .anyRequest().permitAll())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                return http.build();
        }

        @Bean
        public SecurityFilterChain privateFilterChain(HttpSecurity http) throws Exception {
                http
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                                .csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                                // STUDENTS
                                                .requestMatchers(HttpMethod.GET, "/students")
                                                .hasAnyRole("ADMIN", "TEACHERS")
                                                .requestMatchers(HttpMethod.GET, "/students/{id}",
                                                                "/students/code/{code}", "/students/{id}/user")
                                                .hasAnyRole("ADMIN", "TEACHERS", "STUDENTS")
                                                .requestMatchers(HttpMethod.POST, "/students").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.PUT, "/students/{id}")
                                                .hasAnyRole("ADMIN", "STUDENTS")
                                                .requestMatchers(HttpMethod.DELETE, "/students/{id}").hasRole("ADMIN")

                                                .requestMatchers("/teachers/**").hasAnyRole("ADMIN", "TEACHERS")

                                                // TRANSCRIPT
                                                .requestMatchers("/transcript/**").hasAnyRole("ADMIN", "TEACHERS")
                                                // ENROLLMENT
                                                .requestMatchers("/enrollment/**").hasAnyRole("ADMIN", "TEACHERS")

                                                // SHIFT
                                                .requestMatchers(HttpMethod.GET, "/shifts", "/shifts/{id}")
                                                .hasAnyRole("ADMIN", "TEACHERS", "STUDENTS")

                                                .requestMatchers(HttpMethod.POST, "/shifts").hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.PATCH, "/shifts/{id}").hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.DELETE, "/shifts/{id}").hasRole("ADMIN")

                                                // SEMESTER
                                                .requestMatchers(HttpMethod.GET, "/semester", "/semester/{id}")
                                                .hasAnyRole("ADMIN", "TEACHERS", "STUDENTS")

                                                .requestMatchers(HttpMethod.POST, "/semester")
                                                .hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.PATCH, "/semester/{id}")
                                                .hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.DELETE, "/semester/{id}")
                                                .hasRole("ADMIN")

                                                // COURSE
                                                .requestMatchers(HttpMethod.GET, "/courses", "/courses/{id}",
                                                                "/courses/code/{code}")
                                                .hasAnyRole("ADMIN", "TEACHERS", "STUDENTS")

                                                .requestMatchers(HttpMethod.POST, "/courses")
                                                .hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.PATCH, "/courses/{id}")
                                                .hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.DELETE, "/courses/{id}")
                                                .hasRole("ADMIN")

                                                // Subject
                                                .requestMatchers(HttpMethod.GET, "/subjects")
                                                .hasAnyRole("ADMIN", "TEACHERS", "STUDENTS")

                                                .requestMatchers(HttpMethod.POST, "/subjects")
                                                .hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.PATCH, "/subjects/{id}")
                                                .hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.DELETE, "/subjects/{id}")
                                                .hasRole("ADMIN")

                                                // ENROLLMENTS
                                                .requestMatchers(HttpMethod.POST, "/enrollments").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.PUT, "/enrollments/{id}").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/enrollments/{id}")
                                                .hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.GET, "/enrollments/student/{studentId}")
                                                .hasAnyRole("ADMIN", "TEACHERS", "STUDENTS")

                                                .requestMatchers(HttpMethod.GET, "/enrollments/course/{courseId}")
                                                .hasAnyRole("ADMIN", "TEACHERS")

                                                .requestMatchers(HttpMethod.GET, "/enrollments/**")
                                                .hasAnyRole("ADMIN", "TEACHERS")

                                                // TRANSCRIPT
                                                .requestMatchers(HttpMethod.POST, "/transcripts").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.GET, "/transcripts/student/{studentId}")
                                                .hasAnyRole("ADMIN", "TEACHERS", "STUDENTS")
                                                .requestMatchers(HttpMethod.PATCH, "/transcripts/{id}")
                                                .hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/transcripts/{id}")
                                                .hasRole("ADMIN")

                                                // PENSUM
                                                .requestMatchers(HttpMethod.POST, "/pensum").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.GET, "/pensum")
                                                .hasAnyRole("ADMIN", "TEACHERS", "STUDENTS")
                                                .requestMatchers(HttpMethod.PATCH, "/pensum/{id}")
                                                .hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/pensum/{id}")
                                                .hasRole("ADMIN")

                                                // PENSUM SUBEJCT
                                                .requestMatchers(HttpMethod.POST, "/pensum-subject")
                                                .hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.GET, "/pensum-subject")
                                                .hasAnyRole("ADMIN", "TEACHERS", "STUDENTS")
                                                .requestMatchers(HttpMethod.PATCH, "/pensum-subject/{id}")
                                                .hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/pensum-subject/{id}")
                                                .hasRole("ADMIN")

                                                // WAITLIST
                                                .requestMatchers(HttpMethod.POST, "/waitlist").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.GET, "/waitlist")
                                                .hasAnyRole("ADMIN", "TEACHERS", "STUDENTS")
                                                .requestMatchers(HttpMethod.PATCH, "/waitlist/{id}")
                                                .hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/waitlist/{id}")
                                                .hasRole("ADMIN")

                                                .anyRequest().authenticated())

                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowCredentials(true);
                configuration.setAllowedOrigins(List.of(
                                "http://localhost:3000",
                                "http://127.0.0.1:5500",
                                "http://192.168.1.89:8080",
                                "http://10.0.2.2:8080",
                                "https://web-eujndmzxt-armandos-projects-bf6157fe.vercel.app"));
                configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager() {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
                provider.setUserDetailsService(userService);
                provider.setPasswordEncoder(passwordEncoder());
                return new org.springframework.security.authentication.ProviderManager(provider);
        }
}