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
@TestFor(Planet)
@Mock([Coordinate, PlanetAlias, PlanetLocation, Universe, PlanetBuildings, Defences, Fleet])
@TestMixin(GrailsUnitTestMixin)
class PlanetSpec extends Specification {
    PlanetBuildingsService planetBuildingsService
    DefencesService defencesService
    FleetService fleetService
    PlanetService planetService
    CoordinateService coordinateService
    PlanetBuildings defaultPlanetBuildings
    Defences defaultDefences
    Fleet defaultFleet

    def serverGroup = new ServerGroup(countryCode: "en")
    def universe = new Universe(serverGroup: serverGroup, universeId: 135)
    def universe2 = new Universe(serverGroup: serverGroup, universeId: 136)
    def player = new Player(universe: universe, playerId: 103168)
    def player2 = new Player(universe: universe2, playerId: 103168)

    static doWithSpring = {
        planetBuildingsService(PlanetBuildingsService)
        defencesService(DefencesService)
        fleetService(FleetService)
        planetService(PlanetService)
        coordinateService(CoordinateService)
    }

    def setup() {
        planetBuildingsService = grailsApplication.mainContext.getBean("planetBuildingsService")
        defencesService = grailsApplication.mainContext.getBean("defencesService")
        fleetService = grailsApplication.mainContext.getBean("fleetService")
        planetService = grailsApplication.mainContext.getBean("planetService")
        planetService.planetBuildingsService = planetBuildingsService
        planetService.defencesService = defencesService
        planetService.fleetService = fleetService
        coordinateService = grailsApplication.mainContext.getBean("coordinateService")
        planetService.coordinateService = coordinateService
        defaultPlanetBuildings = planetBuildingsService.createDefaultPlanetBuildings()
        defaultDefences = defencesService.createDefaultDefences()
        defaultFleet = fleetService.createDefaultFleet()
    }

    def cleanup() {
    }

    void "save valid planet"() {
        when: "planet is valid"
        def planet = new Planet(player: player, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet)

        then: "planet should be saved"
        planet.save()
    }

    void "universe equals player universe when updating"() {
        when: "planet is valid"
        def planet = new Planet(player: player, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet)
        planet.save()

        then: "universe should be equal to player universe"
        planet.universe == planet.player.universe

        when: "the player gets updated"
        def player2 = new Player(universe: universe, playerId: 103169)
        planet.player = player2
        planet.save()

        then: "the universe should be updated to the new player universe"
        planet.universe == player2.universe

        when: "the universe gets updated"
        def oldUniverse = planet.universe
        def universe2 = new Universe(serverGroup: serverGroup, universeId: 136)
        planet.universe == universe2
        planet.save()

        then: "the universe should not be updated"
        planet.universe == oldUniverse
    }

    void "save two planets with same planetId in same universe"() {
        when: "planets have same planetId in same universe"
        def planet = new Planet(player: player, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet)
        def planet2 = new Planet(player: player, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet)

        then: "second planet should not be saved"
        planet.save(flush: true)
        !planet2.save(failOnError: false)
    }

    void "save two planets with same planetId in different universe"() {
        when: "planets have same planetId in different universe"
        def planet = new Planet(player: player, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet)
        def planet2 = new Planet(player: player2, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet)

        then: "both planets should be saved"
        planet.save()
        planet2.save()
    }

    void "save valid planets belonging to same player"() {
        when: "planets are valid and belong to same player"
        def planet = new Planet(player: player, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet)
        def planet2 = new Planet(player: player, planetId: 1001, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet)

        then: "both planets should be saved"
        planet.save()
        planet2.save()
    }

    void "save valid planet with single location"() {
        when: "planet has single location"
        def now = Instant.now()
        def planet = new Planet(player: player, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet)
        planet.addToLocations(new PlanetLocation(coordinate: new Coordinate(galaxy: 2, solarSystem: 122, position: 12), begin: Instant.ofEpochSecond(0), end: now.plus(50000, ChronoUnit.DAYS)))

        then: "planet should be saved"
        planet.save()
    }

