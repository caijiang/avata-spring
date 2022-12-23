package ai.avata.spring.support

import ai.avata.spring.model.*

/**
 * @author CJ
 */
object Transform {
    fun readNFTDetail(): (Map<String, Any>) -> NFTDetail {
        return {
            NFTDetail(
                readNFT()(it),
                it["uri_hash"] as? String,
                it["data"] as? String,
            )
        }
    }

    fun readNFT(): (Map<String, Any>) -> NFT {
        return {
            NFT(
                it["id"].toString(),
                it["name"].toString(),
                it["class_id"].toString(),
                it["class_name"].toString(),
                it["class_symbol"] as? String,
                it["uri"] as? String,
                it["owner"].toString(),
                it["status"] == "burned", it["timestamp"].toString(),
            )
        }
    }

    fun readNFTTypeDetail(): (Map<String, Any>) -> NFTTypeDetail {
        return {
            NFTTypeDetail(
                readNFTType()(it),
                it["description"] as? String,
                it["uri_hash"] as? String,
                it["data"] as? String,
            )
        }
    }

    fun readNFTType(): (Map<String, Any>) -> NFTType {
        return {
            NFTType(
                it["id"].toString(),
                it["name"].toString(),
                it["symbol"] as? String,
                (it["nft_count"] as Number).toInt(),
                it["uri"].toString(),
                it["owner"].toString(),
                it["timestamp"].toString(),
            )
        }
    }

    fun readAccount(): ((Map<String, Any>) -> Account) {
        return {
            Account(
                it["account"].toString(),
                it["name"].toString(),
                it["gas"] as Number,
                it["biz_fee"] as Number,
                it["operation_id"].toString(),
                (it["status"] as Number).toInt() == 1,
            )
        }
    }
}