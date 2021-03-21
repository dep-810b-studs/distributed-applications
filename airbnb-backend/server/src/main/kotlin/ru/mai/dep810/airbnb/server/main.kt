package ru.mai.dep810.airbnb.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching



@SpringBootApplication
@EnableCaching
class AirBnBApplication{
}


fun main(args: Array<String>) {
    runApplication<AirBnBApplication>(*args)
}