    void "save valid planet with non-intersecting locations"() {
        when: "planet has non-intersecting locations"
        def now = Instant.now()
        def planet = new Planet(player: player, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet)
        planet.addToLocations(new PlanetLocation(coordinate: new Coordinate(galaxy: 2, solarSystem: 122, position: 12), begin: Instant.ofEpochSecond(0), end: now))
        planet.addToLocations(new PlanetLocation(coordinate: new Coordinate(galaxy: 2, solarSystem: 123, position: 12), begin: now, end: now.plus(50000, ChronoUnit.DAYS)))

        then: "planet should be saved"
        planet.save()
    }

    void "save invalid planet with intersecting locations"() {
        when: "planet has intersecting locations"
        def now = Instant.now()
        def planet = new Planet(player: player, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet)
        planet.addToLocations(new PlanetLocation(coordinate: new Coordinate(galaxy: 2, solarSystem: 122, position: 12), begin: Instant.ofEpochSecond(0), end: now.plus(1, ChronoUnit.HOURS)))
        planet.addToLocations(new PlanetLocation(coordinate: new Coordinate(galaxy: 2, solarSystem: 123, position: 12), begin: now, end: now.plus(50000, ChronoUnit.DAYS)))

        then: "planet should not be saved"
        !planet.save(failOnError: false)
    }

    void "save valid planet with same coordinate again"() {
        when: "planet has changed its coordinate back again after having changed it"
        def now = Instant.now()
        def planet = new Planet(player: player, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet)
        planet.addToLocations(new PlanetLocation(coordinate: new Coordinate(galaxy: 2, solarSystem: 122, position: 12), begin: Instant.ofEpochSecond(0), end: now))
        planet.addToLocations(new PlanetLocation(coordinate: new Coordinate(galaxy: 2, solarSystem: 123, position: 12), begin: now, end: now.plus(10, ChronoUnit.DAYS)))
        planet.addToLocations(new PlanetLocation(coordinate: new Coordinate(galaxy: 2, solarSystem: 122, position: 12), begin: now.plus(10, ChronoUnit.DAYS), end: now.plus(50000, ChronoUnit.DAYS)))

        then: "planet should be saved"
        planet.save()
    }

    void "save different planets in same universe on same coordinates"() {
        when: "planets are in same universes and are on the same location"
        def now = Instant.now()

        def planet = new Planet(player: player, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet)
        planet.addToLocations(new PlanetLocation(coordinate: new Coordinate(galaxy: 2, solarSystem: 122, position: 12), begin: Instant.ofEpochSecond(0), end: now))

        def planet2 = new Planet(player: player2, planetId: 1001, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet)
        planet2.addToLocations(new PlanetLocation(coordinate: new Coordinate(galaxy: 2, solarSystem: 122, position: 12), begin: Instant.ofEpochSecond(0), end: now))

        then: "saving second planet should fail"
        planet.save()
        !planet2.save(failOnError: false)
    }

    void "test current coordinate"() {
        given: "a planet with a coordinate"
        def planet = planetService.createPlanet(player, 1000, 2, 122, 12, "Homeworld")

        expect:
        planet.currentCoordinate == coordinateService.getCoordinate(universe, 2, 122, 12)
    }

    void "test coordinate at instant"() {
        given: "a planet with a coordinate"
        def planet = planetService.createPlanet(player, 1000, 2, 122, 12, "Homeworld")

        expect:
        planet.getCoordinateAt(Instant.now().plus(4, ChronoUnit.HOURS)) == coordinateService.getCoordinate(universe, 2, 122, 12)
    }

    void "save valid planet with single alias"() {
        when: "planet has single alias"
        def now = Instant.now()
        def planet = new Planet(player: player, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet)
        planet.addToAliases(new PlanetAlias(name: "Homeworld", begin: Instant.ofEpochSecond(0), end: now.plus(50000, ChronoUnit.DAYS)))

        then: "planet should be saved"
        planet.save()
    }

