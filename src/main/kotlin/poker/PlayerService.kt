package poker

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Undertow
import org.http4k.server.asServer

val player = Player()

fun main(args: Array<String>) {
    val app = routes(
        "/" bind Method.POST to { request ->
            when(val action = request.query("action")) {
                "bet_request" -> {
                    val gameState = request.query("game_state")
                    if (gameState == null) {
                        Response(BAD_REQUEST).body("Missing game_state!")
                    } else {
                        val jsonObj = Json.decodeFromString(JsonObject.serializer(), gameState)
                        val result = player.betRequest(jsonObj).toString()
                        Response(OK).body(result)
                    }
                }
                "shutdown" -> {
                    player.showdown()
                    Response(OK)
                }
                "version" -> {
                    Response(OK).body(player.version())
                }

                else -> Response(BAD_REQUEST).body("Unknown action '$action'!")
            }
        }
    )

    val server = app.asServer(Undertow(getPort())).start()
}

private fun getPort(): Int {
    val port = System.getenv("PORT") ?: "8123"
    return port.toInt()
}
