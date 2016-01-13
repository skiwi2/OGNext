package com.ognext

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Moon)
@Mock([Coordinate, MoonAlias, MoonLocation, Universe, MoonBuildings, Defences, Fleet])
@TestMixin(GrailsUnitTestMixin)
class MoonSpec extends Specification {
    MoonBuildingsService moonBuildingsService
    DefencesService defencesService
    FleetService fleetService
    MoonService moonService
    CoordinateService coordinateService
    MoonBuildings defaultMoonBuildings
    Defences defaultDefences
    Fleet defaultFleet

    def serverGroup = new ServerGroup(countryCode: "en")
    def universe = new Universe(serverGroup: serverGroup, universeId: 135)
    def universe2 = new Universe(serverGroup: serverGroup, universeId: 136)
    def player = new Player(universe: universe, playerId: 103168)
    def player2 = new Player(universe: universe2, playerId: 103168)

    static doWithSpring = {
        moonBuildingsService(MoonBuildingsService)
        defencesService(DefencesService)
        fleetService(FleetService)
        moonService(MoonService)
        coordinateService(CoordinateService)
    }

    def setup() {
        moonBuildingsService = grailsApplication.mainContext.getBean("moonBuildingsService")
        defencesService = grailsApplication.mainContext.getBean("defencesService")
        fleetService = grailsApplication.mainContext.getBean("fleetService")
        moonService = grailsApplication.mainContext.getBean("moonService")
        moonService.moonBuildingsService = moonBuildingsService
        moonService.defencesService = defencesService
        moonService.fleetService = fleetService
        coordinateService = grailsApplication.mainContext.getBean("coordinateService")
        moonService.coordinateService = coordinateService
        defaultMoonBuildings = moonBuildingsService.createDefaultMoonBuildings()
        defaultDefences = defencesService.createDefaultDefences()
        defaultFleet = fleetService.createDefaultFleet()
    }

    def cleanup() {
    }

    void "save valid moon"() {
        when: "moon is valid"
        def moon = new Moon(player: player, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet)

        then: "moon should be saved"
        moon.save()
    }

    void "universe equals player universe when updating"() {
        when: "moon is valid"
        def moon = new Moon(player: player, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet)
        moon.save()

        then: "universe should be equal to player universe"
        moon.universe == moon.player.universe

        when: "the player gets updated"
        def player2 = new Player(universe: universe, playerId: 103169)
        moon.player = player2
        moon.save()

        then: "the universe should be updated to the new player universe"
        moon.universe == player2.universe

        when: "the universe gets updated"
        def oldUniverse = moon.universe
        def universe2 = new Universe(serverGroup: serverGroup, universeId: 136)
        moon.universe == universe2
        moon.save()

        then: "the universe should not be updated"
        moon.universe == oldUniverse
    }

    void "save two moons with same moonId in same universe"() {
        when: "moons have same moonId in same universe"
        def moon = new Moon(player: player, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet)
        def moon2 = new Moon(player: player, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet)

        then: "second moon should not be saved"
        moon.save(flush: true)
        !moon2.save(failOnError: false)
    }

    void "save two moons with same moonId in different universe"() {
        when: "moons have same moonId in different universe"
        def moon = new Moon(player: player, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet)
        def moon2 = new Moon(player: player2, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet)

        then: "both moons should be saved"
        moon.save()
        moon2.save()
    }

    void "save valid moons belonging to same player"() {
        when: "moons are valid and belong to same player"
        def moon = new Moon(player: player, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet)
        def moon2 = new Moon(player: player, moonId: 1001, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet)

        then: "both moons should be saved"
        moon.save()
        moon2.save()
    }

    void "save valid moon with single location"() {
        when: "moon has single location"
        def now = Instant.now()
        def moon = new Moon(player: player, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet)
        moon.addToLocations(new MoonLocation(coordinate: new Coordinate(galaxy: 2, solarSystem: 122, position: 12), begin: Instant.ofEpochSecond(0), end: now.plus(50000, ChronoUnit.DAYS)))

        then: "moon should be saved"
        moon.save()
    }

