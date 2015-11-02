package com.skiwi.olog

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Player)
class PlayerSpec extends Specification {
    def setup() {
    }

    def cleanup() {
    }

    void "save valid player"() {
        when: "player is valid"
        def player = new Player(playerId: "103168")

        then: "player should be saved"
        player.save()
    }
}
