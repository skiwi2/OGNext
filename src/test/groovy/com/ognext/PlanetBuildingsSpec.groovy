package com.ognext

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(PlanetBuildings)
class PlanetBuildingsSpec extends Specification {
    def setup() {
    }

    def cleanup() {
    }

    void "save valid planet buildings"() {
        when: "planet buildings is valid"
        def planetBuildings = new PlanetBuildings(metalMine: 20, crystalMine: 18, deuteriumSynthesizer: 11, solarPlant: 20, fusionReactor: 0, solarSatellite: 0, metalStorage: 6, crystalStorage: 4, deuteriumTank: 4, roboticsFactory: 7, shipyard: 8, researchLab: 7, allianceDepot: 0, missileSilo: 2, naniteFactory: 0, terraformer: 0)

        then: "planet buildings should be saved"
        planetBuildings.save()
    }

    void "save default planet buildings"() {
        when: "planet buildings has default values"
        def planetBuildings = new PlanetBuildings(metalMine: 0, crystalMine: 0, deuteriumSynthesizer: 0, solarPlant: 0, fusionReactor: 0, solarSatellite: 0, metalStorage: 0, crystalStorage: 0, deuteriumTank: 0, roboticsFactory: 0, shipyard: 0, researchLab: 0, allianceDepot: 0, missileSilo: 0, naniteFactory: 0, terraformer: 0)

        then: "planet buildings should be saved"
        planetBuildings.save()
    }

    void "save invalid buildings"() {
        when: "planet buildings is invalid"
        def planetBuildings = new PlanetBuildings(metalMine: -1, crystalMine: -1, deuteriumSynthesizer: -1, solarPlant: -1, fusionReactor: -1, solarSatellite: -1, metalStorage: -1, crystalStorage: -1, deuteriumTank: -1, roboticsFactory: -1, shipyard: -1, researchLab: -1, allianceDepot: -1, missileSilo: -1, naniteFactory: -1, terraformer: -1)

        then: "planet buildings should not be saved"
        !planetBuildings.save(failOnError: false)
    }
}
