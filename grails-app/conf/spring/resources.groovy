import com.skiwi.olog.beans.InfoBean
import org.grails.io.support.ClassPathResource

// Place your Spring DSL code here

def infoProperties = new Properties()
infoProperties.load(new ClassPathResource("info.properties").inputStream)

beans = {
    infoBean(InfoBean) {
        version = infoProperties["version"]
        commitHash = infoProperties["commitHash"]
    }
}
