package com.ognext.api

import com.ognext.CombatReport
import com.ognext.Coordinate
import com.ognext.CoordinateService
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
@Mock([Player, PlayerAlias, SpyReport, CombatReport, RecycleReport, MissileReport, UniverseService, ServerGroup, Universe, ServerGroupService, ReportKeyService, Planet, PlanetService, CoordinateService, Coordinate, PlanetLocation, PlanetAlias, Researches, ResearchesService])
@TestMixin(GrailsUnitTestMixin)
class UserscriptControllerSpec extends Specification {
    UniverseService universeService
    PlayerService playerService
    PlanetService planetService

    static doWithSpring = {
        researchesService(ResearchesService)
        playerService(PlayerService)
        universeService(UniverseService)
        planetService(PlanetService)
        coordinateService(CoordinateService)
    }

    def setup() {
        playerService = grailsApplication.mainContext.getBean("playerService")
        def researchesService = grailsApplication.mainContext.getBean("researchesService")
        playerService.researchesService = researchesService
        universeService = grailsApplication.mainContext.getBean("universeService")
        planetService = grailsApplication.mainContext.getBean("planetService")
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
                [id: "111", level: "8"],
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

    void planetMatches(Player player, Integer planetId, String name, int galaxy, int solarSystem, int position) {
        def planet = Planet.findByPlayerAndPlanetId(player, planetId)
        def now = Instant.now()
        assert planet.getNameAt(now.plus(1, ChronoUnit.MINUTES)) == name
        assert planet.getCoordinateAt(now.plus(1, ChronoUnit.MINUTES)).galaxy == galaxy
        assert planet.getCoordinateAt(now.plus(1, ChronoUnit.MINUTES)).solarSystem == solarSystem
        assert planet.getCoordinateAt(now.plus(1, ChronoUnit.MINUTES)).position == position
    }
}
