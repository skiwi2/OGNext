package com.ognext.api

import com.ognext.Moon
import com.ognext.MoonAlias
import com.ognext.MoonBuildings
import com.ognext.MoonBuildingsService
import com.ognext.MoonLocation
import com.ognext.MoonService
import com.ognext.PlanetBuildings
import com.ognext.PlanetBuildingsService
import com.ognext.CombatReport
import com.ognext.Coordinate
import com.ognext.CoordinateService
import com.ognext.Defences
import com.ognext.DefencesService
import com.ognext.Fleet
import com.ognext.FleetService
import com.ognext.MissileReport
import com.ognext.Planet
import com.ognext.PlanetAlias
import com.ognext.PlanetLocation
import com.ognext.PlanetService
import com.ognext.Player
import com.ognext.PlayerAlias
import com.ognext.PlayerService
import com.ognext.RecycleReport
import com.ognext.ReportKeyService
import com.ognext.Researches
import com.ognext.ResearchesService
import com.ognext.ServerGroup
import com.ognext.ServerGroupService
import com.ognext.SpyReport
import com.ognext.Universe
import com.ognext.UniverseService
import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(UserscriptController)
@Mock([Player, PlayerAlias, SpyReport, CombatReport, RecycleReport, MissileReport, UniverseService, ServerGroup, Universe, ServerGroupService, ReportKeyService, Planet, PlanetService, CoordinateService, Coordinate, PlanetLocation, PlanetAlias, Researches, ResearchesService, PlanetBuildings, PlanetBuildingsService, Defences, DefencesService, Fleet, FleetService, Moon, MoonService, MoonLocation, MoonAlias, MoonBuildings, MoonBuildingsService])
@TestMixin(GrailsUnitTestMixin)
class UserscriptControllerSpec extends Specification {
    UniverseService universeService
    PlayerService playerService
    PlanetService planetService
    MoonService moonService

    static doWithSpring = {
        researchesService(ResearchesService)
        playerService(PlayerService)
        universeService(UniverseService)
        planetBuildingsService(PlanetBuildingsService)
        defencesService(DefencesService)
        fleetService(FleetService)
        planetService(PlanetService)
        moonBuildingsService(MoonBuildingsService)
        moonService(MoonService)
        coordinateService(CoordinateService)
    }

    def setup() {
        playerService = grailsApplication.mainContext.getBean("playerService")
        def researchesService = grailsApplication.mainContext.getBean("researchesService")
        playerService.researchesService = researchesService
        universeService = grailsApplication.mainContext.getBean("universeService")
        planetService = grailsApplication.mainContext.getBean("planetService")
        def planetBuildingsService = grailsApplication.mainContext.getBean("planetBuildingsService")
        planetService.planetBuildingsService = planetBuildingsService
        def defencesService = grailsApplication.mainContext.getBean("defencesService")
        planetService.defencesService = defencesService
        def fleetService = grailsApplication.mainContext.getBean("fleetService")
        planetService.fleetService = fleetService
        moonService = grailsApplication.mainContext.getBean("moonService")
        def moonBuildingsService = grailsApplication.mainContext.getBean("moonBuildingsService")
        moonService.moonBuildingsService = moonBuildingsService
        moonService.defencesService = defencesService
        moonService.fleetService = fleetService
    }

    def cleanup() {
    }

