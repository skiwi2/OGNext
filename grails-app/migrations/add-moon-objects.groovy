databaseChangeLog = {

    changeSet(author: "Beheerder (generated)", id: "1452531585110-1") {
        createTable(tableName: "table_moon") {
            column(autoIncrement: "true", name: "col_id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "table_moonPK")
            }

            column(name: "col_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "col_buildings_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "col_date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "col_date_deleted", type: "timestamp")

            column(name: "col_defences_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "col_deleted", type: "BOOLEAN") {
                constraints(nullable: "false")
            }

            column(name: "col_fleet_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "col_last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "col_moon_id", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_player_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "col_universe_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1452531585110-2") {
        createTable(tableName: "table_moon_alias") {
            column(autoIncrement: "true", name: "col_id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "table_moon_aliasPK")
            }

            column(name: "col_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "col_begin", type: "BYTEA") {
                constraints(nullable: "false")
            }

            column(name: "col_date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "col_end", type: "BYTEA") {
                constraints(nullable: "false")
            }

            column(name: "col_last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "col_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1452531585110-3") {
        createTable(tableName: "table_moon_buildings") {
            column(autoIncrement: "true", name: "col_id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "table_moon_buildingsPK")
            }

            column(name: "col_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "col_crystal_storage", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "col_deuterium_tank", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_jump_gate", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "col_lunar_base", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_metal_storage", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_robotics_factory", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_sensor_phalanx", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_shipyard", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_solar_satellite", type: "INT4") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1452531585110-4") {
        createTable(tableName: "table_moon_location") {
            column(autoIncrement: "true", name: "col_id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "table_moon_locationPK")
            }

            column(name: "col_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "col_begin", type: "BYTEA") {
                constraints(nullable: "false")
            }

            column(name: "col_coordinate_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "col_date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "col_end", type: "BYTEA") {
                constraints(nullable: "false")
            }

            column(name: "col_last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1452531585110-5") {
        createTable(tableName: "table_moon_table_moon_alias") {
            column(name: "table_moon_col_aliases_id", type: "BIGINT")

            column(name: "col_moon_alias_id", type: "BIGINT")

            column(name: "col_aliases_idx", type: "INT4")
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1452531585110-6") {
        createTable(tableName: "table_moon_table_moon_location") {
            column(name: "table_moon_col_locations_id", type: "BIGINT")

            column(name: "col_moon_location_id", type: "BIGINT")

            column(name: "col_locations_idx", type: "INT4")
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1452531585110-7") {
        addUniqueConstraint(columnNames: "col_universe_id, col_moon_id", deferrable: "false", disabled: "false", initiallyDeferred: "false", tableName: "table_moon")
    }

    changeSet(author: "Beheerder (generated)", id: "1452531585110-8") {
        addUniqueConstraint(columnNames: "col_coordinate_id", constraintName: "UC_TABLE_MOON_LOCATIONCOL_COORDINATE_ID_COL", deferrable: "false", disabled: "false", initiallyDeferred: "false", tableName: "table_moon_location")
    }

    changeSet(author: "Beheerder (generated)", id: "1452531585110-9") {
        addForeignKeyConstraint(baseColumnNames: "col_buildings_id", baseTableName: "table_moon", constraintName: "FK_a3ktxfprmfgtc8ved0gjixlpd", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "col_id", referencedTableName: "table_moon_buildings")
    }

    changeSet(author: "Beheerder (generated)", id: "1452531585110-10") {
        addForeignKeyConstraint(baseColumnNames: "col_defences_id", baseTableName: "table_moon", constraintName: "FK_af9nd4obnkj9mljm64pixq7it", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "col_id", referencedTableName: "table_defences")
    }

    changeSet(author: "Beheerder (generated)", id: "1452531585110-11") {
        addForeignKeyConstraint(baseColumnNames: "col_moon_alias_id", baseTableName: "table_moon_table_moon_alias", constraintName: "FK_bx72wim63j7c3q1tb8tfj8ckr", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "col_id", referencedTableName: "table_moon_alias")
    }

    changeSet(author: "Beheerder (generated)", id: "1452531585110-12") {
        addForeignKeyConstraint(baseColumnNames: "col_moon_location_id", baseTableName: "table_moon_table_moon_location", constraintName: "FK_c79hvuxyqx7vs5q6dqlpk4g9j", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "col_id", referencedTableName: "table_moon_location")
    }

    changeSet(author: "Beheerder (generated)", id: "1452531585110-13") {
        addForeignKeyConstraint(baseColumnNames: "col_fleet_id", baseTableName: "table_moon", constraintName: "FK_dfbivbi0jkbq3nsygk2fgvg32", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "col_id", referencedTableName: "table_fleet")
    }

    changeSet(author: "Beheerder (generated)", id: "1452531585110-14") {
        addForeignKeyConstraint(baseColumnNames: "col_universe_id", baseTableName: "table_moon", constraintName: "FK_hcsioo2v2h2p0m12htd9odeif", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "col_id", referencedTableName: "table_universe")
    }

    changeSet(author: "Beheerder (generated)", id: "1452531585110-15") {
        addForeignKeyConstraint(baseColumnNames: "col_player_id", baseTableName: "table_moon", constraintName: "FK_ivk8brktbkxn1iqybrxlrtggr", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "col_id", referencedTableName: "table_player")
    }

    changeSet(author: "Beheerder (generated)", id: "1452531585110-16") {
        addForeignKeyConstraint(baseColumnNames: "col_coordinate_id", baseTableName: "table_moon_location", constraintName: "FK_lghfwp9qngpxpl6k9rs0ekx92", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "col_id", referencedTableName: "table_coordinate")
    }
}
