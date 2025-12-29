package club.ozgur.repositories

import club.ozgur.models.Book
import club.ozgur.models.BooksTable
import club.ozgur.models.ReviewsTable
import club.ozgur.models.toBook
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.avg
import org.jetbrains.exposed.sql.count
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class BookRepository {

    fun findAllBooks(title: String?, filter: String?): List<Book> = transaction {
        var query = BooksTable.selectAll()

        if (!title.isNullOrBlank()) {
            query = query.andWhere { BooksTable.title like "%${title.lowercase()}%" }
        }

        query = when (filter) {
            "popular_last_month" -> popularQuery(query, LocalDateTime.now().minusMonths(1), LocalDateTime.now())
            "highest_rated_last_month" -> highestRatedQuery(query, LocalDateTime.now().minusMonths(6), LocalDateTime.now())
            else -> query.orderBy(BooksTable.createdAt, SortOrder.DESC)
        }

        query.map { it.toBook() }
    }

    private fun popularQuery(query: Query, from: LocalDateTime, to: LocalDateTime): Query {
        val countAlias = ReviewsTable.id.count().alias("reviews_count")
        return query.adjustColumnSet { join(ReviewsTable, JoinType.LEFT, BooksTable.id, ReviewsTable.bookId) }
            .adjustSelect { select(BooksTable.columns + countAlias) }
            .andWhere { ReviewsTable.createdAt.between(from, to) }
            .groupBy(BooksTable.id)
            .orderBy(countAlias, SortOrder.DESC)
    }

    private fun highestRatedQuery(query: Query, from: LocalDateTime, to: LocalDateTime): Query {
        val avgAlias = ReviewsTable.rating.avg().alias("reviews_avg_rating")
        return query.adjustColumnSet { join(ReviewsTable, JoinType.LEFT, BooksTable.id, ReviewsTable.bookId) }
            .adjustSelect { select(BooksTable.columns + avgAlias) }
            .andWhere { ReviewsTable.createdAt.between(from, to) }
            .groupBy(BooksTable.id)
            .orderBy(avgAlias, SortOrder.DESC)
    }
}