    void "post keys"() {
        when: "post keys request has been made"
        request.json = [
            serverGroup: "en",
            universe: "135",
            playerId: "103168",
            playerName: "skiwi",
            reportKeys: [
                sr: [
                    "sr-key-1",
                    "sr-key-2"
                ],
                cr: [
                    "cr-key-1",
                    "cr-key-2",
                    "cr-key-3"
                ],
                rr: [
                    "rr-key-1",
                    "rr-key-2",
                    "rr-key-3",
                    "rr-key-4"
                ],
                mr: [
                    "mr-key-1",
                    "mr-key-2",
                    "mr-key-3",
                    "mr-key-4",
                    "mr-key-5"
                ]
            ]
        ] as JSON
        request.method = "POST"
        request.contentType = "text/json"
        controller.keys()

        then: "keys should be persisted"
        response.json.result.success == true
        def universe = universeService.getUniverse("en", 135)
        def player = playerService.findPlayer(universe, 103168)
        SpyReport.findByKeyAndPlayer("sr-key-1", player)
        SpyReport.findByKeyAndPlayer("sr-key-2", player)
        CombatReport.findByKeyAndPlayer("cr-key-1", player)
        CombatReport.findByKeyAndPlayer("cr-key-2", player)
        CombatReport.findByKeyAndPlayer("cr-key-3", player)
        RecycleReport.findByKeyAndPlayer("rr-key-1", player)
        RecycleReport.findByKeyAndPlayer("rr-key-2", player)
        RecycleReport.findByKeyAndPlayer("rr-key-3", player)
        RecycleReport.findByKeyAndPlayer("rr-key-4", player)
        MissileReport.findByKeyAndPlayer("mr-key-1", player)
        MissileReport.findByKeyAndPlayer("mr-key-2", player)
        MissileReport.findByKeyAndPlayer("mr-key-3", player)
        MissileReport.findByKeyAndPlayer("mr-key-4", player)
        MissileReport.findByKeyAndPlayer("mr-key-5", player)
    }

    void "post planets"() {
        when: "post planets request has been made"
        request.json = [
            serverGroup: "en",
            universe: "135",
            playerId: "103168",
            playerName: "skiwi",
            planets: [
                [id: "33628117", name: "Homeworld1", galaxy: "2", solarSystem: "122", position: "12"],
                [id: "33631804", name: "Homeworld2", galaxy: "2", solarSystem: "153", position: "8"],
                [id: "33634537", name: "Homeworld3", galaxy: "2", solarSystem: "113", position: "8"],
                [id: "33637742", name: "Homeworld4", galaxy: "3", solarSystem: "340", position: "8"],
                [id: "33644850", name: "Homeworld5", galaxy: "1", solarSystem: "311", position: "8"],
                [id: "33649830", name: "Homeworld6", galaxy: "4", solarSystem: "390", position: "8"]
            ]
        ] as JSON
        request.method = "POST"
        request.contentType = "text/json"
        controller.planets()

        then: "planets should be persisted"
        response.json.result.success == true
        def universe = universeService.getUniverse("en", 135)
        def player = playerService.findPlayer(universe, 103168)
        planetMatches(player, 33628117, "Homeworld1", 2, 122, 12)
        planetMatches(player, 33631804, "Homeworld2", 2, 153, 8)
        planetMatches(player, 33634537, "Homeworld3", 2, 113, 8)
        planetMatches(player, 33637742, "Homeworld4", 3, 340, 8)
        planetMatches(player, 33644850, "Homeworld5", 1, 311, 8)
        planetMatches(player, 33649830, "Homeworld6", 4, 390, 8)
    }

    void "post planets with new names and locations"() {
        setup:
        def universe = universeService.getUniverse("en", 135)
        def player = playerService.createPlayer(universe, 103168, "skiwi")
        planetService.createPlanet(player, 33628117, 2, 122, 12, "Homeworld1")
        planetService.createPlanet(player, 33631804, 2, 153, 8, "Homeworld2")
        planetService.createPlanet(player, 33634537, 2, 113, 8, "Homeworld3")
        planetService.createPlanet(player, 33637742, 3, 340, 8, "Homeworld4")
        planetService.createPlanet(player, 33644850, 1, 311, 8, "Homeworld5")
        planetService.createPlanet(player, 33649830, 4, 390, 8, "Homeworld6")

        when: "post planets request has been made"
        request.json = [
            serverGroup: "en",
            universe: "135",
            playerId: "103168",
            playerName: "skiwi",
            planets: [
                [id: "33628117", name: "Homeworld1", galaxy: "2", solarSystem: "122", position: "12"],
                [id: "33631804", name: "Homeworld2", galaxy: "2", solarSystem: "153", position: "8"],
                [id: "33634537", name: "Homeworld3", galaxy: "2", solarSystem: "113", position: "8"],
                [id: "33637742", name: "Colony2", galaxy: "5", solarSystem: "320", position: "8"],
                [id: "33644850", name: "Homeworld5", galaxy: "9", solarSystem: "151", position: "9"],
                [id: "33649830", name: "Colony", galaxy: "4", solarSystem: "390", position: "8"]
            ]
        ] as JSON
        request.method = "POST"
        request.contentType = "text/json"
        controller.planets()

        then: "planets should be updated"
        response.json.result.success == true
        planetMatches(player, 33628117, "Homeworld1", 2, 122, 12)
        planetMatches(player, 33631804, "Homeworld2", 2, 153, 8)
        planetMatches(player, 33634537, "Homeworld3", 2, 113, 8)
        planetMatches(player, 33637742, "Colony2", 5, 320, 8)
        planetMatches(player, 33644850, "Homeworld5", 9, 151, 9)
        planetMatches(player, 33649830, "Colony", 4, 390, 8)
    }

