package com.ognext

class PlayerController {
    def index(FindUniverseCommand findUniverseCommand) {
        def players = Player.findAllByUniverse(findUniverseCommand.universe).sort { it.currentName }    //TODO bad for performance, handle in database
        [players: players]
    }
}

class FindUniverseCommand {
    String serverGroupCountryCode
    Integer universeId

    Universe getUniverse() {
        def serverGroup = ServerGroup.findByCountryCode(serverGroupCountryCode)
        Universe.findByServerGroupAndUniverseId(serverGroup, universeId)
    }
}