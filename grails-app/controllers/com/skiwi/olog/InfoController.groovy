package com.skiwi.olog

class InfoController {
    def infoBean

    def index() {
        [info: infoBean]
    }
}
