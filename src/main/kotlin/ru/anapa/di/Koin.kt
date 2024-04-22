package ru.anapa.di

import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import ru.anapa.config.AppConfig
import ru.anapa.service.MailService

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(serviceModules)
    }
}


val serviceModules = module {
    single { AppConfig() }
    single { MailService() }
}