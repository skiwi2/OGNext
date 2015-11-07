package com.skiwi.olog

import grails.transaction.Transactional

@Transactional
class ServerGroupService {
    ServerGroup getOrCreateServerGroup(String countryCode) {
        ServerGroup.findByCountryCode(countryCode) ?: {
            def serverGroup = new ServerGroup(countryCode: countryCode)
            serverGroup.save()
        }()
    }
}
