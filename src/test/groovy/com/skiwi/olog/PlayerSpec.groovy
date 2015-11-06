package com.skiwi.olog

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import grails.test.mixin.web.ControllerUnitTestMixin
import spock.lang.Specification

import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Player)
@Mock([PlayerAlias, Server, Universe])
@TestMixin(GrailsUnitTestMixin)
class PlayerSpec extends Specification {
    PlayerService playerService
    UniverseService universeService
    Universe universe

    static doWithSpring = {
        playerService(PlayerService)
        serverService(ServerService)
        universeService(UniverseService)
    }

    def setup() {
        playerService = grailsApplication.mainContext.getBean("playerService")
        def serverService = grailsApplication.mainContext.getBean("serverService")
        universeService = grailsApplication.mainContext.getBean("universeService")
        universeService.serverService = serverService
        universe = universeService.getOrCreateUniverse("en", 1, "1")
    }

    def cleanup() {
    }

    void "save valid player"() {
        when: "player is valid"
        def player = new Player(universe: universe, playerId: 103168)

        then: "player should be saved"
        player.save()
    }

    void "save two valid players"() {
        when: "players have different playerId in same universe"
        def player = new Player(universe: universe, playerId: 103168)
        def player2 = new Player(universe: universe, playerId: 103169)

        then: "players should be saved"
        player.save()
        player2.save()
    }

    void "save two players with same playerId in same universe"() {
        when: "players have same playerId in same universe"
        def player = new Player(universe: universe, playerId: 103168)
        def player2 = new Player(universe: universe, playerId: 103168)

        then: "second player should not be saved"
        player.save(flush: true)
        !player2.save(failOnError: false)
    }

    void "save two players with same playerId in different universes"() {
        when: "players have same playerId in different universes"
        def universe1 = universeService.getOrCreateUniverse("en", 1, "1")
        def universe2 = universeService.getOrCreateUniverse("en", 2, "2")
        def player = new Player(universe: universe1, playerId: 103168)
        def player2 = new Player(universe: universe2, playerId: 103168)

        then: "players should be saved"
        player.save()
        player2.save()
    }

    void "save valid player with single alias"() {
        when: "player has single alias"
        def now = Instant.now()
        def player = new Player(universe: universe, playerId: 103168)
        player.addToAliases(new PlayerAlias(name: "skiwi", begin: Instant.ofEpochSecond(0), end: now.plus(50000, ChronoUnit.DAYS)))

        then: "player should be saved"
        player.save()
    }

    void "save valid player with non-intersecting aliases"() {
        when: "player has non-intersecting aliases"
        def now = Instant.now()
        def player = new Player(universe: universe, playerId: 103168)
        player.addToAliases(new PlayerAlias(name: "skiwi", begin: Instant.ofEpochSecond(0), end: now))
        player.addToAliases(new PlayerAlias(name: "skiwi2", begin: now, end: now.plus(50000, ChronoUnit.DAYS)))

        then: "player should be saved"
        player.save()
    }

    void "save invalid player with intersecting aliases"() {
        when: "player has intersecting aliases"
        def now = Instant.now()
        def player = new Player(universe: universe, playerId: 103168)
        player.addToAliases(new PlayerAlias(name: "skiwi", begin: Instant.ofEpochSecond(0), end: now.plus(1, ChronoUnit.HOURS)))
        player.addToAliases(new PlayerAlias(name: "skiwi2", begin: now, end: now.plus(50000, ChronoUnit.DAYS)))

        then: "player should not be saved"
        !player.save(failOnError: false)
    }

    void "save valid player with same name again"() {
        when: "player has changed his name back again after having changed it"
        def now = Instant.now()
        def player = new Player(universe: universe, playerId: 103168)
        player.addToAliases(new PlayerAlias(name: "skiwi", begin: Instant.ofEpochSecond(0), end: now))
        player.addToAliases(new PlayerAlias(name: "skiwi2", begin: now, end: now.plus(10, ChronoUnit.DAYS)))
        player.addToAliases(new PlayerAlias(name: "skiwi", begin: now.plus(10, ChronoUnit.DAYS), end: now.plus(50000, ChronoUnit.DAYS)))

        then: "player should be saved"
        player.save()
    }

    void "test current name"() {
        given: "a player with a name"
        def player = playerService.getOrCreatePlayer(universe, 103168, "skiwi")

        expect:
        player.currentName == "skiwi"
    }

    void "test name at instant"() {
        given: "a player with a name"
        def player = playerService.getOrCreatePlayer(universe, 103168, "skiwi")

        expect:
        player.getNameAt(Instant.now().plus(4, ChronoUnit.HOURS)) == "skiwi"
    }
}
