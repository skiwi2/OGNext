package com.ognext

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(MoonBuildings)
class MoonBuildingsSpec extends Specification {
    def setup() {
    }

    def cleanup() {
    }

    void "save valid moon buildings"() {
        when: "moon buildings is valid"
        def moonBuildings = new MoonBuildings(solarSatellite: 0, metalStorage: 1, crystalStorage: 2, deuteriumTank: 3, roboticsFactory: 4, shipyard: 5, lunarBase: 6, sensorPhalanx: 7, jumpGate: 2)

        then: "moon buildings should be saved"
        moonBuildings.save()
    }

    void "save default moon buildings"() {
        when: "moon buildings has default values"
        def moonBuildings = new MoonBuildings(solarSatellite: 0, metalStorage: 0, crystalStorage: 0, deuteriumTank: 0, roboticsFactory: 0, shipyard: 0, lunarBase: 0, sensorPhalanx: 0, jumpGate: 0)

        then: "moon buildings should be saved"
        moonBuildings.save()
    }

    void "save invalid buildings"() {
        when: "moon buildings is invalid"
        def moonBuildings = new MoonBuildings(solarSatellite: -1, metalStorage: -1, crystalStorage: -1, deuteriumTank: -1, roboticsFactory: -1, shipyard: -1, lunarBase: -1, sensorPhalanx: -1, jumpGate: -1)

        then: "moon buildings should not be saved"
        !moonBuildings.save(failOnError: false)
    }
}
