package com.ognext.api

import java.time.Instant

class UserscriptController {
    def universeService
    def playerService
    def reportKeyService
    def planetService
    def researchesService

    static allowedMethods = [
        keys: "POST",
        planets: "POST",
        researches: "POST"
    ]

    def keys() {
        def json = request.JSON

        def serverGroupCountryCode = json.serverGroup
        def universeId = json.universe.toInteger()
        def universe = universeService.getUniverse(serverGroupCountryCode, universeId)

        def playerId = json.playerId.toInteger()
        def playerName = json.playerName
        def player = playerService.findPlayer(universe, playerId) ?: playerService.createPlayer(universe, playerId, playerName)

        json.reportKeys.sr.each { reportKeyService.addSpyReport(player, it) }
        json.reportKeys.cr.each { reportKeyService.addCombatReport(player, it) }
        json.reportKeys.rr.each { reportKeyService.addRecycleReport(player, it) }
        json.reportKeys.mr.each { reportKeyService.addMissileReport(player, it) }

        render(contentType: "application/json") {
            result(success: true)
        }
    }

    def planets() {
        def json = request.JSON

        def serverGroupCountryCode = json.serverGroup
        def universeId = json.universe.toInteger()
        def universe = universeService.getUniverse(serverGroupCountryCode, universeId)

        def playerId = json.playerId.toInteger()
        def playerName = json.playerName
        def player = playerService.findPlayer(universe, playerId) ?: playerService.createPlayer(universe, playerId, playerName)

        def sentPlanets = []
        json.planets.each { planetJson ->
            def planetId = planetJson.id.toInteger()
            def name = planetJson.name
            def galaxy = planetJson.galaxy.toInteger()
            def solarSystem = planetJson.solarSystem.toInteger()
            def position = planetJson.position.toInteger()

            def planet =  planetService.findPlanet(universe, planetId)
            if (planet) {
                planetService.storePlanetLocation(planet, galaxy, solarSystem, position, Instant.now())
                planetService.storePlanetName(planet, name, Instant.now())
                sentPlanets << planet
            }
            else {
                sentPlanets << planetService.createPlanet(player, planetId, galaxy, solarSystem, position, name)
            }
        }

        def deletedPlanets = planetService.findCurrentPlanetsOfPlayer(player).toSet() - sentPlanets
        deletedPlanets.each { planetService.deletePlanet(it) }

        render(contentType: "application/json") {
            result(success: true)
        }
    }

    def researches() {
        def json = request.JSON

        def serverGroupCountryCode = json.serverGroup
        def universeId = json.universe.toInteger()
        def universe = universeService.getUniverse(serverGroupCountryCode, universeId)

        def playerId = json.playerId.toInteger()
        def playerName = json.playerName
        def player = playerService.findPlayer(universe, playerId) ?: playerService.createPlayer(universe, playerId, playerName)

        def researchMap = json.researches.collectEntries { [it.id.toInteger(), it.level.toInteger()] }
        def researchLevels = [113, 120, 121, 114, 122, 115, 117, 118, 106, 108, 124, 123, 199, 109, 110, 111].collect { researchMap[it] }
        researchesService.updatePlayerResearches(player, *researchLevels)

        render(contentType: "application/json") {
            result(success: true)
        }
    }
}