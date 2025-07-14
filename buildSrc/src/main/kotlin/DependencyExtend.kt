import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

fun Project.hiltDependency() {
    project.dependencies {
        implementation(libs.hilt.android)
        ksp(libs.hilt.compiler)
    }
}

private val Project.libs get() = the<LibrariesForLibs>()

private fun DependencyHandlerScope.implementation(vararg list: Any) {
    list.forEach { add("implementation", it) }
}

private fun DependencyHandlerScope.debugImplementation(vararg list: Any) {
    list.forEach { add("debugImplementation", it) }
}

private fun DependencyHandlerScope.kapt(vararg list: Any) {
    list.forEach { add("kapt", it) }
}

private fun DependencyHandlerScope.ksp(vararg list: Any) {
    list.forEach { add("ksp", it) }
}

private fun DependencyHandlerScope.testImplementation(vararg list: Any) {
    list.forEach { add("testImplementation", it) }
}

private fun DependencyHandlerScope.testRuntimeOnly(vararg list: Any) {
    list.forEach { add("testRuntimeOnly", it) }
}

private fun DependencyHandlerScope.androidTestImplementation(vararg list: Any) {
    list.forEach { add("androidTestImplementation", it) }
}

private fun DependencyHandlerScope.androidTestRuntimeOnly(vararg list: Any) {
    list.forEach { add("androidTestRuntimeOnly", it) }
}

private fun DependencyHandlerScope.annotationProcessor(vararg list: Any) {
    list.forEach { add("annotationProcessor", it) }
}