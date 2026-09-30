plugins {
    id("java")
    application;
}

application {
    mainClass.set("com.konst_gall.noteLib.GUI");
}

group = "com.konst_gall.noteLib"
version = "1.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.formdev:flatlaf:3.5.4")
    implementation("com.formdev:flatlaf-intellij-themes:3.5.4")
}

tasks.jar{
    manifest {
        attributes("Main-Class" to "com.konst_gall.noteLib.GUI")
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}