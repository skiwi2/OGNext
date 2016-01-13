package com.ognext

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Player)
@Mock([PlayerAlias, ServerGroup, Universe, Researches])
@TestMixin(GrailsUnitTestMixin)
class PlayerSpec extends Specification {
    ResearchesService researchesService
    PlayerService playerService
    UniverseService universeService
    Universe universe
    Universe universe2
    Researches defaultResearches

    static doWithSpring = {
        researchesService(ResearchesService)
        playerService(PlayerService)
        serverGroupService(ServerGroupService)
        universeService(UniverseService)
    }

    def setup() {
        researchesService = grailsApplication.mainContext.getBean("researchesService")
        playerService = grailsApplication.mainContext.getBean("playerService")
        playerService.researchesService = researchesService
        def serverGroupService = grailsApplication.mainContext.getBean("serverGroupService")
        universeService = grailsApplication.mainContext.getBean("universeService")
        universeService.serverGroupService = serverGroupService
        universe = universeService.getUniverse("en", 1)
        universe2 = universeService.getUniverse("en", 2)
        defaultResearches = researchesService.createDefaultResearches()
    }

    def cleanup() {
    }

    void "save valid player"() {
        when: "player is valid"
        def player = new Player(universe: universe, playerId: 103168, researches: defaultResearches)

        println player

        then: "player should be saved"
        player.save()
    }

    void "save two valid players"() {
        when: "players have different playerId in same universe"
        def player = new Player(universe: universe, playerId: 103168, researches: defaultResearches)
        def player2 = new Player(universe: universe, playerId: 103169, researches: defaultResearches)

        then: "players should be saved"
        player.save()
        player2.save()
    }

    void "save two players with same playerId in same universe"() {
        when: "players have same playerId in same universe"
        def player = new Player(universe: universe, playerId: 103168, researches: defaultResearches)
        def player2 = new Player(universe: universe, playerId: 103168, researches: defaultResearches)

        then: "second player should not be saved"
        player.save(flush: true)
        !player2.save(failOnError: false)
    }

    void "save two players with same playerId in different universes"() {
        when: "players have same playerId in different universes"
        def universe1 = universeService.getUniverse("en", 1)
        def universe2 = universeService.getUniverse("en", 2)
        def player = new Player(universe: universe1, playerId: 103168, researches: defaultResearches)
        def player2 = new Player(universe: universe2, playerId: 103168, researches: defaultResearches)

        then: "players should be saved"
        player.save()
        player2.save()
    }

    void "save valid player with single alias"() {
        when: "player has single alias"
        def now = Instant.now()
        def player = new Player(universe: universe, playerId: 103168, researches: defaultResearches)
        player.addToAliases(new PlayerAlias(name: "skiwi", begin: Instant.ofEpochSecond(0), end: now.plus(50000, ChronoUnit.DAYS)))

        then: "player should be saved"
        player.save()
    }

    void "save valid player with non-intersecting aliases"() {
        when: "player has non-intersecting aliases"
        def now = Instant.now()
        def player = new Player(universe: universe, playerId: 103168, researches: defaultResearches)
        player.addToAliases(new PlayerAlias(name: "skiwi", begin: Instant.ofEpochSecond(0), end: now))
        player.addToAliases(new PlayerAlias(name: "skiwi2", begin: now, end: now.plus(50000, ChronoUnit.DAYS)))

        then: "player should be saved"
        player.save()
    }

    void "save invalid player with intersecting aliases"() {
        when: "player has intersecting aliases"
        def now = Instant.now()
        def player = new Player(universe: universe, playerId: 103168, researches: defaultResearches)
        player.addToAliases(new PlayerAlias(name: "skiwi", begin: Instant.ofEpochSecond(0), end: now.plus(1, ChronoUnit.HOURS)))
        player.addToAliases(new PlayerAlias(name: "skiwi2", begin: now, end: now.plus(50000, ChronoUnit.DAYS)))

        then: "player should not be saved"
        !player.save(failOnError: false)
    }

    void "save valid player with same name again"() {
        when: "player has changed his name back again after having changed it"
        def now = Instant.now()
        def player = new Player(universe: universe, playerId: 103168, researches: defaultResearches)
        player.addToAliases(new PlayerAlias(name: "skiwi", begin: Instant.ofEpochSecond(0), end: now))
        player.addToAliases(new PlayerAlias(name: "skiwi2", begin: now, end: now.plus(10, ChronoUnit.DAYS)))
        player.addToAliases(new PlayerAlias(name: "skiwi", begin: now.plus(10, ChronoUnit.DAYS), end: now.plus(50000, ChronoUnit.DAYS)))

        then: "player should be saved"
        player.save()
    }

    void "test current name"() {
        given: "a player with a name"
        def player = playerService.createPlayer(universe, 103168, "skiwi")

        expect:
        player.currentName == "skiwi"
    }

    void "test name at instant"() {
        given: "a player with a name"
        def player = playerService.createPlayer(universe, 103168, "skiwi")

        expect:
        player.getNameAt(Instant.now().plus(4, ChronoUnit.HOURS)) == "skiwi"
    }

    void "test equals and hash code"() {
        given:
        def playerAlias = new PlayerAlias(name: "skiwi", begin: Instant.ofEpochSecond(0), end:  Instant.now().plus(50000, ChronoUnit.DAYS))
        def playerAlias2 = new PlayerAlias(name: "skiwi2", begin: Instant.ofEpochSecond(0), end:  Instant.now().plus(50000, ChronoUnit.DAYS))

        expect:
        new Player(universe: universe, playerId: 103168, researches: defaultResearches) == new Player(universe: universe, playerId: 103168, researches: defaultResearches)
        new Player(universe: universe, playerId: 103168, researches: defaultResearches) != new Player(universe: universe2, playerId: 103168, researches: defaultResearches)
        new Player(universe: universe, playerId: 103168, researches: defaultResearches) != new Player(universe: universe, playerId: 103169, researches: defaultResearches)

        new Player(universe: universe, playerId: 103168, researches: defaultResearches).addToAliases(playerAlias) == new Player(universe: universe, playerId: 103168, researches: defaultResearches).addToAliases(playerAlias2)
    }

    void "test to string"() {
        expect:
        new Player(universe: universe, playerId: 103168, researches: defaultResearches).toString() == "Player(null, Universe(1, en, 1), 103168)"
    }
}
