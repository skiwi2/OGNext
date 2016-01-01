package com.ognext

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import org.grails.config.yaml.YamlPropertySourceLoader
import org.springframework.context.EnvironmentAware
import org.springframework.core.env.Environment
import org.springframework.core.io.FileSystemResource

import javax.naming.InitialContext
import javax.naming.NoInitialContextException
import java.nio.file.Paths

class Application extends GrailsAutoConfiguration implements EnvironmentAware {
    static void main(String[] args) {
        GrailsApp.run(Application, args)
    }

    @Override
    void setEnvironment(Environment environment) {
        try {
            def initialContext = new InitialContext()
            def environmentContext = initialContext.lookup("java:comp/env")
            def applicationYmlPath = (String)environmentContext.lookup("applicationYmlPath")

            def applicationYmlFile = Paths.get(applicationYmlPath).toFile()
            def resource = new FileSystemResource(applicationYmlFile)
            def yamlPropertySource = new YamlPropertySourceLoader().load(applicationYmlFile.name, resource, null)

            environment.propertySources.addFirst(yamlPropertySource)
        } catch (NoInitialContextException ex) {
            //suppress, the code is not being run as a WAR inside Tomcat
        }
    }
}