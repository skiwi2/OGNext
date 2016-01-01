package com.ognext

class SpyReportController {
    def index(FindPlayerCommand findPlayerCommand) {
        def spyReports = SpyReport.findAllByPlayer(findPlayerCommand.player)
        [spyReports: spyReports]
    }
}

class FindPlayerCommand {
    String serverGroupCountryCode
    Integer universeId
    Integer playerId

    Player getPlayer() {
        def serverGroup = ServerGroup.findByCountryCode(serverGroupCountryCode)
        def universe = Universe.findByServerGroupAndUniverseId(serverGroup, universeId)
        Player.findByUniverseAndPlayerId(universe, playerId)
    }
}