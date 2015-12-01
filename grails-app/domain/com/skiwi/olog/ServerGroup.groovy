package com.skiwi.olog

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = ["countryCode"])
class ServerGroup {
    static constraints = {
        countryCode unique: true
    }

    String countryCode
}
