package ai.avata.spring.model

/**
 * @author CJ
 */
data class Account(
    /**
     * 链账户地址 128
     */
    val account: String,
    /**
     * 链账户名称 20
     */
    val name: String,
    /**
     * 文昌链能量值余额
     */
    val gas: Number,
    /**
     * 文昌链 DDC 业务费余额，单位：分
     */
    val fee: Number,
    val operationId: String,
    val enabled: Boolean,
)
