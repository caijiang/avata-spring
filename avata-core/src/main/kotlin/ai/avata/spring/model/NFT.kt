package ai.avata.spring.model

/**
 * @author CJ
 */
data class NFT(
    /**
     * NFT ID
     */
    val id: String,
    /**
     * NFT 名称
     */
    val name: String,
    /**
     * NFT 类别 ID
     */
    val typeClassId: String,
    /**
     * NFT 类别名称
     */
    val className: String,
    /**
     * NFT 类别标识
     */
    val classSymbol: String?,
    /**
     * 链外数据链接
     */
    val uri: String?,
    /**
     * NFT 持有者地址
     */
    val owner: String,
    /**
     * NFT 状态
     */
    val burned: Boolean,
    /**
     * NFT 发行时间戳（UTC 时间）
     */
    val timestamp: String,
)