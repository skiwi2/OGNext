package com.ognext

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(MissileReport)
class MissileReportSpec extends Specification {
    def player = new Player(playerId: 103168)
    def player2 = new Player(playerId: 103169)
    def key = "mr-en-135-b47906dc63fafa3c47185e09c23908945c802781"
    def key2 = "mr-en-136-b47906dc63fafa3c47185e09c23908945c802781"

    def setup() {
    }

    def cleanup() {
    }

    void "save valid missile report"() {
        when: "missile report is valid"
        def missileReport = new MissileReport(player: player, key: key)

        then: "missile report should be saved"
        missileReport.save()
    }

    void "key should be unique"() {
        when: "missile reports have the same key"
        def missileReport = new MissileReport(player: player, key: key)
        def missileReport2 = new MissileReport(player: player, key: key)

        then: "second missile report should not be saved"
        missileReport.save(flush: true)
        !missileReport2.save(failOnError: false)
    }

    void "test equals and hash code"() {
        expect:
        new MissileReport(player: player, key: key) == new MissileReport(player: player, key: key)
        new MissileReport(player: player, key: key) != new MissileReport(player: player2, key: key)
        new MissileReport(player: player, key: key) != new MissileReport(player: player, key: key2)
    }

    void "test to string"() {
        expect:
        new MissileReport(player: player, key: key).toString() == "MissileReport(null, Player(null, null, 103168), mr-en-135-b47906dc63fafa3c47185e09c23908945c802781)"
    }
}
