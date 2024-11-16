plugins {
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.allopen") version "2.0.21"
    id("org.openapi.generator") version "5.4.0"
    id("io.quarkus")
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-resteasy-jsonb")
    implementation("io.quarkus:quarkus-smallrye-openapi")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-resteasy")
    implementation("io.quarkus:quarkus-resteasy-jackson")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.quarkus:quarkus-arc")
    implementation("org.mongodb:mongodb-driver-sync:4.9.0")
    implementation("com.squareup.moshi:moshi:1.13.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.13.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("io.swagger:swagger-annotations:1.6.3")
    implementation("org.hibernate.validator:hibernate-validator:6.0.13.Final")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
    testImplementation("org.wiremock:wiremock:3.9.2")
}

group = "com.example"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
allOpen {
    annotation("jakarta.ws.rs.Path")
    annotation("jakarta.enterprise.context.ApplicationScoped")
    annotation("jakarta.persistence.Entity")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

tasks.register("generateApiSpec", Exec::class) {
    // Define the OpenAPI Generator command
    val generatorVersion = "6.0.0" // Make sure this is a version you want
    val outputDir = "src/main/kotlin/generated" // Output directory for generated code
    val specFile = "src/main/resources/openapi.yaml" // Path to your OpenAPI spec file

    // Check if the OpenAPI generator JAR is present, download if necessary
    val generatorJar = file("src/main/kotlin/generated/openapi-generator-cli.jar")
    if (!generatorJar.exists()) {
        exec {
            commandLine("curl", "-o", generatorJar.absolutePath, "-L", "https://repo1.maven.org/maven2/org/openapitools/openapi-generator-cli/$generatorVersion/openapi-generator-cli-$generatorVersion.jar")
        }
    }

    commandLine(
        "java", "-jar", generatorJar.absolutePath,
        "generate",
        "-i", specFile,                        // Input specification file
        "-g", "jaxrs-spec",                    // Generator for Java JAX-RS spec (interface-based)
        "-o", outputDir,                       // Output directory
        "--additional-properties",
        "interfaceOnly=true"
    )

    doLast {
        fileTree(outputDir).matching {
            include("**/*.java", "**/*.kt")
        }.forEach { file ->
            var content = file.readText()
            var modifiedContent = content.replace("javax", "jakarta")
            file.writeText(modifiedContent)

            content = file.readText()
            modifiedContent = content.replace("org.openapitools", "src.gen.java.org.openapitools")
            file.writeText(modifiedContent)
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
    kotlinOptions.javaParameters = true
}

sourceSets {
    main {
        java.srcDir("src/main/kotlin/generated")
    }
}
