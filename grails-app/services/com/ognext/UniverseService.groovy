package com.ognext

import com.ognext.ServerGroup
import com.ognext.Universe
import grails.transaction.Transactional

@Transactional
class UniverseService {
    def serverGroupService

    Universe getUniverse(ServerGroup serverGroup, Integer universeId) {
        Universe.findOrSaveByServerGroupAndUniverseId(serverGroup, universeId)
    }

    Universe getUniverse(String serverGroupCountryCode, Integer universeId) {
        getUniverse(serverGroupService.getServerGroup(serverGroupCountryCode), universeId)
    }
}
