package com.skiwi.olog

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ReportKeyService)
@Mock([SpyReport, CombatReport, RecycleReport, MissileReport, Player, PlayerAlias])
class ReportKeyServiceSpec extends Specification {
    Player player

    def setup() {
        def playerService = mockService(PlayerService)
        player = playerService.createPlayer(103168, "skiwi")
    }

    def cleanup() {
    }

    void "test add spy report"() {
        given: "a spy report key"
        def key = "sr-en-135-73536e717b84e3ebd7cc6c415a3b3675cc1af166"

        when: "add spy report"
        def spyReport = service.addSpyReport(player, key)

        then: "spy report should be added"
        spyReport
        spyReport.player == player
        spyReport.key == key
        SpyReport.findByKey(key) == spyReport
    }

    void "test add combat report"() {
        given: "a combat report key"
        def key = "cr-en-135-3be2512d98e266343c100f71d6c14b7a68e639f4"

        when: "add combat report"
        def combatReport = service.addCombatReport(player, key)

        then: "combat report should be added"
        combatReport
        combatReport.player == player
        combatReport.key == key
        CombatReport.findByKey(key) == combatReport
    }

    void "test add recycle report"() {
        given: "a recycle report key"
        def key = "rr-en-135-3105606a1fdb509f9f51459b5ddf0d36afc8a074"

        when: "add recycle report"
        def recycleReport = service.addRecycleReport(player, key)

        then: "recycle report should be added"
        recycleReport
        recycleReport.player == player
        recycleReport.key == key
        RecycleReport.findByKey(key) == recycleReport
    }

    void "test add missile report"() {
        given: "a missile report key"
        def key = "mr-en-135-b47906dc63fafa3c47185e09c23908945c802781"

        when: "add missile report"
        def missileReport = service.addMissileReport(player, key)

        then: "missile report should be added"
        missileReport
        missileReport.player == player
        missileReport.key == key
        MissileReport.findByKey(key) == missileReport
    }
}
