package com.ognext

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Coordinate)
class CoordinateSpec extends Specification {
    def serverGroup = new ServerGroup(countryCode: "en")
    def universe = new Universe(universeId: 135, serverGroup: serverGroup)
    def universe2 = new Universe(universeId: 136, serverGroup: serverGroup)

    def setup() {
    }

    def cleanup() {
    }

    void "save coordinates"() {
        expect:
        new Coordinate(universe: universe, galaxy: 2, solarSystem: 122, position: 12).save(failOnError: false)

        new Coordinate(universe: universe, galaxy: 1, solarSystem: 1, position: 1).save(failOnError: false)
        new Coordinate(universe: universe, galaxy: 9, solarSystem: 1, position: 1).save(failOnError: false)
        new Coordinate(universe: universe, galaxy: 1, solarSystem: 499, position: 1).save(failOnError: false)
        new Coordinate(universe: universe, galaxy: 1, solarSystem: 1, position: 15).save(failOnError: false)

        !new Coordinate(universe: universe, galaxy: 0, solarSystem: 122, position: 12).save(failOnError: false)
        !new Coordinate(universe: universe, galaxy: 10, solarSystem: 122, position: 12).save(failOnError: false)
        !new Coordinate(universe: universe, galaxy: 2, solarSystem: 0, position: 12).save(failOnError: false)
        !new Coordinate(universe: universe, galaxy: 2, solarSystem: 500, position: 12).save(failOnError: false)
        !new Coordinate(universe: universe, galaxy: 2, solarSystem: 122, position: 0).save(failOnError: false)
        !new Coordinate(universe: universe, galaxy: 2, solarSystem: 122, position: 16).save(failOnError: false)
    }

    void "test equals and hash code"() {
        new Coordinate(universe: universe, galaxy: 2, solarSystem: 122, position: 12) == new Coordinate(universe: universe, galaxy: 2, solarSystem: 122, position: 12)
        new Coordinate(universe: universe, galaxy: 2, solarSystem: 122, position: 12) != new Coordinate(universe: universe2, galaxy: 2, solarSystem: 122, position: 12)
        new Coordinate(universe: universe, galaxy: 2, solarSystem: 122, position: 12) != new Coordinate(universe: universe, galaxy: 3, solarSystem: 122, position: 12)
        new Coordinate(universe: universe, galaxy: 2, solarSystem: 122, position: 12) != new Coordinate(universe: universe, galaxy: 2, solarSystem: 123, position: 12)
        new Coordinate(universe: universe, galaxy: 2, solarSystem: 122, position: 12) != new Coordinate(universe: universe, galaxy: 2, solarSystem: 122, position: 13)
    }

    void "test to string"() {
        expect:
        new Coordinate(universe: universe, galaxy: 2, solarSystem: 122, position: 12).toString() == "Coordinate(null, Universe(null, en, 135), 2, 122, 12)"
    }
}
