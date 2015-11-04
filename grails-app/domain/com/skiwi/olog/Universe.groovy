package com.skiwi.olog

class Universe {
    static constraints = {
        universeId unique: "server"
        name unique: "server"
    }

    Server server
    Integer universeId
    String name
}
