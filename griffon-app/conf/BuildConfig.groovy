griffon.project.dependency.resolution = {
    inherits "global"
    log "warn"
    repositories {
        griffonHome()
        mavenCentral()
        mavenLocal()
        String basePath = pluginDirPath? "${pluginDirPath}/" : ''
        flatDir name: "carbonadoLibDir", dirs: ["${basePath}lib"]
    }
    dependencies {
        compile('commons-dbcp:commons-dbcp:1.4',
                'commons-pool:commons-pool:1.6',
                'com.h2database:h2:1.3.170',
                'joda-time:joda-time:2.1') {
            transitive = false
        }
        String carbonadoVersion = '1.2.3'
        compile 'com.sleepycat:berkeleydb-je:5.0.58',
                "com.amazon:carbonado:$carbonadoVersion",
                "com.amazon:carbonado-sleepycat-db:$carbonadoVersion",
                "com.amazon:carbonado-sleepycat-je:$carbonadoVersion",
                'org.cojen:cojen:2.2.3'
        build('org.eclipse.jdt:org.eclipse.jdt.core:3.6.0.v_A58') {
            export = false
        }
        String lombokIdea = '0.5'
        build("de.plushnikov.lombok-intellij-plugin:processor-api:$lombokIdea",
              "de.plushnikov.lombok-intellij-plugin:processor-core:$lombokIdea",
              "de.plushnikov.lombok-intellij-plugin:intellij-facade-factory:$lombokIdea",
              "de.plushnikov.lombok-intellij-plugin:intellij-facade-api:$lombokIdea",
              "de.plushnikov.lombok-intellij-plugin:intellij-facade-9:$lombokIdea",
              "de.plushnikov.lombok-intellij-plugin:intellij-facade-10:$lombokIdea",
              "de.plushnikov.lombok-intellij-plugin:intellij-facade-11:$lombokIdea") {
            export = false
            transitive = false
        }
        String ideaVersion = '11.1.4'
        build("org.jetbrains.idea:idea-openapi:$ideaVersion",
              "org.jetbrains.idea:extensions:$ideaVersion",
              "org.jetbrains.idea:util:$ideaVersion",
              "org.jetbrains.idea:annotations:$ideaVersion") {
            export = false
        }
    }
}

log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    appenders {
        console name: 'stdout', layout: pattern(conversionPattern: '%d [%t] %-5p %c - %m%n')
    }

    error 'org.codehaus.griffon',
          'org.springframework',
          'org.apache.karaf',
          'groovyx.net'
    warn  'griffon'
}