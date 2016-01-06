package com.ognext

class Defences {
    static constraints = {
        rocketLauncher min: 0
        lightLaser min: 0
        heavyLaser min: 0
        gaussCannon min: 0
        ionCannon min: 0
        plasmaTurret min: 0
        smallShieldDome range: 0..1
        largeShieldDome range: 0..1
        antiBallisticMissiles min: 0
        interplanetaryMissiles min: 0
    }

    Integer rocketLauncher
    Integer lightLaser
    Integer heavyLaser
    Integer gaussCannon
    Integer ionCannon
    Integer plasmaTurret
    Integer smallShieldDome
    Integer largeShieldDome
    Integer antiBallisticMissiles
    Integer interplanetaryMissiles

    Date dateCreated
    Date lastUpdated
}
