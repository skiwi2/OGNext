databaseChangeLog = {

    changeSet(author: "Beheerder (generated)", id: "1452254750268-1") {
        createTable(tableName: "table_fleet") {
            column(autoIncrement: "true", name: "col_id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "table_fleetPK")
            }

            column(name: "col_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "col_battlecruiser", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_battleship", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_bomber", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_colony_ship", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_cruiser", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "col_deathstar", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_destroyer", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_espionage_probe", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_heavy_fighter", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_large_cargo", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "col_light_fighter", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_recycler", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_small_cargo", type: "INT4") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1452254750268-2") {
        addColumn(tableName: "table_planet") {
            column(name: "col_fleet_id", type: "int8")
        }

        addColumn(tableName: "table_fleet") {
            column(name: "temp_col_planet_id", type: "int8")
        }

        //select all planets where fleet is null
        //for those planets, create an entry in the planets table
        //update those planets such that that fleet id is set
        sql("""
WITH null_fleet AS (
    SELECT col_id AS planet_id
    FROM table_planet
    WHERE col_fleet_id IS NULL
    ),
inserted_fleet AS (
    INSERT INTO table_fleet (
        temp_col_planet_id,
        col_version,
        col_light_fighter,
        col_heavy_fighter,
        col_cruiser,
        col_battleship,
        col_small_cargo,
        col_large_cargo,
        col_colony_ship,
        col_battlecruiser,
        col_bomber,
        col_destroyer,
        col_deathstar,
        col_recycler,
        col_espionage_probe,
        col_date_created,
        col_last_updated)
    SELECT planet_id, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
    FROM null_fleet RETURNING temp_col_planet_id AS planet_id, col_id AS fleet_id
    )
UPDATE table_planet
SET col_fleet_id = inserted_fleet.fleet_id
FROM inserted_fleet
WHERE col_id = inserted_fleet.planet_id
""")

        dropColumn(tableName: "table_fleet") {
            column(name: "temp_col_planet_id")
        }

        addNotNullConstraint(tableName: "table_planet", columnName: "col_fleet_id")
    }

    changeSet(author: "Beheerder (generated)", id: "1452254750268-3") {
        addForeignKeyConstraint(baseColumnNames: "col_fleet_id", baseTableName: "table_planet", constraintName: "FK_iu71bnoarq9ry27mb7gnjhtl", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "col_id", referencedTableName: "table_fleet")
    }
}
