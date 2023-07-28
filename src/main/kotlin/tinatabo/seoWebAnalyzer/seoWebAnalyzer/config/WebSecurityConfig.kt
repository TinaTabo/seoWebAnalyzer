package tinatabo.seoWebAnalyzer.seoWebAnalyzer.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

//-- Archivo de configuracion CORS para habilitar el acceso a la API desde la app web
//-- Angular (front de este proyecto) y permiso para utilizar los métodos GET, POST, PUT y DELETE.
@Configuration
class WebSecurityConfig: WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:4200")
            .allowedMethods("*")
    }
}