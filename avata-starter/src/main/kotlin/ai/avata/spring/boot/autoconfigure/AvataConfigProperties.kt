package ai.avata.spring.boot.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * @author CJ
 */
@ConfigurationProperties("ai.avata")
class AvataConfigProperties(
    /**
     * Avata API Key,缺省会使用`L2I290W7L2J6109962H4n2w3D060E5R`
     */
    var apiKey: String? = null,
    /**
     * Avata API Secret,缺省会使用`o2C2C0E7t2L6H0f9o26492Y3I0e0h5B`
     */
    var apiSecret: String? = null,
    /**
     * api服务的访问目标服务器，缺省会使用`https://stage.apis.avata.bianjie.ai`,正式环境应该使用`https://apis.avata.bianjie.ai`
     */
    var apiTarget: String? = null,
    /**
     * 是否应用测试机制
     */
    var testZone: Boolean = false,
)