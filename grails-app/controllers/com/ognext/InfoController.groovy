package com.ognext

class InfoController {
    def infoBean

    def index() {
        [info: infoBean]
    }
}
