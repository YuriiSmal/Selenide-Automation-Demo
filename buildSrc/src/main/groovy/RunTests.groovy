import org.gradle.api.tasks.testing.Test

abstract class RunTests extends Test {

    void run(String testType, String project) {
        group = 'auto'
        systemProperties System.getProperties()
        doFirst {
            println 'Running ' + testType + ' in ' + project
            useTestNG() {
                useDefaultListeners = true
                switch (testType) {
                    case ('debugTest'):
                        threadCount = 3
                        parallel = 'methods'
                        setSuiteName("${project.toUpperCase()}  ${testType} suite")
                        setTestName("${project.toUpperCase()}  ${testType} tests")
                        break
                    case ('suiteTest'):
                        suites 'src/test/resources/suites/smoke.yaml'
                        break
                    case ('cucumber'):
                        maxParallelForks = 2
                        suites 'src/test/resources/suites/cucumber_example.yaml'
                        break
                    default:
                        logger.error("Couldn't find suite ${testType}")
                        System.exit(0)
                }
            }
        }

        testLogging {
            showStandardStreams = true
            exceptionFormat = 'full'
        }

        outputs.upToDateWhen { false }
    }
}
