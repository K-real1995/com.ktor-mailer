package ru.anapa

import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import ru.anapa.di.configureKoin
import ru.anapa.plugins.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    mailRouting()
    configureKoin()
    install(CORS) {
        allowNonSimpleContentTypes = true
        anyHost()
    }
}
