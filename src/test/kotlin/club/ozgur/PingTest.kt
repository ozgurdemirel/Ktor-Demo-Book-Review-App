package club.ozgur

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*

class PingTest : FunSpec({

    test("GET /ping should return pong") {
        testApplication {
            routing {
                get("/ping") {
                    call.respondText("pong")
                }
            }
            val response = client.get("/ping")
            response.status shouldBe HttpStatusCode.OK
        }
    }
})
