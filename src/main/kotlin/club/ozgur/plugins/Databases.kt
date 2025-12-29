package club.ozgur.plugins

import io.ktor.server.application.*
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import org.jetbrains.exposed.sql.Database
import java.sql.DriverManager

fun Application.configureDatabases() {
    val config = environment.config.config("database")
    val url = config.property("url").getString()
    val driver = config.property("driver").getString()
    val user = config.property("user").getString()
    val password = config.property("password").getString()

    Database.connect(url, driver, user = user, password = password)

    val connection = DriverManager.getConnection(url, user, password)
    val database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(JdbcConnection(connection))
    val liquibase = Liquibase("changelog/db.changelog-master.yaml", ClassLoaderResourceAccessor(), database)
    liquibase.update("")
}