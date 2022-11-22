package com.example.yourssuassignment

import com.example.yourssuassignment.application.jwt.JwtAccessDeniedHandler
import com.example.yourssuassignment.application.jwt.JwtAuthenticationEntryPoint
import com.example.yourssuassignment.application.jwt.JwtFilter
import com.example.yourssuassignment.application.jwt.TokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    prePostEnabled = true,
)
class SecurityConfig(
    private val tokenProvider: TokenProvider,
) {
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @get: Bean
    val jwtFilter: JwtFilter
        get() = JwtFilter(tokenProvider)

    @get: Bean
    val accessDeniedHandler: AccessDeniedHandler
        get() = JwtAccessDeniedHandler()

    @get: Bean
    val authenticationEntryPoint: AuthenticationEntryPoint
        get() = JwtAuthenticationEntryPoint()

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .cors().and().csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
            .accessDeniedHandler(accessDeniedHandler)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/auth/**", "/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter::class.java,
            )
            .build()
    }
}