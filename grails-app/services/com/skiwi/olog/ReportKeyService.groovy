package com.skiwi.olog

import grails.transaction.Transactional

@Transactional
class ReportKeyService {
    SpyReport addSpyReport(Player player, String reportKey) {
        def spyReport = new SpyReport(player: player, key: reportKey)
        spyReport.save()
    }

    CombatReport addCombatReport(Player player, String reportKey) {
        def combatReport = new CombatReport(player: player, key: reportKey)
        combatReport.save()
    }

    RecycleReport addRecycleReport(Player player, String reportKey) {
        def recycleReport = new RecycleReport(player: player, key: reportKey)
        recycleReport.save()
    }

    MissileReport addMissileReport(Player player, String reportKey) {
        def missileReport = new MissileReport(player: player, key: reportKey)
        missileReport.save()
    }
}
