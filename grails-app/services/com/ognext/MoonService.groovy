package com.ognext

import grails.transaction.Transactional

import java.time.Instant
import java.time.temporal.ChronoUnit

@Transactional
class MoonService {
    def moonBuildingsService
    def defencesService
    def fleetService
    def coordinateService

    Moon findMoon(Universe universe, Integer moonId) {
        Moon.findByUniverseAndMoonId(universe, moonId)
    }

    Moon createMoon(Player player, Integer moonId, int galaxy, int solarSystem, int position, String name) {
        def moonBuildings = moonBuildingsService.createDefaultMoonBuildings()
        def defences = defencesService.createDefaultDefences()
        def fleet = fleetService.createDefaultFleet()
        def coordinate = coordinateService.getCoordinate(player.universe, galaxy, solarSystem, position)
        def moon = new Moon(player: player, moonId: moonId, buildings: moonBuildings, defences: defences, fleet: fleet)
        moon.addToLocations(new MoonLocation(coordinate: coordinate, begin: Instant.ofEpochMilli(0), end: Instant.now().plus(50000, ChronoUnit.DAYS)))
        moon.addToAliases(new MoonAlias(name: name, begin: Instant.ofEpochMilli(0), end: Instant.now().plus(50000, ChronoUnit.DAYS)))
        moon.save()
    }

    void storeMoonLocation(Moon moon, int galaxy, int solarSystem, int position, Instant instant) {
        int index = moon.locations.findIndexOf { it.inInterval(instant) }
        def moonLocation = moon.locations[index]
        def coordinate = coordinateService.getCoordinate(moon.universe, galaxy, solarSystem, position)
        if (moonLocation.coordinate != coordinate) {
            def oldEnd = moonLocation.end
            moonLocation.end = instant
            def newMoonLocation = new MoonLocation(coordinate: coordinate, begin: instant, end: oldEnd)
            moon.addToLocations(newMoonLocation)
            moon.save()
        }
    }

    void storeMoonName(Moon moon, String name, Instant instant) {
        int index = moon.aliases.findIndexOf { it.inInterval(instant) }
        def moonAlias = moon.aliases[index]
        if (moonAlias.name != name) {
            def oldEnd = moonAlias.end
            moonAlias.end = instant
            def newMoonAlias = new MoonAlias(name: name, begin: instant, end: oldEnd)
            moon.addToAliases(newMoonAlias)
            moon.save()
        }
    }

    void deleteMoon(Moon moon) {
        if (moon.deleted) {
            return
        }

        def now = Instant.now()

        int locationIndex = moon.locations.findIndexOf { it.inInterval(now) }
        moon.locations[locationIndex].end = now

        int aliasIndex = moon.aliases.findIndexOf { it.inInterval(now) }
        moon.aliases[aliasIndex].end = now

        moon.deleted = true
        moon.dateDeleted = Date.from(now)
        moon.save()
    }

    MoonAlias getMoonAlias(Moon moon, Instant instant) {
        moon.aliases.find { it.inInterval(instant) }
    }

    MoonLocation getMoonLocation(Moon moon, Instant instant) {
        moon.locations.find { it.inInterval(instant) }
    }

    List<Moon> findCurrentMoonsOfPlayer(Player player) {
        Moon.findAllByPlayerAndDeleted(player, false)
    }

    List<Moon> findMoonsOfPlayer(Player player, Instant instant) {
        Moon.createCriteria().list {
            eq "player", player
            or {
                eq "deleted", false
                and {
                    eq "deleted", true
                    gt "dateDeleted", Date.from(instant)
                }
            }
        }
    }
}
