package com.ognext

class MoonController {
    def index(FindUniverseCommand findUniverseCommand) {
        def moons = Moon.findAllByUniverse(findUniverseCommand.universe).sort { m1, m2 ->
            def m1c = m1.currentCoordinate
            def m2c = m2.currentCoordinate
            m1c.galaxy <=> m2c.galaxy ?: m1c.solarSystem <=> m2c.solarSystem ?: m1c.position <=> m2c.position
        }   //TODO bad for performance, handle in database
        [moons: moons]
    }
}
