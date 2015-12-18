databaseChangeLog = {

    changeSet(author: "Beheerder (generated)", id: "1450457161988-1") {
        addColumn(tableName: "table_planet") {
            column(name: "col_date_deleted", type: "timestamp")
        }
    }

    changeSet(author: "Beheerder (generated)", id: "1450457161988-2") {
        addColumn(tableName: "table_planet") {
            column(name: "col_deleted", type: "boolean")
        }

        addNotNullConstraint(tableName: "table_planet", columnName: "col_deleted", defaultNullValue: "false")
    }
}
