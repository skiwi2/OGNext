package com.skiwi.olog

class ServerGroup {
    static constraints = {
        countryCode unique: true
    }

    String countryCode
}
