package com.skiwi.olog

import java.time.Instant

class PlanetLocation {
    static constraints = {
        end validator: { end, object -> end >= object.begin }
        coordinate unique: true
    }

    Coordinate coordinate
    Instant begin
    Instant end

    boolean intervalIntersects(PlanetLocation other) {
        begin < other.end && other.begin < end
    }

    boolean inInterval(Instant instant) {
        begin <= instant && instant < end
    }
}
