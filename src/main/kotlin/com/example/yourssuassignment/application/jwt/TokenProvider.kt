package com.example.yourssuassignment.application.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.example.yourssuassignment.application.errorhandling.exception.TokenNotValidateException
import com.example.yourssuassignment.dto.AuthenticationDto
import com.example.yourssuassignment.dto.TokenDto
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import java.util.stream.Collectors

@Component
class TokenProvider(
    private val tokenAlgorithm: Algorithm,
    private val tokenVerifier: JWTVerifier,
) {
    private val secretKey = "yourssu-secret-key"

    fun createToken(
        authentication: Authentication,
    ): TokenDto {
        val authority = authentication.authorities
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","))

        val accessToken = JWT.create()
            .withClaim("ema", authentication.name)
            .withClaim("rol", authority)
            .withExpiresAt(
                Date.from(
                    LocalDateTime
                        .now()
                        .plusMinutes(30)
                        .atZone(ZoneId.of("Asia/Seoul"))
                        .toInstant(),
                ),
            )
            .sign(tokenAlgorithm)

        val refreshToken = JWT.create()
            .withExpiresAt(
                Date.from(
                    LocalDateTime
                        .now()
                        .plusWeeks(1)
                        .atZone(ZoneId.of("Asia/Seoul"))
                        .toInstant(),
                ),
            )
            .sign(tokenAlgorithm)

        return TokenDto(
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }

    fun validateToken(
        accessToken: String?,
    ): UsernamePasswordAuthenticationToken? {
        try {
            val tokenVerifier = tokenVerifier.verify(accessToken)
            val authenticationDto = AuthenticationDto(
                email = tokenVerifier.getClaim("ema").asString(),
                role = tokenVerifier.getClaim("rol").asString(),
            )

            return UsernamePasswordAuthenticationToken(
                authenticationDto, "", listOf(SimpleGrantedAuthority(tokenVerifier.getClaim("rol").asString()))
            )
        } catch (e: TokenExpiredException) {
            val decodedToken = JWT.decode(accessToken)
            val authenticationDto = AuthenticationDto(
                email = decodedToken.getClaim("ema").asString(),
                role = decodedToken.getClaim("rol").asString(),
            )

            return UsernamePasswordAuthenticationToken(
                authenticationDto, "", listOf(SimpleGrantedAuthority(decodedToken.getClaim("rol").asString()))
            )
        } catch (e: JWTVerificationException) {
            null
        }

        return null
    }

    fun getAuthentication(accessToken: String): Authentication {
        val tokenVerifier = tokenVerifier.verify(accessToken)

        if (tokenVerifier.getClaim("rol") == null)
            throw TokenNotValidateException()

        val principal =
            User(
                tokenVerifier.getClaim("ema").asString(),
                "",
                listOf(SimpleGrantedAuthority(tokenVerifier.getClaim("rol").asString()))
            )

        return UsernamePasswordAuthenticationToken(
            principal,
            "",
            listOf(SimpleGrantedAuthority(tokenVerifier.getClaim("rol").asString()))
        )
    }

    fun getToken(): String {
        val httpServletRequest = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request

        val bearerToken = httpServletRequest.getHeader("Authorization")

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7)
        else
            throw TokenNotValidateException()
    }
}
