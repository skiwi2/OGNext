package com.ognext

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(MoonBuildingsService)
@Mock([MoonBuildings, ServerGroup, ServerGroupService, Universe, Player, PlayerAlias, Researches, ResearchesService, Moon, MoonAlias, MoonLocation, Coordinate, CoordinateService, Defences, DefencesService, Fleet, FleetService])
class MoonBuildingsServiceSpec extends Specification {
    Moon moon

    def setup() {
        def universeService = mockService(UniverseService)
        def universe = universeService.getUniverse("en", 136)
        def playerService = mockService(PlayerService)
        def player = playerService.createPlayer(universe, 100153, "skiwi")
        def moonService = mockService(MoonService)
        moon = moonService.createMoon(player, 1001, 1, 204, 8, "Moon")
    }

    def cleanup() {
    }

    void "test create default buildings"() {
        when: "create default buildings"
        def defaultMoonBuildings = service.createDefaultMoonBuildings()

        then: "default buildings should be created"
        defaultMoonBuildings.solarSatellite == 0
        defaultMoonBuildings.metalStorage == 0
        defaultMoonBuildings.crystalStorage == 0
        defaultMoonBuildings.deuteriumTank == 0
        defaultMoonBuildings.roboticsFactory == 0
        defaultMoonBuildings.shipyard == 0
        defaultMoonBuildings.lunarBase == 0
        defaultMoonBuildings.sensorPhalanx == 0
        defaultMoonBuildings.jumpGate == 0
    }

    void "test update moon resource buildings"() {
        when: "update moon resource buildings"
        service.updateMoonResourceBuildings(moon, 1, 2, 3, 4)

        then: "moon resource buildings should be updated"
        moon.buildings.solarSatellite == 1
        moon.buildings.metalStorage == 2
        moon.buildings.crystalStorage == 3
        moon.buildings.deuteriumTank == 4
    }

    void "test update moon facility buildings"() {
        when: "update moon facility buildings"
        service.updateMoonFacilityBuildings(moon, 1, 2, 3, 4, 5)

        then: "moon facility buildings should be updated"
        moon.buildings.roboticsFactory == 1
        moon.buildings.shipyard == 2
        moon.buildings.lunarBase == 3
        moon.buildings.sensorPhalanx == 4
        moon.buildings.jumpGate == 5
    }

    void "test update moon solar satellite"() {
        when: "update moon solar satellite"
        service.updateMoonSolarSatellite(moon, 5)

        then: "moon solar satellite should be updated"
        moon.buildings.solarSatellite == 5
    }
}
