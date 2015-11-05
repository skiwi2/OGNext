package com.skiwi.olog

import java.time.Instant

class Player {
    def playerService

    static constraints = {
        playerId unique: true
        aliases validator: { aliases, object ->
            if (!aliases) {
                return true
            }
            def addedAlias = aliases[-1]
            aliases[0..<-1].every { !it.intervalIntersects(addedAlias) }
        }
    }

    static hasMany = [aliases: PlayerAlias]

    Integer playerId
    List<PlayerAlias> aliases

    String getCurrentName() {
        getNameAt(Instant.now())
    }

    String getNameAt(Instant instant) {
        playerService.getPlayerAlias(this, instant).name
    }
}
