package com.skiwi.olog

import java.time.Instant

class PlayerAlias {
    static constraints = {
        end validator: { end, object -> end >= object.begin }
    }

    String name
    Instant begin
    Instant end

    Date dateCreated
    Date lastUpdated

    boolean intervalIntersects(PlayerAlias other) {
        begin < other.end && other.begin < end
    }

    boolean inInterval(Instant instant) {
        begin <= instant && instant < end
    }
}