    void "save valid planet with non-intersecting aliases"() {
        when: "planet has non-intersecting aliases"
        def now = Instant.now()
        def planet = new Planet(player: player, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet)
        planet.addToAliases(new PlanetAlias(name: "Homeworld", begin: Instant.ofEpochSecond(0), end: now))
        planet.addToAliases(new PlanetAlias(name: "Homeworld2", begin: now, end: now.plus(50000, ChronoUnit.DAYS)))

        then: "planet should be saved"
        planet.save()
    }

    void "save invalid planet with intersecting aliases"() {
        when: "planet has intersecting aliases"
        def now = Instant.now()
        def planet = new Planet(player: player, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet)
        planet.addToAliases(new PlanetAlias(name: "Homeworld", begin: Instant.ofEpochSecond(0), end: now.plus(1, ChronoUnit.HOURS)))
        planet.addToAliases(new PlanetAlias(name: "Homeworld2", begin: now, end: now.plus(50000, ChronoUnit.DAYS)))

        then: "planet should not be saved"
        !planet.save(failOnError: false)
    }

    void "save valid planet with same name again"() {
        when: "planet has changed its name back again after having changed it"
        def now = Instant.now()
        def planet = new Planet(player: player, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet)
        planet.addToAliases(new PlanetAlias(name: "Homeworld", begin: Instant.ofEpochSecond(0), end: now))
        planet.addToAliases(new PlanetAlias(name: "Homeworld2", begin: now, end: now.plus(10, ChronoUnit.DAYS)))
        planet.addToAliases(new PlanetAlias(name: "Homeworld", begin: now.plus(10, ChronoUnit.DAYS), end: now.plus(50000, ChronoUnit.DAYS)))

        then: "planet should be saved"
        planet.save()
    }

    void "test current name"() {
        given: "a planet with a name"
        def planet = planetService.createPlanet(player, 1000, 2, 122, 12, "Homeworld")

        expect:
        planet.currentName == "Homeworld"
    }

    void "test name at instant"() {
        given: "a planet with a name"
        def planet = planetService.createPlanet(player, 1000, 2, 122, 12, "Homeworld")

        expect:
        planet.getNameAt(Instant.now().plus(4, ChronoUnit.HOURS)) == "Homeworld"
    }

    void "test equals and hash code"() {
        given:
        def coordinate = new Coordinate(universe: universe, galaxy:  2, solarSystem: 122, position: 12)
        def coordinate2 = new Coordinate(universe: universe, galaxy:  2, solarSystem: 123, position: 12)
        def planetLocation = new PlanetLocation(coordinate: coordinate, begin:  Instant.ofEpochSecond(0), end: Instant.now().plus(50000, ChronoUnit.DAYS))
        def planetLocation2 = new PlanetLocation(coordinate: coordinate2, begin:  Instant.ofEpochSecond(0), end: Instant.now().plus(50000, ChronoUnit.DAYS))
        def planetAlias = new PlanetAlias(name: "Homeworld", begin: Instant.ofEpochSecond(0), end: Instant.now().plus(50000, ChronoUnit.DAYS))
        def planetAlias2 = new PlanetAlias(name: "Homeworld2", begin: Instant.ofEpochSecond(0), end: Instant.now().plus(50000, ChronoUnit.DAYS))

        expect:
        new Planet(player: player, universe: universe, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet) == new Planet(player: player, universe: universe, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet)
        new Planet(player: player, universe: universe, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet) != new Planet(player: player2, universe: universe, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet)
        new Planet(player: player, universe: universe, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet) != new Planet(player: player, universe: universe2, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet)
        new Planet(player: player, universe: universe, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet) != new Planet(player: player, universe: universe, planetId: 1001, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet)

        new Planet(player: player, universe: universe, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet).addToLocations(planetLocation) == new Planet(player: player, universe: universe, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet).addToLocations(planetLocation2)
        new Planet(player: player, universe: universe, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet).addToAliases(planetAlias) == new Planet(player: player, universe: universe, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet).addToAliases(planetAlias2)
    }

    void "test to string"() {
        expect:
        new Planet(player: player, universe: universe, planetId: 1000, buildings: defaultPlanetBuildings, defences: defaultDefences, fleet: defaultFleet).toString() == "Planet(null, Player(null, Universe(null, en, 135), 103168), 1000)"
    }
}
