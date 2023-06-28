package poker

import kotlinx.serialization.json.JsonObject

class Player {
    fun betRequest(gameState: JsonObject): Int {
        return 0
    }

    fun showdown() {
    }

    fun version(): String {
        return "Kotlin Player 0.0.1"
    }
}
