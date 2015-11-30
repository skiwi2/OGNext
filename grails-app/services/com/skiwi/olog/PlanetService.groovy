package com.skiwi.olog

import grails.transaction.Transactional

import java.time.Instant
import java.time.temporal.ChronoUnit

@Transactional
class PlanetService {
    def coordinateService

    Planet findPlanet(Universe universe, Integer planetId) {
        Planet.findByUniverseAndPlanetId(universe, planetId)
    }

    Planet createPlanet(Player player, Integer planetId, int galaxy, int solarSystem, int position, String name) {
        def coordinate = coordinateService.getOrCreateCoordinate(player.universe, galaxy, solarSystem, position)
        def planet = new Planet(player: player, planetId: planetId)
        planet.addToLocations(new PlanetLocation(coordinate: coordinate, begin: Instant.ofEpochMilli(0), end: Instant.now().plus(50000, ChronoUnit.DAYS)))
        planet.addToAliases(new PlanetAlias(name: name, begin: Instant.ofEpochMilli(0), end: Instant.now().plus(50000, ChronoUnit.DAYS)))
        planet.save()
    }

    void storePlanetLocation(Planet planet, int galaxy, int solarSystem, int position, Instant instant) {
        int index = planet.locations.findIndexOf { it.inInterval(instant) }
        def planetLocation = planet.locations[index]
        def coordinate = coordinateService.getOrCreateCoordinate(planet.universe, galaxy, solarSystem, position)
        if (planetLocation.coordinate != coordinate) {
            def oldEnd = planetLocation.end
            planetLocation.end = instant
            def newPlanetLocation = new PlanetLocation(coordinate: coordinate, begin: instant, end: oldEnd)
            planet.addToLocations(newPlanetLocation)
            planet.save()
        }
    }

    void storePlanetName(Planet planet, String name, Instant instant) {
        int index = planet.aliases.findIndexOf { it.inInterval(instant) }
        def planetAlias = planet.aliases[index]
        if (planetAlias.name != name) {
            def oldEnd = planetAlias.end
            planetAlias.end = instant
            def newPlanetAlias = new PlanetAlias(name: name, begin: instant, end: oldEnd)
            planet.addToAliases(newPlanetAlias)
            planet.save()
        }
    }

    PlanetAlias getPlanetAlias(Planet planet, Instant instant) {
        planet.aliases.find { it.inInterval(instant) }
    }

    PlanetLocation getPlanetLocation(Planet planet, Instant instant) {
        planet.locations.find { it.inInterval(instant) }
    }
}
