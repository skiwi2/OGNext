package com.ognext

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ResearchesService)
@Mock([Researches, ServerGroup, ServerGroupService, Universe, Player, PlayerAlias])
class ResearchesServiceSpec extends Specification {
    Player player

    def setup() {
        def universeService = mockService(UniverseService)
        def universe = universeService.getUniverse("en", 136)
        def playerService = mockService(PlayerService)
        player = playerService.createPlayer(universe, 103168, "skiwi")
    }

    def cleanup() {
    }

    void "test create default researches"() {
        when: "create default researches"
        def defaultResearches = service.createDefaultResearches()

        then: "default researches should be created"
        defaultResearches.energyTechnology == 0
        defaultResearches.laserTechnology == 0
        defaultResearches.ionTechnology == 0
        defaultResearches.hyperspaceTechnology == 0
        defaultResearches.plasmaTechnology == 0
        defaultResearches.combustionDrive == 0
        defaultResearches.impulseDrive == 0
        defaultResearches.hyperspaceDrive == 0
        defaultResearches.espionageTechnology == 0
        defaultResearches.computerTechnology == 0
        defaultResearches.astrophysics == 0
        defaultResearches.intergalacticResearchNetwork == 0
        defaultResearches.gravitonTechnology == 0
        defaultResearches.weaponsTechnology == 0
        defaultResearches.shieldingTechnology == 0
        defaultResearches.armourTechnology == 0
    }

    void "test update player researches"() {
        when: "update player researches"
        service.updatePlayerResearches(player, 6, 10, 5, 3, 0, 7, 5, 3, 6, 7, 5, 0, 0, 8, 7, 8)
        
        then: "player researches should be updated"
        player.researches.energyTechnology == 6
        player.researches.laserTechnology == 10
        player.researches.ionTechnology == 5
        player.researches.hyperspaceTechnology == 3
        player.researches.plasmaTechnology == 0
        player.researches.combustionDrive == 7
        player.researches.impulseDrive == 5
        player.researches.hyperspaceDrive == 3
        player.researches.espionageTechnology == 6
        player.researches.computerTechnology == 7
        player.researches.astrophysics == 5
        player.researches.intergalacticResearchNetwork == 0
        player.researches.gravitonTechnology == 0
        player.researches.weaponsTechnology == 8
        player.researches.shieldingTechnology == 7
        player.researches.armourTechnology == 8
    }
}
