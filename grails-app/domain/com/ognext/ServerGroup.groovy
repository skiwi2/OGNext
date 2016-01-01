package com.ognext

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = ["countryCode"])
class ServerGroup {
    static constraints = {
        countryCode unique: true
    }

    String countryCode

    Date dateCreated
    Date lastUpdated
}
