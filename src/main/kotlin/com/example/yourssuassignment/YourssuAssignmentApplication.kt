package com.example.yourssuassignment

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class YourssuAssignmentApplication

fun main(args: Array<String>) {
    runApplication<YourssuAssignmentApplication>(*args)
}
