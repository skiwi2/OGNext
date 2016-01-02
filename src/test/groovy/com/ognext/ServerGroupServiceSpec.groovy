package com.ognext

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

    void "test get server group"() {
        when: "get non-existing serverGroup"
        def createdServerGroup = service.getServerGroup(serverGroupCountryCode)

        then: "serverGroup group should be created"
        createdServerGroup
        createdServerGroup.countryCode == serverGroupCountryCode
        ServerGroup.findByCountryCode(serverGroupCountryCode) == createdServerGroup

        when: "get existing serverGroup group"
        def existingServerGroup = service.getServerGroup(serverGroupCountryCode)

        then: "serverGroup group should exist"
        existingServerGroup
        existingServerGroup.countryCode == serverGroupCountryCode
        ServerGroup.findByCountryCode(serverGroupCountryCode) == existingServerGroup
    }
}
