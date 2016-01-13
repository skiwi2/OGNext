package com.ognext

import java.time.Instant

class MoonLocation {
    static constraints = {
        end validator: { end, object ->
            if (end < object.begin) {
                'invalidRange'
            }
        }
        coordinate unique: true
    }

    Coordinate coordinate
    Instant begin
    Instant end

    Date dateCreated
    Date lastUpdated

    boolean intervalIntersects(MoonLocation other) {
        begin < other.end && other.begin < end
    }

    boolean inInterval(Instant instant) {
        begin <= instant && instant < end
    }

    @Override
    String toString() {
        "${this.class.simpleName}($id, $coordinate, $begin, $end)"
    }
}
