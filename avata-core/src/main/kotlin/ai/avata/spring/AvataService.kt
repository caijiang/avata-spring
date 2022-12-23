package ai.avata.spring

import ai.avata.spring.model.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDate

/**
 * 每个方法都有可能抛出
 * - [协议内异常](ai.avata.spring.exception.ApiException)
 * - [响应异常](HttpResponseException)
 * - [其他读写异常](java.io.IOException)
 * @author CJ
 */
interface AvataService {

    /**
     * 创建链账户。
     * @param name 链账户名称，支持 1-20 位汉字、大小写字母及数字组成的字符串
     * @return 链账户地址(128字符串)
     */
    fun createAccount(name: String): String

    /**
     * 查询链账户
     *
     * 可根据文档中给出的具体的查询条件查询和获取与应用方某一项目 ID 相互绑定的链账户地址
     * @param account 链账户地址
     * @param name 链账户名称，支持模糊查询
     * @param operationId 操作 ID。此操作 ID 需要填写在请求创建链账户/批量创建链账户接口时，返回的 Operation ID。
     * @param start 创建日期范围 - 开始(UTC)
     * @param end 创建日期范围 - 结束(UTC)
     * @param pageable 分页和排序
     */
    fun queryAccounts(
        account: String? = null,
        name: String? = null,
        operationId: String? = null,
        start: LocalDate? = null,
        end: LocalDate? = null,
        pageable: Pageable? = null
    ): Page<Account>

    /**
     * 创建 NFT 类别。
     * NFT 类别是 IRITA 底层链对同一资产类型的识别和集合，方便资产发行方对链上资产进行管理和查询。所以在发行 NFT 前，都需要创建 NFT 类别，用
     * 以声明其抽象属性。
     *
     * 请参考[文献](https://forum.avata.bianjie.ai/t/topic/81),简单地概括就是如果资产需要较多说明的，外链地址[uri]可以用json响应,若是
     * 简单的图片文字则可以用更直接的方式渲染在[uri]上；但[hash]都是不可或缺的，都应该是对外链资源的哈希体现。
     *
     * 即便响应成功了，最终数据生效也会存在一定的延时。
     * @param name NFT 类别名称，不超过20字符
     * @param owner NFT 类别权属者地址，拥有在该 NFT 类别中发行 NFT 的权限和转让该 NFT 类别的权限。
     * @param classId NFT 类别 ID，仅支持小写字母及数字，以字母开头; 3到32位字符
     * @param symbol 标识;3到32位字符
     * @param uri 链外数据链接,不超过256字符
     * @param hash 链外数据 Hash,不超过512字符
     * @param tag 交易标签;自定义 key：支持大小写英文字母和汉字和数字，长度 6-12 位，自定义 value：长度限制在 64 位字符，支持大小写字母和数字;不能超过3个元素。
     * @param data 自定义链上元数据;不超过1024字符
     * @param description 描述;不超过300字符
     * @return 操作 ID
     */
    fun createNFTType(
        name: String,
        owner: String,
        classId: String? = null,
        symbol: String? = null,
        uri: String? = null,
        hash: String? = null,
        tag: Map<String, String>? = null,
        data: String? = null,
        description: String? = null,
    ): String

    /**
     * 查询 NFT 类别
     *
     * 根据查询条件，展示 Avata 平台内的 NFT 类别列表
     * @param id NFT 类别 ID
     * @param owner NFT 类别权属者地址
     * @param name NFT 类别名称，支持模糊查询
     * @param start 创建日期范围 - 开始(UTC)
     * @param end 创建日期范围 - 结束(UTC)
     * @param pageable 分页和排序
     */
    fun queryNFTTypes(
        id: String? = null,
        name: String? = null,
        owner: String? = null,
        start: LocalDate? = null,
        end: LocalDate? = null,
        pageable: Pageable? = null
    ): Page<NFTType>

    /**
     * 查询 NFT 类别详情
     * 根据查询条件，展示 Avata 平台内的 NFT 类别的详情信息
     * @param id NFT 类别 ID
     */
    fun queryNFTTypeDetail(id: String): NFTTypeDetail

// TODO   转让 NFT 类别

