package com.skiwi.olog.api

class UserscriptController {
    def universeService
    def playerService
    def reportKeyService

    static allowedMethods = [
        keys: "POST"
    ]

    def keys() {
        def json = request.JSON

        def serverGroupCountryCode = json.serverGroup
        def universeId = json.universe.toInteger()
        def universe = universeService.getUniverse(serverGroupCountryCode, universeId)

        def playerId = json.playerId.toInteger()
        def playerName = json.playerName
        def player = playerService.findPlayer(universe, playerId) ?: playerService.createPlayer(universe, playerId, playerName)

        json.reportKeys.sr.each { reportKeyService.addSpyReport(player, it) }
        json.reportKeys.cr.each { reportKeyService.addCombatReport(player, it) }
        json.reportKeys.rr.each { reportKeyService.addRecycleReport(player, it) }
        json.reportKeys.mr.each { reportKeyService.addMissileReport(player, it) }

        render(contentType: "application/json") {
            result(success: true)
        }
    }
}