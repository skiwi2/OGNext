package com.skiwi.olog

class Universe {
    static constraints = {
        universeId unique: "serverGroup"
        name unique: "serverGroup", nullable: true
    }

    ServerGroup serverGroup
    Integer universeId
    String name
}
