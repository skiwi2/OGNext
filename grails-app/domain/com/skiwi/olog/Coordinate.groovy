package com.skiwi.olog

class Coordinate {
    static constraints = {
        galaxy range: 1..9
        solarSystem range: 1..499
        position range: 1..15
    }

    Integer galaxy
    Integer solarSystem
    Integer position

    def beforeValidate() {
        println "Validating $galaxy:$solarSystem:$position"
    }
}
