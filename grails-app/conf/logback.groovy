import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.FileAppender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy
import ch.qos.logback.core.status.OnConsoleStatusListener
import grails.util.BuildSettings
import grails.util.Environment

// See http://logback.qos.ch/manual/groovy.html for details on configuration

statusListener(OnConsoleStatusListener)

def CATALINA_HOME = System.getProperty("catalina.base")
def APP_NAME = "OGNext" //TODO do not hardcode this, might be possible via http://stackoverflow.com/a/10466224/2057294

appender('STDOUT', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%level %logger - %msg%n"
    }
}

appender('FILE', RollingFileAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d %level %thread %logger - %msg%n"
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${CATALINA_HOME}/logs/${APP_NAME}/error-%d{yyyy-MM-dd}.log"
    }
}

def loggerNames = ['STDOUT']
if (Environment.warDeployed) {
    loggerNames << 'FILE'
}
root(ERROR, loggerNames)

def targetDir = BuildSettings.TARGET_DIR
if (Environment.developmentMode && targetDir) {
    appender("FULL_STACKTRACE", FileAppender) {
        file = "${targetDir}/stacktrace.log"
        append = true
        encoder(PatternLayoutEncoder) {
            pattern = "%level %logger - %msg%n"
        }
    }
    logger("StackTrace", ERROR, ['FULL_STACKTRACE'], false)
}
