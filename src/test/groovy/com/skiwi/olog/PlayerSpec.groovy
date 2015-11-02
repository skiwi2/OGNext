package com.skiwi.olog

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.domain.DomainClassUnitTestMixin
import spock.lang.Specification

import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Player)
@Mock([PlayerAlias])
class PlayerSpec extends Specification {
    def setup() {
    }

    def cleanup() {
    }

    void "save valid player"() {
        when: "player is valid"
        def player = new Player(playerId: 103168)

        then: "player should be saved"
        player.save()
    }

    void "save two valid players"() {
        when: "players have different playerId"
        def player = new Player(playerId: 103168)
        def player2 = new Player(playerId: 103169)

        then: "players should be saved"
        player.save()
        player2.save()
    }

    void "sae two players with same playerId"() {
        when: "players have same playerId"
        def player = new Player(playerId: 103168)
        def player2 = new Player(playerId: 103168)

        then: "second player should not be saved"
        player.save(flush: true)
        !player2.save(failOnError: false)
    }

    void "save valid player with single alias"() {
        when: "player has single alias"
        def now = Instant.now()
        def player = new Player(playerId: 103168)
        player.addToAliases(new PlayerAlias(name: "skiwi", begin: Instant.ofEpochSecond(0), end: now.plus(50000, ChronoUnit.DAYS)))

        then: "player should be saved"
        player.save()
    }

    void "save valid player with non-intersecting aliases"() {
        when: "player has non-intersecting aliases"
        def now = Instant.now()
        def player = new Player(playerId: 103168)
        player.addToAliases(new PlayerAlias(name: "skiwi", begin: Instant.ofEpochSecond(0), end: now))
        player.addToAliases(new PlayerAlias(name: "skiwi2", begin: now, end: now.plus(50000, ChronoUnit.DAYS)))

        then: "player should be saved"
        player.save()
    }

    void "save invalid player with intersecting aliases"() {
        when: "player has intersecting aliases"
        def now = Instant.now()
        def player = new Player(playerId: 103168)
        player.addToAliases(new PlayerAlias(name: "skiwi", begin: Instant.ofEpochSecond(0), end: now.plus(1, ChronoUnit.HOURS)))
        player.addToAliases(new PlayerAlias(name: "skiwi2", begin: now, end: now.plus(50000, ChronoUnit.DAYS)))

        then: "player should not be saved"
        !player.save(failOnError: false)
    }

    void "save valid player with same name again"() {
        when: "player has changed his name back again after having changed it"
        def now = Instant.now()
        def player = new Player(playerId: 103168)
        player.addToAliases(new PlayerAlias(name: "skiwi", begin: Instant.ofEpochSecond(0), end: now))
        player.addToAliases(new PlayerAlias(name: "skiwi2", begin: now, end: now.plus(10, ChronoUnit.DAYS)))
        player.addToAliases(new PlayerAlias(name: "skiwi", begin: now.plus(10, ChronoUnit.DAYS), end: now.plus(50000, ChronoUnit.DAYS)))

        then: "player should be saved"
        player.save()
    }
}
