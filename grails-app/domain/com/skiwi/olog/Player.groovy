package com.skiwi.olog

import groovy.transform.EqualsAndHashCode

import java.time.Instant

@EqualsAndHashCode(includes = ["universe", "playerId"])
class Player {
    def playerService

    static constraints = {
        playerId unique: "universe"
        aliases validator: { aliases, object ->
            if (!aliases) {
                return true
            }
            def addedAlias = aliases[-1]
            aliases[0..<-1].every { !it.intervalIntersects(addedAlias) }
        }
    }

    static hasMany = [aliases: PlayerAlias]

    Universe universe
    Integer playerId
    List<PlayerAlias> aliases

    Researches researches

    Date dateCreated
    Date lastUpdated

    String getCurrentName() {
        getNameAt(Instant.now())
    }

    String getNameAt(Instant instant) {
        playerService.getPlayerAlias(this, instant).name
    }
}
