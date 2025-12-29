package club.ozgur.models

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object BooksTable : Table("books") {
    val id = long("id").autoIncrement()
    val title = varchar("title", 255)
    val author = varchar("author", 255)
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)


    override val primaryKey = PrimaryKey(id)
}
