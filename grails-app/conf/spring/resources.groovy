import com.ognext.beans.InfoBean
import org.grails.io.support.ClassPathResource

// Place your Spring DSL code here

def infoProperties = new Properties()
infoProperties.load(new ClassPathResource("info.properties").inputStream)

def infoUncommittedChanges = (infoProperties["uncommittedChanges"] == "true")
def infoGitDiff = ""
if (infoUncommittedChanges) {
    infoGitDiff = new ClassPathResource("gitdiff.txt").file.text
}

beans = {
    infoBean(InfoBean) {
        version = infoProperties["version"]
        commitHash = infoProperties["commitHash"]
        uncommittedChanges = infoUncommittedChanges
        gitDiff = infoGitDiff
    }
}
