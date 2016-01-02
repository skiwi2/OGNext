package com.ognext

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Buildings)
class BuildingsSpec extends Specification {
    def setup() {
    }

    def cleanup() {
    }

    void "save valid buildings"() {
        when: "buildings is valid"
        def buildings = new Buildings(metalMine: 20, crystalMine: 18, deuteriumSynthesizer: 11, solarPlant: 20, fusionReactor: 0, solarSatellite: 0, metalStorage: 6, crystalStorage: 4, deuteriumTank: 4, roboticsFactory: 7, shipyard: 8, researchLab: 7, allianceDepot: 0, missileSilo: 2, naniteFactory: 0, terraformer: 0)

        then: "buildings should be saved"
        buildings.save()
    }

    void "save default buildings"() {
        when: "buildings has default values"
        def buildings = new Buildings(metalMine: 0, crystalMine: 0, deuteriumSynthesizer: 0, solarPlant: 0, fusionReactor: 0, solarSatellite: 0, metalStorage: 0, crystalStorage: 0, deuteriumTank: 0, roboticsFactory: 0, shipyard: 0, researchLab: 0, allianceDepot: 0, missileSilo: 0, naniteFactory: 0, terraformer: 0)

        then: "buildings should be saved"
        buildings.save()
    }

    void "save invalid buildings"() {
        when: "buildings is invalid"
        def buildings = new Buildings(metalMine: -1, crystalMine: -1, deuteriumSynthesizer: -1, solarPlant: -1, fusionReactor: -1, solarSatellite: -1, metalStorage: -1, crystalStorage: -1, deuteriumTank: -1, roboticsFactory: -1, shipyard: -1, researchLab: -1, allianceDepot: -1, missileSilo: -1, naniteFactory: -1, terraformer: -1)

        then: "buildings should not be saved"
        !buildings.save(failOnError: false)
    }
}
