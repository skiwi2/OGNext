package com.skiwi.olog

class Player {
    static constraints = {
        aliases validator: { aliases, object ->
            if (!aliases) {
                return true
            }
            def addedAlias = aliases[-1]
            aliases[0..<-1].every { !it.intervalIntersects(addedAlias) }
        }
    }

    static hasMany = [aliases: PlayerAlias]

    Integer playerId
    List<PlayerAlias> aliases
}
