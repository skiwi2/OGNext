package com.ognext

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.validation.ValidationException
import spock.lang.Specification

import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(MoonService)
@Mock([Moon, ServerGroup, Universe, Player, Coordinate, CoordinateService, MoonLocation, MoonAlias, ServerGroup, ServerGroupService, PlayerAlias, Researches, ResearchesService, MoonBuildings, MoonBuildingsService, Defences, DefencesService, Fleet, FleetService])
class MoonServiceSpec extends Specification {
    Universe universe
    Universe universe2
    Player player
    Player player2
    def moonId = 1000
    def galaxy = 2
    def solarSystem = 122
    def position = 12
    def moonName = "Moon"
    Instant now

    CoordinateService coordinateService

    def setup() {
        def universeService = mockService(UniverseService)
        def playerService = mockService(PlayerService)
        universe = universeService.getUniverse("en", 135)
        universe2 = universeService.getUniverse("en", 136)
        player = playerService.createPlayer(universe, 103168, "skiwi")
        player2 = playerService.createPlayer(universe2, 103168, "skiwi2")
        now = Instant.now()
        coordinateService = mockService(CoordinateService)
    }

    def cleanup() {
    }

    void "test find moon"() {
        when: "moon does not exist"
        def nonExistingMoon = service.findMoon(universe, moonId)

        then: "null should be returned"
        !nonExistingMoon

        when: "moon gets created"
        service.createMoon(player, moonId, galaxy, solarSystem, position, moonName)
        def moon = service.findMoon(universe, moonId)

        then: "moon should be returned"
        moon
        moon.player == player
        moon.moonId == moonId
    }

    void "test create moon"() {
        when: "create new moon"
        def createdMoon = service.createMoon(player, moonId, galaxy, solarSystem, position, moonName)

        then: "moon should be created"
        createdMoon
        createdMoon.player == player
        createdMoon.moonId == moonId
        createdMoon.currentCoordinate.galaxy == galaxy
        createdMoon.currentCoordinate.solarSystem == solarSystem
        createdMoon.currentCoordinate.position == position
        createdMoon.currentName == moonName
        Moon.findByPlayerAndMoonId(player, moonId) == createdMoon

        when: "create other moon with same moonId in same universe"
        service.createMoon(player, moonId, galaxy, solarSystem, position, moonName)

        then: "validation error should be thrown"
        def ex = thrown(ValidationException)
        ex.errors.allErrors.stream().flatMap({objectError -> objectError.codes.toList().stream()}).any { it == "unique" }

        when: "create other moon with same moonId in different universe"
        def otherMoon = service.createMoon(player2, moonId, galaxy, solarSystem, position, moonName)

        then: "that moon should be created"
        otherMoon
        otherMoon.player == player2
        otherMoon.moonId == moonId
        otherMoon.currentCoordinate.galaxy == galaxy
        otherMoon.currentCoordinate.solarSystem == solarSystem
        otherMoon.currentCoordinate.position == position
        otherMoon.currentName == moonName
        Moon.findByPlayerAndMoonId(player2, moonId) == otherMoon
    }

    void "test store moon location and get moon location"() {
        given: "a moon"
        def moon = service.createMoon(player, moonId, galaxy, solarSystem, position, moonName)
        def moonCoordinate = moon.currentCoordinate
        def newMoonCoordinate = coordinateService.getCoordinate(universe, galaxy, solarSystem + 1, position)

        when: "moon has been seen with same location"
        service.storeMoonLocation(moon, galaxy, solarSystem, position, now)

        then: "moon does not have a new location"
        moon.locations.size() == 1
        moon.getCoordinateAt(now.minus(1, ChronoUnit.HOURS)) == moonCoordinate
        moon.getCoordinateAt(now) == moonCoordinate
        moon.getCoordinateAt(now.plus(1, ChronoUnit.HOURS)) == moonCoordinate

        when: "moon has been seen with a different name"
        def updateInstant = now.minus(2, ChronoUnit.HOURS)
        service.storeMoonLocation(moon, galaxy, solarSystem + 1, position, updateInstant)

        then: "moon has a new alias"
        moon.locations.size() == 2
        service.getMoonLocation(moon, now.minus(4, ChronoUnit.HOURS)).coordinate == moonCoordinate
        service.getMoonLocation(moon, now.minus(2, ChronoUnit.HOURS)).coordinate == newMoonCoordinate
        service.getMoonLocation(moon, now.plus(2, ChronoUnit.HOURS)).coordinate == newMoonCoordinate
        service.getMoonLocation(moon, now.minus(4, ChronoUnit.HOURS)).end == updateInstant
        service.getMoonLocation(moon, now.minus(2, ChronoUnit.HOURS)).begin == updateInstant
    }

