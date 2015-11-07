package com.skiwi.olog

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ServerGroupService)
@Mock(ServerGroup)
class ServerGroupServiceSpec extends Specification {
    def serverGroupCountryCode = "en"

    def setup() {
    }

    def cleanup() {
    }

    void "test get or create server group"() {
        when: "get non-existing serverGroup"
        def createdServerGroup = service.getOrCreateServerGroup(serverGroupCountryCode)

        then: "serverGroup group should be created"
        createdServerGroup
        createdServerGroup.countryCode == serverGroupCountryCode
        ServerGroup.findByCountryCode(serverGroupCountryCode) == createdServerGroup

        when: "get existing serverGroup group"
        def existingServerGroup = service.getOrCreateServerGroup(serverGroupCountryCode)

        then: "serverGroup group should exist"
        existingServerGroup
        existingServerGroup.countryCode == serverGroupCountryCode
        ServerGroup.findByCountryCode(serverGroupCountryCode) == existingServerGroup
    }
}
