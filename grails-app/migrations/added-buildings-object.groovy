databaseChangeLog = {

    changeSet(author: "Beheerder (generated)", id: "1451742195029-1") {
        createTable(tableName: "table_buildings") {
            column(autoIncrement: "true", name: "col_id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "table_buildingsPK")
            }

            column(name: "col_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "col_alliance_depot", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_crystal_mine", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_crystal_storage", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "col_deuterium_synthesizer", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_deuterium_tank", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_fusion_reactor", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "col_metal_mine", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_metal_storage", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_missile_silo", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_nanite_factory", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_research_lab", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_robotics_factory", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_shipyard", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_solar_plant", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_solar_satellite", type: "INT4") {
                constraints(nullable: "false")
            }

            column(name: "col_terraformer", type: "INT4") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1451742195029-2") {
        addColumn(tableName: "table_planet") {
            column(name: "col_buildings_id", type: "int8")
        }

        addColumn(tableName: "table_buildings") {
            column(name: "temp_col_planet_id", type: "int8")
        }

        //select all planets where buildings is null
        //for those planets, create an entry in the planets table
        //update those planets such that that buildings id is set
        sql("""
WITH null_buildings AS (
    SELECT col_id AS planet_id
    FROM table_planet
    WHERE col_buildings_id IS NULL
    ),
inserted_buildings AS (
    INSERT INTO table_buildings (
        temp_col_planet_id,
        col_version,
        col_metal_mine,
        col_crystal_mine,
        col_deuterium_synthesizer,
        col_solar_plant,
        col_fusion_reactor,
        col_solar_satellite,
        col_metal_storage,
        col_crystal_storage,
        col_deuterium_tank,
        col_robotics_factory,
        col_shipyard,
        col_research_lab,
        col_alliance_depot,
        col_missile_silo,
        col_nanite_factory,
        col_terraformer,
        col_date_created,
        col_last_updated)
    SELECT planet_id, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
    FROM null_buildings RETURNING temp_col_planet_id AS planet_id, col_id AS buildings_id
    )
UPDATE table_planet
SET col_buildings_id = inserted_buildings.buildings_id
FROM inserted_buildings
WHERE col_id = inserted_buildings.planet_id
""")

        dropColumn(tableName: "table_buildings") {
            column(name: "temp_col_planet_id")
        }

        addNotNullConstraint(tableName: "table_planet", columnName: "col_buildings_id")
    }

    changeSet(author: "Beheerder (generated)", id: "1451742195029-3") {
        addForeignKeyConstraint(baseColumnNames: "col_buildings_id", baseTableName: "table_planet", constraintName: "FK_bgtouvnxmxtg4q3ue5um2j31r", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "col_id", referencedTableName: "table_buildings")
    }
}
