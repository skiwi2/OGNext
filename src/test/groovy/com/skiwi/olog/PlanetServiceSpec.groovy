package com.skiwi.olog

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(PlanetService)
@Mock([Planet, ServerGroup, Universe, Player, Coordinate, CoordinateService])
class PlanetServiceSpec extends Specification {
    def serverGroup = new ServerGroup(countryCode: "en")
    def universe = new Universe(serverGroup: serverGroup, universeId: 135)
    def player = new Player(playerId: 103168, universe: universe)
    def galaxy = 2
    def solarSystem = 122
    def position = 12

    def setup() {
    }

    def cleanup() {
    }

    void "test get or create planet"() {
        when: "get non-existing planet"
        def createdPlanet = service.getOrCreatePlanet(player, galaxy, solarSystem, position)

        then: "planet should be created"
        createdPlanet
        createdPlanet.player == player
        createdPlanet.coordinate.galaxy == galaxy
        createdPlanet.coordinate.solarSystem == solarSystem
        createdPlanet.coordinate.position == position
        Planet.findByPlayerAndCoordinate(player, Coordinate.findByGalaxyAndSolarSystemAndPosition(galaxy, solarSystem, position)) == createdPlanet

        when: "get existing planet"
        def existingPlanet = service.getOrCreatePlanet(player, galaxy, solarSystem, position)

        then: "planet should exist"
        existingPlanet
        existingPlanet.player == player
        existingPlanet.coordinate.galaxy == galaxy
        existingPlanet.coordinate.solarSystem == solarSystem
        existingPlanet.coordinate.position == position
        Planet.findByPlayerAndCoordinate(player, Coordinate.findByGalaxyAndSolarSystemAndPosition(galaxy, solarSystem, position)) == existingPlanet
    }
}
