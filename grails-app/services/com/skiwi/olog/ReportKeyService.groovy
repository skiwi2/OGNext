package com.skiwi.olog

import grails.transaction.Transactional

@Transactional
class ReportKeyService {
    SpyReport addOrGetSpyReport(Player player, String reportKey) {
        SpyReport.findByKey(reportKey) ?: {
            def spyReport = new SpyReport(player: player, key: reportKey)
            spyReport.save()
        }()
    }

    CombatReport addOrGetCombatReport(Player player, String reportKey) {
        CombatReport.findByKey(reportKey) ?: {
            def combatReport = new CombatReport(player: player, key: reportKey)
            combatReport.save()
        }()
    }

    RecycleReport addOrGetRecycleReport(Player player, String reportKey) {
        RecycleReport.findByKey(reportKey) ?: {
            def recycleReport = new RecycleReport(player: player, key: reportKey)
            recycleReport.save()
        }()
    }

    MissileReport addOrGetMissileReport(Player player, String reportKey) {
        MissileReport.findByKey(reportKey) ?: {
            def missileReport = new MissileReport(player: player, key: reportKey)
            missileReport.save()
        }()
    }
}