    void "save valid moon with non-intersecting locations"() {
        when: "moon has non-intersecting locations"
        def now = Instant.now()
        def moon = new Moon(player: player, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet)
        moon.addToLocations(new MoonLocation(coordinate: new Coordinate(galaxy: 2, solarSystem: 122, position: 12), begin: Instant.ofEpochSecond(0), end: now))
        moon.addToLocations(new MoonLocation(coordinate: new Coordinate(galaxy: 2, solarSystem: 123, position: 12), begin: now, end: now.plus(50000, ChronoUnit.DAYS)))

        then: "moon should be saved"
        moon.save()
    }

    void "save invalid moon with intersecting locations"() {
        when: "moon has intersecting locations"
        def now = Instant.now()
        def moon = new Moon(player: player, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet)
        moon.addToLocations(new MoonLocation(coordinate: new Coordinate(galaxy: 2, solarSystem: 122, position: 12), begin: Instant.ofEpochSecond(0), end: now.plus(1, ChronoUnit.HOURS)))
        moon.addToLocations(new MoonLocation(coordinate: new Coordinate(galaxy: 2, solarSystem: 123, position: 12), begin: now, end: now.plus(50000, ChronoUnit.DAYS)))

        then: "moon should not be saved"
        !moon.save(failOnError: false)
    }

    void "save valid moon with same coordinate again"() {
        when: "moon has changed its coordinate back again after having changed it"
        def now = Instant.now()
        def moon = new Moon(player: player, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet)
        moon.addToLocations(new MoonLocation(coordinate: new Coordinate(galaxy: 2, solarSystem: 122, position: 12), begin: Instant.ofEpochSecond(0), end: now))
        moon.addToLocations(new MoonLocation(coordinate: new Coordinate(galaxy: 2, solarSystem: 123, position: 12), begin: now, end: now.plus(10, ChronoUnit.DAYS)))
        moon.addToLocations(new MoonLocation(coordinate: new Coordinate(galaxy: 2, solarSystem: 122, position: 12), begin: now.plus(10, ChronoUnit.DAYS), end: now.plus(50000, ChronoUnit.DAYS)))

        then: "moon should be saved"
        moon.save()
    }

    void "save different moons in same universe on same coordinates"() {
        when: "moons are in same universes and are on the same location"
        def now = Instant.now()

        def moon = new Moon(player: player, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet)
        moon.addToLocations(new MoonLocation(coordinate: new Coordinate(galaxy: 2, solarSystem: 122, position: 12), begin: Instant.ofEpochSecond(0), end: now))

        def moon2 = new Moon(player: player2, moonId: 1001, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet)
        moon2.addToLocations(new MoonLocation(coordinate: new Coordinate(galaxy: 2, solarSystem: 122, position: 12), begin: Instant.ofEpochSecond(0), end: now))

        then: "saving second moon should fail"
        moon.save()
        !moon2.save(failOnError: false)
    }

    void "test current coordinate"() {
        given: "a moon with a coordinate"
        def moon = moonService.createMoon(player, 1000, 2, 122, 12, "Moon")

        expect:
        moon.currentCoordinate == coordinateService.getCoordinate(universe, 2, 122, 12)
    }

    void "test coordinate at instant"() {
        given: "a moon with a coordinate"
        def moon = moonService.createMoon(player, 1000, 2, 122, 12, "Moon")

        expect:
        moon.getCoordinateAt(Instant.now().plus(4, ChronoUnit.HOURS)) == coordinateService.getCoordinate(universe, 2, 122, 12)
    }

    void "save valid moon with single alias"() {
        when: "moon has single alias"
        def now = Instant.now()
        def moon = new Moon(player: player, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet)
        moon.addToAliases(new MoonAlias(name: "Moon", begin: Instant.ofEpochSecond(0), end: now.plus(50000, ChronoUnit.DAYS)))

        then: "moon should be saved"
        moon.save()
    }