    void "post planets where planets have been deleted"() {
        setup:
        def universe = universeService.getUniverse("en", 135)
        def player = playerService.createPlayer(universe, 103168, "skiwi")
        planetService.createPlanet(player, 33628117, 2, 122, 12, "Homeworld1")
        planetService.createPlanet(player, 33631804, 2, 153, 8, "Homeworld2")
        planetService.createPlanet(player, 33634537, 2, 113, 8, "Homeworld3")
        planetService.createPlanet(player, 33637742, 3, 340, 8, "Homeworld4")
        planetService.createPlanet(player, 33644850, 1, 311, 8, "Homeworld5")
        planetService.createPlanet(player, 33649830, 4, 390, 8, "Homeworld6")

        when: "post planets request has been made"
        request.json = [
            serverGroup: "en",
            universe: "135",
            playerId: "103168",
            playerName: "skiwi",
            planets: [
                [id: "33628117", name: "Homeworld1", galaxy: "2", solarSystem: "122", position: "12"],
                [id: "33637742", name: "Homeworld4", galaxy: "3", solarSystem: "340", position: "8"],
                [id: "33644850", name: "Homeworld5", galaxy: "1", solarSystem: "311", position: "8"],
                [id: "33649830", name: "Homeworld6", galaxy: "4", solarSystem: "390", position: "8"]
            ]
        ] as JSON
        request.method = "POST"
        request.contentType = "text/json"
        controller.planets()

        then: "those planets should be removed"
        planetService.findPlanet(universe, 33628117) in planetService.findCurrentPlanetsOfPlayer(player)
        !(planetService.findPlanet(universe, 33631804) in planetService.findCurrentPlanetsOfPlayer(player))
        !(planetService.findPlanet(universe, 33634537) in planetService.findCurrentPlanetsOfPlayer(player))
        planetService.findPlanet(universe, 33637742) in planetService.findCurrentPlanetsOfPlayer(player)
        planetService.findPlanet(universe, 33644850) in planetService.findCurrentPlanetsOfPlayer(player)
        planetService.findPlanet(universe, 33649830) in planetService.findCurrentPlanetsOfPlayer(player)
    }

    void "post moons"() {
        when: "post moons request has been made"
        request.json = [
            serverGroup: "en",
            universe: "135",
            playerId: "103168",
            playerName: "skiwi",
            moons: [
                [id: "33628117", name: "Moon1", galaxy: "2", solarSystem: "122", position: "12"],
                [id: "33631804", name: "Moon2", galaxy: "2", solarSystem: "153", position: "8"],
                [id: "33634537", name: "Moon3", galaxy: "2", solarSystem: "113", position: "8"],
                [id: "33637742", name: "Moon4", galaxy: "3", solarSystem: "340", position: "8"],
                [id: "33644850", name: "Moon5", galaxy: "1", solarSystem: "311", position: "8"],
                [id: "33649830", name: "Moon6", galaxy: "4", solarSystem: "390", position: "8"]
            ]
        ] as JSON
        request.method = "POST"
        request.contentType = "text/json"
        controller.moons()

        then: "moons should be persisted"
        response.json.result.success == true
        def universe = universeService.getUniverse("en", 135)
        def player = playerService.findPlayer(universe, 103168)
        moonMatches(player, 33628117, "Moon1", 2, 122, 12)
        moonMatches(player, 33631804, "Moon2", 2, 153, 8)
        moonMatches(player, 33634537, "Moon3", 2, 113, 8)
        moonMatches(player, 33637742, "Moon4", 3, 340, 8)
        moonMatches(player, 33644850, "Moon5", 1, 311, 8)
        moonMatches(player, 33649830, "Moon6", 4, 390, 8)
    }

