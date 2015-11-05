package com.skiwi.olog

import grails.transaction.Transactional

@Transactional
class UniverseService {
    def serverService

    Universe getOrCreateUniverse(Server server, Integer universeId, String name) {
        Universe.findByServerAndUniverseId(server, universeId) ?: {
            def universe = new Universe(server: server, universeId: universeId, name: name)
            universe.save()
        }()
    }

    Universe getOrCreateUniverse(String serverCountryCode, Integer universeId, String name) {
        getOrCreateUniverse(serverService.getOrCreateServer(serverCountryCode), universeId, name)
    }
}