    void "test store moon name and get moon alias"() {
        given: "a moon"
        def moon = service.createMoon(player, moonId, galaxy, solarSystem, position, moonName)
        def newMoonName = "Moon2"

        when: "moon has been seen with same name"
        service.storeMoonName(moon, moonName, now)

        then: "moon does not have a new alias"
        moon.aliases.size() == 1
        moon.getNameAt(now.minus(1, ChronoUnit.HOURS)) == moonName
        moon.getNameAt(now) == moonName
        moon.getNameAt(now.plus(1, ChronoUnit.HOURS)) == moonName

        when: "moon has been seen with a different name"
        def updateInstant = now.minus(2, ChronoUnit.HOURS)
        service.storeMoonName(moon, newMoonName, updateInstant)

        then: "moon has a new alias"
        moon.aliases.size() == 2
        service.getMoonAlias(moon, now.minus(4, ChronoUnit.HOURS)).name == moonName
        service.getMoonAlias(moon, now.minus(2, ChronoUnit.HOURS)).name == newMoonName
        service.getMoonAlias(moon, now.plus(2, ChronoUnit.HOURS)).name == newMoonName
        service.getMoonAlias(moon, now.minus(4, ChronoUnit.HOURS)).end == updateInstant
        service.getMoonAlias(moon, now.minus(2, ChronoUnit.HOURS)).begin == updateInstant
    }

    void "test delete moon"() {
        given: "a moon"
        def moon = service.createMoon(player, moonId, galaxy, solarSystem, position, moonName)

        when: "moon gets deleted"
        service.deleteMoon(moon)

        then: "moon should be be marked as deleted"
        moon.deleted
        moon.dateDeleted
        service.getMoonLocation(moon, now)
        service.getMoonAlias(moon, now)
        !service.getMoonLocation(moon, now.plus(1, ChronoUnit.MINUTES))
        !service.getMoonAlias(moon, now.plus(1, ChronoUnit.MINUTES))
        Moon.findByPlayerAndMoonId(player, moonId) == moon

        when: "non-existing moon gets deleted"
        def oldDateDeleted = moon.dateDeleted
        service.deleteMoon(moon)

        then: "nothing should happen"
        noExceptionThrown()
        moon.dateDeleted == oldDateDeleted
    }

    void "test find current moons of player"() {
        when: "list moons of player when there are none"
        def emptyMoons = service.findCurrentMoonsOfPlayer(player)

        then: "list should be empty"
        emptyMoons.empty

        when: "moons are added"
        def moon = service.createMoon(player, moonId, galaxy, solarSystem, position, moonName)
        def moon2 = service.createMoon(player, moonId + 1, galaxy + 1, solarSystem + 1, position + 1, moonName + "2")
        def moons = service.findCurrentMoonsOfPlayer(player)

        then: "list should contain the added moons"
        moons.size() == 2
        moons.toSet() == [moon, moon2].toSet()

        when: "moon of another player is added"
        service.createMoon(player2, moonId, galaxy, solarSystem, position, moonName)
        def sameMoons = service.findCurrentMoonsOfPlayer(player)

        then: "list should not be changed"
        sameMoons == moons

        when: "moon gets deleted"
        service.deleteMoon(moon2)
        def newMoons = service.findCurrentMoonsOfPlayer(player)

        then: "moon should no longer be in the list"
        newMoons.size() == 1
        newMoons == [moon]
    }

    void "test find moons of player"() {
        when: "list moons of player when there are none"
        def emptyMoons = service.findMoonsOfPlayer(player, now)

        then: "list should be empty"
        emptyMoons.empty

        when: "moons are added"
        def moon = service.createMoon(player, moonId, galaxy, solarSystem, position, moonName)
        def moon2 = service.createMoon(player, moonId + 1, galaxy + 1, solarSystem + 1, position + 1, moonName + "2")
        def moons = service.findMoonsOfPlayer(player, now)

        then: "list should contain the added moons"
        moons.size() == 2
        moons.toSet() == [moon, moon2].toSet()

        when: "moon of another player is added"
        service.createMoon(player2, moonId, galaxy, solarSystem, position, moonName)
        def sameMoons = service.findMoonsOfPlayer(player, now)

        then: "list should not be changed"
        sameMoons == moons

        when: "moon gets deleted"
        service.deleteMoon(moon2)
        def newMoons = service.findMoonsOfPlayer(player, now.plus(1, ChronoUnit.MINUTES))

        then: "moon should no longer be in the list"
        newMoons.size() == 1
        newMoons == [moon]

        when: "moon is not yet deleted"
        def notYetDeletedMoons = service.findMoonsOfPlayer(player, now.minus(1, ChronoUnit.HOURS))

        then: "moon should not be deleted in the past"
        notYetDeletedMoons.size() == 2
        notYetDeletedMoons.toSet() == [moon, moon2].toSet()

        when: "moon is already deleted"
        def alreadyDeletedMoons = service.findMoonsOfPlayer(player, now.plus(1, ChronoUnit.HOURS))

        then: "moon should already be deleted in the future"
        alreadyDeletedMoons.size() == 1
        alreadyDeletedMoons == [moon]
    }
}