    void "post moons with new names and locations"() {
        setup:
        def universe = universeService.getUniverse("en", 135)
        def player = playerService.createPlayer(universe, 103168, "skiwi")
        moonService.createMoon(player, 33628117, 2, 122, 12, "Moon1")
        moonService.createMoon(player, 33631804, 2, 153, 8, "Moon2")
        moonService.createMoon(player, 33634537, 2, 113, 8, "Moon3")
        moonService.createMoon(player, 33637742, 3, 340, 8, "Moon4")
        moonService.createMoon(player, 33644850, 1, 311, 8, "Moon5")
        moonService.createMoon(player, 33649830, 4, 390, 8, "Moon6")

        when: "post moons request has been made"
        request.json = [
            serverGroup: "en",
            universe: "135",
            playerId: "103168",
            playerName: "skiwi",
            moons: [
                [id: "33628117", name: "Moon1", galaxy: "2", solarSystem: "122", position: "12"],
                [id: "33631804", name: "Moon2", galaxy: "2", solarSystem: "153", position: "8"],
                [id: "33634537", name: "Moon3", galaxy: "2", solarSystem: "113", position: "8"],
                [id: "33637742", name: "NewMoon2", galaxy: "5", solarSystem: "320", position: "8"],
                [id: "33644850", name: "Moon5", galaxy: "9", solarSystem: "151", position: "9"],
                [id: "33649830", name: "NewMoon", galaxy: "4", solarSystem: "390", position: "8"]
            ]
        ] as JSON
        request.method = "POST"
        request.contentType = "text/json"
        controller.moons()

        then: "moons should be updated"
        response.json.result.success == true
        moonMatches(player, 33628117, "Moon1", 2, 122, 12)
        moonMatches(player, 33631804, "Moon2", 2, 153, 8)
        moonMatches(player, 33634537, "Moon3", 2, 113, 8)
        moonMatches(player, 33637742, "NewMoon2", 5, 320, 8)
        moonMatches(player, 33644850, "Moon5", 9, 151, 9)
        moonMatches(player, 33649830, "NewMoon", 4, 390, 8)
    }

    void "post moons where moons have been deleted"() {
        setup:
        def universe = universeService.getUniverse("en", 135)
        def player = playerService.createPlayer(universe, 103168, "skiwi")
        moonService.createMoon(player, 33628117, 2, 122, 12, "Moon1")
        moonService.createMoon(player, 33631804, 2, 153, 8, "Moon2")
        moonService.createMoon(player, 33634537, 2, 113, 8, "Moon3")
        moonService.createMoon(player, 33637742, 3, 340, 8, "Moon4")
        moonService.createMoon(player, 33644850, 1, 311, 8, "Moon5")
        moonService.createMoon(player, 33649830, 4, 390, 8, "Moon6")

        when: "post moons request has been made"
        request.json = [
            serverGroup: "en",
            universe: "135",
            playerId: "103168",
            playerName: "skiwi",
            moons: [
                [id: "33628117", name: "Moon1", galaxy: "2", solarSystem: "122", position: "12"],
                [id: "33637742", name: "Moon4", galaxy: "3", solarSystem: "340", position: "8"],
                [id: "33644850", name: "Moon5", galaxy: "1", solarSystem: "311", position: "8"],
                [id: "33649830", name: "Moon6", galaxy: "4", solarSystem: "390", position: "8"]
            ]
        ] as JSON
        request.method = "POST"
        request.contentType = "text/json"
        controller.moons()

        then: "those moons should be removed"
        moonService.findMoon(universe, 33628117) in moonService.findCurrentMoonsOfPlayer(player)
        !(moonService.findMoon(universe, 33631804) in moonService.findCurrentMoonsOfPlayer(player))
        !(moonService.findMoon(universe, 33634537) in moonService.findCurrentMoonsOfPlayer(player))
        moonService.findMoon(universe, 33637742) in moonService.findCurrentMoonsOfPlayer(player)
        moonService.findMoon(universe, 33644850) in moonService.findCurrentMoonsOfPlayer(player)
        moonService.findMoon(universe, 33649830) in moonService.findCurrentMoonsOfPlayer(player)
    }

