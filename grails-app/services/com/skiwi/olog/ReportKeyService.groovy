package com.skiwi.olog

import grails.transaction.Transactional

@Transactional
class ReportKeyService {
    SpyReport addOrGetSpyReport(Player player, String reportKey) {
        SpyReport.findOrSaveByPlayerAndKey(player, reportKey)
    }

    CombatReport addOrGetCombatReport(Player player, String reportKey) {
        CombatReport.findOrSaveByPlayerAndKey(player, reportKey)
    }

    RecycleReport addOrGetRecycleReport(Player player, String reportKey) {
        RecycleReport.findOrSaveByPlayerAndKey(player, reportKey)
    }

    MissileReport addOrGetMissileReport(Player player, String reportKey) {
        MissileReport.findOrSaveByPlayerAndKey(player, reportKey)
    }
}
