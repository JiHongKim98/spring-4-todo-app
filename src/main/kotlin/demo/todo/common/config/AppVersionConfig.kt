package demo.todo.common.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class AppVersionConfig : WebMvcConfigurer {
    /**
     * 쿼리 파라미터 및 헤더 기반 API version
     *
     * VersionParser 는 지정하지 않으면 기본적으로 [org.springframework.web.accept.SemanticApiVersionParser]로 지정됨
     */
    override fun configureApiVersioning(configurer: ApiVersionConfigurer) {
        configurer.apply {
            setDefaultVersion("v1")

//            usePathSegment(1)
            useQueryParam("version")
            useRequestHeader("X-Api-Version")

            // 커스텀시
//            setVersionParser { version ->
//                val raw = version.removePrefix("v")
//                log.info { "version=$version || raw=$raw" }
//
//                when (raw) {
//                    "latest" -> "1"
//                    else -> version
//                }
//            }
        }
    }
}
