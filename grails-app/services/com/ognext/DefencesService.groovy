package com.ognext

import grails.transaction.Transactional

@Transactional
class DefencesService {
    Defences createDefaultDefences() {
        def defences = new Defences(rocketLauncher: 0, lightLaser: 0, heavyLaser: 0, gaussCannon: 0, ionCannon: 0, plasmaTurret: 0, smallShieldDome: 0, largeShieldDome: 0, antiBallisticMissiles: 0, interplanetaryMissiles: 0)
        defences.save()
    }

    void updatePlanetDefences(Planet planet, Integer rocketLauncher, Integer lightLaser, Integer heavyLaser, Integer gaussCannon, Integer ionCannon, Integer plasmaTurret, Integer smallShieldDome, Integer largeShieldDome, Integer antiBallisticMissiles, Integer interplanetaryMissiles) {
        planet.defences.rocketLauncher = rocketLauncher
        planet.defences.lightLaser = lightLaser
        planet.defences.heavyLaser = heavyLaser
        planet.defences.gaussCannon = gaussCannon
        planet.defences.ionCannon = ionCannon
        planet.defences.plasmaTurret = plasmaTurret
        planet.defences.smallShieldDome = smallShieldDome
        planet.defences.largeShieldDome = largeShieldDome
        planet.defences.antiBallisticMissiles = antiBallisticMissiles
        planet.defences.interplanetaryMissiles = interplanetaryMissiles
        planet.save()
    }

    void updateMoonDefences(Moon moon, Integer rocketLauncher, Integer lightLaser, Integer heavyLaser, Integer gaussCannon, Integer ionCannon, Integer plasmaTurret, Integer smallShieldDome, Integer largeShieldDome, Integer antiBallisticMissiles, Integer interplanetaryMissiles) {
        moon.defences.rocketLauncher = rocketLauncher
        moon.defences.lightLaser = lightLaser
        moon.defences.heavyLaser = heavyLaser
        moon.defences.gaussCannon = gaussCannon
        moon.defences.ionCannon = ionCannon
        moon.defences.plasmaTurret = plasmaTurret
        moon.defences.smallShieldDome = smallShieldDome
        moon.defences.largeShieldDome = largeShieldDome
        moon.defences.antiBallisticMissiles = antiBallisticMissiles
        moon.defences.interplanetaryMissiles = interplanetaryMissiles
        moon.save()
    }
}
