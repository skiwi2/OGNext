package com.skiwi.olog

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = ["serverGroup", "universeId"])
class Universe {
    static constraints = {
        universeId unique: "serverGroup"
        name unique: "serverGroup", nullable: true
    }

    ServerGroup serverGroup
    Integer universeId
    String name
}
