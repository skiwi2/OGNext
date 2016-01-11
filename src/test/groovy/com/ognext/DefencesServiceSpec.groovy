package com.ognext

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(DefencesService)
@Mock([Defences, ServerGroup, ServerGroupService, Universe, Player, PlayerAlias, Researches, ResearchesService, Planet, PlanetAlias, PlanetLocation, Coordinate, CoordinateService, PlanetBuildings, PlanetBuildingsService, Fleet, FleetService, Moon, MoonAlias, MoonLocation, MoonBuildings, MoonBuildingsService])
class DefencesServiceSpec extends Specification {
    Planet planet
    Moon moon

    def setup() {
        def universeService = mockService(UniverseService)
        def universe = universeService.getUniverse("en", 136)
        def playerService = mockService(PlayerService)
        def player = playerService.createPlayer(universe, 100153, "skiwi")
        def planetService = mockService(PlanetService)
        planet = planetService.createPlanet(player, 1001, 1, 204, 8, "Homeworld")
        def moonService = mockService(MoonService)
        moon = moonService.createMoon(player, 1002, 1, 204, 8, "Moon")
    }

    def cleanup() {
    }

    void "test create default defences"() {
        when: "create default defences"
        def defaultDefences = service.createDefaultDefences()

        then: "default defences should be created"
        defaultDefences.rocketLauncher == 0
        defaultDefences.lightLaser == 0
        defaultDefences.heavyLaser == 0
        defaultDefences.gaussCannon == 0
        defaultDefences.ionCannon == 0
        defaultDefences.plasmaTurret == 0
        defaultDefences.smallShieldDome == 0
        defaultDefences.largeShieldDome == 0
        defaultDefences.antiBallisticMissiles == 0
        defaultDefences.interplanetaryMissiles == 0
    }

    void "test update planet defences"() {
        when: "update planet defences"
        service.updatePlanetDefences(planet, 69, 158, 12, 14, 0, 1, 1, 1, 20, 0)

        then: "planet defences should be updated"
        planet.defences.rocketLauncher == 69
        planet.defences.lightLaser == 158
        planet.defences.heavyLaser == 12
        planet.defences.gaussCannon == 14
        planet.defences.ionCannon == 0
        planet.defences.plasmaTurret == 1
        planet.defences.smallShieldDome == 1
        planet.defences.largeShieldDome == 1
        planet.defences.antiBallisticMissiles == 20
        planet.defences.interplanetaryMissiles == 0
    }

    void "test update moon defences"() {
        when: "update moon defences"
        service.updateMoonDefences(moon, 69, 158, 12, 14, 0, 1, 1, 1, 20, 0)

        then: "moon defences should be updated"
        moon.defences.rocketLauncher == 69
        moon.defences.lightLaser == 158
        moon.defences.heavyLaser == 12
        moon.defences.gaussCannon == 14
        moon.defences.ionCannon == 0
        moon.defences.plasmaTurret == 1
        moon.defences.smallShieldDome == 1
        moon.defences.largeShieldDome == 1
        moon.defences.antiBallisticMissiles == 20
        moon.defences.interplanetaryMissiles == 0
    }
}
