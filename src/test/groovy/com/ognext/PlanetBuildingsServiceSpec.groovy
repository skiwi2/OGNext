package com.ognext

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(PlanetBuildingsService)
@Mock([PlanetBuildings, ServerGroup, ServerGroupService, Universe, Player, PlayerAlias, Researches, ResearchesService, Planet, PlanetAlias, PlanetLocation, Coordinate, CoordinateService, Defences, DefencesService, Fleet, FleetService])
class PlanetBuildingsServiceSpec extends Specification {
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
        def defaultPlanetBuildings = service.createDefaultPlanetBuildings()

        then: "default buildings should be created"
        defaultPlanetBuildings.metalMine == 0
        defaultPlanetBuildings.crystalMine == 0
        defaultPlanetBuildings.deuteriumSynthesizer == 0
        defaultPlanetBuildings.solarPlant == 0
        defaultPlanetBuildings.fusionReactor == 0
        defaultPlanetBuildings.solarSatellite == 0
        defaultPlanetBuildings.metalStorage == 0
        defaultPlanetBuildings.crystalStorage == 0
        defaultPlanetBuildings.deuteriumTank == 0
        defaultPlanetBuildings.roboticsFactory == 0
        defaultPlanetBuildings.shipyard == 0
        defaultPlanetBuildings.researchLab == 0
        defaultPlanetBuildings.allianceDepot == 0
        defaultPlanetBuildings.missileSilo == 0
        defaultPlanetBuildings.naniteFactory == 0
        defaultPlanetBuildings.terraformer == 0
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

    void "test update planet solar satellite"() {
        when: "update planet solar satellite"
        service.updatePlanetSolarSatellite(planet, 5)

        then: "planet solar satellite should be updated"
        planet.buildings.solarSatellite == 5
    }
}
