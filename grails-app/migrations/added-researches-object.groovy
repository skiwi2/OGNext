databaseChangeLog = {

    changeSet(author: "Beheerder (generated)", id: "1451328438981-1") {
        createTable(tableName: "table_researches") {
            column(autoIncrement: "true", name: "col_id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "table_researchesPK")
            }

            column(name: "col_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "col_armour_technology", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_astrophysics", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_combustion_drive", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_computer_technology", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_energy_technology", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_espionage_technology", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_graviton_technology", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_hyperspace_drive", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_hyperspace_technology", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_impulse_drive", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_intergalactic_research_network", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_ion_technology", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_laser_technology", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_plasma_technology", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_shielding_technology", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_weapons_technology", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "col_last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1451328438981-2") {
        addColumn(tableName: "table_player") {
            column(name: "col_researches_id", type: "int8")
        }

        addColumn(tableName: "table_researches") {
            column(name: "temp_col_player_id", type: "int8")
        }

        //select all players where researches is null
        //for those players, create an entry in the researches table
        //update those players such that that researches id is set
        sql("""
WITH null_researches AS (
    SELECT col_id AS player_id
    FROM table_player
    WHERE col_researches_id IS NULL
    ),
inserted_researches AS (
    INSERT INTO table_researches (
        temp_col_player_id,
        col_version,
        col_energy_technology,
        col_laser_technology,
        col_ion_technology,
        col_hyperspace_technology,
        col_plasma_technology,
        col_combustion_drive,
        col_impulse_drive,
        col_hyperspace_drive,
        col_espionage_technology,
        col_computer_technology,
        col_astrophysics,
        col_intergalactic_research_network,
        col_graviton_technology,
        col_weapons_technology,
        col_shielding_technology,
        col_armour_technology,
        col_date_created,
        col_last_updated)
    SELECT player_id, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
    FROM null_researches RETURNING temp_col_player_id AS player_id, col_id AS researches_id
    )
UPDATE table_player
SET col_researches_id = inserted_researches.researches_id
FROM inserted_researches
WHERE col_id = inserted_researches.player_id
""")

        dropColumn(tableName: "table_researches") {
            column(name: "temp_col_player_id")
        }

        addNotNullConstraint(tableName: "table_player", columnName: "col_researches_id")
    }

    changeSet(author: "Beheerder (generated)", id: "1451328438981-3") {
        addForeignKeyConstraint(baseColumnNames: "col_researches_id", baseTableName: "table_player", constraintName: "FK_6x636l43fl7pnma8qkhfdvb8n", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "col_id", referencedTableName: "table_researches")
    }
}
