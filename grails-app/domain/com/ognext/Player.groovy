package com.ognext

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
            def aliasIntersects = aliases[0..<-1].any { it.intervalIntersects(addedAlias) }
            if (aliasIntersects) {
                'aliasIntersects'
            }
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

    @Override
    String toString() {
        "${this.class.simpleName}($id, $universe, $playerId)"
    }
}
