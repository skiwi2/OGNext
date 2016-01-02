package com.ognext

import grails.transaction.Transactional

@Transactional
class ReportKeyService {
    SpyReport addSpyReport(Player player, String reportKey) {
        SpyReport.findOrSaveByPlayerAndKey(player, reportKey)
    }

    CombatReport addCombatReport(Player player, String reportKey) {
        CombatReport.findOrSaveByPlayerAndKey(player, reportKey)
    }

    RecycleReport addRecycleReport(Player player, String reportKey) {
        RecycleReport.findOrSaveByPlayerAndKey(player, reportKey)
    }

    MissileReport addMissileReport(Player player, String reportKey) {
        MissileReport.findOrSaveByPlayerAndKey(player, reportKey)
    }
}
