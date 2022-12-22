package ai.avata.spring.boot.autoconfigure

import ai.avata.spring.AvataService
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

/**
 * @author CJ
 */
@Configuration
@ConditionalOnClass(AvataService::class)
//    @AutoConfigureBefore(
//        DataSourceAutoConfiguration.class)
//@EnableConfigurationProperties({ DruidStatProperties.class, DataSourceProperties .class })
class AvataAutoConfigure {

    @PostConstruct
    fun init(){
        println("init")
    }
}