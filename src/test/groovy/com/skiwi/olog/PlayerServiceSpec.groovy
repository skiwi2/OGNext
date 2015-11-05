package com.skiwi.olog

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.validation.ValidationException
import spock.lang.Specification

import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(PlayerService)
@Mock([Player, PlayerAlias])
class PlayerServiceSpec extends Specification {
    def playerId = 103168
    def playerName = "skiwi"
    Instant now

    def setup() {
        now = Instant.now()
    }

    def cleanup() {
    }

    void "test get or create player"() {
        when: "get non-existing player"
        def createdPlayer = service.getOrCreatePlayer(playerId, playerName)

        then: "player should be created"
        createdPlayer
        createdPlayer.playerId == playerId
        createdPlayer.currentName == playerName
        Player.findByPlayerId(playerId) == createdPlayer

        when: "get existing player"
        def existingPlayer = service.getOrCreatePlayer(playerId, playerName)

        then: "player should exist"
        existingPlayer
        existingPlayer.playerId == playerId
        service.getPlayerAlias(existingPlayer, now).name == playerName
        Player.findByPlayerId(playerId) == existingPlayer
    }

    void "test create player"() {
        when: "create new player"
        def createdPlayer = service.createPlayer(playerId, playerName)

        then: "player should be created"
        createdPlayer
        createdPlayer.playerId == playerId
        createdPlayer.currentName == playerName
        Player.findByPlayerId(playerId) == createdPlayer

        when: "create other player with same playerId"
        service.createPlayer(playerId, playerName)

        then:
        def ex = thrown(ValidationException)
        ex.errors.allErrors.stream().flatMap({objectError -> objectError.codes.toList().stream()}).any { it == "unique" }
    }

    void "test update player name and get player alias"() {
        given: "a player"
        def player = service.createPlayer(playerId, playerName)
        def newPlayerName = "skiwi2"

        when: "player has been seen with same name"
        service.updatePlayerName(player, playerName, now)

        then: "player does not have a new alias"
        player.aliases.size() == 1
        player.getNameAt(now.minus(1, ChronoUnit.HOURS)) == playerName
        player.getNameAt(now) == playerName
        player.getNameAt(now.plus(1, ChronoUnit.HOURS)) == playerName

        when: "player has been seen with a different name"
        def updateInstant = now.minus(2, ChronoUnit.HOURS)
        service.updatePlayerName(player, newPlayerName, updateInstant)

        then: "player has a new alias"
        player.aliases.size() == 2
        service.getPlayerAlias(player, now.minus(4, ChronoUnit.HOURS)).name == playerName
        service.getPlayerAlias(player, now.minus(2, ChronoUnit.HOURS)).name == newPlayerName
        service.getPlayerAlias(player, now.plus(2, ChronoUnit.HOURS)).name == newPlayerName
        service.getPlayerAlias(player, now.minus(4, ChronoUnit.HOURS)).end == updateInstant
        service.getPlayerAlias(player, now.minus(2, ChronoUnit.HOURS)).begin == updateInstant
    }
}
