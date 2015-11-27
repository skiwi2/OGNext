package com.skiwi.olog

import grails.transaction.Transactional

@Transactional
class PlanetService {
    def coordinateService

    Planet getOrCreatePlanet(Player player, int galaxy, int solarSystem, int position) {
        def coordinate = coordinateService.getOrCreateCoordinate(galaxy, solarSystem, position)
        Planet.findOrSaveByPlayerAndCoordinate(player, coordinate)
    }
}
