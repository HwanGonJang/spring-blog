package com.example.yourssuassignment

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import java.util.function.Predicate


@Configuration
class SwaggerConfig {
    private var version: String? = null
    private var title: String? = null

    @Bean
    fun apiV1(): Docket? {
        version = "V1"
        title = "Blog API $version"
        return Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .groupName(version)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .paths(Predicate.not(PathSelectors.regex("/error.*")))
            .build()
            .apiInfo(apiInfo(title!!, version!!))
    }

    private fun apiInfo(title: String, version: String): ApiInfo? {
        return ApiInfo(
            title,
            "Swagger Blog API Docs",
            version,
            "https://carpal-cactus-f64.notion.site/c36e2f5c42834fd6bb836dcdd66718e1",
            Contact(
                "Contact Me",
                "https://carpal-cactus-f64.notion.site/c36e2f5c42834fd6bb836dcdd66718e1",
                "myggona@gmail.com"
            ),
            "Licenses",
            "www.example.com",
            ArrayList()
        )
    }
}