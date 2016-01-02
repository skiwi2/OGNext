package com.ognext

import grails.transaction.Transactional

import java.time.Instant
import java.time.temporal.ChronoUnit

@Transactional
class PlayerService {
    def researchesService

    Player findPlayer(Universe universe, Integer playerId) {
        Player.findByUniverseAndPlayerId(universe, playerId)
    }

    Player createPlayer(Universe universe, Integer playerId, String name) {
        def researches = researchesService.createDefaultResearches()
        def player = new Player(universe: universe, playerId: playerId, researches: researches)
        player.addToAliases(new PlayerAlias(name: name, begin: Instant.ofEpochMilli(0), end: Instant.now().plus(50000, ChronoUnit.DAYS)))
        player.save()
    }

    void storePlayerName(Player player, String name, Instant instant) {
        int index = player.aliases.findIndexOf { it.inInterval(instant) }
        def playerAlias = player.aliases[index]
        if (playerAlias.name != name) {
            def oldEnd = playerAlias.end
            playerAlias.end = instant
            def newPlayerAlias = new PlayerAlias(name: name, begin: instant, end: oldEnd)
            player.addToAliases(newPlayerAlias)
            player.save()
        }
    }

    PlayerAlias getPlayerAlias(Player player, Instant instant) {
        player.aliases.find { it.inInterval(instant) }
    }
}
