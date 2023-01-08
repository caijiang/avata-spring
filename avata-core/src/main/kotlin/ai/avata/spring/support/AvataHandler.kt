package ai.avata.spring.support

import ai.avata.spring.exception.ApiException
import com.alibaba.fastjson.JSON
import org.apache.commons.logging.LogFactory
import org.apache.http.HttpResponse
import org.apache.http.client.HttpResponseException
import org.apache.http.client.ResponseHandler
import java.nio.charset.Charset

/**
 * @author CJ
 */
@Suppress("UNCHECKED_CAST")
class AvataHandler<T> : ResponseHandler<T> {

    private val log = LogFactory.getLog(AvataHandler::class.java)

    override fun handleResponse(response: HttpResponse): T {
        val statusLine = response.statusLine
        val entity = response.entity
        val text = entity.content.bufferedReader(Charset.forName("UTF-8")).readText()
        if (statusLine.statusCode != 200) {
            try {
                val d1 = JSON.parseObject(text)
//                "{"error":{"code_space":"NFT","code":"BAD_REQUEST","message":"invalid class_id format"}}"
//            EntityUtils.consume(entity)
                val error = d1.getJSONObject("error") ?: d1
                throw ApiException(
                    error["code"]?.toString(),
                    error["code_space"]?.toString(),
                    error["message"]?.toString(),
                )
            } catch (e: ApiException) {
                throw e
            } catch (e: Exception) {
                log.error("avata渲染异常失败", e)
                throw HttpResponseException(
                    statusLine.statusCode,
                    statusLine.reasonPhrase
                )
            }
        }

        val d2 = JSON.parseObject(text)
        return d2["data"] as T
    }
}