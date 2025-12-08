package demo.todo.common.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("spring.api-version")
data class ApiVersionProperties(
    val supportVersions: List<String>,
)
