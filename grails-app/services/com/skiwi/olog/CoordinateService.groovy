package com.skiwi.olog

import grails.transaction.Transactional

@Transactional
class CoordinateService {
    Coordinate getOrCreateCoordinate(Universe universe, int galaxy, int solarSystem, int position) {
        Coordinate.findOrSaveByUniverseAndGalaxyAndSolarSystemAndPosition(universe, galaxy, solarSystem, position)
    }
}
