databaseChangeLog = {

    changeSet(author: "Beheerder (generated)", id: "1449264449541-1") {
        createSequence(sequenceName: "hibernate_sequence")
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-2") {
        createTable(tableName: "table_coordinate") {
            column(autoIncrement: "true", name: "col_id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "table_coordinatePK")
            }

            column(name: "col_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "col_galaxy", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_position", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_solar_system", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_universe_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-3") {
        createTable(tableName: "table_planet") {
            column(autoIncrement: "true", name: "col_id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "table_planetPK")
            }

            column(name: "col_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "col_planet_id", type: "INT4") {
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

    changeSet(author: "Beheerder (generated)", id: "1449264449541-4") {
        createTable(tableName: "table_planet_alias") {
            column(autoIncrement: "true", name: "col_id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "table_planet_aliasPK")
            }

            column(name: "col_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "col_begin", type: "BYTEA") {
                constraints(nullable: "false")
            }

            column(name: "col_end", type: "BYTEA") {
                constraints(nullable: "false")
            }

            column(name: "col_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-5") {
        createTable(tableName: "table_planet_location") {
            column(autoIncrement: "true", name: "col_id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "table_planet_locationPK")
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

            column(name: "col_end", type: "BYTEA") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-6") {
        createTable(tableName: "table_planet_table_planet_alias") {
            column(name: "table_planet_col_aliases_id", type: "BIGINT")

            column(name: "col_planet_alias_id", type: "BIGINT")

            column(name: "col_aliases_idx", type: "INT4")
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-7") {
        createTable(tableName: "table_planet_table_planet_location") {
            column(name: "table_planet_col_locations_id", type: "BIGINT")

            column(name: "col_planet_location_id", type: "BIGINT")

            column(name: "col_locations_idx", type: "INT4")
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-8") {
        createTable(tableName: "table_player") {
            column(autoIncrement: "true", name: "col_id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "table_playerPK")
            }

            column(name: "col_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "col_player_id", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_universe_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-9") {
        createTable(tableName: "table_player_alias") {
            column(autoIncrement: "true", name: "col_id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "table_player_aliasPK")
            }

            column(name: "col_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "col_begin", type: "BYTEA") {
                constraints(nullable: "false")
            }

            column(name: "col_end", type: "BYTEA") {
                constraints(nullable: "false")
            }

            column(name: "col_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-10") {
        createTable(tableName: "table_player_table_player_alias") {
            column(name: "table_player_col_aliases_id", type: "BIGINT")

            column(name: "col_player_alias_id", type: "BIGINT")

            column(name: "col_aliases_idx", type: "INT4")
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-11") {
        createTable(tableName: "table_report") {
            column(autoIncrement: "true", name: "col_id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "table_reportPK")
            }

            column(name: "col_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "col_key", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "col_player_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "class", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-12") {
        createTable(tableName: "table_server_group") {
            column(autoIncrement: "true", name: "col_id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "table_server_groupPK")
            }

            column(name: "col_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "col_country_code", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-13") {
        createTable(tableName: "table_universe") {
            column(autoIncrement: "true", name: "col_id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "table_universePK")
            }

            column(name: "col_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "col_name", type: "VARCHAR(255)")

            column(name: "col_server_group_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "col_universe_id", type: "INT4") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-14") {
        addUniqueConstraint(columnNames: "col_position, col_solar_system, col_galaxy, col_universe_id", deferrable: "false", disabled: "false", initiallyDeferred: "false", tableName: "table_coordinate")
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-15") {
        addUniqueConstraint(columnNames: "col_universe_id, col_planet_id", deferrable: "false", disabled: "false", initiallyDeferred: "false", tableName: "table_planet")
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-16") {
        addUniqueConstraint(columnNames: "col_coordinate_id", deferrable: "false", disabled: "false", initiallyDeferred: "false", tableName: "table_planet_location")
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-17") {
        addUniqueConstraint(columnNames: "col_universe_id, col_player_id", deferrable: "false", disabled: "false", initiallyDeferred: "false", tableName: "table_player")
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-18") {
        addUniqueConstraint(columnNames: "col_country_code", deferrable: "false", disabled: "false", initiallyDeferred: "false", tableName: "table_server_group")
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-19") {
        addUniqueConstraint(columnNames: "col_server_group_id, col_name", deferrable: "false", disabled: "false", initiallyDeferred: "false", tableName: "table_universe")
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-20") {
        addUniqueConstraint(columnNames: "col_server_group_id, col_universe_id", deferrable: "false", disabled: "false", initiallyDeferred: "false", tableName: "table_universe")
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-21") {
        addForeignKeyConstraint(baseColumnNames: "col_universe_id", baseTableName: "table_planet", constraintName: "FK_2o0de6026t8jh6fee7ewh8ee6", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "col_id", referencedTableName: "table_universe")
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-22") {
        addForeignKeyConstraint(baseColumnNames: "col_universe_id", baseTableName: "table_coordinate", constraintName: "FK_3ripk9ojy17a5nbslap3189pp", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "col_id", referencedTableName: "table_universe")
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-23") {
        addForeignKeyConstraint(baseColumnNames: "col_player_id", baseTableName: "table_planet", constraintName: "FK_5jxkfdgrg3ksvv09htknqwf79", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "col_id", referencedTableName: "table_player")
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-24") {
        addForeignKeyConstraint(baseColumnNames: "col_player_id", baseTableName: "table_report", constraintName: "FK_7ifjvr1cy9gj9bbjoa3y48pjs", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "col_id", referencedTableName: "table_player")
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-25") {
        addForeignKeyConstraint(baseColumnNames: "col_coordinate_id", baseTableName: "table_planet_location", constraintName: "FK_cdulr5p0htnoahdls63iqor9f", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "col_id", referencedTableName: "table_coordinate")
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-26") {
        addForeignKeyConstraint(baseColumnNames: "col_universe_id", baseTableName: "table_player", constraintName: "FK_cs1ebyl90odknt41crjddu1p5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "col_id", referencedTableName: "table_universe")
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-27") {
        addForeignKeyConstraint(baseColumnNames: "col_planet_location_id", baseTableName: "table_planet_table_planet_location", constraintName: "FK_dowbfhf5qhpn5whqi4ht0gcob", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "col_id", referencedTableName: "table_planet_location")
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-28") {
        addForeignKeyConstraint(baseColumnNames: "col_planet_alias_id", baseTableName: "table_planet_table_planet_alias", constraintName: "FK_fk86rngqblf97dnd0hxws8nu6", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "col_id", referencedTableName: "table_planet_alias")
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-29") {
        addForeignKeyConstraint(baseColumnNames: "col_player_alias_id", baseTableName: "table_player_table_player_alias", constraintName: "FK_i1wg8rvneqxlnyy2yadk5gw2p", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "col_id", referencedTableName: "table_player_alias")
    }

    changeSet(author: "Beheerder (generated)", id: "1449264449541-30") {
        addForeignKeyConstraint(baseColumnNames: "col_server_group_id", baseTableName: "table_universe", constraintName: "FK_qlddsfijvbixd61n7gibdtckn", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "col_id", referencedTableName: "table_server_group")
    }
    include file: 'added-created-updated-fields.groovy'
    include file: 'added-planet-deleted-fields.groovy'
    include file: 'added-researches-object.groovy'
    include file: 'added-buildings-object.groovy'
    include file: 'added-defences-object.groovy'
    include file: 'added-fleet-object.groovy'
    include file: 'rename-buildings-to-planet-buildings.groovy'
    include file: 'add-moon-objects.groovy'
}
