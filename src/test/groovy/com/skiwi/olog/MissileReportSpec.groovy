package com.skiwi.olog

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(MissileReport)
class MissileReportSpec extends Specification {
    def setup() {
    }

    def cleanup() {
    }

    void "save valid missile report"() {
        given: "a player"
        def player = new Player(id: "103168")

        when: "missile report is valid"
        def missileReport = new MissileReport(player: player, key: "mr-en-135-b47906dc63fafa3c47185e09c23908945c802781")

        then: "missile report should be saved"
        missileReport.save()
    }
}
