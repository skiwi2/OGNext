package com.ognext

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Defences)
class DefencesSpec extends Specification {
    def setup() {
    }

    def cleanup() {
    }

    void "save valid defences"() {
        when: "defences is valid"
        def defences = new Defences(rocketLauncher: 69, lightLaser: 158, heavyLaser: 12, gaussCannon: 14, ionCannon: 0, plasmaTurret: 1, smallShieldDome: 1, largeShieldDome: 1, antiBallisticMissiles: 20, interplanetaryMissiles: 0)

        then: "defences should be saved"
        defences.save()
    }

    void "save default defences"() {
        when: "defences has default values"
        def defences = new Defences(rocketLauncher: 0, lightLaser: 0, heavyLaser: 0, gaussCannon: 0, ionCannon: 0, plasmaTurret: 0, smallShieldDome: 0, largeShieldDome: 0, antiBallisticMissiles: 0, interplanetaryMissiles: 0)

        then: "defences should be saved"
        defences.save()
    }

    void "save invalid defences under minimum"() {
        when: "defences is invalid because numbers are under minimum"
        def defences = new Defences(rocketLauncher: -1, lightLaser: -1, heavyLaser: -1, gaussCannon: -1, ionCannon: -1, plasmaTurret: -1, smallShieldDome: -1, largeShieldDome: -1, antiBallisticMissiles: -1, interplanetaryMissiles: -1)

        then: "defences should not be saved"
        !defences.save(failOnError: false)
    }

    void "save invalid defences over maximum"() {
        when: "defences is invalid because numbers are over maximum"
        def defences = new Defences(rocketLauncher: 0, lightLaser: 0, heavyLaser: 0, gaussCannon: 0, ionCannon: 0, plasmaTurret: 0, smallShieldDome: 2, largeShieldDome: 2, antiBallisticMissiles: 0, interplanetaryMissiles: 0)

        then: "defences should not be saved"
        !defences.save(failOnError: false)
    }
}
