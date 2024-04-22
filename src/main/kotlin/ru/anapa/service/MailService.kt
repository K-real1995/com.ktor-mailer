package ru.anapa.service

import io.vertx.core.Vertx
import io.vertx.ext.mail.MailClient
import io.vertx.ext.mail.MailConfig
import io.vertx.ext.mail.MailMessage
import io.vertx.ext.mail.StartTLSOptions
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.anapa.config.AppConfig
import ru.anapa.service.MailService.VertxMailObjects.MESSAGE_FROM
import ru.anapa.service.MailService.VertxMailObjects.MESSAGE_SUBJECT
import ru.anapa.service.MailService.VertxMailObjects.file
import ru.anapa.service.MailService.VertxMailObjects.mailConfig
import java.io.File

class MailService {
    private object VertxMailObjects: KoinComponent {
        private val config by inject<AppConfig>()
        val vertx: Vertx = Vertx.vertx()

        val mailConfig: MailConfig =
            MailConfig().setHostname(config.hostname).setPort(config.port).setStarttls(StartTLSOptions.REQUIRED).setSsl(true)
                .setUsername(config.username).setPassword(config.password)
        val file = File("src/main/kotlin/ru/anapa/html/mail.html").readText()
        const val MESSAGE_FROM = "" //TODO укажите откуда направляется сообщение
        const val MESSAGE_SUBJECT = "Обратный звонок"
    }

    fun sendEmail(name: String, phone: String, email: String) {
        val mailClient = MailClient.create(VertxMailObjects.vertx, mailConfig)

        val messageWithData = file.replace("{name}", name)
            .replace("{phone}", phone)
            .replace("{e-mail}", email)

        val message = MailMessage()
        message.from = MESSAGE_FROM
        message.to = listOf("") //TODO укажите куда направить сообщение, можно несколько. Вне списка не работает
        message.subject = MESSAGE_SUBJECT
        message.html = messageWithData

        mailClient.sendMail(message) { result ->
            if (result.succeeded()) {
                println(result.result())
            } else {
                result.cause().printStackTrace()
            }
        }
    }
}