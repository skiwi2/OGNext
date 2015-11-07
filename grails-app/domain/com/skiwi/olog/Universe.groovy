package com.skiwi.olog

class Universe {
    static constraints = {
        universeId unique: "server"
        name unique: "server", nullable: true
    }

    Server server
    Integer universeId
    String name
}
