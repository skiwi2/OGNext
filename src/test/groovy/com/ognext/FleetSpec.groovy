package com.ognext

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Fleet)
class FleetSpec extends Specification {
    def setup() {
    }

    def cleanup() {
    }

    void "save valid fleet"() {
        when: "fleet is valid"
        def fleet = new Fleet(lightFighter: 0, heavyFighter: 0, cruiser: 106, battleship: 14, smallCargo: 49, largeCargo: 19, colonyShip: 0, battlecruiser: 0, bomber: 5, destroyer: 0, deathstar: 0, recycler: 20, espionageProbe: 9)

        then: "fleet should be saved"
        fleet.save()
    }

    void "save default fleet"() {
        when: "fleet has default values"
        def fleet = new Fleet(lightFighter: 0, heavyFighter: 0, cruiser: 0, battleship: 0, smallCargo: 0, largeCargo: 0, colonyShip: 0, battlecruiser: 0, bomber: 0, destroyer: 0, deathstar: 0, recycler: 0, espionageProbe: 0)

        then: "fleet should be saved"
        fleet.save()
    }

    void "save invalid fleet"() {
        when: "fleet is invalid"
        def fleet = new Fleet(lightFighter: -1, heavyFighter: -1, cruiser: -1, battleship: -1, smallCargo: -1, largeCargo: -1, colonyShip: -1, battlecruiser: -1, bomber: -1, destroyer: -1, deathstar: -1, recycler: -1, espionageProbe: -1)

        then: "fleet should not be saved"
        !fleet.save(failOnError: false)
    }
}
