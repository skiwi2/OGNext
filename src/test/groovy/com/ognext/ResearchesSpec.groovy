package com.ognext

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Researches)
class ResearchesSpec extends Specification {
    def setup() {
    }

    def cleanup() {
    }

    void "save valid researches"() {
        when: "researches is valid"
        def researches = new Researches(energyTechnology: 6, laserTechnology: 10, ionTechnology: 5, hyperspaceTechnology: 3, plasmaTechnology: 0, combustionDrive: 7, impulseDrive: 5, hyperspaceDrive: 3, espionageTechnology: 6, computerTechnology: 7, astrophysics: 5, intergalacticResearchNetwork: 0, gravitonTechnology: 0, weaponsTechnology: 8, shieldingTechnology: 7, armourTechnology: 8)
        
        then: "researches should be saved"
        researches.save()
    }

    void "save default researches"() {
        when: "researches has default values"
        def researches = new Researches(energyTechnology: 0, laserTechnology: 0, ionTechnology: 0, hyperspaceTechnology: 0, plasmaTechnology: 0, combustionDrive: 0, impulseDrive: 0, hyperspaceDrive: 0, espionageTechnology: 0, computerTechnology: 0, astrophysics: 0, intergalacticResearchNetwork: 0, gravitonTechnology: 0, weaponsTechnology: 0, shieldingTechnology: 0, armourTechnology: 0)

        then: "researches should be saved"
        researches.save()
    }
    
    void "save invalid researches"() {
        when: "researches is invalid"
        def researches = new Researches(energyTechnology: -1, laserTechnology: -1, ionTechnology: -1, hyperspaceTechnology: -1, plasmaTechnology: -1, combustionDrive: -1, impulseDrive: -1, hyperspaceDrive: -1, espionageTechnology: -1, computerTechnology: -1, astrophysics: -1, intergalacticResearchNetwork: -1, gravitonTechnology: -1, weaponsTechnology: -1, shieldingTechnology: -1, armourTechnology: -1)
        
        then: "researches should not be saved"
        !researches.save(failOnError: false)
    }
}
