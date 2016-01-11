package com.ognext

import grails.transaction.Transactional

@Transactional
class PlanetBuildingsService {
    PlanetBuildings createDefaultPlanetBuildings() {
        def planetBuildings = new PlanetBuildings(metalMine: 0, crystalMine: 0, deuteriumSynthesizer: 0, solarPlant: 0, fusionReactor: 0, solarSatellite: 0, metalStorage: 0, crystalStorage: 0, deuteriumTank: 0, roboticsFactory: 0, shipyard: 0, researchLab: 0, allianceDepot: 0, missileSilo: 0, naniteFactory: 0, terraformer: 0)
        planetBuildings.save()
    }

    void updatePlanetResourceBuildings(Planet planet, Integer metalMine, Integer crystalMine, Integer deuteriumSynthesizer, Integer solarPlant, Integer fusionReactor, Integer solarSatellite, Integer metalStorage, Integer crystalStorage, Integer deuteriumTank) {
        planet.buildings.metalMine = metalMine
        planet.buildings.crystalMine = crystalMine
        planet.buildings.deuteriumSynthesizer = deuteriumSynthesizer
        planet.buildings.solarPlant = solarPlant
        planet.buildings.fusionReactor = fusionReactor
        planet.buildings.solarSatellite = solarSatellite
        planet.buildings.metalStorage = metalStorage
        planet.buildings.crystalStorage = crystalStorage
        planet.buildings.deuteriumTank = deuteriumTank
        planet.save()
    }

    void updatePlanetFacilityBuildings(Planet planet, Integer roboticsFactory, Integer shipyard, Integer researchLab, Integer allianceDepot, Integer missileSilo, Integer naniteFactory, Integer terraformer) {
        planet.buildings.roboticsFactory = roboticsFactory
        planet.buildings.shipyard = shipyard
        planet.buildings.researchLab = researchLab
        planet.buildings.allianceDepot = allianceDepot
        planet.buildings.missileSilo = missileSilo
        planet.buildings.naniteFactory = naniteFactory
        planet.buildings.terraformer = terraformer
        planet.save()
    }

    void updatePlanetSolarSatellite(Planet planet, Integer solarSatellite) {
        planet.buildings.solarSatellite = solarSatellite
        planet.save()
    }
}
