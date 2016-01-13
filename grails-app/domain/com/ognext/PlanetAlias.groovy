package com.ognext

import java.time.Instant

class PlanetAlias {
    static constraints = {
        end validator: { end, object ->
            if (end < object.begin) {
                'invalidRange'
            }
        }
    }

    String name
    Instant begin
    Instant end

    Date dateCreated
    Date lastUpdated

    boolean intervalIntersects(PlanetAlias other) {
        begin < other.end && other.begin < end
    }

    boolean inInterval(Instant instant) {
        begin <= instant && instant < end
    }

    @Override
    String toString() {
        "${this.class.simpleName}($id, $name, $begin, $end)"
    }
}
