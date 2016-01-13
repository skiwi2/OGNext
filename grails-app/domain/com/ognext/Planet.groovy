package com.ognext

import groovy.transform.EqualsAndHashCode

import java.time.Instant

@EqualsAndHashCode(includes = ["player", "universe", "planetId"])
class Planet {
    def planetService

    static constraints = {
        universe validator: { universe, object ->
            universe == object.player.universe
        }
        planetId unique: "universe"
        locations validator: { locations, object ->
            if (!locations) {
                return true
            }
            def addedLocation = locations[-1]
            locations[0..<-1].every { !it.intervalIntersects(addedLocation) } &&
                Planet.findAllByUniverse(object.universe).stream()
                    .filter({ it != object })
                    .flatMap({ it.locations.stream() })
                    .filter({location -> location.coordinate == addedLocation.coordinate})
                    .allMatch { !it.intervalIntersects(addedLocation) }
        }
        aliases validator: { aliases, object ->
            if (!aliases) {
                return true
            }
            def addedAlias = aliases[-1]
            aliases[0..<-1].every { !it.intervalIntersects(addedAlias) }
        }
        dateDeleted nullable: true
    }

    Player player
    Universe universe
    Integer planetId
    List<PlanetLocation> locations
    List<PlanetAlias> aliases
    Boolean deleted = false
    Date dateDeleted

    PlanetBuildings buildings
    Defences defences
    Fleet fleet

    Date dateCreated
    Date lastUpdated

    static hasMany = [
        locations: PlanetLocation,
        aliases: PlanetAlias
    ]

    def beforeValidate() {
        universe = player?.universe
    }

    Coordinate getCoordinateAt(Instant instant) {
        planetService.getPlanetLocation(this, instant).coordinate
    }

    Coordinate getCurrentCoordinate() {
        getCoordinateAt(Instant.now())
    }

    String getCurrentName() {
        getNameAt(Instant.now())
    }

    String getNameAt(Instant instant) {
        planetService.getPlanetAlias(this, instant).name
    }

    @Override
    String toString() {
        "${this.class.simpleName}($id, $player, $planetId)"
    }
}
