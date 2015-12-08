databaseChangeLog = {

    changeSet(author: "Beheerder (generated)", id: "1449580913886-1") {
        addColumn(tableName: "table_coordinate") {
            column(name: "col_date_created", type: "timestamp") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449580913886-2") {
        addColumn(tableName: "table_planet") {
            column(name: "col_date_created", type: "timestamp") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449580913886-3") {
        addColumn(tableName: "table_planet_alias") {
            column(name: "col_date_created", type: "timestamp") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449580913886-4") {
        addColumn(tableName: "table_planet_location") {
            column(name: "col_date_created", type: "timestamp") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449580913886-5") {
        addColumn(tableName: "table_player") {
            column(name: "col_date_created", type: "timestamp") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449580913886-6") {
        addColumn(tableName: "table_player_alias") {
            column(name: "col_date_created", type: "timestamp") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449580913886-7") {
        addColumn(tableName: "table_report") {
            column(name: "col_date_created", type: "timestamp") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449580913886-8") {
        addColumn(tableName: "table_server_group") {
            column(name: "col_date_created", type: "timestamp") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449580913886-9") {
        addColumn(tableName: "table_universe") {
            column(name: "col_date_created", type: "timestamp") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449580913886-10") {
        addColumn(tableName: "table_coordinate") {
            column(name: "col_last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449580913886-11") {
        addColumn(tableName: "table_planet") {
            column(name: "col_last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449580913886-12") {
        addColumn(tableName: "table_planet_alias") {
            column(name: "col_last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449580913886-13") {
        addColumn(tableName: "table_planet_location") {
            column(name: "col_last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449580913886-14") {
        addColumn(tableName: "table_player") {
            column(name: "col_last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449580913886-15") {
        addColumn(tableName: "table_player_alias") {
            column(name: "col_last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449580913886-16") {
        addColumn(tableName: "table_report") {
            column(name: "col_last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449580913886-17") {
        addColumn(tableName: "table_server_group") {
            column(name: "col_last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449580913886-18") {
        addColumn(tableName: "table_universe") {
            column(name: "col_last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }
        }
    }
}
