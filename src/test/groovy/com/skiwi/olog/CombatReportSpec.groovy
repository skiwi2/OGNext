package com.skiwi.olog

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(CombatReport)
class CombatReportSpec extends Specification {
    def setup() {
    }

    def cleanup() {
    }

    void "save valid combat report"() {
        given: "a player"
        def player = new Player(id: 103168)

        when: "combat report is valid"
        def combatReport = new CombatReport(player: player, key: "cr-en-135-3be2512d98e266343c100f71d6c14b7a68e639f4")

        then: "combat report should be saved"
        combatReport.save()
    }
}
