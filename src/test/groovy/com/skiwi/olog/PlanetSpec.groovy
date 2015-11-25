package com.skiwi.olog

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Planet)
@Mock(Coordinate)
@TestMixin(GrailsUnitTestMixin)
class PlanetSpec extends Specification {
    def serverGroup = new ServerGroup(countryCode: "en")
    def universe = new Universe(serverGroup: serverGroup, universeId: 135)
    def player = new Player(universe: universe, playerId: 103168)

    def setup() {
    }

    def cleanup() {
    }

    void "save valid planet"() {
        when: "planet is valid"
        def planet = new Planet(player: player, coordinate: new Coordinate(galaxy: 2, solarSystem: 122, position: 12))

        then: "planet should be saved"
        planet.save()
    }

    void "universe equals player universe when updating"() {
        when: "planet is valid"
        def planet = new Planet(player: player, coordinate: new Coordinate(galaxy: 2, solarSystem: 122, position: 12))
        planet.save()

        then: "universe should be equal to player universe"
        planet.universe == planet.player.universe

        when: "the player gets updated"
        def player2 = new Player(universe: universe, playerId: 103169)
        planet.player = player2
        planet.save()

        then: "the universe should be updated to the new player universe"
        planet.universe == player2.universe

        when: "the universe gets updated"
        def oldUniverse = planet.universe
        def universe2 = new Universe(serverGroup: serverGroup, universeId: 136)
        planet.universe == universe2
        planet.save()

        then: "the universe should not be updated"
        planet.universe == oldUniverse
    }

    void "save valid planets on same coordinates but in different universes"() {
        when: "planets are valid on the same coordinates but in different universes"
        def universe2 = new Universe(serverGroup: serverGroup, universeId: 136)
        def player2 = new Player(universe: universe2, playerId: 103168)
        def planet = new Planet(player: player, coordinate: new Coordinate(galaxy: 2, solarSystem: 122, position: 12))
        def planet2 = new Planet(player: player2, coordinate: new Coordinate(galaxy: 2, solarSystem: 122, position: 12))

        then: "both planets should be saved"
        planet.save()
        planet2.save()
    }

    void "save valid planets belonging to same player"() {
        when: "planets are valid and belong to same player"
        def planet = new Planet(player: player, coordinate: new Coordinate(galaxy: 2, solarSystem: 122, position: 12))
        def planet2 = new Planet(player: player, coordinate: new Coordinate(galaxy: 2, solarSystem: 153, position: 8))

        then: "both planets should be saved"
        planet.save()
        planet2.save()
    }

    void "save invalid planets on same coordinates"() {
        when: "planets are invalid because they are on the same coordinates"
        def planet = new Planet(player: player, coordinate: new Coordinate(galaxy: 2, solarSystem: 122, position: 12))
        def planet2 = new Planet(player: player, coordinate: new Coordinate(galaxy: 2, solarSystem: 122, position: 12))

        then: "saving should fail"
        planet.save(flush: true)
        !planet2.save(failOnError: false)
    }
}
