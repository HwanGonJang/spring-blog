package com.example.yourssuassignment

import com.auth0.jwt.JWTVerifier
import com.example.yourssuassignment.application.annotation.Email
import com.example.yourssuassignment.application.errorhandling.exception.TokenNotValidateException
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.util.StringUtils
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ArgumentResolverConfiguration(
    private val tokenVerifier: JWTVerifier
): WebMvcConfigurer {
    fun resolveEmail(): String {
        val httpServletRequest = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request

        val bearerToken = httpServletRequest.getHeader("Authorization")
        var token = ""

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            token = bearerToken.substring(7)
        else
            throw TokenNotValidateException()

        val decodedToken = tokenVerifier.verify(token)

        return decodedToken.getClaim("ema").asString()
    }

    val emailArgumentResolver: HandlerMethodArgumentResolver =
        object: HandlerMethodArgumentResolver {
            override fun supportsParameter(parameter: MethodParameter): Boolean =
                parameter.getParameterAnnotation(Email::class.java) != null &&
                    parameter.parameterType == String::class.java

            override fun resolveArgument(
                parameter: MethodParameter,
                mavContainer: ModelAndViewContainer?,
                webRequest: NativeWebRequest,
                binderFactory: WebDataBinderFactory?,
            ): String = resolveEmail()
        }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(emailArgumentResolver)
    }
}
