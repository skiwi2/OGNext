package com.ognext

import grails.transaction.Transactional

@Transactional
class ServerGroupService {
    ServerGroup getServerGroup(String countryCode) {
        ServerGroup.findOrSaveByCountryCode(countryCode)
    }
}
