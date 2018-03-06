package eu.wojciechzurek.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class MainApplication

fun main(args: Array<String>) {
    runApplication<MainApplication>(*args)
}
