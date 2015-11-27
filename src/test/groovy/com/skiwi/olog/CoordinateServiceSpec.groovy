package com.skiwi.olog

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(CoordinateService)
@Mock(Coordinate)
class CoordinateServiceSpec extends Specification {
    def galaxy = 2
    def solarSystem = 122
    def position = 12

    def setup() {
    }

    def cleanup() {
    }

    void "test get or create coordinate"() {
        when: "get non-existing coordinate"
        def createdCoordinate = service.getOrCreateCoordinate(galaxy, solarSystem, position)

        then: "coordinate should be created"
        createdCoordinate
        createdCoordinate.galaxy == galaxy
        createdCoordinate.solarSystem == solarSystem
        createdCoordinate.position == position
        Coordinate.findByGalaxyAndSolarSystemAndPosition(galaxy, solarSystem, position) == createdCoordinate

        when: "get existing coordinate"
        def existingCoordinate = service.getOrCreateCoordinate(galaxy, solarSystem, position)

        then: "coordinate should exist"
        existingCoordinate
        existingCoordinate.galaxy == galaxy
        existingCoordinate.solarSystem == solarSystem
        existingCoordinate.position == position
        Coordinate.findByGalaxyAndSolarSystemAndPosition(galaxy, solarSystem, position) == existingCoordinate
    }
}
