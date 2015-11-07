package com.skiwi.olog

class SpyReportController {
    def index() {
        def spyReports = SpyReport.list()
        [spyReports: spyReports]
    }
}
