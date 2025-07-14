import org.gradle.api.Project
import java.util.Properties

fun Project.getBuildConfigProperty(propertyKey: String): String =
    getLocalProperties("./local.properties").getProperty(propertyKey)

fun Project.getLocalProperties(fileName: String) = Properties().apply {
    load(project.rootProject.file(fileName).inputStream())
}