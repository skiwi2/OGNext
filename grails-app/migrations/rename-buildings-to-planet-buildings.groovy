databaseChangeLog = {

    changeSet(author: "Beheerder (generated)", id: "1452522636568-1") {
        renameTable(newTableName: "table_planet_buildings", oldTableName: "table_buildings")

        sql("ALTER TABLE table_planet_buildings RENAME CONSTRAINT \"table_buildingsPK\" TO \"table_planet_buildingsPK\"")
    }
}
