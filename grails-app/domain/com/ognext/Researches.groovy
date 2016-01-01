package com.ognext

class Researches {
    static constraints = {
        energyTechnology min: 0
        laserTechnology min: 0
        ionTechnology min: 0
        hyperspaceTechnology min: 0
        plasmaTechnology min: 0
        combustionDrive min: 0
        impulseDrive min: 0
        hyperspaceDrive min: 0
        espionageTechnology min: 0
        computerTechnology min: 0
        astrophysics min: 0
        intergalacticResearchNetwork min: 0
        gravitonTechnology min: 0
        weaponsTechnology min: 0
        shieldingTechnology min: 0
        armourTechnology min: 0
    }

    Integer energyTechnology
    Integer laserTechnology
    Integer ionTechnology
    Integer hyperspaceTechnology
    Integer plasmaTechnology
    Integer combustionDrive
    Integer impulseDrive
    Integer hyperspaceDrive
    Integer espionageTechnology
    Integer computerTechnology
    Integer astrophysics
    Integer intergalacticResearchNetwork
    Integer gravitonTechnology
    Integer weaponsTechnology
    Integer shieldingTechnology
    Integer armourTechnology

    Date dateCreated
    Date lastUpdated
}
