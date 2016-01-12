OGNext
====

What is OGNext?
-------------

OGNext is planning to be the next big support tool for OGame.

- Store reports (combat, espionage, harvest and missile)
- Easily accessible universe overview
- Search for players and possible targets
- Simplify the calculation of whether a recently spied upon planet is profitable to attack
- Recommendations on fleet ratio and consistency when targeting a specific planet

All by making as much use as possible of the official OGame API.

Getting started
---------------

OGNext mainly consists of two parts. You are currently looking at the server part.

Clone the repository, checkout the develop tree and you've got yourself your own OGNext server.
Once it runs, use the OGNext userscript to send data to the server.

Requirements
------------

- [Grails 3](https://grails.org/download.html) - Easy to install using SDKMAN

- Java 8 - [JRE](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) if you just want to run your own OGNext server, [JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) if you also want to contribute to the development

- [PostgreSQL](http://www.postgresql.org/) database

- [Git](https://git-scm.com/downloads) should be available on the command line

OGNext has been tested to work with Grails 3.0.9, Java 8u45, PostgreSQL 9.4 and Git 1.8.5.2. We **strongly** recommend you to use the newest versions though for enhanced security.

Development
-----------

Once you've set-up a database, running a development server is quite easy:

    grails run-app

When run from the source, the server is available by default at `localhost:8080`.

If you have made any changes to the domain classes, then you will need to update the database migrations file, as temporary workaround copy the `dataSource` config in your external config file to be at:
 
    environments:
        production:
            dataSource:
 
in your `application.yml` file, such that when you create migrations for a PostgreSQL database, then the there is a production database available. Please note that this database should be up and running.
 
Then run:

    grails prod dbm-gorm-diff describewhatyoudid.groovy --add

Deployment
----------

Additional requirement when deploying a server:

- [Tomcat](https://tomcat.apache.org/) or similar
- The `CATALINA_HOME` environment variable should be set to the top-level directory of the Tomcat installation.

OGNext has been tested to both work with Tomcat 7 and Tomcat 8.

To deploy the server in environments like Tomcat, you'll need a WAR file.

    grails war

By default the WAR will be build in `build/libs/` as `OGNext-*.war`, where `*` is the current version.

In Tomcat, deploy the WAR in the `webapps` folder. By default, the WAR will be extracted to a `OGNext-*` directory.

Create a new YML in a location of your choosing named `ognext-config.yml` and give it the following content:

    dataSource:
        dbCreate: none
        url: "jdbc:postgresql://localhost:5432/ognextdb"
        driverClassName: org.postgresql.Driver
        dialect: org.hibernate.dialect.PostgreSQLDialect
        username: ognextuser
        password: ognextpassword
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
                
You **need** to modify the following values:

 - `ognextdb` (in `url`) - this is the database name
 - `ognextuser` - this is the database user, be sure to give it permissions to create tables and modify them
 - `ognextpassword` - this is the password for the database user
 
You **must not** modify the following values:

 - `dbCreate: none`
 - `driverClassName: org.postgresql.Driver`
 - `dialect: org.hibernate.dialect.PostgreSQLDialect`
 
You may modify anything else, please look at [the Grails documentation](http://grails.github.io/grails-doc/latest/guide/conf.html#dataSource) to do so.

You can also disable `grails.plugin.databasemigration.updateOnStart`, but then you will need to update the database yourself any time the domain classes have changes, this is not recommended.

Then create a new XML in `tomcat/conf/Catalina/localhost` named `OGNext-*.war` and give it the following content (where `*` is still the OGNext version):

    <Context>
        <Environment name="applicationYmlPath" value="**/ognext-config.yml" type="java.lang.String" />
    </Context>

Where `**` is the directory in which your YML file is located.

When deployed, the server is available at `localhost:8080/OGNext-*`.

Run unit tests
--------------

Run unit tests with:

    grails test-app

*Note: unit tests are still being worked on.*

How to contribute
-----------------

This is a rapidly changing work in progress. That means pull requests are in danger of becoming obsolete before they arrive.

Find us in the [OGNext chat](http://chat.stackexchange.com/rooms/30740/ognext-next-level-personal-ogame-assistant) or open a ticket for feature requests.
