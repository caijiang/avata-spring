package ai.avata.spring.support

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializerFeature
import org.apache.commons.codec.binary.Hex
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.function.BiConsumer

/**
 * @author CJ
 */
object Sign {

    /**
     * 对请求参数进行签名处理
     *
     * @param path      请求路径，仅截取域名后及 Query 参数前部分，例："/v1beta1/accounts";
     * @param query     Query 参数，例："key1=value1&key2=value2"，需转为 Map 格式
     * @param body      Body 参数，例："{\"count\": 1, \"operation_id\": \"random_string\"}"，需转为 Map 格式
     * @param timestamp 当前时间戳（毫秒），例：1647751123703
     * @param apiSecret 应用方的 API Secret，例："AKIDz8krbsJ5yKBZQpn74WFkmLPc5ab"
     * @return 返回签名结果
     */
    fun signRequest(
        path: String,
        query: Map<String, Any>?,
        body: Map<String, Any>?,
        timestamp: Long,
        apiSecret: String
    ): String {
        val paramsMap: MutableMap<String, Any> = HashMap()
        paramsMap["path_url"] = path
        if (!query.isNullOrEmpty()) {
            query.forEach(BiConsumer { key: String, value: Any ->
                paramsMap["query_$key"] = value.toString()
            })
        }
        if (!body.isNullOrEmpty()) {
            body.forEach(BiConsumer { key: String, value: Any ->
                paramsMap["body_$key"] = value
            })
        }

        // 重要提示：下载相应的依赖，请使用上方Java代码前的版本号

        // 将请求参数序列化为排序后的 JSON 字符串
        val jsonStr = JSON.toJSONString(paramsMap, SerializerFeature.MapSortField)

        // 执行签名
        return sha256Sum(jsonStr + timestamp.toString() + apiSecret)
    }

    /**
     * SHA256 摘要
     *
     * @param str
     * @return
     */
    private fun sha256Sum(str: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val encodedHash = digest.digest(str.toByteArray(StandardCharsets.UTF_8))
        return Hex.encodeHexString(encodedHash, true)
//        return bytesToHex(encodedHash)
    }

//    /**
//     * 将 bytes 转为 Hex
//     *
//     * @param hash
//     * @return
//     */
//    private fun bytesToHex(hash: ByteArray): String {
//        val hexString = StringBuilder(2 * hash.size)
//        for (i in hash.indices) {
//            val hex = Integer.toHexString(0xff and hash[i].toInt())
//            if (hex.length == 1) {
//                hexString.append('0')
//            }
//            hexString.append(hex)
//        }
//        return hexString.toString()
//    }
}