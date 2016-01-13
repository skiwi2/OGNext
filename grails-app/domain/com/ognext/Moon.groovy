package com.ognext

import groovy.transform.EqualsAndHashCode

import java.time.Instant

@EqualsAndHashCode(includes = ["player", "universe", "moonId"])
class Moon {
    def moonService

    static constraints = {
        universe validator: { universe, object ->
            if (universe != object.player.universe) {
                'universeDoesNotMatch'
            }
        }
        moonId unique: "universe"
        locations validator: { locations, object ->
            if (!locations) {
                return true
            }
            def addedLocation = locations[-1]
            def locationIntersects = locations[0..<-1].any { it.intervalIntersects(addedLocation) } ||
                Moon.findAllByUniverse(object.universe).stream()
                    .filter({ it != object })
                    .flatMap({ it.locations.stream() })
                    .filter({location -> location.coordinate == addedLocation.coordinate})
                    .anyMatch { it.intervalIntersects(addedLocation) }
            if (locationIntersects) {
                'locationIntersects'
            }
        }
        aliases validator: { aliases, object ->
            if (!aliases) {
                return true
            }
            def addedAlias = aliases[-1]
            def aliasIntersects = aliases[0..<-1].any { it.intervalIntersects(addedAlias) }
            if (aliasIntersects) {
                'aliasIntersects'
            }
        }
        dateDeleted nullable: true
    }

    Player player
    Universe universe
    Integer moonId
    List<MoonLocation> locations
    List<MoonAlias> aliases
    Boolean deleted = false
    Date dateDeleted

    MoonBuildings buildings
    Defences defences
    Fleet fleet

    Date dateCreated
    Date lastUpdated

    static hasMany = [
        locations: MoonLocation,
        aliases: MoonAlias
    ]

    def beforeValidate() {
        universe = player?.universe
    }

    Coordinate getCoordinateAt(Instant instant) {
        moonService.getMoonLocation(this, instant).coordinate
    }

    Coordinate getCurrentCoordinate() {
        getCoordinateAt(Instant.now())
    }

    String getCurrentName() {
        getNameAt(Instant.now())
    }

    String getNameAt(Instant instant) {
        moonService.getMoonAlias(this, instant).name
    }

    @Override
    String toString() {
        "${this.class.simpleName}($id, $player, $moonId)"
    }
}
