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

        def serverCountryCode = json.server
        def universeId = json.universe.toInteger()
        def universe = universeService.getOrCreateUniverse(serverCountryCode, universeId)

        def playerId = json.playerId.toInteger()
        def playerName = json.playerName
        def player = playerService.getOrCreatePlayer(universe, playerId, playerName)

        json.reportKeys.sr.each { reportKeyService.addOrGetSpyReport(player, it) }
        json.reportKeys.cr.each { reportKeyService.addOrGetCombatReport(player, it) }
        json.reportKeys.rr.each { reportKeyService.addOrGetRecycleReport(player, it) }
        json.reportKeys.mr.each { reportKeyService.addOrGetMissileReport(player, it) }

        render(contentType: "application/json") {
            result(success: true)
        }
    }
}