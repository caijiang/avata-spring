package ai.avata.spring.boot.autoconfigure

import ai.avata.spring.AvataService
import ai.avata.spring.bean.AvataServiceImpl
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author CJ
 */
@Configuration
@ConditionalOnClass(AvataService::class)
//@AutoConfigureBefore
@EnableConfigurationProperties(AvataConfigProperties::class)
class AvataAutoConfigure {

    @Bean
    fun avataService(properties: AvataConfigProperties): AvataService {
        return AvataServiceImpl(
            apiKey = properties.apiKey ?: "L2I290W7L2J6109962H4n2w3D060E5R",
            apiSecret = properties.apiSecret ?: "o2C2C0E7t2L6H0f9o26492Y3I0e0h5B",
            apiTarget = properties.apiTarget ?: "https://stage.apis.avata.bianjie.ai",
            testZone = properties.testZone
        )
    }
}