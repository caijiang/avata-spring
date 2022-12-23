package ai.avata.spring.model

/**
 * @author CJ
 */
data class NFTTypeDetail(
    val summary: NFTType,
    /**
     * NFT 类别描述
     */
    val description: String?,
    /**
     * 链外数据 Hash
     */
    val hash: String?,
    /**
     * 自定义链上元数据
     */
    val data: String?,
)
