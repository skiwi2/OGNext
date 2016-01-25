package com.ognext

class PlanetController {
    def index(FindUniverseCommand findUniverseCommand) {
        def planets = Planet.findAllByUniverse(findUniverseCommand.universe).sort { p1, p2 ->
            def p1c = p1.currentCoordinate
            def p2c = p2.currentCoordinate
            p1c.galaxy <=> p2c.galaxy ?: p1c.solarSystem <=> p2c.solarSystem ?: p1c.position <=> p2c.position
        }   //TODO bad for performance, handle in database
        [planets: planets]
    }
}
