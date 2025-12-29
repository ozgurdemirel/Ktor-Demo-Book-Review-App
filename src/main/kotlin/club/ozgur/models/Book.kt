package club.ozgur.models

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.avg
import org.jetbrains.exposed.sql.count
import java.time.LocalDateTime

data class Book(
    val id: Long,
    val title: String,
    val author: String,
    val createdAt: LocalDateTime,

    val reviewsCount: Long = 0,
    val reviewAvgRating: Double? = null
)

fun ResultRow.toBook(): Book {
    return Book(
        id = this[BooksTable.id],
        title = this[BooksTable.title],
        author = this[BooksTable.author],
        createdAt = this[BooksTable.createdAt],

        reviewsCount = try {
            this[ReviewsTable.id.count()]
        } catch (_: Exception) {
            0L
        },
        reviewAvgRating = try {
            this[ReviewsTable.rating.avg()]?.toDouble()
        } catch (_: Exception) {
            null
        }
    )
}