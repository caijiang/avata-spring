package ai.avata.spring.bean

import ai.avata.spring.AvataService
import ai.avata.spring.model.*
import ai.avata.spring.support.AvataHandler
import ai.avata.spring.support.Common
import ai.avata.spring.support.Sign
import ai.avata.spring.support.Transform
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializerFeature
import org.apache.commons.logging.LogFactory
import org.apache.http.HttpMessage
import org.apache.http.client.entity.EntityBuilder
import org.apache.http.client.methods.*
import org.apache.http.client.utils.URIBuilder
import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.entity.ContentType
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.ssl.SSLContextBuilder
import org.springframework.data.domain.*
import java.net.URI
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.annotation.PostConstruct

/**
 * @author CJ
 */
//@Service
@Suppress("UNCHECKED_CAST")
class AvataServiceImpl(
    private val apiKey: String = "L2I290W7L2J6109962H4n2w3D060E5R",
    private val apiSecret: String = "o2C2C0E7t2L6H0f9o26492Y3I0e0h5B",
    private val apiTarget: String = "https://stage.apis.avata.bianjie.ai",
    private val testZone: Boolean = true
) : AvataService {
    private val log = LogFactory.getLog(AvataServiceImpl::class.java)
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    @PostConstruct
    fun init() {
        if (!apiTarget.startsWith("https://apis.")) {
            log.warn("现在 AvataService 并不是工作在正式环境中。")
        }
        if (testZone) {
            log.warn("AvataService 开启了测试专用机制，这会降低安全性能。")
        }
    }

    override fun queryAccounts(
        account: String?,
        name: String?,
        operationId: String?,
        start: LocalDate?,
        end: LocalDate?,
        pageable: Pageable?
    ): Page<Account> {
        val parameters = mutableMapOf<String, String>()
        account?.let { parameters["account"] = it }
        name?.let { parameters["name"] = it }
        operationId?.let { parameters["operation_id"] = it }
        start?.let { parameters["start_date"] = it.format(formatter) }
        end?.let { parameters["end_date"] = it.format(formatter) }
        requestWithPageable(pageable, parameters)

        // 读取Pageable,需要
        val result = executeWithoutBody<Map<String, Any>>(GET, "/v1beta1/accounts", parameters)
        val list = (result["accounts"] as List<Map<String, Any>>).map(Transform.readAccount())
        return resolveToPage(pageable, result, list)
    }

    override fun createAccount(name: String): String {
        val x = executeWithBody<Map<String, String>>(
            POST, "/v1beta1/account", mapOf(
                "name" to name,
                "operation_id" to Common.uuidString()
            )
        )
        return x["account"]!!
    }

    override fun createNFTType(
        name: String,
        owner: String,
        classId: String?,
        symbol: String?,
        uri: String?,
        hash: String?,
        tag: Map<String, String>?,
        data: String?,
        description: String?
    ): String {
        val body = mutableMapOf<String, Any>()
        body["name"] = name
        body["owner"] = owner
        classId?.let {
            body["class_id"] = it
        }
        symbol?.let {
            body["symbol"] = it
        }
        description?.let {
            body["description"] = it
        }
        uri?.let {
            body["uri"] = it
        }
        hash?.let {
            body["uri_hash"] = it
        }
        data?.let {
            body["data"] = it
        }
        tag?.let {
            body["tag"] = it
        }
        body["operation_id"] = Common.uuidString()

        val rs = executeWithBody<Map<String, String>>(POST, "/v1beta1/nft/classes", body)
        return rs["operation_id"].toString()
    }

    override fun queryNFTTypes(
        id: String?,
        name: String?,
        owner: String?,
        start: LocalDate?,
        end: LocalDate?,
        pageable: Pageable?
    ): Page<NFTType> {
        val parameters = mutableMapOf<String, String>()
        id?.let { parameters["id"] = it }
        name?.let { parameters["name"] = it }
        owner?.let { parameters["owner"] = it }
        start?.let { parameters["start_date"] = it.format(formatter) }
        end?.let { parameters["end_date"] = it.format(formatter) }
        requestWithPageable(pageable, parameters)

        val result = executeWithoutBody<Map<String, Any>>(GET, "/v1beta1/nft/classes", parameters)
        val list = (result["classes"] as List<Map<String, Any>>).map(Transform.readNFTType())
        return resolveToPage(pageable, result, list)
    }

    override fun queryNFTTypeDetail(id: String): NFTTypeDetail {
        val rs = executeWithoutBody<Map<String, Any>>(GET, "/v1beta1/nft/classes/$id")
        return Transform.readNFTTypeDetail()(rs)
    }

    override fun issueNFT(
        typeClassId: String,
        name: String,
        recipient: String?,
        uri: String?,
        hash: String?,
        tag: Map<String, String>?,
        data: String?
    ): String {
        val body = mutableMapOf<String, Any>()
        body["name"] = name
        body["operation_id"] = Common.uuidString()
        uri?.let {
            body["uri"] = it
        }
        hash?.let {
            body["uri_hash"] = it
        }
        data?.let {
            body["data"] = it
        }
        recipient?.let {
            body["recipient"] = it
        }
        tag?.let {
            body["tag"] = it
        }
        val rs = executeWithBody<Map<String, String>>(POST, "/v1beta1/nft/nfts/${typeClassId}", body)
        return rs["operation_id"].toString()
    }

    override fun queryNFT(
        id: String?,
        name: String?,
        typeClassId: String?,
        owner: String?,
        burned: Boolean,
        start: LocalDate?,
        end: LocalDate?,
        pageable: Pageable?
    ): Page<NFT> {
        val parameters = mutableMapOf<String, String>()
        id?.let { parameters["id"] = it }
        name?.let { parameters["name"] = it }
        typeClassId?.let { parameters["class_id"] = it }
        owner?.let { parameters["owner"] = it }
        parameters["status"] = if (burned) "burned" else "active"
        start?.let { parameters["start_date"] = it.format(formatter) }
        end?.let { parameters["end_date"] = it.format(formatter) }
        requestWithPageable(pageable, parameters)

        val result = executeWithoutBody<Map<String, Any>>(GET, "/v1beta1/nft/nfts", parameters)
        val list = (result["nfts"] as List<Map<String, Any>>).map(Transform.readNFT())
        return resolveToPage(pageable, result, list)
    }

    override fun deleteNFT(typeClassId: String, owner: String, id: String, tag: Map<String, String>?): String {
        val body = mutableMapOf<String, Any>()
        body["operation_id"] = Common.uuidString()
        tag?.let {
            body["tag"] = it
        }
        val rs = executeWithBody<Map<String, String>>(DELETE, "/v1beta1/nft/nfts/${typeClassId}/${owner}/${id}", body)
        return rs["operation_id"].toString()
    }

    override fun queryNFTDetail(typeClassId: String, id: String): NFTDetail {
        val rs = executeWithoutBody<Map<String, Any>>(GET, "/v1beta1/nft/nfts/${typeClassId}/$id")
        return Transform.readNFTDetail()(rs)
    }

    private fun <T : Any> executeWithoutBody(
        method: (String) -> HttpRequestBase,
        uri: String,
        parameters: Map<String, String>? = null,
    ): T {
        createClient()
            .use { client ->
                val time = System.currentTimeMillis()
                val m = method("$apiTarget$uri")

                withParameters(parameters, m)

                withSign(m, time, uri, null, parameters)

                return client.execute(m, AvataHandler<T>())
            }
    }

    private fun <T : Any> executeWithBody(
        method: (String) -> HttpEntityEnclosingRequestBase,
        uri: String,
        body: Map<String, Any>?,
        parameters: Map<String, String>? = null,
    ): T {
        createClient()
            .use { client ->
                val time = System.currentTimeMillis()
                val m = method("$apiTarget$uri")

                withParameters(parameters, m)

                body?.let {
                    m.entity = EntityBuilder
                        .create()
                        .setContentType(ContentType.APPLICATION_JSON)
                        .setText(JSON.toJSONString(it, SerializerFeature.MapSortField))
                        .build()
                }

                withSign(m, time, uri, body, parameters)

                return client.execute(m, AvataHandler<T>())
            }
    }

    private fun withParameters(parameters: Map<String, String>?, method: HttpRequestBase) {
        parameters?.let {
            var uriBuilder = URIBuilder(method.uri)

            it.forEach { (t, u) -> uriBuilder = uriBuilder.addParameter(t, u) }

            method.uri = uriBuilder.build()
        }
    }

    private fun withSign(
        m: HttpMessage,
        time: Long,
        uri: String,
        body: Map<String, Any>?,
        parameters: Map<String, String>?,
    ) {
        m.addHeader("X-Api-Key", apiKey)
        m.addHeader("X-Timestamp", time.toString())
        val s = Sign.signRequest(uri, parameters, body, time, apiSecret)
        m.addHeader("X-Signature", s)
    }

    private fun createClient(): CloseableHttpClient {
        return HttpClientBuilder.create()
            .setSSLSocketFactory(if (testZone) {
                val builder = SSLContextBuilder()
                builder.loadTrustMaterial(null) { _, _ -> true }
                SSLConnectionSocketFactory(builder.build(), NoopHostnameVerifier.INSTANCE)
            } else null)
            .build()
    }

    private fun requestWithPageable(
        pageable: Pageable?,
        parameters: MutableMap<String, String>
    ) {
        pageable?.let {
            parameters["limit"] = it.pageSize.coerceAtMost(50).toString()
            parameters["offset"] = it.offset.toString()

            if (!it.sort.isUnsorted && !it.sort.isEmpty) {
                // 有确定的排序要求
                // 只取第一个、
                val sort = it.sort.first()
                parameters["sort_by"] = (sort.property + "_" + sort.direction).uppercase()
            }
        }
    }

    private fun <T> resolveToPage(pageable: Pageable?, data: Map<String, Any>, list: List<T>): Page<T> {
        val size = (data["limit"] as Number).toInt().let { if (it == 0) 10 else it }

        return PageImpl(
            list,
            PageRequest.of((data["offset"] as Number).toInt() / size, size, pageable?.sort ?: Sort.unsorted()),
            (data["total_count"] as Number).toLong()
        )
    }

//        Chain-ID：testing
//        RPC：testnet.bianjie.ai:26657
//        gRPC：testnet.bianjie.ai:9090

//        Chain ID
//                testing
//        项目名称
//        Avata平台测试项目
//        项目 ID
//                U2b2q0H7H2b6R0J9
//        Avata API Key
//        L2I290W7L2J6109962H4n2w3D060E5R
//        Avata API Secret
//        o2C2C0E7t2L6H0f9o26492Y3I0e0h5B
//        API 服务请求域名
//                https://stage.apis.avata.bianjie.ai/

//        https://www.bsnbase.com/static/tmpFile/bzsc/openper/7-3-1.html
//        val mnemonic =
//            "opera vivid pride shallow brick crew found resist decade neck expect apple chalk belt sick author know try tank detail tree impact hand best"
//
//        val km = KeyManagerFactory.createDefault();
//        km.recover(mnemonic)
//
//        val clientConfig = ClientConfig(
//            "https://stage.apis.avata.bianjie.ai",
//            null,
//            "testing",
//            null
//        )
//
//        val opbConfig = OpbConfig("U2b2q0H7H2b6R0J9", "L2I290W7L2J6109962H4n2w3D060E5R", km.currentKeyInfo.address)
//
//        val client = OpbClient(clientConfig, opbConfig, km)

    companion object {
        private val POST: (String) -> HttpPost = {
            HttpPost(it)
        }

        private val GET: (String) -> HttpGet = {
            HttpGet(it)
        }

        private val DELETE: (String) -> HttpEntityEnclosingRequestBase = {
            MyDelete(it)
        }
    }

    class MyDelete(uri: String) : HttpEntityEnclosingRequestBase() {
        init {
            setURI(URI(uri))
        }

        override fun getMethod(): String = "DELETE"

    }

}