    void "post researches"() {
        when: "post researches request has been made"
        request.json = [
            serverGroup: "en",
            universe: "136",
            playerId: "100153",
            playerName: "skiwi",
            researches: [
                [id: "113", level: "6"],
                [id: "120", level: "10"],
                [id: "121", level: "5"],
                [id: "114", level: "3"],
                [id: "122", level: "0"],
                [id: "115", level: "7"],
                [id: "117", level: "5"],
                [id: "118", level: "3"],
                [id: "106", level: "6"],
                [id: "108", level: "7"],
                [id: "124", level: "5"],
                [id: "123", level: "0"],
                [id: "199", level: "0"],
                [id: "109", level: "9"],
                [id: "110", level: "7"],
                [id: "111", level: "8"]
            ]
        ] as JSON
        request.method = "POST"
        request.contentType = "text/json"
        controller.researches()

        then: "researches should be persisted"
        response.json.result.success == true
        def universe = universeService.getUniverse("en", 136)
        def player = playerService.findPlayer(universe, 100153)
        player.researches.energyTechnology == 6
        player.researches.laserTechnology == 10
        player.researches.ionTechnology == 5
        player.researches.hyperspaceTechnology == 3
        player.researches.plasmaTechnology == 0
        player.researches.combustionDrive == 7
        player.researches.impulseDrive == 5
        player.researches.hyperspaceDrive == 3
        player.researches.espionageTechnology == 6
        player.researches.computerTechnology == 7
        player.researches.astrophysics == 5
        player.researches.intergalacticResearchNetwork == 0
        player.researches.gravitonTechnology == 0
        player.researches.weaponsTechnology == 9
        player.researches.shieldingTechnology == 7
        player.researches.armourTechnology == 8
    }

