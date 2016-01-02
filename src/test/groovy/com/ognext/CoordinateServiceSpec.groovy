package com.ognext

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(CoordinateService)
@Mock(Coordinate)
class CoordinateServiceSpec extends Specification {
    def serverGroup = new ServerGroup(countryCode: "en")
    def universe = new Universe(universeId: 135, serverGroup: serverGroup)
    def galaxy = 2
    def solarSystem = 122
    def position = 12

    def setup() {
    }

    def cleanup() {
    }

    void "test get coordinate"() {
        when: "get non-existing coordinate"
        def createdCoordinate = service.getCoordinate(universe, galaxy, solarSystem, position)

        then: "coordinate should be created"
        createdCoordinate
        createdCoordinate.universe == universe
        createdCoordinate.galaxy == galaxy
        createdCoordinate.solarSystem == solarSystem
        createdCoordinate.position == position
        Coordinate.findByUniverseAndGalaxyAndSolarSystemAndPosition(universe, galaxy, solarSystem, position) == createdCoordinate

        when: "get existing coordinate"
        def existingCoordinate = service.getCoordinate(universe, galaxy, solarSystem, position)

        then: "coordinate should exist"
        existingCoordinate
        existingCoordinate.universe == universe
        existingCoordinate.galaxy == galaxy
        existingCoordinate.solarSystem == solarSystem
        existingCoordinate.position == position
        Coordinate.findByUniverseAndGalaxyAndSolarSystemAndPosition(universe, galaxy, solarSystem, position) == existingCoordinate
    }
}
