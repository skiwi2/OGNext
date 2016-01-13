package com.ognext

import grails.test.mixin.TestFor
import spock.lang.Specification

import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(PlayerAlias)
class PlayerAliasSpec extends Specification {
    def setup() {
    }

    def cleanup() {
    }

    void "save valid player alias"() {
        when: "player alias is valid"
        def now = Instant.now()
        def playerAlias = new PlayerAlias(name: "skiwi", begin: now, end: now.plus(1, ChronoUnit.HOURS))

        then: "player alias should be saved"
        playerAlias.save()
    }

    void "save player alias with end before start"() {
        when: "player alias is invalid"
        def now = Instant.now()
        def playerAlias = new PlayerAlias(name: "skiwi", begin: now, end: now.minus(1, ChronoUnit.HOURS))

        then: "player alias should not be saved"
        !playerAlias.save(failOnError: false)
    }

    void "player alias intersects"() {
        given: "a player alias"
        def now = Instant.now()
        def otherPlayerAlias = new PlayerAlias(name: "test", begin: now, end:  now.plus(4, ChronoUnit.HOURS))

        expect:
        new PlayerAlias(name: "testBefore", begin: now.minus(1, ChronoUnit.HOURS), end:  now.plus(1, ChronoUnit.HOURS)).intervalIntersects(otherPlayerAlias)
        new PlayerAlias(name: "testAfter", begin: now.plus(2, ChronoUnit.HOURS), end:  now.plus(6, ChronoUnit.HOURS)).intervalIntersects(otherPlayerAlias)
        new PlayerAlias(name: "testEqual", begin:  now, end:  now.plus(4, ChronoUnit.HOURS)).intervalIntersects(otherPlayerAlias)
        new PlayerAlias(name: "testWithin", begin: now.plus(1, ChronoUnit.HOURS), end: now.plus(3, ChronoUnit.HOURS)).intervalIntersects(otherPlayerAlias)
        new PlayerAlias(name: "testEncloses", begin: now.minus(1, ChronoUnit.HOURS), end: now.plus(5, ChronoUnit.HOURS)).intervalIntersects(otherPlayerAlias)

        !new PlayerAlias(name: "testBeforeWithGap", begin: now.minus(4, ChronoUnit.HOURS), end: now.minus(1, ChronoUnit.HOURS)).intervalIntersects(otherPlayerAlias)
        !new PlayerAlias(name: "testBeforeWithoutGap", begin: now.minus(4, ChronoUnit.HOURS), end: now).intervalIntersects(otherPlayerAlias)
        !new PlayerAlias(name: "testAfterWithoutGap", begin: now.plus(4, ChronoUnit.HOURS), end: now.plus(7, ChronoUnit.HOURS)).intervalIntersects(otherPlayerAlias)
        !new PlayerAlias(name: "testAfterWithGap", begin: now.plus(5, ChronoUnit.HOURS), end: now.plus(7, ChronoUnit.HOURS)).intervalIntersects(otherPlayerAlias)
    }

    void "player alias interval"() {
        given: "a player alias"
        def now = Instant.now()
        def playerAlias = new PlayerAlias(name: "test", begin: now, end: now.plus(4, ChronoUnit.HOURS))

        expect:
        playerAlias.inInterval(now)
        playerAlias.inInterval(now.plus(2, ChronoUnit.HOURS))
        !playerAlias.inInterval(now.plus(4, ChronoUnit.HOURS))
    }

    void "test to string"() {
        given: "the dates"
        def now = Instant.now()
        def begin = now
        def end = now.plus(1, ChronoUnit.HOURS)

        expect:
        new PlayerAlias(name: "skiwi", begin: begin, end: end).toString() == "PlayerAlias(null, skiwi, $begin, $end)".toString()
    }
}