    void "save valid moon with non-intersecting aliases"() {
        when: "moon has non-intersecting aliases"
        def now = Instant.now()
        def moon = new Moon(player: player, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet)
        moon.addToAliases(new MoonAlias(name: "Moon", begin: Instant.ofEpochSecond(0), end: now))
        moon.addToAliases(new MoonAlias(name: "Moon2", begin: now, end: now.plus(50000, ChronoUnit.DAYS)))

        then: "moon should be saved"
        moon.save()
    }

    void "save invalid moon with intersecting aliases"() {
        when: "moon has intersecting aliases"
        def now = Instant.now()
        def moon = new Moon(player: player, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet)
        moon.addToAliases(new MoonAlias(name: "Moon", begin: Instant.ofEpochSecond(0), end: now.plus(1, ChronoUnit.HOURS)))
        moon.addToAliases(new MoonAlias(name: "Moon2", begin: now, end: now.plus(50000, ChronoUnit.DAYS)))

        then: "moon should not be saved"
        !moon.save(failOnError: false)
    }

    void "save valid moon with same name again"() {
        when: "moon has changed its name back again after having changed it"
        def now = Instant.now()
        def moon = new Moon(player: player, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet)
        moon.addToAliases(new MoonAlias(name: "Moon", begin: Instant.ofEpochSecond(0), end: now))
        moon.addToAliases(new MoonAlias(name: "Moon2", begin: now, end: now.plus(10, ChronoUnit.DAYS)))
        moon.addToAliases(new MoonAlias(name: "Moon", begin: now.plus(10, ChronoUnit.DAYS), end: now.plus(50000, ChronoUnit.DAYS)))

        then: "moon should be saved"
        moon.save()
    }

    void "test current name"() {
        given: "a moon with a name"
        def moon = moonService.createMoon(player, 1000, 2, 122, 12, "Moon")

        expect:
        moon.currentName == "Moon"
    }

    void "test name at instant"() {
        given: "a moon with a name"
        def moon = moonService.createMoon(player, 1000, 2, 122, 12, "Moon")

        expect:
        moon.getNameAt(Instant.now().plus(4, ChronoUnit.HOURS)) == "Moon"
    }

    void "test equals and hash code"() {
        given:
        def coordinate = new Coordinate(universe: universe, galaxy:  2, solarSystem: 122, position: 12)
        def coordinate2 = new Coordinate(universe: universe, galaxy:  2, solarSystem: 123, position: 12)
        def moonLocation = new MoonLocation(coordinate: coordinate, begin:  Instant.ofEpochSecond(0), end: Instant.now().plus(50000, ChronoUnit.DAYS))
        def moonLocation2 = new MoonLocation(coordinate: coordinate2, begin:  Instant.ofEpochSecond(0), end: Instant.now().plus(50000, ChronoUnit.DAYS))
        def moonAlias = new MoonAlias(name: "Moon", begin: Instant.ofEpochSecond(0), end: Instant.now().plus(50000, ChronoUnit.DAYS))
        def moonAlias2 = new MoonAlias(name: "Moon2", begin: Instant.ofEpochSecond(0), end: Instant.now().plus(50000, ChronoUnit.DAYS))

        expect:
        new Moon(player: player, universe: universe, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet) == new Moon(player: player, universe: universe, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet)
        new Moon(player: player, universe: universe, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet) != new Moon(player: player2, universe: universe, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet)
        new Moon(player: player, universe: universe, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet) != new Moon(player: player, universe: universe2, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet)
        new Moon(player: player, universe: universe, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet) != new Moon(player: player, universe: universe, moonId: 1001, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet)

        new Moon(player: player, universe: universe, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet).addToLocations(moonLocation) == new Moon(player: player, universe: universe, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet).addToLocations(moonLocation2)
        new Moon(player: player, universe: universe, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet).addToAliases(moonAlias) == new Moon(player: player, universe: universe, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet).addToAliases(moonAlias2)
    }

    void "test to string"() {
        expect:
        new Moon(player: player, universe: universe, moonId: 1000, buildings: defaultMoonBuildings, defences: defaultDefences, fleet: defaultFleet).toString() == "Moon(null, Player(null, Universe(null, en, 135), 103168), 1000)"
    }
}
