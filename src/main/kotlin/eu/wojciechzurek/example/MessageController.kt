package eu.wojciechzurek.example

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/message")
class MessageController(private val messageService: MessageService) {

    @GetMapping
    fun message() = messageService.getMessage()
}