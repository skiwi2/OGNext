package com.skiwi.olog

class Server {
    static constraints = {
        countryCode unique: true
    }

    String countryCode
}
