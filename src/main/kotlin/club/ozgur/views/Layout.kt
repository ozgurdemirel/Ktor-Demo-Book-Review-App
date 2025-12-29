package club.ozgur.views

import kotlinx.html.BODY
import kotlinx.html.HTML
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.script
import kotlinx.html.style
import kotlinx.html.title
import kotlinx.html.unsafe

fun HTML.appLayout(pageTitle: String, bodyContent: BODY.() -> Unit) {
    head {
        title { +pageTitle }
        script { src = "https://cdn.tailwindcss.com" }
        style {
            unsafe {
                raw(".filter-item { @apply px-4 py-2 rounded-md bg-white shadow-sm border; }")
            }
        }
    }
    body(classes ="bg-gray-100 text-gray-900 font-sans") {
        div(classes="container mx-auto mt-10 mb-10 max-w-3xl") {
            h1(classes = "text-3xl font-bold mb-6") {
                a(href = "/books") { +"Book Reviews" }
            }
        }
        bodyContent()
    }
}