package com.skiwi.olog

import java.time.Instant

class PlayerAlias {
    static constraints = {
        end validator: { end, object -> end >= object.begin }
    }

    String name
    Instant begin
    Instant end

    boolean intervalIntersects(PlayerAlias other) {
        begin < other.end && other.begin < end
    }
}
