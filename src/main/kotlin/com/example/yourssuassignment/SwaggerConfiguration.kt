package com.example.yourssuassignment

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.media.Schema
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.SpringDocUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.awt.Color
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Configuration
class SwaggerConfiguration {
    private val securityScheme = io.swagger.v3.oas.models.security.SecurityScheme()
        .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")
        .`in`(io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER)
        .name("Authorization")
    private val securityRequirementName = "bearerAuth"

    init {
        SpringDocUtils
            .getConfig()
            .replaceWithSchema(
                Color::class.java,
                Schema<String>()
                    .type("string")
                    .format("color")
                    .example("#FFFFFFFF"),
            )
            .replaceWithSchema(
                LocalDateTime::class.java,
                Schema<LocalDateTime>()
                    .type("string")
                    .format("date-time")
                    .example(
                        LocalDateTime
                            .now()
                            .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                    ),
            )
            .replaceWithSchema(
                LocalDate::class.java,
                Schema<LocalDate>()
                    .type("string")
                    .format("date")
                    .example(
                        LocalDate
                            .now()
                            .format(DateTimeFormatter.ISO_LOCAL_DATE),
                    ),
            )
            .replaceWithSchema(
                LocalTime::class.java,
                Schema<LocalTime>()
                    .type("string")
                    .format("time")
                    .example(
                        LocalTime
                            .now()
                            .format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                    ),
            )
    }

    @Bean
    fun getOpenAPI(): OpenAPI {
        return OpenAPI()
            .servers(listOf(Server().apply { url = "/" }))
            .security(
                listOf(
                    SecurityRequirement()
                        .addList(securityRequirementName),
                ),
            )
            .components(
                Components()
                    .addSecuritySchemes(
                        securityRequirementName,
                        securityScheme,
                    ),
            )
            .info(
                Info()
                    .title("Spring Blog")
                    .description("스프링 블로그 API 구현")
                    .version("v0.0.0"),
            )
            .externalDocs(
                ExternalDocumentation()
                    .description("BLOG API"),
            )
    }
}