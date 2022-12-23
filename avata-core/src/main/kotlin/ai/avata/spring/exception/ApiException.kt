package ai.avata.spring.exception

/**
 * @author CJ
 */
class ApiException(
    val code: String?,
    val space: String?,
    message: String?
) : Exception(message)