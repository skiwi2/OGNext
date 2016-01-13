package com.ognext

import grails.test.mixin.TestFor
import spock.lang.Specification

import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(MoonLocation)
class MoonLocationSpec extends Specification {
    def serverGroup = new ServerGroup(countryCode: "en")
    def universe = new Universe(universeId: 135, serverGroup: serverGroup)
    def coordinate = new Coordinate(galaxy: 2, solarSystem: 122, position: 12, universe: universe)
    def otherCoordinate = new Coordinate(galaxy: 2, solarSystem: 153, position: 8, universe: universe)

    def setup() {
    }

    def cleanup() {
    }

    void "save valid moon location"() {
        when: "moon location is valid"
        def now = Instant.now()
        def moonLocation = new MoonLocation(coordinate: coordinate, begin: now, end: now.plus(1, ChronoUnit.HOURS))

        then: "moon location should be saved"
        moonLocation.save()
    }

    void "save moon location with end before start"() {
        when: "moon location is invalid"
        def now = Instant.now()
        def moonLocation = new MoonLocation(coordinate: coordinate, begin: now, end: now.minus(1, ChronoUnit.HOURS))

        then: "moon location should not be saved"
        !moonLocation.save(failOnError: false)
    }

    void "moon location intersects"() {
        given: "a moon location"
        def now = Instant.now()
        def otherMoonLocation = new MoonLocation(coordinate: otherCoordinate, begin: now, end:  now.plus(4, ChronoUnit.HOURS))

        expect:
        new MoonLocation(coordinate: coordinate, begin: now.minus(1, ChronoUnit.HOURS), end:  now.plus(1, ChronoUnit.HOURS)).intervalIntersects(otherMoonLocation)
        new MoonLocation(coordinate: coordinate, begin: now.plus(2, ChronoUnit.HOURS), end:  now.plus(6, ChronoUnit.HOURS)).intervalIntersects(otherMoonLocation)
        new MoonLocation(coordinate: coordinate, begin:  now, end:  now.plus(4, ChronoUnit.HOURS)).intervalIntersects(otherMoonLocation)
        new MoonLocation(coordinate: coordinate, begin: now.plus(1, ChronoUnit.HOURS), end: now.plus(3, ChronoUnit.HOURS)).intervalIntersects(otherMoonLocation)
        new MoonLocation(coordinate: coordinate, begin: now.minus(1, ChronoUnit.HOURS), end: now.plus(5, ChronoUnit.HOURS)).intervalIntersects(otherMoonLocation)

        !new MoonLocation(coordinate: coordinate, begin: now.minus(4, ChronoUnit.HOURS), end: now.minus(1, ChronoUnit.HOURS)).intervalIntersects(otherMoonLocation)
        !new MoonLocation(coordinate: coordinate, begin: now.minus(4, ChronoUnit.HOURS), end: now).intervalIntersects(otherMoonLocation)
        !new MoonLocation(coordinate: coordinate, begin: now.plus(4, ChronoUnit.HOURS), end: now.plus(7, ChronoUnit.HOURS)).intervalIntersects(otherMoonLocation)
        !new MoonLocation(coordinate: coordinate, begin: now.plus(5, ChronoUnit.HOURS), end: now.plus(7, ChronoUnit.HOURS)).intervalIntersects(otherMoonLocation)
    }

    void "moon location interval"() {
        given: "a moon location"
        def now = Instant.now()
        def moonLocation = new MoonLocation(coordinate: coordinate, begin: now, end: now.plus(4, ChronoUnit.HOURS))

        expect:
        moonLocation.inInterval(now)
        moonLocation.inInterval(now.plus(2, ChronoUnit.HOURS))
        !moonLocation.inInterval(now.plus(4, ChronoUnit.HOURS))
    }

    void "test to string"() {
        given: "the dates"
        def now = Instant.now()
        def begin = now
        def end = now.plus(1, ChronoUnit.HOURS)

        expect:
        new MoonLocation(coordinate: coordinate, begin: begin, end: end).toString() == "MoonLocation(null, Coordinate(null, Universe(null, en, 135), 2, 122, 12), $begin, $end)".toString()
    }
}
