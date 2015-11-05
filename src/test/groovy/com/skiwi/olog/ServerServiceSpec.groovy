package com.skiwi.olog

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ServerService)
@Mock(Server)
class ServerServiceSpec extends Specification {
    def serverCountryCode = "en"

    def setup() {
    }

    def cleanup() {
    }

    void "test get or create server"() {
        when: "get non-existing server"
        def createdServer = service.getOrCreateServer(serverCountryCode)

        then: "server should be created"
        createdServer
        createdServer.countryCode == serverCountryCode
        Server.findByCountryCode(serverCountryCode) == createdServer

        when: "get existing server"
        def existingServer = service.getOrCreateServer(serverCountryCode)

        then: "server should exist"
        existingServer
        existingServer.countryCode == serverCountryCode
        Server.findByCountryCode(serverCountryCode) == existingServer
    }
}
