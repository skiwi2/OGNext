package com.skiwi.olog

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = ["player", "key"])
abstract class Report {
    static constraints = {
    }

    Player player
    String key
}
