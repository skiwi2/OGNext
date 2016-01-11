package com.ognext

import grails.transaction.Transactional

@Transactional
class FleetService {
    Fleet createDefaultFleet() {
        def fleet = new Fleet(lightFighter: 0, heavyFighter: 0, cruiser: 0, battleship: 0, smallCargo: 0, largeCargo: 0, colonyShip: 0, battlecruiser: 0, bomber: 0, destroyer: 0, deathstar: 0, recycler: 0, espionageProbe: 0)
        fleet.save()
    }

    void updatePlanetFleet(Planet planet, Integer lightFighter, Integer heavyFighter, Integer cruiser, Integer battleship, Integer smallCargo, Integer largeCargo, Integer colonyShip, Integer battlecruiser, Integer bomber, Integer destroyer, Integer deathstar, Integer recycler, Integer espionageProbe) {
        planet.fleet.lightFighter = lightFighter
        planet.fleet.heavyFighter = heavyFighter
        planet.fleet.cruiser = cruiser
        planet.fleet.battleship = battleship
        planet.fleet.smallCargo = smallCargo
        planet.fleet.largeCargo = largeCargo
        planet.fleet.colonyShip = colonyShip
        planet.fleet.battlecruiser = battlecruiser
        planet.fleet.bomber = bomber
        planet.fleet.destroyer = destroyer
        planet.fleet.deathstar = deathstar
        planet.fleet.recycler = recycler
        planet.fleet.espionageProbe = espionageProbe
        planet.save()
    }

    void updateMoonFleet(Moon moon, Integer lightFighter, Integer heavyFighter, Integer cruiser, Integer battleship, Integer smallCargo, Integer largeCargo, Integer colonyShip, Integer battlecruiser, Integer bomber, Integer destroyer, Integer deathstar, Integer recycler, Integer espionageProbe) {
        moon.fleet.lightFighter = lightFighter
        moon.fleet.heavyFighter = heavyFighter
        moon.fleet.cruiser = cruiser
        moon.fleet.battleship = battleship
        moon.fleet.smallCargo = smallCargo
        moon.fleet.largeCargo = largeCargo
        moon.fleet.colonyShip = colonyShip
        moon.fleet.battlecruiser = battlecruiser
        moon.fleet.bomber = bomber
        moon.fleet.destroyer = destroyer
        moon.fleet.deathstar = deathstar
        moon.fleet.recycler = recycler
        moon.fleet.espionageProbe = espionageProbe
        moon.save()
    }
}
