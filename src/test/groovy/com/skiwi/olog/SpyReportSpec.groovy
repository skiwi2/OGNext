package com.skiwi.olog

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(SpyReport)
class SpyReportSpec extends Specification {
    def player = new Player(id: 103168)
    def key = "sr-en-135-73536e717b84e3ebd7cc6c415a3b3675cc1af166"

    def setup() {
    }

    def cleanup() {
    }

    void "save valid spy report"() {
        when: "spy report is valid"
        def spyReport = new SpyReport(player: player, key: key)

        then: "spy report should be saved"
        spyReport.save()
    }

    void "key should be unique"() {
        when: "spy reports have the same key"
        def spyReport = new SpyReport(player: player, key: key)
        def spyReport2 = new SpyReport(player: player, key: key)

        then: "second spy report should not be saved"
        spyReport.save(flush: true)
        !spyReport2.save(failOnError: false)
    }
}
