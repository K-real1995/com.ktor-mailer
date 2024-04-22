package ru.anapa.plugins

import io.ktor.http.*

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.anapa.plugins.MailRoutingComponent.SEND_PATH
import ru.anapa.plugins.MailRoutingComponent.mailService
import ru.anapa.service.MailService

private object MailRoutingComponent: KoinComponent {
    val mailService by inject<MailService>()
    const val SEND_PATH = "/send"
}

fun Application.mailRouting() {
    routing {
        post(SEND_PATH) {
            mailService.sendEmail(
                name = call.parameters["name"] ?: "",
                phone = call.parameters["phone"] ?: "",
                email = call.parameters["email"] ?: ""
            )
            call.respond(HttpStatusCode.OK)
        }
    }
}
