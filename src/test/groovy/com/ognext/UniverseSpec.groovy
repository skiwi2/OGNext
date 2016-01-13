package com.ognext

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
        def universe = new Universe(serverGroup: new ServerGroup(countryCode: "en"), universeId: 1, name: "1")

        then: "universe should be saved"
        universe.save()

        when: "universe has no name"
        def universe2 = new Universe(serverGroup: new ServerGroup(countryCode: "en"), universeId: 1)

        then: "universe should also be saved"
        universe2.save()
    }

    void "save two universes with same universeId on same server group"() {
        when: "universes have same universeId on same server group"
        def serverGroup = new ServerGroup(countryCode: "en")
        def universe = new Universe(serverGroup: serverGroup, universeId: 1, name: "1a")
        def universe2 = new Universe(serverGroup: serverGroup, universeId: 1, name: "1b")

        then: "second universe should not be saved"
        universe.save(flush: true)
        !universe2.save(failOnError: false)
    }

    void "save two universes with same name on same server group"() {
        when: "universes have same name on same server group"
        def serverGroup = new ServerGroup(countryCode: "en")
        def universe = new Universe(serverGroup: serverGroup, universeId: 1, name: "test")
        def universe2 = new Universe(serverGroup: serverGroup, universeId: 2, name: "test")

        then: "second universe should not be saved"
        universe.save(flush: true)
        !universe2.save(failOnError: false)
    }

    void "save two universes with same universeId and name on different server groups"() {
        when: "universes have same universeId and name on different server groups"
        def universe = new Universe(serverGroup: new ServerGroup(countryCode: "en"), universeId: 1, name: "1")
        def universe2 = new Universe(serverGroup: new ServerGroup(countryCode: "nl"), universeId: 1, name: "1")

        then: "both universes should be saved"
        universe.save()
        universe2.save()
    }

    void "test equals and hash code"() {
        given:
        def serverGroup = new ServerGroup(countryCode: "en")
        def serverGroup2 = new ServerGroup(countryCode: "nl")

        expect:
        new Universe(serverGroup: serverGroup, universeId: 135) == new Universe(serverGroup: serverGroup, universeId: 135)
        new Universe(serverGroup: serverGroup, universeId: 135) != new Universe(serverGroup: serverGroup2, universeId: 135)
        new Universe(serverGroup: serverGroup, universeId: 135) != new Universe(serverGroup: serverGroup, universeId: 136)

        new Universe(serverGroup: serverGroup, universeId: 135, name: "test1") == new Universe(serverGroup: serverGroup, universeId: 135, name: "test2")
    }

    void "test to string"() {
        expect:
        new Universe(serverGroup: new ServerGroup(countryCode: "en"), universeId: 135).toString() == "Universe(null, en, 135)"
    }
}
