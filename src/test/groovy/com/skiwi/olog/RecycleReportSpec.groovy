package com.skiwi.olog

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(RecycleReport)
class RecycleReportSpec extends Specification {
    def setup() {
    }

    def cleanup() {
    }

    void "save valid recycle report"() {
        given: "a player"
        def player = new Player(id: 103168)

        when: "recycle report is valid"
        def recycleReport = new RecycleReport(player: player, key: "rr-en-135-3105606a1fdb509f9f51459b5ddf0d36afc8a074")

        then: "recycle report should be saved"
        recycleReport.save()
    }
}
