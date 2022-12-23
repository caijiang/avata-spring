package ai.avata.spring.support

import java.util.*

/**
 * @author CJ
 */
object Common {
    fun uuidString(): String {
        return UUID.randomUUID().toString()
    }
}