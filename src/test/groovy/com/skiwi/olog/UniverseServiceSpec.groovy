package com.skiwi.olog

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(UniverseService)
@Mock([Universe, ServerGroup])
@TestMixin(GrailsUnitTestMixin)
class UniverseServiceSpec extends Specification {
    def serverGroupCountryCode = "en"
    def serverGroup = new ServerGroup(countryCode: serverGroupCountryCode)
    def universeId = 1
    def universeName = "1"

    static doWithSpring = {
        serverGroupService(ServerGroupService)
    }

    def setup() {
    }

    def cleanup() {
    }

    void "test get or create universe given server group"() {
        when: "get non-existing universe"
        def createdUniverse = service.getOrCreateUniverse(serverGroup, universeId, universeName)

        then: "universe should be created"
        createdUniverse
        createdUniverse.serverGroup == serverGroup
        createdUniverse.universeId == universeId
        createdUniverse.name == universeName
        Universe.findByServerGroupAndUniverseId(serverGroup, universeId) == createdUniverse

        when: "get existing universe"
        def existingUniverse = service.getOrCreateUniverse(serverGroup, universeId, universeName)

        then: "universe should exist"
        existingUniverse
        existingUniverse.serverGroup == serverGroup
        existingUniverse.universeId == universeId
        existingUniverse.name == universeName
        Universe.findByServerGroupAndUniverseId(serverGroup, universeId) == existingUniverse
    }

    void "test get or create universe given server group country code"() {
        when: "get non-existing universe"
        def createdUniverse = service.getOrCreateUniverse(serverGroupCountryCode, universeId, universeName)

        then: "universe should be created"
        createdUniverse
        createdUniverse.serverGroup.countryCode == serverGroupCountryCode
        createdUniverse.universeId == universeId
        createdUniverse.name == universeName
        Universe.createCriteria().get {
            serverGroup {
                eq "countryCode", serverGroupCountryCode
            }
            eq "universeId", universeId
        } == createdUniverse

        when: "get existing universe"
        def existingUniverse = service.getOrCreateUniverse(serverGroupCountryCode, universeId, universeName)

        then: "universe should exist"
        existingUniverse
        existingUniverse.serverGroup.countryCode == serverGroupCountryCode
        existingUniverse.universeId == universeId
        existingUniverse.name == universeName
        Universe.createCriteria().get {
            serverGroup {
                eq "countryCode", serverGroupCountryCode
            }
            eq "universeId", universeId
        } == existingUniverse
    }

    void "test get or create universe without name given server group"() {
        when: "get non-existing universe"
        def createdUniverse = service.getOrCreateUniverse(serverGroup, universeId)

        then: "universe should be created"
        createdUniverse
        createdUniverse.serverGroup == serverGroup
        createdUniverse.universeId == universeId
        Universe.findByServerGroupAndUniverseId(serverGroup, universeId) == createdUniverse

        when: "get existing universe"
        def existingUniverse = service.getOrCreateUniverse(serverGroup, universeId)

        then: "universe should exist"
        existingUniverse
        existingUniverse.serverGroup == serverGroup
        existingUniverse.universeId == universeId
        Universe.findByServerGroupAndUniverseId(serverGroup, universeId) == existingUniverse
    }

    void "test get or create universe without name given server group country code"() {
        when: "get non-existing universe"
        def createdUniverse = service.getOrCreateUniverse(serverGroupCountryCode, universeId)

        then: "universe should be created"
        createdUniverse
        createdUniverse.serverGroup.countryCode == serverGroupCountryCode
        createdUniverse.universeId == universeId
        Universe.createCriteria().get {
            serverGroup {
                eq "countryCode", serverGroupCountryCode
            }
            eq "universeId", universeId
        } == createdUniverse

        when: "get existing universe"
        def existingUniverse = service.getOrCreateUniverse(serverGroupCountryCode, universeId)

        then: "universe should exist"
        existingUniverse
        existingUniverse.serverGroup.countryCode == serverGroupCountryCode
        existingUniverse.universeId == universeId
        Universe.createCriteria().get {
            serverGroup {
                eq "countryCode", serverGroupCountryCode
            }
            eq "universeId", universeId
        } == existingUniverse
    }
}
