package com.skiwi.olog

class Planet {
    static constraints = {
        universe validator: { universe, object ->
            universe == object.player.universe
        }
        coordinate unique: ["universe", "coordinate"]
    }

    Player player
    Universe universe
    Coordinate coordinate

    def beforeValidate() {
        universe = player?.universe
    }
}
