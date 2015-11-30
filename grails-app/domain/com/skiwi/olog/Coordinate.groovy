package com.skiwi.olog

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Coordinate {
    static constraints = {
        universe unique: ["galaxy", "solarSystem", "position"]
        galaxy range: 1..9
        solarSystem range: 1..499
        position range: 1..15
    }

    Universe universe
    Integer galaxy
    Integer solarSystem
    Integer position
}
