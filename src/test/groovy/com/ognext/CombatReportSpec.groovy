package com.ognext

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(CombatReport)
class CombatReportSpec extends Specification {
    def player = new Player(playerId: 103168)
    def player2 = new Player(playerId: 103169)
    def key = "cr-en-135-3be2512d98e266343c100f71d6c14b7a68e639f4"
    def key2 = "cr-en-136-3be2512d98e266343c100f71d6c14b7a68e639f4"

    def setup() {
    }

    def cleanup() {
    }

    void "save valid combat report"() {
        when: "combat report is valid"
        def combatReport = new CombatReport(player: player, key: key)

        then: "combat report should be saved"
        combatReport.save()
    }

    void "key should be unique"() {
        when: "combat reports have the same key"
        def combatReport = new CombatReport(player: player, key: key)
        def combatReport2 = new CombatReport(player: player, key: key)

        then: "second combat report should not be saved"
        combatReport.save(flush: true)
        !combatReport2.save(failOnError: false)
    }

    void "test equals and hash code"() {
        expect:
        new CombatReport(player: player, key: key) == new CombatReport(player: player, key: key)
        new CombatReport(player: player, key: key) != new CombatReport(player: player2, key: key)
        new CombatReport(player: player, key: key) != new CombatReport(player: player, key: key2)
    }

    void "test to string"() {
        expect:
        new CombatReport(player: player, key: key).toString() == "CombatReport(null, Player(null, null, 103168), cr-en-135-3be2512d98e266343c100f71d6c14b7a68e639f4)"
    }
}
