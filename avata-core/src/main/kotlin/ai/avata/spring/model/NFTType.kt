package ai.avata.spring.model

/**
 * @author CJ
 */
data class NFTType(
    /**
     * NFT 类别 ID
     */
    val id: String,
    /**
     * NFT 类别名称
     */
    val name: String,
    /**
     * NFT 类别标识
     */
    val symbol: String?,
    /**
     * NFT 类别包含的 NFT 总量
     */
    val count: Int,
    /**
     * 链外数据链接
     */
    val uri: String,
    /**
     * NFT 类别权属者地址
     */
    val owner: String,
    /**
     * 创建 NFT 类别的时间戳（UTC 时间）
     */
    val timestamp: String,
)
