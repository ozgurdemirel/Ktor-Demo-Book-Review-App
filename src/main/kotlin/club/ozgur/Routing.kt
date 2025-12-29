package club.ozgur

import club.ozgur.repositories.BookRepository
import club.ozgur.routes.bookRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val bookRepository = BookRepository()

    routing {
        bookRoutes(bookRepository)
    }
}