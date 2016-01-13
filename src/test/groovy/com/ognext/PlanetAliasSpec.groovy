package com.ognext

import grails.test.mixin.TestFor
import spock.lang.Specification

import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(PlanetAlias)
class PlanetAliasSpec extends Specification {
    def setup() {
    }

    def cleanup() {
    }

    void "save valid planet alias"() {
        when: "planet alias is valid"
        def now = Instant.now()
        def planetAlias = new PlanetAlias(name: "Homeworld", begin: now, end: now.plus(1, ChronoUnit.HOURS))

        then: "planet alias should be saved"
        planetAlias.save()
    }

    void "save planet alias with end before start"() {
        when: "planet alias is invalid"
        def now = Instant.now()
        def planetAlias = new PlanetAlias(name: "Homeworld", begin: now, end: now.minus(1, ChronoUnit.HOURS))

        then: "planet alias should not be saved"
        !planetAlias.save(failOnError: false)
    }

    void "planet alias intersects"() {
        given: "a planet alias"
        def now = Instant.now()
        def otherPlanetAlias = new PlanetAlias(name: "test", begin: now, end:  now.plus(4, ChronoUnit.HOURS))

        expect:
        new PlanetAlias(name: "testBefore", begin: now.minus(1, ChronoUnit.HOURS), end:  now.plus(1, ChronoUnit.HOURS)).intervalIntersects(otherPlanetAlias)
        new PlanetAlias(name: "testAfter", begin: now.plus(2, ChronoUnit.HOURS), end:  now.plus(6, ChronoUnit.HOURS)).intervalIntersects(otherPlanetAlias)
        new PlanetAlias(name: "testEqual", begin:  now, end:  now.plus(4, ChronoUnit.HOURS)).intervalIntersects(otherPlanetAlias)
        new PlanetAlias(name: "testWithin", begin: now.plus(1, ChronoUnit.HOURS), end: now.plus(3, ChronoUnit.HOURS)).intervalIntersects(otherPlanetAlias)
        new PlanetAlias(name: "testEncloses", begin: now.minus(1, ChronoUnit.HOURS), end: now.plus(5, ChronoUnit.HOURS)).intervalIntersects(otherPlanetAlias)

        !new PlanetAlias(name: "testBeforeWithGap", begin: now.minus(4, ChronoUnit.HOURS), end: now.minus(1, ChronoUnit.HOURS)).intervalIntersects(otherPlanetAlias)
        !new PlanetAlias(name: "testBeforeWithoutGap", begin: now.minus(4, ChronoUnit.HOURS), end: now).intervalIntersects(otherPlanetAlias)
        !new PlanetAlias(name: "testAfterWithoutGap", begin: now.plus(4, ChronoUnit.HOURS), end: now.plus(7, ChronoUnit.HOURS)).intervalIntersects(otherPlanetAlias)
        !new PlanetAlias(name: "testAfterWithGap", begin: now.plus(5, ChronoUnit.HOURS), end: now.plus(7, ChronoUnit.HOURS)).intervalIntersects(otherPlanetAlias)
    }

    void "planet alias interval"() {
        given: "a planet alias"
        def now = Instant.now()
        def planetAlias = new PlanetAlias(name: "test", begin: now, end: now.plus(4, ChronoUnit.HOURS))

        expect:
        planetAlias.inInterval(now)
        planetAlias.inInterval(now.plus(2, ChronoUnit.HOURS))
        !planetAlias.inInterval(now.plus(4, ChronoUnit.HOURS))
    }

    void "test to string"() {
        given: "the dates"
        def now = Instant.now()
        def begin = now
        def end = now.plus(1, ChronoUnit.HOURS)

        expect:
        new PlanetAlias(name: "Homeworld", begin: begin, end: end).toString() == "PlanetAlias(null, Homeworld, $begin, $end)".toString()
    }
}
