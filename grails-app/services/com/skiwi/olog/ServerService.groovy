package com.skiwi.olog

import grails.transaction.Transactional

@Transactional
class ServerService {
    Server getOrCreateServer(String countryCode) {
        Server.findByCountryCode(countryCode) ?: {
            def server = new Server(countryCode: countryCode)
            server.save()
        }()
    }
}