    /**
     * 发行 NFT
     * NFT 是链上唯一的、可识别的资产，由用户自己在区块链上铸造一个NFT
     *
     * 请参考[文献](https://forum.avata.bianjie.ai/t/topic/81),简单地概括就是如果资产需要较多说明的，外链地址[uri]可以用json响应,若是
     * 简单的图片文字则可以用更直接的方式渲染在[uri]上；但[hash]都是不可或缺的，都应该是对外链资源的哈希体现。
     *
     * 即便响应成功了，最终数据生效也会存在一定的延时。
     * @param typeClassId NFT 类别 ID ,[NFTType.id]
     * @param name NFT 名称; 不超过64字符
     * @param recipient NFT 接收者地址，支持任一文昌链合法链账户地址，默认为 NFT 类别的权属者地址
     * @param uri 链外数据链接,不超过256字符
     * @param hash 链外数据 Hash,不超过512字符
     * @param tag 交易标签;自定义 key：支持大小写英文字母和汉字和数字，长度 6-12 位，自定义 value：长度限制在 64 位字符，支持大小写字母和数字;不能超过3个元素。
     * @param data 自定义链上元数据;不超过1024字符
     * @return
     */
    fun issueNFT(
        typeClassId: String, name: String, recipient: String? = null, uri: String? = null,
        hash: String? = null,
        tag: Map<String, String>? = null,
        data: String? = null,
    ): String

    /**
     * 查询 NFT
     *
     * 根据查询条件，展示 Avata 平台内的 NFT 列表
     * @param id NFT ID
     * @param name NFT 名称，支持模糊查询
     * @param typeClassId NFT 类别 ID
     * @param owner NFT 持有者地址
     * @param burned NFT 状态；是否被销毁，燃尽
     *
     */
    fun queryNFT(
        id: String? = null,
        name: String? = null,
        typeClassId: String? = null,
        owner: String? = null,
        burned: Boolean = false,
        start: LocalDate? = null,
        end: LocalDate? = null,
        pageable: Pageable? = null,
    ): Page<NFT>

    /**
     * 销毁 NFT
     * 用户可以销毁自己链账户地址中拥有的 NFT 。NFT 销毁后，链上依然会保留与该 NFT 相关的链上历史记录信息，但不可再对该 NFT 进行任何的操作。
     *
     * @param token nft
     * @param tag 交易标签;自定义 key：支持大小写英文字母和汉字和数字，长度 6-12 位，自定义 value：长度限制在 64 位字符，支持大小写字母和数字;不能超过3个元素。
     */
    fun deleteNFT(token: NFT, tag: Map<String, String>? = null) {
        deleteNFT(token.typeClassId, token.owner, token.id, tag)
    }

    /**
     * 销毁 NFT
     * 用户可以销毁自己链账户地址中拥有的 NFT 。NFT 销毁后，链上依然会保留与该 NFT 相关的链上历史记录信息，但不可再对该 NFT 进行任何的操作。
     *
     * @param typeClassId NFT 类别 ID
     * @param owner NFT 持有者地址，也是 Tx 签名者地址
     * @param id NFT ID
     * @param tag 交易标签;自定义 key：支持大小写英文字母和汉字和数字，长度 6-12 位，自定义 value：长度限制在 64 位字符，支持大小写字母和数字;不能超过3个元素。
     * @return 操作 ID
     */
    fun deleteNFT(typeClassId: String, owner: String, id: String, tag: Map<String, String>? = null): String

    fun queryNFTDetail(token: NFT): NFTDetail {
        return queryNFTDetail(token.typeClassId, token.id)
    }

    /**
     * 查询 NFT 详情
     * 根据查询条件，展示 Avata 平台内的 NFT 对应的详情信息
     *
     * @param typeClassId NFT 类别 ID
     * @param id NFT ID
     * @return 详情
     */
    fun queryNFTDetail(typeClassId: String, id: String): NFTDetail
}