    void "post planet resource buildings"() {
        when: "post planet resource buildings request has been made"
        request.json = [
            serverGroup: "en",
            universe: "136",
            playerId: "100153",
            playerName: "skiwi",
            planetId: "33623391",
            planetName: "Homeworld",
            planetGalaxy: "1",
            planetSolarSystem: "204",
            planetPosition: "8",
            buildings: [
                [id: "1", level: "20"],
                [id: "2", level: "18"],
                [id: "3", level: "11"],
                [id: "4", level: "20"],
                [id: "12", level: "0"],
                [id: "212", level: "0"],
                [id: "22", level: "6"],
                [id: "23", level: "4"],
                [id: "24", level: "4"]
            ]
        ] as JSON
        request.method = "POST"
        request.contentType = "text/json"
        controller.planetResourceBuildings()

        then: "resource buildings should be persisted"
        response.json.result.success == true
        def universe = universeService.getUniverse("en", 136)
        def planet = planetService.findPlanet(universe, 33623391)
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

    void "post planet facility buildings"() {
        when: "post planet facility buildings request has been made"
        request.json = [
            serverGroup: "en",
            universe: "136",
            playerId: "100153",
            playerName: "skiwi",
            planetId: "33623391",
            planetName: "Homeworld",
            planetGalaxy: "1",
            planetSolarSystem: "204",
            planetPosition: "8",
            buildings: [
                [id: "14", level: "7"],
                [id: "21", level: "8"],
                [id: "31", level: "7"],
                [id: "34", level: "0"],
                [id: "44", level: "2"],
                [id: "15", level: "0"],
                [id: "33", level: "0"]
            ]
        ] as JSON
        request.method = "POST"
        request.contentType = "text/json"
        controller.planetFacilityBuildings()

        then: "facility buildings should be persisted"
        response.json.result.success == true
        def universe = universeService.getUniverse("en", 136)
        def planet = planetService.findPlanet(universe, 33623391)
        planet.buildings.roboticsFactory == 7
        planet.buildings.shipyard == 8
        planet.buildings.researchLab == 7
        planet.buildings.allianceDepot == 0
        planet.buildings.missileSilo == 2
        planet.buildings.naniteFactory == 0
        planet.buildings.fusionReactor == 0
    }

    void "post planet defences"() {
        when: "post planet defences request has been made"
        request.json = [
            serverGroup: "en",
            universe: "136",
            playerId: "100153",
            playerName: "skiwi",
            planetId: "33623391",
            planetName: "Homeworld",
            planetGalaxy: "1",
            planetSolarSystem: "204",
            planetPosition: "8",
            defences: [
                [id: "401", amount: "69"],
                [id: "402", amount: "158"],
                [id: "403", amount: "12"],
                [id: "404", amount: "14"],
                [id: "405", amount: "0"],
                [id: "406", amount: "1"],
                [id: "407", amount: "1"],
                [id: "408", amount: "1"],
                [id: "502", amount: "20"],
                [id: "503", amount: "0"]
            ]
        ] as JSON
        request.method = "POST"
        request.contentType = "text/json"
        controller.planetDefences()

        then: "defences should be persisted"
        response.json.result.success == true
        def universe = universeService.getUniverse("en", 136)
        def planet = planetService.findPlanet(universe, 33623391)
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

    void "post planet fleet"() {
        when: "post planet fleet request has been made"
        request.json = [
            serverGroup: "en",
            universe: "136",
            playerId: "100153",
            playerName: "skiwi",
            planetId: "33623391",
            planetName: "Homeworld",
            planetGalaxy: "1",
            planetSolarSystem: "204",
            planetPosition: "8",
            fleet: [
                [id: "204", amount: "0"],
                [id: "205", amount: "0"],
                [id: "206", amount: "106"],
                [id: "207", amount: "20"],
                [id: "202", amount: "63"],
                [id: "203", amount: "25"],
                [id: "208", amount: "0"],
                [id: "215", amount: "0"],
                [id: "211", amount: "5"],
                [id: "213", amount: "0"],
                [id: "214", amount: "0"],
                [id: "209", amount: "35"],
                [id: "210", amount: "12"]
            ]
        ] as JSON
        request.method = "POST"
        request.contentType = "text/json"
        controller.planetFleet()

        then: "fleet should be persisted"
        response.json.result.success == true
        def universe = universeService.getUniverse("en", 136)
        def planet = planetService.findPlanet(universe, 33623391)
        planet.fleet.lightFighter == 0
        planet.fleet.heavyFighter == 0
        planet.fleet.cruiser == 106
        planet.fleet.battleship == 20
        planet.fleet.smallCargo == 63
        planet.fleet.largeCargo == 25
        planet.fleet.colonyShip == 0
        planet.fleet.battlecruiser == 0
        planet.fleet.bomber == 5
        planet.fleet.destroyer == 0
        planet.fleet.deathstar == 0
        planet.fleet.recycler == 35
        planet.fleet.espionageProbe == 12
    }

    void "post planet shipyard"() {
        when: "post planet shipyard request has been made"
        request.json = [
            serverGroup: "en",
            universe: "136",
            playerId: "100153",
            playerName: "skiwi",
            planetId: "33623391",
            planetName: "Homeworld",
            planetGalaxy: "1",
            planetSolarSystem: "204",
            planetPosition: "8",
            shipyard: [
                [id: "204", amount: "0"],
                [id: "205", amount: "0"],
                [id: "206", amount: "106"],
                [id: "207", amount: "20"],
                [id: "202", amount: "63"],
                [id: "203", amount: "25"],
                [id: "208", amount: "0"],
                [id: "215", amount: "0"],
                [id: "211", amount: "5"],
                [id: "213", amount: "0"],
                [id: "214", amount: "0"],
                [id: "209", amount: "35"],
                [id: "210", amount: "12"],
                [id: "212", amount: "5"]
            ]
        ] as JSON
        request.method = "POST"
        request.contentType = "text/json"
        controller.planetShipyard()

        then: "shipyard should be persisted"
        response.json.result.success == true
        def universe = universeService.getUniverse("en", 136)
        def planet = planetService.findPlanet(universe, 33623391)
        planet.fleet.lightFighter == 0
        planet.fleet.heavyFighter == 0
        planet.fleet.cruiser == 106
        planet.fleet.battleship == 20
        planet.fleet.smallCargo == 63
        planet.fleet.largeCargo == 25
        planet.fleet.colonyShip == 0
        planet.fleet.battlecruiser == 0
        planet.fleet.bomber == 5
        planet.fleet.destroyer == 0
        planet.fleet.deathstar == 0
        planet.fleet.recycler == 35
        planet.fleet.espionageProbe == 12
        planet.buildings.solarSatellite == 5
    }

    void "post moon resource buildings"() {
        when: "post moon resource buildings request has been made"
        request.json = [
            serverGroup: "en",
            universe: "136",
            playerId: "100153",
            playerName: "skiwi",
            moonId: "33623391",
            moonName: "Moon",
            moonGalaxy: "1",
            moonSolarSystem: "204",
            moonPosition: "8",
            buildings: [
                [id: "212", level: "0"],
                [id: "22", level: "6"],
                [id: "23", level: "4"],
                [id: "24", level: "4"]
            ]
        ] as JSON
        request.method = "POST"
        request.contentType = "text/json"
        controller.moonResourceBuildings()

        then: "resource buildings should be persisted"
        response.json.result.success == true
        def universe = universeService.getUniverse("en", 136)
        def moon = moonService.findMoon(universe, 33623391)
        moon.buildings.solarSatellite == 0
        moon.buildings.metalStorage == 6
        moon.buildings.crystalStorage == 4
        moon.buildings.deuteriumTank == 4
    }

    void "post moon facility buildings"() {
        when: "post moon facility buildings request has been made"
        request.json = [
            serverGroup: "en",
            universe: "136",
            playerId: "100153",
            playerName: "skiwi",
            moonId: "33623391",
            moonName: "Moon",
            moonGalaxy: "1",
            moonSolarSystem: "204",
            moonPosition: "8",
            buildings: [
                [id: "14", level: "7"],
                [id: "21", level: "8"],
                [id: "41", level: "1"],
                [id: "42", level: "2"],
                [id: "43", level: "3"]
            ]
        ] as JSON
        request.method = "POST"
        request.contentType = "text/json"
        controller.moonFacilityBuildings()

        then: "facility buildings should be persisted"
        response.json.result.success == true
        def universe = universeService.getUniverse("en", 136)
        def moon = moonService.findMoon(universe, 33623391)
        moon.buildings.roboticsFactory == 7
        moon.buildings.shipyard == 8
        moon.buildings.lunarBase == 1
        moon.buildings.sensorPhalanx == 2
        moon.buildings.jumpGate == 3
    }

    void "post moon defences"() {
        when: "post moon defences request has been made"
        request.json = [
            serverGroup: "en",
            universe: "136",
            playerId: "100153",
            playerName: "skiwi",
            moonId: "33623391",
            moonName: "Moon",
            moonGalaxy: "1",
            moonSolarSystem: "204",
            moonPosition: "8",
            defences: [
                [id: "401", amount: "69"],
                [id: "402", amount: "158"],
                [id: "403", amount: "12"],
                [id: "404", amount: "14"],
                [id: "405", amount: "0"],
                [id: "406", amount: "1"],
                [id: "407", amount: "1"],
                [id: "408", amount: "1"],
                [id: "502", amount: "20"],
                [id: "503", amount: "0"]
            ]
        ] as JSON
        request.method = "POST"
        request.contentType = "text/json"
        controller.moonDefences()

        then: "defences should be persisted"
        response.json.result.success == true
        def universe = universeService.getUniverse("en", 136)
        def moon = moonService.findMoon(universe, 33623391)
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

    void "post moon fleet"() {
        when: "post moon fleet request has been made"
        request.json = [
            serverGroup: "en",
            universe: "136",
            playerId: "100153",
            playerName: "skiwi",
            moonId: "33623391",
            moonName: "Moon",
            moonGalaxy: "1",
            moonSolarSystem: "204",
            moonPosition: "8",
            fleet: [
                [id: "204", amount: "0"],
                [id: "205", amount: "0"],
                [id: "206", amount: "106"],
                [id: "207", amount: "20"],
                [id: "202", amount: "63"],
                [id: "203", amount: "25"],
                [id: "208", amount: "0"],
                [id: "215", amount: "0"],
                [id: "211", amount: "5"],
                [id: "213", amount: "0"],
                [id: "214", amount: "0"],
                [id: "209", amount: "35"],
                [id: "210", amount: "12"]
            ]
        ] as JSON
        request.method = "POST"
        request.contentType = "text/json"
        controller.moonFleet()

        then: "fleet should be persisted"
        response.json.result.success == true
        def universe = universeService.getUniverse("en", 136)
        def moon = moonService.findMoon(universe, 33623391)
        moon.fleet.lightFighter == 0
        moon.fleet.heavyFighter == 0
        moon.fleet.cruiser == 106
        moon.fleet.battleship == 20
        moon.fleet.smallCargo == 63
        moon.fleet.largeCargo == 25
        moon.fleet.colonyShip == 0
        moon.fleet.battlecruiser == 0
        moon.fleet.bomber == 5
        moon.fleet.destroyer == 0
        moon.fleet.deathstar == 0
        moon.fleet.recycler == 35
        moon.fleet.espionageProbe == 12
    }

    void "post moon shipyard"() {
        when: "post moon shipyard request has been made"
        request.json = [
            serverGroup: "en",
            universe: "136",
            playerId: "100153",
            playerName: "skiwi",
            moonId: "33623391",
            moonName: "Moon",
            moonGalaxy: "1",
            moonSolarSystem: "204",
            moonPosition: "8",
            shipyard: [
                [id: "204", amount: "0"],
                [id: "205", amount: "0"],
                [id: "206", amount: "106"],
                [id: "207", amount: "20"],
                [id: "202", amount: "63"],
                [id: "203", amount: "25"],
                [id: "208", amount: "0"],
                [id: "215", amount: "0"],
                [id: "211", amount: "5"],
                [id: "213", amount: "0"],
                [id: "214", amount: "0"],
                [id: "209", amount: "35"],
                [id: "210", amount: "12"],
                [id: "212", amount: "5"]
            ]
        ] as JSON
        request.method = "POST"
        request.contentType = "text/json"
        controller.moonShipyard()

        then: "shipyard should be persisted"
        response.json.result.success == true
        def universe = universeService.getUniverse("en", 136)
        def moon = moonService.findMoon(universe, 33623391)
        moon.fleet.lightFighter == 0
        moon.fleet.heavyFighter == 0
        moon.fleet.cruiser == 106
        moon.fleet.battleship == 20
        moon.fleet.smallCargo == 63
        moon.fleet.largeCargo == 25
        moon.fleet.colonyShip == 0
        moon.fleet.battlecruiser == 0
        moon.fleet.bomber == 5
        moon.fleet.destroyer == 0
        moon.fleet.deathstar == 0
        moon.fleet.recycler == 35
        moon.fleet.espionageProbe == 12
        moon.buildings.solarSatellite == 5
    }

    void planetMatches(Player player, Integer planetId, String name, int galaxy, int solarSystem, int position) {
        def planet = Planet.findByPlayerAndPlanetId(player, planetId)
        def now = Instant.now()
        assert planet.getNameAt(now.plus(1, ChronoUnit.MINUTES)) == name
        assert planet.getCoordinateAt(now.plus(1, ChronoUnit.MINUTES)).galaxy == galaxy
        assert planet.getCoordinateAt(now.plus(1, ChronoUnit.MINUTES)).solarSystem == solarSystem
        assert planet.getCoordinateAt(now.plus(1, ChronoUnit.MINUTES)).position == position
    }

    void moonMatches(Player player, Integer moonId, String name, int galaxy, int solarSystem, int position) {
        def moon = Moon.findByPlayerAndMoonId(player, moonId)
        def now = Instant.now()
        assert moon.getNameAt(now.plus(1, ChronoUnit.MINUTES)) == name
        assert moon.getCoordinateAt(now.plus(1, ChronoUnit.MINUTES)).galaxy == galaxy
        assert moon.getCoordinateAt(now.plus(1, ChronoUnit.MINUTES)).solarSystem == solarSystem
        assert moon.getCoordinateAt(now.plus(1, ChronoUnit.MINUTES)).position == position
    }
}
