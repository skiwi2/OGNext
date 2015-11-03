package com.skiwi.olog

import grails.transaction.Transactional

import java.time.Instant
import java.time.temporal.ChronoUnit

@Transactional
class PlayerService {
    Player getOrCreatePlayer(Integer playerId, String name) {
        Player.findByPlayerId(playerId) ?: createPlayer(playerId, name)
    }

    Player createPlayer(Integer playerId, String name) {
        def player = new Player(playerId: playerId)
        player.addToAliases(new PlayerAlias(name: name, begin: Instant.ofEpochMilli(0), end: Instant.now().plus(50000, ChronoUnit.DAYS)))
        player.save()
    }

    void updatePlayerName(Player player, String name, Instant instant) {
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
