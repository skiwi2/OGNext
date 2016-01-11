package com.ognext

class PlanetBuildings {
    static constraints = {
        metalMine min: 0
        crystalMine min: 0
        deuteriumSynthesizer min: 0
        solarPlant min: 0
        fusionReactor min: 0
        solarSatellite min: 0
        metalStorage min: 0
        crystalStorage min: 0
        deuteriumTank min: 0
        roboticsFactory min: 0
        shipyard min: 0
        researchLab min: 0
        allianceDepot min: 0
        missileSilo min: 0
        naniteFactory min: 0
        terraformer min: 0
    }

    Integer metalMine
    Integer crystalMine
    Integer deuteriumSynthesizer
    Integer solarPlant
    Integer fusionReactor
    Integer solarSatellite
    Integer metalStorage
    Integer crystalStorage
    Integer deuteriumTank
    Integer roboticsFactory
    Integer shipyard
    Integer researchLab
    Integer allianceDepot
    Integer missileSilo
    Integer naniteFactory
    Integer terraformer

    Date dateCreated
    Date lastUpdated
}
