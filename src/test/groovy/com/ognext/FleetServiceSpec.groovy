package com.ognext

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(FleetService)
@Mock([Fleet, ServerGroup, ServerGroupService, Universe, Player, PlayerAlias, Researches, ResearchesService, Planet, PlanetAlias, PlanetLocation, Coordinate, CoordinateService, Buildings, BuildingsService, Defences, DefencesService])
class FleetServiceSpec extends Specification {
    Planet planet

    def setup() {
        def universeService = mockService(UniverseService)
        def universe = universeService.getUniverse("en", 136)
        def playerService = mockService(PlayerService)
        def player = playerService.createPlayer(universe, 100153, "skiwi")
        def planetService = mockService(PlanetService)
        planet = planetService.createPlanet(player, 1001, 1, 204, 8, "Homeworld")
    }

    def cleanup() {
    }

    void "test create default fleet"() {
        when: "create default fleet"
        def defaultFleet = service.createDefaultFleet()

        then: "default fleet should be created"
        defaultFleet.lightFighter == 0
        defaultFleet.heavyFighter == 0
        defaultFleet.cruiser == 0
        defaultFleet.battleship == 0
        defaultFleet.smallCargo == 0
        defaultFleet.largeCargo == 0
        defaultFleet.colonyShip == 0
        defaultFleet.battlecruiser == 0
        defaultFleet.bomber == 0
        defaultFleet.destroyer == 0
        defaultFleet.deathstar == 0
        defaultFleet.recycler == 0
        defaultFleet.espionageProbe == 0
    }

    void "test update planet fleet"() {
        when: "update planet fleet"
        service.updatePlanetFleet(planet, 0, 0, 31, 14, 49, 9, 0, 0, 5, 0, 0, 20, 11)

        then: "planet fleet should be updated"
        planet.fleet.lightFighter == 0
        planet.fleet.heavyFighter == 0
        planet.fleet.cruiser == 31
        planet.fleet.battleship == 14
        planet.fleet.smallCargo == 49
        planet.fleet.largeCargo == 9
        planet.fleet.colonyShip == 0
        planet.fleet.battlecruiser == 0
        planet.fleet.bomber == 5
        planet.fleet.destroyer == 0
        planet.fleet.deathstar == 0
        planet.fleet.recycler == 20
        planet.fleet.espionageProbe == 11
    }
}
