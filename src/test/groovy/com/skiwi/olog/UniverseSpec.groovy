package com.skiwi.olog

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Universe)
class UniverseSpec extends Specification {
    def setup() {
    }

    def cleanup() {
    }

    void "save valid universe"() {
        when: "universe is valid"
        def universe = new Universe(server: new Server(countryCode: "en"), universeId: 1, name: "1")

        then: "universe should be saved"
        universe.save()

        when: "universe has no name"
        def universe2 = new Universe(server: new Server(countryCode: "en"), universeId: 1)

        then: "universe should also be saved"
        universe2.save()
    }

    void "save two universes with same universeId on same server"() {
        when: "universes have same universeId on same server"
        def server = new Server(countryCode: "en")
        def universe = new Universe(server: server, universeId: 1, name: "1a")
        def universe2 = new Universe(server: server, universeId: 1, name: "1b")

        then: "second universe should not be saved"
        universe.save(flush: true)
        !universe2.save(failOnError: false)
    }

    void "save two universes with same name on same server"() {
        when: "universes have same name on same server"
        def server = new Server(countryCode: "en")
        def universe = new Universe(server: server, universeId: 1, name: "test")
        def universe2 = new Universe(server: server, universeId: 2, name: "test")

        then: "second universe should not be saved"
        universe.save(flush: true)
        !universe2.save(failOnError: false)
    }

    void "save two universes with same universeId and name on different servers"() {
        when: "universes have same universeId and name on different servers"
        def universe = new Universe(server: new Server(countryCode: "en"), universeId: 1, name: "1")
        def universe2 = new Universe(server: new Server(countryCode: "nl"), universeId: 1, name: "1")

        then: "both universes should be saved"
        universe.save()
        universe2.save()
    }
}
