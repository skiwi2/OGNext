package com.ognext

import grails.transaction.Transactional

@Transactional
class CoordinateService {
    Coordinate getCoordinate(Universe universe, int galaxy, int solarSystem, int position) {
        Coordinate.findOrSaveByUniverseAndGalaxyAndSolarSystemAndPosition(universe, galaxy, solarSystem, position)
    }
}
