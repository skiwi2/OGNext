package com.ognext

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(BuildingsService)
@Mock([Buildings, ServerGroup, ServerGroupService, Universe, Player, PlayerAlias, Researches, ResearchesService, Planet, PlanetAlias, PlanetLocation, Coordinate, CoordinateService, Defences, DefencesService, Fleet, FleetService])
class BuildingsServiceSpec extends Specification {
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

    void "test create default buildings"() {
        when: "create default buildings"
        def defaultBuildings = service.createDefaultBuildings()

        then: "default buildings should be created"
        defaultBuildings.metalMine == 0
        defaultBuildings.crystalMine == 0
        defaultBuildings.deuteriumSynthesizer == 0
        defaultBuildings.solarPlant == 0
        defaultBuildings.fusionReactor == 0
        defaultBuildings.solarSatellite == 0
        defaultBuildings.metalStorage == 0
        defaultBuildings.crystalStorage == 0
        defaultBuildings.deuteriumTank == 0
        defaultBuildings.roboticsFactory == 0
        defaultBuildings.shipyard == 0
        defaultBuildings.researchLab == 0
        defaultBuildings.allianceDepot == 0
        defaultBuildings.missileSilo == 0
        defaultBuildings.naniteFactory == 0
        defaultBuildings.terraformer == 0
    }

    void "test update planet resource buildings"() {
        when: "update planet resource buildings"
        service.updatePlanetResourceBuildings(planet, 20, 18, 11, 20, 0, 0, 6, 4, 4)

        then: "planet resource buildings should be updated"
        planet.buildings.metalMine == 20
        planet.buildings.crystalMine == 18
        planet.buildings.deuteriumSynthesizer == 11
        planet.buildings.solarPlant == 20
        planet.buildings.fusionReactor == 0
        planet.buildings.solarSatellite == 0
        planet.buildings.metalStorage == 6
        planet.buildings.crystalStorage == 4
        planet.buildings.deuteriumTank == 4
    }

    void "test update planet facility buildings"() {
        when: "update planet facility buildings"
        service.updatePlanetFacilityBuildings(planet, 7, 8, 7, 0, 2, 0, 0)

        then: "planet facility buildings should be updated"
        planet.buildings.roboticsFactory == 7
        planet.buildings.shipyard == 8
        planet.buildings.researchLab == 7
        planet.buildings.allianceDepot == 0
        planet.buildings.missileSilo == 2
        planet.buildings.naniteFactory == 0
        planet.buildings.terraformer == 0
    }
}
