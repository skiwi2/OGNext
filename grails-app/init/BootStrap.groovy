import grails.util.Holders
import org.grails.config.PropertySourcesConfig
import org.grails.config.yaml.YamlPropertySourceLoader
import org.springframework.core.io.FileSystemResource

import javax.naming.InitialContext
import javax.naming.NoInitialContextException
import java.nio.file.Paths

class BootStrap {
    def init = { servletContext ->
        try {
            def initialContext = new InitialContext()
            def environmentContext = initialContext.lookup("java:comp/env")
            def applicationYmlPath = (String)environmentContext.lookup("applicationYmlPath")

            def applicationYmlFile = Paths.get(applicationYmlPath).toFile()
            def resource = new FileSystemResource(applicationYmlFile)
            def mapPropertySource = new YamlPropertySourceLoader().load(applicationYmlFile.name, resource, null)
            def externalConfig = new PropertySourcesConfig(mapPropertySource.getSource())   // do NOT change .getSource() to source

            Holders.config.merge(externalConfig)
            Holders.grailsApplication.configChanged()

        } catch (NoInitialContextException ex) {
            //suppress, the code is not being run as a WAR inside Tomcat
        }
    }

    def destroy = {
    }
}
