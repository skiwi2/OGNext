package com.skiwi.olog

import grails.transaction.Transactional

@Transactional
class CoordinateService {
    Coordinate getOrCreateCoordinate(int galaxy, int solarSystem, int position) {
        Coordinate.findOrSaveByGalaxyAndSolarSystemAndPosition(galaxy, solarSystem, position)
    }
}
