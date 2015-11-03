package com.skiwi.olog.api

import com.skiwi.olog.*
import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(UserscriptController)
@Mock([Player, PlayerAlias, SpyReport, CombatReport, RecycleReport, MissileReport])
@TestMixin(GrailsUnitTestMixin)
class UserscriptControllerSpec extends Specification {
    PlayerService playerService

    static doWithSpring = {
        playerService(PlayerService)
        reportKeyService(ReportKeyService)
    }

    def setup() {
        playerService = grailsApplication.mainContext.getBean("playerService")
    }

    def cleanup() {
    }

    def "post keys"() {
        when: "post keys request has been made"
        request.json = [
            server: "en",
            universe: 135,
            playerId: 103168,
            playerName: "skiwi",
            reportKeys: [
                sr: [
                    "sr-key-1",
                    "sr-key-2"
                ],
                cr: [
                    "cr-key-1",
                    "cr-key-2",
                    "cr-key-3"
                ],
                rr: [
                    "rr-key-1",
                    "rr-key-2",
                    "rr-key-3",
                    "rr-key-4"
                ],
                mr: [
                    "mr-key-1",
                    "mr-key-2",
                    "mr-key-3",
                    "mr-key-4",
                    "mr-key-5"
                ]
            ]
        ] as JSON
        request.method = "POST"
        request.contentType = "text/json"
        controller.keys()

        then: "keys should be persisted"
        response.json.result.success == true
        def player = playerService.getOrCreatePlayer(103168, "skiwi")
        SpyReport.findByKeyAndPlayer("sr-key-1", player)
        SpyReport.findByKeyAndPlayer("sr-key-2", player)
        CombatReport.findByKeyAndPlayer("cr-key-1", player)
        CombatReport.findByKeyAndPlayer("cr-key-2", player)
        CombatReport.findByKeyAndPlayer("cr-key-3", player)
        RecycleReport.findByKeyAndPlayer("rr-key-1", player)
        RecycleReport.findByKeyAndPlayer("rr-key-2", player)
        RecycleReport.findByKeyAndPlayer("rr-key-3", player)
        RecycleReport.findByKeyAndPlayer("rr-key-4", player)
        MissileReport.findByKeyAndPlayer("mr-key-1", player)
        MissileReport.findByKeyAndPlayer("mr-key-2", player)
        MissileReport.findByKeyAndPlayer("mr-key-3", player)
        MissileReport.findByKeyAndPlayer("mr-key-4", player)
        MissileReport.findByKeyAndPlayer("mr-key-5", player)
    }
}
