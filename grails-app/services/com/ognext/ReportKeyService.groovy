package com.ognext

import com.ognext.CombatReport
import com.ognext.MissileReport
import com.ognext.Player
import com.ognext.RecycleReport
import com.ognext.SpyReport
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
