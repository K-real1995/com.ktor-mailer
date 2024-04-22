package ru.anapa.config

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*

class AppConfig {
    private val config = HoconApplicationConfig(ConfigFactory.load())

    //Mail
    val hostname = config.property("vertx.mail.hostname").getString()
    val username = config.property("vertx.mail.username").getString()
    val password = config.property("vertx.mail.password").getString()
    val port = config.property("vertx.mail.port").getString().toInt()
}