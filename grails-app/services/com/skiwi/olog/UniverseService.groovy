package com.skiwi.olog

import grails.transaction.Transactional

@Transactional
class UniverseService {
    def serverGroupService

    Universe getOrCreateUniverse(ServerGroup serverGroup, Integer universeId, String name = null) {
        Universe.findByServerGroupAndUniverseId(serverGroup, universeId) ?: {
            def universe = new Universe(serverGroup: serverGroup, universeId: universeId, name: name)
            universe.save()
        }()
    }

    Universe getOrCreateUniverse(String serverGroupCountryCode, Integer universeId, String name = null) {
        getOrCreateUniverse(serverGroupService.getServerGroup(serverGroupCountryCode), universeId, name)
    }
}
