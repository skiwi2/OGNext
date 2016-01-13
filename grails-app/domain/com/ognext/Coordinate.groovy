package com.ognext

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = ["universe", "galaxy", "solarSystem", "position"])
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

    Date dateCreated
    Date lastUpdated

    @Override
    String toString() {
        "${this.class.simpleName}($id, $universe, $galaxy, $solarSystem, $position)"
    }
}
