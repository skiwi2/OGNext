package com.ognext

import grails.transaction.Transactional

@Transactional
class ResearchesService {
    Researches createDefaultResearches() {
        def researches = new Researches(energyTechnology: 0, laserTechnology: 0, ionTechnology: 0, hyperspaceTechnology: 0, plasmaTechnology: 0, combustionDrive: 0, impulseDrive: 0, hyperspaceDrive: 0, espionageTechnology: 0, computerTechnology: 0, astrophysics: 0, intergalacticResearchNetwork: 0, gravitonTechnology: 0, weaponsTechnology: 0, shieldingTechnology: 0, armourTechnology: 0)
        researches.save()
    }

    void updatePlayerResearches(Player player, Integer energyTechnology, Integer laserTechnology, Integer ionTechnology, Integer hyperspaceTechnology, Integer plasmaTechnology, Integer combustionDrive, Integer impulseDrive, Integer hyperspaceDrive, Integer espionageTechnology, Integer computerTechnology, Integer astrophysics, Integer intergalacticResearchNetwork, Integer gravitonTechnology, Integer weaponsTechnology, Integer shieldingTechnology, Integer armourTechnology) {
        player.researches.energyTechnology = energyTechnology
        player.researches.laserTechnology = laserTechnology
        player.researches.ionTechnology = ionTechnology
        player.researches.hyperspaceTechnology = hyperspaceTechnology
        player.researches.plasmaTechnology = plasmaTechnology
        player.researches.combustionDrive = combustionDrive
        player.researches.impulseDrive = impulseDrive
        player.researches.hyperspaceDrive = hyperspaceDrive
        player.researches.espionageTechnology = espionageTechnology
        player.researches.computerTechnology = computerTechnology
        player.researches.astrophysics = astrophysics
        player.researches.intergalacticResearchNetwork = intergalacticResearchNetwork
        player.researches.gravitonTechnology = gravitonTechnology
        player.researches.weaponsTechnology = weaponsTechnology
        player.researches.shieldingTechnology = shieldingTechnology
        player.researches.armourTechnology = armourTechnology
        player.save()
    }
}
