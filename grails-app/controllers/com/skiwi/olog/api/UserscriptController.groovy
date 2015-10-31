package com.skiwi.olog.api

class UserscriptController {
    static allowedMethods = [
        keys: "POST"
    ]

    def keys() {
        def json = request.JSON

        println "keys data: " + json
        json.reportKeys.sr.each { println "SR key: $it" }

        render(contentType: "application/json") {
            result(success: true)
        }
    }
}