package com.skiwi.olog

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Server)
class ServerSpec extends Specification {
    def setup() {
    }

    def cleanup() {
    }

    void "save valid server"() {
        when: "server is valid"
        def server = new Server(countryCode: "en")

        then: "server should be saved"
        server.save()
    }

    void "save two servers with same country code"() {
        when: "servers have same country code"
        def server = new Server(countryCode: "en")
        def server2 = new Server(countryCode: "en")

        then: "second server should not be saved"
        server.save(flush: true)
        !server2.save(failOnError: false)
    }
}
