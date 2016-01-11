package com.ognext

class MoonBuildings {
    static constraints = {
        solarSatellite min: 0
        metalStorage min: 0
        crystalStorage min: 0
        deuteriumTank min: 0
        roboticsFactory min: 0
        shipyard min: 0
        lunarBase min: 0
        sensorPhalanx min: 0
        jumpGate min: 0
    }

    Integer solarSatellite
    Integer metalStorage
    Integer crystalStorage
    Integer deuteriumTank
    Integer roboticsFactory
    Integer shipyard
    Integer lunarBase
    Integer sensorPhalanx
    Integer jumpGate

    Date dateCreated
    Date lastUpdated
}
