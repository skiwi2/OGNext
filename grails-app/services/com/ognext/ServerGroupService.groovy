package com.ognext

import com.ognext.ServerGroup
import grails.transaction.Transactional

@Transactional
class ServerGroupService {
    ServerGroup getServerGroup(String countryCode) {
        ServerGroup.findOrSaveByCountryCode(countryCode)
    }
}
