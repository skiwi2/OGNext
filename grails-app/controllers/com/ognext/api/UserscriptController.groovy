package com.ognext.api

import java.time.Instant

class UserscriptController {
    def universeService
    def playerService
    def reportKeyService
    def planetService
    def moonService
    def researchesService
    def planetBuildingsService
    def moonBuildingsService
    def defencesService
    def fleetService

    static allowedMethods = [
        keys: "POST",
        planets: "POST",
        moons: "POST",
        researches: "POST",
        planetResourceBuildings: "POST",
        planetFacilityBuildings: "POST",
        planetDefences: "POST",
        planetFleet: "POST",
        planetShipyard: "POST",
        moonResourceBuildings: "POST",
        moonFacilityBuildings: "POST",
        moonDefences: "POST",
        moonFleet: "POST",
        moonShipyard: "POST"
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

    def moons() {
        def json = request.JSON

        def serverGroupCountryCode = json.serverGroup
        def universeId = json.universe.toInteger()
        def universe = universeService.getUniverse(serverGroupCountryCode, universeId)

        def playerId = json.playerId.toInteger()
        def playerName = json.playerName
        def player = playerService.findPlayer(universe, playerId) ?: playerService.createPlayer(universe, playerId, playerName)

        def sentMoons = []
        json.moons.each { moonJson ->
            def moonId = moonJson.id.toInteger()
            def name = moonJson.name
            def galaxy = moonJson.galaxy.toInteger()
            def solarSystem = moonJson.solarSystem.toInteger()
            def position = moonJson.position.toInteger()

            def moon =  moonService.findMoon(universe, moonId)
            if (moon) {
                moonService.storeMoonLocation(moon, galaxy, solarSystem, position, Instant.now())
                moonService.storeMoonName(moon, name, Instant.now())
                sentMoons << moon
            }
            else {
                sentMoons << moonService.createMoon(player, moonId, galaxy, solarSystem, position, name)
            }
        }

        def deletedMoons = moonService.findCurrentMoonsOfPlayer(player).toSet() - sentMoons
        deletedMoons.each { moonService.deleteMoon(it) }

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

    def planetResourceBuildings() {
        def json = request.JSON

        def serverGroupCountryCode = json.serverGroup
        def universeId = json.universe.toInteger()
        def universe = universeService.getUniverse(serverGroupCountryCode, universeId)

        def playerId = json.playerId.toInteger()
        def playerName = json.playerName
        def player = playerService.findPlayer(universe, playerId) ?: playerService.createPlayer(universe, playerId, playerName)

        def planetId = json.planetId.toInteger()
        def planetName = json.planetName
        def planetGalaxy = json.planetGalaxy.toInteger()
        def planetSolarSystem = json.planetSolarSystem.toInteger()
        def planetPosition = json.planetPosition.toInteger()
        def planet = planetService.findPlanet(universe, planetId) ?: planetService.createPlanet(player, planetId, planetGalaxy, planetSolarSystem, planetPosition, planetName)

        def buildingMap = json.buildings.collectEntries { [it.id.toInteger(), it.level.toInteger()] }
        def buildingLevels = [1, 2, 3, 4, 12, 212, 22, 23, 24].collect { buildingMap[it] }
        planetBuildingsService.updatePlanetResourceBuildings(planet, *buildingLevels)

        render(contentType: "application/json") {
            result(success: true)
        }
    }

    def planetFacilityBuildings() {
        def json = request.JSON

        def serverGroupCountryCode = json.serverGroup
        def universeId = json.universe.toInteger()
        def universe = universeService.getUniverse(serverGroupCountryCode, universeId)

        def playerId = json.playerId.toInteger()
        def playerName = json.playerName
        def player = playerService.findPlayer(universe, playerId) ?: playerService.createPlayer(universe, playerId, playerName)

        def planetId = json.planetId.toInteger()
        def planetName = json.planetName
        def planetGalaxy = json.planetGalaxy.toInteger()
        def planetSolarSystem = json.planetSolarSystem.toInteger()
        def planetPosition = json.planetPosition.toInteger()
        def planet = planetService.findPlanet(universe, planetId) ?: planetService.createPlanet(player, planetId, planetGalaxy, planetSolarSystem, planetPosition, planetName)

        def buildingMap = json.buildings.collectEntries { [it.id.toInteger(), it.level.toInteger()] }
        def buildingLevels = [14, 21, 31, 34, 44, 15, 33].collect { buildingMap[it] }
        planetBuildingsService.updatePlanetFacilityBuildings(planet, *buildingLevels)

        render(contentType: "application/json") {
            result(success: true)
        }
    }

    def planetDefences() {
        def json = request.JSON

        def serverGroupCountryCode = json.serverGroup
        def universeId = json.universe.toInteger()
        def universe = universeService.getUniverse(serverGroupCountryCode, universeId)

        def playerId = json.playerId.toInteger()
        def playerName = json.playerName
        def player = playerService.findPlayer(universe, playerId) ?: playerService.createPlayer(universe, playerId, playerName)

        def planetId = json.planetId.toInteger()
        def planetName = json.planetName
        def planetGalaxy = json.planetGalaxy.toInteger()
        def planetSolarSystem = json.planetSolarSystem.toInteger()
        def planetPosition = json.planetPosition.toInteger()
        def planet = planetService.findPlanet(universe, planetId) ?: planetService.createPlanet(player, planetId, planetGalaxy, planetSolarSystem, planetPosition, planetName)

        def defencesMap = json.defences.collectEntries { [it.id.toInteger(), it.amount.toInteger()] }
        def defencesNumbers = [401, 402, 403, 404, 405, 406, 407, 408, 502, 503].collect { defencesMap[it] }
        defencesService.updatePlanetDefences(planet, *defencesNumbers)

        render(contentType: "application/json") {
            result(success: true)
        }
    }

    def planetFleet() {
        def json = request.JSON

        def serverGroupCountryCode = json.serverGroup
        def universeId = json.universe.toInteger()
        def universe = universeService.getUniverse(serverGroupCountryCode, universeId)

        def playerId = json.playerId.toInteger()
        def playerName = json.playerName
        def player = playerService.findPlayer(universe, playerId) ?: playerService.createPlayer(universe, playerId, playerName)

        def planetId = json.planetId.toInteger()
        def planetName = json.planetName
        def planetGalaxy = json.planetGalaxy.toInteger()
        def planetSolarSystem = json.planetSolarSystem.toInteger()
        def planetPosition = json.planetPosition.toInteger()
        def planet = planetService.findPlanet(universe, planetId) ?: planetService.createPlanet(player, planetId, planetGalaxy, planetSolarSystem, planetPosition, planetName)

        def fleetMap = json.fleet.collectEntries { [it.id.toInteger(), it.amount.toInteger()] }
        def fleetNumbers = [204, 205, 206, 207, 202, 203, 208, 215, 211, 213, 214, 209, 210].collect { fleetMap[it] }
        fleetService.updatePlanetFleet(planet, *fleetNumbers)

        render(contentType: "application/json") {
            result(success: true)
        }
    }

    def planetShipyard() {
        def json = request.JSON

        def serverGroupCountryCode = json.serverGroup
        def universeId = json.universe.toInteger()
        def universe = universeService.getUniverse(serverGroupCountryCode, universeId)

        def playerId = json.playerId.toInteger()
        def playerName = json.playerName
        def player = playerService.findPlayer(universe, playerId) ?: playerService.createPlayer(universe, playerId, playerName)

        def planetId = json.planetId.toInteger()
        def planetName = json.planetName
        def planetGalaxy = json.planetGalaxy.toInteger()
        def planetSolarSystem = json.planetSolarSystem.toInteger()
        def planetPosition = json.planetPosition.toInteger()
        def planet = planetService.findPlanet(universe, planetId) ?: planetService.createPlanet(player, planetId, planetGalaxy, planetSolarSystem, planetPosition, planetName)

        def shipyardMap = json.shipyard.collectEntries { [it.id.toInteger(), it.amount.toInteger()] }
        def shipyardNumbers = [204, 205, 206, 207, 202, 203, 208, 215, 211, 213, 214, 209, 210].collect { shipyardMap[it] }
        def solarSatellites = shipyardMap[212]
        fleetService.updatePlanetFleet(planet, *shipyardNumbers)
        planetBuildingsService.updatePlanetSolarSatellite(planet, solarSatellites)

        render(contentType: "application/json") {
            result(success: true)
        }
    }

    def moonResourceBuildings() {
        def json = request.JSON

        def serverGroupCountryCode = json.serverGroup
        def universeId = json.universe.toInteger()
        def universe = universeService.getUniverse(serverGroupCountryCode, universeId)

        def playerId = json.playerId.toInteger()
        def playerName = json.playerName
        def player = playerService.findPlayer(universe, playerId) ?: playerService.createPlayer(universe, playerId, playerName)

        def moonId = json.moonId.toInteger()
        def moonName = json.moonName
        def moonGalaxy = json.moonGalaxy.toInteger()
        def moonSolarSystem = json.moonSolarSystem.toInteger()
        def moonPosition = json.moonPosition.toInteger()
        def moon = moonService.findMoon(universe, moonId) ?: moonService.createMoon(player, moonId, moonGalaxy, moonSolarSystem, moonPosition, moonName)

        def buildingMap = json.buildings.collectEntries { [it.id.toInteger(), it.level.toInteger()] }
        def buildingLevels = [212, 22, 23, 24].collect { buildingMap[it] }
        moonBuildingsService.updateMoonResourceBuildings(moon, *buildingLevels)

        render(contentType: "application/json") {
            result(success: true)
        }
    }

    def moonFacilityBuildings() {
        def json = request.JSON

        def serverGroupCountryCode = json.serverGroup
        def universeId = json.universe.toInteger()
        def universe = universeService.getUniverse(serverGroupCountryCode, universeId)

        def playerId = json.playerId.toInteger()
        def playerName = json.playerName
        def player = playerService.findPlayer(universe, playerId) ?: playerService.createPlayer(universe, playerId, playerName)

        def moonId = json.moonId.toInteger()
        def moonName = json.moonName
        def moonGalaxy = json.moonGalaxy.toInteger()
        def moonSolarSystem = json.moonSolarSystem.toInteger()
        def moonPosition = json.moonPosition.toInteger()
        def moon = moonService.findMoon(universe, moonId) ?: moonService.createMoon(player, moonId, moonGalaxy, moonSolarSystem, moonPosition, moonName)

        def buildingMap = json.buildings.collectEntries { [it.id.toInteger(), it.level.toInteger()] }
        def buildingLevels = [14, 21, 41, 42, 43].collect { buildingMap[it] }
        moonBuildingsService.updateMoonFacilityBuildings(moon, *buildingLevels)

        render(contentType: "application/json") {
            result(success: true)
        }
    }

    def moonDefences() {
        def json = request.JSON

        def serverGroupCountryCode = json.serverGroup
        def universeId = json.universe.toInteger()
        def universe = universeService.getUniverse(serverGroupCountryCode, universeId)

        def playerId = json.playerId.toInteger()
        def playerName = json.playerName
        def player = playerService.findPlayer(universe, playerId) ?: playerService.createPlayer(universe, playerId, playerName)

        def moonId = json.moonId.toInteger()
        def moonName = json.moonName
        def moonGalaxy = json.moonGalaxy.toInteger()
        def moonSolarSystem = json.moonSolarSystem.toInteger()
        def moonPosition = json.moonPosition.toInteger()
        def moon = moonService.findMoon(universe, moonId) ?: moonService.createMoon(player, moonId, moonGalaxy, moonSolarSystem, moonPosition, moonName)

        def defencesMap = json.defences.collectEntries { [it.id.toInteger(), it.amount.toInteger()] }
        def defencesNumbers = [401, 402, 403, 404, 405, 406, 407, 408, 502, 503].collect { defencesMap[it] }
        defencesService.updateMoonDefences(moon, *defencesNumbers)

        render(contentType: "application/json") {
            result(success: true)
        }
    }

    def moonFleet() {
        def json = request.JSON

        def serverGroupCountryCode = json.serverGroup
        def universeId = json.universe.toInteger()
        def universe = universeService.getUniverse(serverGroupCountryCode, universeId)

        def playerId = json.playerId.toInteger()
        def playerName = json.playerName
        def player = playerService.findPlayer(universe, playerId) ?: playerService.createPlayer(universe, playerId, playerName)

        def moonId = json.moonId.toInteger()
        def moonName = json.moonName
        def moonGalaxy = json.moonGalaxy.toInteger()
        def moonSolarSystem = json.moonSolarSystem.toInteger()
        def moonPosition = json.moonPosition.toInteger()
        def moon = moonService.findMoon(universe, moonId) ?: moonService.createMoon(player, moonId, moonGalaxy, moonSolarSystem, moonPosition, moonName)

        def fleetMap = json.fleet.collectEntries { [it.id.toInteger(), it.amount.toInteger()] }
        def fleetNumbers = [204, 205, 206, 207, 202, 203, 208, 215, 211, 213, 214, 209, 210].collect { fleetMap[it] }
        fleetService.updateMoonFleet(moon, *fleetNumbers)

        render(contentType: "application/json") {
            result(success: true)
        }
    }

    def moonShipyard() {
        def json = request.JSON

        def serverGroupCountryCode = json.serverGroup
        def universeId = json.universe.toInteger()
        def universe = universeService.getUniverse(serverGroupCountryCode, universeId)

        def playerId = json.playerId.toInteger()
        def playerName = json.playerName
        def player = playerService.findPlayer(universe, playerId) ?: playerService.createPlayer(universe, playerId, playerName)

        def moonId = json.moonId.toInteger()
        def moonName = json.moonName
        def moonGalaxy = json.moonGalaxy.toInteger()
        def moonSolarSystem = json.moonSolarSystem.toInteger()
        def moonPosition = json.moonPosition.toInteger()
        def moon = moonService.findMoon(universe, moonId) ?: moonService.createMoon(player, moonId, moonGalaxy, moonSolarSystem, moonPosition, moonName)

        def shipyardMap = json.shipyard.collectEntries { [it.id.toInteger(), it.amount.toInteger()] }
        def shipyardNumbers = [204, 205, 206, 207, 202, 203, 208, 215, 211, 213, 214, 209, 210].collect { shipyardMap[it] }
        def solarSatellites = shipyardMap[212]
        fleetService.updateMoonFleet(moon, *shipyardNumbers)
        moonBuildingsService.updateMoonSolarSatellite(moon, solarSatellites)

        render(contentType: "application/json") {
            result(success: true)
        }
    }
}