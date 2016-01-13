package com.ognext

import grails.test.mixin.TestFor
import spock.lang.Specification

import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(MoonAlias)
class MoonAliasSpec extends Specification {
    def setup() {
    }

    def cleanup() {
    }

    void "save valid moon alias"() {
        when: "moon alias is valid"
        def now = Instant.now()
        def moonAlias = new MoonAlias(name: "Moon", begin: now, end: now.plus(1, ChronoUnit.HOURS))

        then: "moon alias should be saved"
        moonAlias.save()
    }

    void "save moon alias with end before start"() {
        when: "moon alias is invalid"
        def now = Instant.now()
        def moonAlias = new MoonAlias(name: "Moon", begin: now, end: now.minus(1, ChronoUnit.HOURS))

        then: "moon alias should not be saved"
        !moonAlias.save(failOnError: false)
    }

    void "moon alias intersects"() {
        given: "a moon alias"
        def now = Instant.now()
        def otherMoonAlias = new MoonAlias(name: "test", begin: now, end:  now.plus(4, ChronoUnit.HOURS))

        expect:
        new MoonAlias(name: "testBefore", begin: now.minus(1, ChronoUnit.HOURS), end:  now.plus(1, ChronoUnit.HOURS)).intervalIntersects(otherMoonAlias)
        new MoonAlias(name: "testAfter", begin: now.plus(2, ChronoUnit.HOURS), end:  now.plus(6, ChronoUnit.HOURS)).intervalIntersects(otherMoonAlias)
        new MoonAlias(name: "testEqual", begin:  now, end:  now.plus(4, ChronoUnit.HOURS)).intervalIntersects(otherMoonAlias)
        new MoonAlias(name: "testWithin", begin: now.plus(1, ChronoUnit.HOURS), end: now.plus(3, ChronoUnit.HOURS)).intervalIntersects(otherMoonAlias)
        new MoonAlias(name: "testEncloses", begin: now.minus(1, ChronoUnit.HOURS), end: now.plus(5, ChronoUnit.HOURS)).intervalIntersects(otherMoonAlias)

        !new MoonAlias(name: "testBeforeWithGap", begin: now.minus(4, ChronoUnit.HOURS), end: now.minus(1, ChronoUnit.HOURS)).intervalIntersects(otherMoonAlias)
        !new MoonAlias(name: "testBeforeWithoutGap", begin: now.minus(4, ChronoUnit.HOURS), end: now).intervalIntersects(otherMoonAlias)
        !new MoonAlias(name: "testAfterWithoutGap", begin: now.plus(4, ChronoUnit.HOURS), end: now.plus(7, ChronoUnit.HOURS)).intervalIntersects(otherMoonAlias)
        !new MoonAlias(name: "testAfterWithGap", begin: now.plus(5, ChronoUnit.HOURS), end: now.plus(7, ChronoUnit.HOURS)).intervalIntersects(otherMoonAlias)
    }

    void "moon alias interval"() {
        given: "a moon alias"
        def now = Instant.now()
        def moonAlias = new MoonAlias(name: "test", begin: now, end: now.plus(4, ChronoUnit.HOURS))

        expect:
        moonAlias.inInterval(now)
        moonAlias.inInterval(now.plus(2, ChronoUnit.HOURS))
        !moonAlias.inInterval(now.plus(4, ChronoUnit.HOURS))
    }

    void "test to string"() {
        given: "the dates"
        def now = Instant.now()
        def begin = now
        def end = now.plus(1, ChronoUnit.HOURS)

        expect:
        new MoonAlias(name: "Moon", begin: begin, end: end).toString() == "MoonAlias(null, Moon, $begin, $end)".toString()
    }
}
