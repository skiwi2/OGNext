package com.ognext

import grails.transaction.Transactional

@Transactional
class MoonBuildingsService {
    MoonBuildings createDefaultMoonBuildings() {
        def moonBuildings = new MoonBuildings(solarSatellite: 0, metalStorage: 0, crystalStorage: 0, deuteriumTank: 0, roboticsFactory: 0, shipyard: 0, lunarBase: 0, sensorPhalanx: 0, jumpGate: 0)
        moonBuildings.save()
    }

    void updateMoonResourceBuildings(Moon moon, Integer solarSatellite, Integer metalStorage, Integer crystalStorage, Integer deuteriumTank) {
        moon.buildings.solarSatellite = solarSatellite
        moon.buildings.metalStorage = metalStorage
        moon.buildings.crystalStorage = crystalStorage
        moon.buildings.deuteriumTank = deuteriumTank
        moon.save()
    }

    void updateMoonFacilityBuildings(Moon moon, Integer roboticsFactory, Integer shipyard, Integer lunarBase, Integer sensorPhalanx, Integer jumpGate) {
        moon.buildings.roboticsFactory = roboticsFactory
        moon.buildings.shipyard = shipyard
        moon.buildings.lunarBase = lunarBase
        moon.buildings.sensorPhalanx = sensorPhalanx
        moon.buildings.jumpGate = jumpGate
        moon.save()
    }

    void updateMoonSolarSatellite(Moon moon, Integer solarSatellite) {
        moon.buildings.solarSatellite = solarSatellite
        moon.save()
    }
}
