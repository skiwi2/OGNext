package com.ognext

import grails.test.mixin.TestFor
import spock.lang.Specification

import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(PlanetLocation)
class PlanetLocationSpec extends Specification {
    def serverGroup = new ServerGroup(countryCode: "en")
    def universe = new Universe(universeId: 135, serverGroup: serverGroup)
    def coordinate = new Coordinate(galaxy: 2, solarSystem: 122, position: 12, universe: universe)
    def otherCoordinate = new Coordinate(galaxy: 2, solarSystem: 153, position: 8, universe: universe)

    def setup() {
    }

    def cleanup() {
    }

    void "save valid planet location"() {
        when: "planet location is valid"
        def now = Instant.now()
        def planetLocation = new PlanetLocation(coordinate: coordinate, begin: now, end: now.plus(1, ChronoUnit.HOURS))

        then: "planet location should be saved"
        planetLocation.save()
    }

    void "save planet location with end before start"() {
        when: "planet location is invalid"
        def now = Instant.now()
        def planetLocation = new PlanetLocation(coordinate: coordinate, begin: now, end: now.minus(1, ChronoUnit.HOURS))

        then: "planet location should not be saved"
        !planetLocation.save(failOnError: false)
    }

    void "planet location intersects"() {
        given: "a planet location"
        def now = Instant.now()
        def otherPlanetLocation = new PlanetLocation(coordinate: otherCoordinate, begin: now, end:  now.plus(4, ChronoUnit.HOURS))

        expect:
        new PlanetLocation(coordinate: coordinate, begin: now.minus(1, ChronoUnit.HOURS), end:  now.plus(1, ChronoUnit.HOURS)).intervalIntersects(otherPlanetLocation)
        new PlanetLocation(coordinate: coordinate, begin: now.plus(2, ChronoUnit.HOURS), end:  now.plus(6, ChronoUnit.HOURS)).intervalIntersects(otherPlanetLocation)
        new PlanetLocation(coordinate: coordinate, begin:  now, end:  now.plus(4, ChronoUnit.HOURS)).intervalIntersects(otherPlanetLocation)
        new PlanetLocation(coordinate: coordinate, begin: now.plus(1, ChronoUnit.HOURS), end: now.plus(3, ChronoUnit.HOURS)).intervalIntersects(otherPlanetLocation)
        new PlanetLocation(coordinate: coordinate, begin: now.minus(1, ChronoUnit.HOURS), end: now.plus(5, ChronoUnit.HOURS)).intervalIntersects(otherPlanetLocation)

        !new PlanetLocation(coordinate: coordinate, begin: now.minus(4, ChronoUnit.HOURS), end: now.minus(1, ChronoUnit.HOURS)).intervalIntersects(otherPlanetLocation)
        !new PlanetLocation(coordinate: coordinate, begin: now.minus(4, ChronoUnit.HOURS), end: now).intervalIntersects(otherPlanetLocation)
        !new PlanetLocation(coordinate: coordinate, begin: now.plus(4, ChronoUnit.HOURS), end: now.plus(7, ChronoUnit.HOURS)).intervalIntersects(otherPlanetLocation)
        !new PlanetLocation(coordinate: coordinate, begin: now.plus(5, ChronoUnit.HOURS), end: now.plus(7, ChronoUnit.HOURS)).intervalIntersects(otherPlanetLocation)
    }

    void "planet location interval"() {
        given: "a planet location"
        def now = Instant.now()
        def planetLocation = new PlanetLocation(coordinate: coordinate, begin: now, end: now.plus(4, ChronoUnit.HOURS))

        expect:
        planetLocation.inInterval(now)
        planetLocation.inInterval(now.plus(2, ChronoUnit.HOURS))
        !planetLocation.inInterval(now.plus(4, ChronoUnit.HOURS))
    }

    void "test to string"() {
        given: "the dates"
        def now = Instant.now()
        def begin = now
        def end = now.plus(1, ChronoUnit.HOURS)

        expect:
        new PlanetLocation(coordinate: coordinate, begin: begin, end: end).toString() == "PlanetLocation(null, Coordinate(null, Universe(null, en, 135), 2, 122, 12), $begin, $end)".toString()
    }
}
