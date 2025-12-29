package club.ozgur.views

import club.ozgur.models.Book
import kotlinx.html.*

fun BODY.bookIndexView(books: List<Book>, currentTitle: String?, currentFilter: String?) {
    // 1. Arama Formu
    form(action = "/books", method = FormMethod.get, classes = "flex gap-2 mb-6") {
        input(type = InputType.text, name = "title", classes = "border rounded px-4 py-2 w-full") {
            placeholder = "Kitap adına göre ara..."
            value = currentTitle ?: ""
        }
        input(type = InputType.hidden, name = "filter") { value = currentFilter ?: "" }

        button(type = ButtonType.submit, classes = "bg-blue-500 text-white px-6 py-2 rounded") { +"Ara" }
        a(href = "/books", classes = "bg-gray-300 px-4 py-2 rounded flex items-center") { +"Temizle" }
    }

    // 2. Filtre Tabları
    val filters = mapOf(
        "" to "En Yeniler",
        "popular_last_month" to "Geçen Ayın Popülerleri",
        "highest_rated_last_six_months" to "Son 6 Ayın En İyileri"
    )

    div(classes = "flex gap-4 mb-8 text-sm") {
        filters.forEach { (key, label) ->
            val isActive = (currentFilter ?: "") == key
            a(href = "/books?filter=$key&title=${currentTitle ?: ""}",
              classes = if(isActive) "font-bold text-blue-600 underline" else "text-gray-500") {
                +label
            }
        }
    }

    // 3. Kitap Listesi
    if (books.isEmpty()) {
        p { +"Henüz kitap bulunamadı." }
    } else {
        ul(classes = "space-y-4") {
            books.forEach { book ->
                li(classes = "bg-white p-6 rounded-lg shadow-sm border border-gray-200") {
                    div(classes = "flex justify-between items-start") {
                        div {
                            h2(classes = "text-xl font-semibold") { +book.title }
                            p(classes = "text-gray-600 text-sm") { +"Yazar: ${book.author}" }
                        }
                        div(classes = "text-yellow-500 font-bold") {
                            +"★ ${book.reviewAvgRating?.let { String.format("%.1f", it) } ?: "-"}"
                        }
                    }
                }
            }
        }
    }
}
