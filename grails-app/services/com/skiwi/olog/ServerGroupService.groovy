package com.skiwi.olog

import grails.transaction.Transactional

@Transactional
class ServerGroupService {
    ServerGroup getOrCreateServerGroup(String countryCode) {
        ServerGroup.findOrSaveByCountryCode(countryCode)
    }
}
