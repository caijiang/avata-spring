package demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * @author CJ
 */
@SpringBootApplication
class AppApplication {
}

fun main(args: Array<String>) {
    runApplication<AppApplication>(*args)
}