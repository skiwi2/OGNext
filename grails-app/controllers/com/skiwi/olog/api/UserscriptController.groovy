package com.skiwi.olog.api

class UserscriptController {
    def playerService
    def reportKeyService

    static allowedMethods = [
        keys: "POST"
    ]

    def keys() {
        def json = request.JSON

        def playerId = json.playerId.toInteger()
        def playerName = json.playerName
        def player = playerService.getOrCreatePlayer(playerId, playerName)

        json.reportKeys.sr.each { reportKeyService.addOrGetSpyReport(player, it) }
        json.reportKeys.cr.each { reportKeyService.addOrGetCombatReport(player, it) }
        json.reportKeys.rr.each { reportKeyService.addOrGetRecycleReport(player, it) }
        json.reportKeys.mr.each { reportKeyService.addOrGetMissileReport(player, it) }

        render(contentType: "application/json") {
            result(success: true)
        }
    }
}