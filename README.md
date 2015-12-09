OLog
====

Getting started
---------------

OLog mainly consists of two parts. You are currently looking at the server part.

Clone the repository, checkout the develop tree and you got yourself your own OLog server.
Once it runs, use the OLog userscript to send data to the server.

Requirements
------------

- [Grails 3](https://grails.org/download.html) - Easy to install using SDKMAN

Working in an IDE
-----------------

You can import the project in IntelliJ by opening `build.gradle`.

Note: there may be [some quirks](https://github.com/libgdx/libgdx/wiki/Gradle-and-Intellij-IDEA) with Gradle.

Database
--------

To succesfully store and retrieve data a [PostgreSQL](http://www.postgresql.org/) database is required.

Build, run and deploy
---------------------

To compile the server fresh from the source and run, the following will suffice:

    grails run-app

To deploy the server in environments like Tomcat, you'll need a WAR file.

    grails war

By default the WAR will be build in `build/libs/` as `OLog-*.war`, where `*` is the current version.

In Tomcat, deploy the WAR in the `webapps` folder. By default, the WAR will be extracted to a `OLog-*` directory.

Create a new YML in a location of your choosing named `olog-config.yml` and give it the following content:

    dataSource:
        dbCreate: none
        url: "jdbc:postgresql://localhost:5432/postgresdb"
        driverClassName: org.postgresql.Driver
        dialect: org.hibernate.dialect.PostgreSQLDialect
        username: postgresuser
        password: passwd
        properties:
            jmxEnabled: true
            initialSize: 5
            maxActive: 50
            minIdle: 5
            maxIdle: 25
            maxWait: 10000
            maxAge: 600000
            timeBetweenEvictionRunsMillis: 5000
            minEvictableIdleTimeMillis: 60000
            validationQuery: SELECT 1
            validationQueryTimeout: 3
            validationInterval: 15000
            testOnBorrow: true
            testWhileIdle: true
            testOnReturn: false
            jdbcInterceptors: ConnectionState
            defaultTransactionIsolation: 2 # TRANSACTION_READ_COMMITTED
       
    grails:
        plugin:
            databasemigration:
                updateOnStart: true
                updateOnStartFileNames: "changelog.groovy"
                changelogLocation: ""

Where `postgresdb` is your database name, `postgresuser` your database username and `passwd` the username's password.

Create a new XML in `tomcat/conf/Catalina/localhost` named `OLog-*.war` and give it the following content:

    <Context>
        <Environment name="applicationYmlPath" value="**/olog-config.yml" type="java.lang.String" />
    </Context>

Where `**` is the location of your YML.

When run from the source, the server is available by default at `localhost:8080`.

When deployed, the server is available at `localhost:8080/OLog-*`.

Run unit tests
--------------

Run unit tests with:

    grails test-app

*Note: unit tests are still being worked on.*

How to contribute
-----------------

This is a rapidly changing work in progress. That means pull requests are in danger of becoming obsolete before they arrive.

Find us in the [OLog chat](http://chat.stackexchange.com/rooms/30740/olog-ogame-logger-and-personal-assistant) or open a ticket for feature requests.
