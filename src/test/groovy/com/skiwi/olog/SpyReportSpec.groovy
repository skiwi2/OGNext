package com.skiwi.olog

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(SpyReport)
class SpyReportSpec extends Specification {
    def setup() {
    }

    def cleanup() {
    }

    void "save valid spy report"() {
        given: "a player"
        def player = new Player(id: 103168)

        when: "spy report is valid"
        def spyReport = new SpyReport(player: player, key: "sr-en-135-73536e717b84e3ebd7cc6c415a3b3675cc1af166")

        then: "spy report should be saved"
        spyReport.save()
    }
}
