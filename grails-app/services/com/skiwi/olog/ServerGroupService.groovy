package com.skiwi.olog

import grails.transaction.Transactional

@Transactional
class ServerGroupService {
    ServerGroup getServerGroup(String countryCode) {
        ServerGroup.findOrSaveByCountryCode(countryCode)
    }
}
