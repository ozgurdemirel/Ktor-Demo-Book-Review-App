package club.ozgur.models

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object ReviewsTable : Table("reviews") {
    val id = long("id").autoIncrement()
    val bookId = long("book_id") references BooksTable.id
    val review = text("review")
    val rating = integer("rating")

    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(id)
}
