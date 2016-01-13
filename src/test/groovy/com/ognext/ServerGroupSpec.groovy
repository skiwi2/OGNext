package com.ognext

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(ServerGroup)
class ServerGroupSpec extends Specification {
    def setup() {
    }

    def cleanup() {
    }

    void "save valid server group"() {
        when: "serverGroup group is valid"
        def serverGroup = new ServerGroup(countryCode: "en")

        then: "serverGroup group should be saved"
        serverGroup.save()
    }

    void "save two server groups with same country code"() {
        when: "serverGroup groups have same country code"
        def serverGroup = new ServerGroup(countryCode: "en")
        def serverGroup2 = new ServerGroup(countryCode: "en")

        then: "second serverGroup group should not be saved"
        serverGroup.save(flush: true)
        !serverGroup2.save(failOnError: false)
    }

    void "test equals and hash code"() {
        expect:
        new ServerGroup(countryCode: "en") == new ServerGroup(countryCode: "en")
        new ServerGroup(countryCode: "en") != new ServerGroup(countryCode: "nl")
    }

    void "test to string"() {
        expect:
        new ServerGroup(countryCode: "en").toString() == "ServerGroup(null, en)"
    }
}
