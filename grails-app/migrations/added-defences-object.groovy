databaseChangeLog = {

    changeSet(author: "Beheerder (generated)", id: "1452086093407-1") {
        createTable(tableName: "table_defences") {
            column(autoIncrement: "true", name: "col_id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "table_defencesPK")
            }

            column(name: "col_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "col_anti_ballistic_missiles", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "col_gauss_cannon", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_heavy_laser", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_interplanetary_missiles", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_ion_cannon", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_large_shield_dome", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "col_light_laser", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_plasma_turret", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_rocket_launcher", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_small_shield_dome", type: "INT4") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1452086093407-2") {
        addColumn(tableName: "table_planet") {
            column(name: "col_defences_id", type: "int8")
        }

        addColumn(tableName: "table_defences") {
            column(name: "temp_col_planet_id", type: "int8")
        }

        //select all planets where defences is null
        //for those planets, create an entry in the planets table
        //update those planets such that that defences id is set
        sql("""
WITH null_defences AS (
    SELECT col_id AS planet_id
    FROM table_planet
    WHERE col_defences_id IS NULL
    ),
inserted_defences AS (
    INSERT INTO table_defences (
        temp_col_planet_id,
        col_version,
        col_rocket_launcher,
        col_light_laser,
        col_heavy_laser,
        col_gauss_cannon,
        col_ion_cannon,
        col_plasma_turret,
        col_small_shield_dome,
        col_large_shield_dome,
        col_anti_ballistic_missiles,
        col_interplanetary_missiles,
        col_date_created,
        col_last_updated)
    SELECT planet_id, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
    FROM null_defences RETURNING temp_col_planet_id AS planet_id, col_id AS defences_id
    )
UPDATE table_planet
SET col_defences_id = inserted_defences.defences_id
FROM inserted_defences
WHERE col_id = inserted_defences.planet_id
""")

        dropColumn(tableName: "table_defences") {
            column(name: "temp_col_planet_id")
        }

        addNotNullConstraint(tableName: "table_planet", columnName: "col_defences_id")
    }

    changeSet(author: "Beheerder (generated)", id: "1452086093407-3") {
        addForeignKeyConstraint(baseColumnNames: "col_defences_id", baseTableName: "table_planet", constraintName: "FK_mpthdc0slkx1gbmrw8c7bl3eh", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "col_id", referencedTableName: "table_defences")
    }
}
