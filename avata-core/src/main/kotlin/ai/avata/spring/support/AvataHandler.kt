package ai.avata.spring.support

import ai.avata.spring.exception.ApiException
import com.alibaba.fastjson.JSON
import org.apache.http.HttpResponse
import org.apache.http.client.HttpResponseException
import org.apache.http.client.ResponseHandler
import java.nio.charset.Charset

/**
 * @author CJ
 */
@Suppress("UNCHECKED_CAST")
class AvataHandler<T> : ResponseHandler<T> {

    override fun handleResponse(response: HttpResponse): T {
        val statusLine = response.statusLine
        val entity = response.entity
        val text = entity.content.bufferedReader(Charset.forName("UTF-8")).readText()
        if (statusLine.statusCode != 200) {
            try {
                val d1 = JSON.parseObject(text)
//            EntityUtils.consume(entity)
                throw ApiException(
                    d1["code"]?.toString(),
                    d1["code_space"]?.toString(),
                    d1["message"]?.toString(),
                )
            } catch (e: Exception) {
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