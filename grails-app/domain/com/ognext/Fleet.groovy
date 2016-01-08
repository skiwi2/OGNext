package com.ognext

class Fleet {
    static constraints = {
        lightFighter min: 0
        heavyFighter min: 0
        cruiser min: 0
        battleship min: 0
        smallCargo min: 0
        largeCargo min: 0
        colonyShip min: 0
        battlecruiser min: 0
        bomber min: 0
        destroyer min: 0
        deathstar min: 0
        recycler min: 0
        espionageProbe min: 0
    }

    Integer lightFighter
    Integer heavyFighter
    Integer cruiser
    Integer battleship
    Integer smallCargo
    Integer largeCargo
    Integer colonyShip
    Integer battlecruiser
    Integer bomber
    Integer destroyer
    Integer deathstar
    Integer recycler
    Integer espionageProbe

    Date dateCreated
    Date lastUpdated
}
