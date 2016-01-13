package com.ognext

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(RecycleReport)
class RecycleReportSpec extends Specification {
    def player = new Player(playerId: 103168)
    def player2 = new Player(playerId: 103169)
    def key = "rr-en-135-3105606a1fdb509f9f51459b5ddf0d36afc8a074"
    def key2 = "rr-en-136-3105606a1fdb509f9f51459b5ddf0d36afc8a074"

    def setup() {
    }

    def cleanup() {
    }

    void "save valid recycle report"() {
        when: "recycle report is valid"
        def recycleReport = new RecycleReport(player: player, key: key)

        then: "recycle report should be saved"
        recycleReport.save()
    }

    void "key should be unique"() {
        when: "recycle reports have the same key"
        def recycleReport = new RecycleReport(player: player, key: key)
        def recycleReport2 = new RecycleReport(player: player, key: key)

        then: "second recycle report should not be saved"
        recycleReport.save(flush: true)
        !recycleReport2.save(failOnError: false)
    }

    void "test equals and hash code"() {
        expect:
        new RecycleReport(player: player, key: key) == new RecycleReport(player: player, key: key)
        new RecycleReport(player: player, key: key) != new RecycleReport(player: player2, key: key)
        new RecycleReport(player: player, key: key) != new RecycleReport(player: player, key: key2)
    }

    void "test to string"() {
        expect:
        new RecycleReport(player: player, key: key).toString() == "RecycleReport(null, Player(null, null, 103168), rr-en-135-3105606a1fdb509f9f51459b5ddf0d36afc8a074)"
    }
}
