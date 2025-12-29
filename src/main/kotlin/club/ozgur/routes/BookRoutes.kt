package club.ozgur.routes

import club.ozgur.repositories.BookRepository
import club.ozgur.views.appLayout
import club.ozgur.views.bookIndexView
import io.ktor.resources.*
import io.ktor.server.html.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*

@Resource("/books")
data class Books(
    val title: String? = null,
    val filter: String? = null
)

fun Route.bookRoutes(repository: BookRepository) {
    get<Books> { params ->
        val books = repository.findAllBooks(params.title, params.filter)

        call.respondHtml {
            appLayout(pageTitle = "Kitaplar") {
                bookIndexView(books, params.title, params.filter)
            }
        }
    }
}