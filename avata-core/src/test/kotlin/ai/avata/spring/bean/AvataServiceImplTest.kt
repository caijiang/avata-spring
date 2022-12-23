package ai.avata.spring.bean

import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.springframework.data.domain.Page
import kotlin.test.Test

/**
 * @author CJ
 */
class AvataServiceImplTest {
    private val service = AvataServiceImpl()

    @Test
    fun types() {
        printPage(service.queryNFTTypes(owner = "iaa17n2nge7mclw0gc872y5fvtej7dql89jdvs2eqg"))
    }

    @Test
    fun well() {
        val currentAccounts = service.queryAccounts()
        printPage(currentAccounts)
        val newAccount = service.createAccount(RandomStringUtils.randomAlphabetic(19))
        println("new account: $newAccount")

        assertThat(service.queryAccounts().totalElements)
            .`as`("")
            .isEqualTo(currentAccounts.totalElements + 1)

        val typeName = RandomStringUtils.randomAlphabetic(19)
        val currentTypes = service.queryNFTTypes(owner = newAccount)
        printPage(currentTypes)
        val cn1 = service.createNFTType(typeName, newAccount)
        println("创建 NFTType 结果:$cn1")
        while (true) {
            if (service.queryNFTTypes(owner = newAccount).totalElements >= 1) {
                break
            }
            Thread.sleep(1000)
        }
        val types = service.queryNFTTypes(owner = newAccount)
        assertThat(types.totalElements)
            .`as`("现在有一个了")
            .isEqualTo(currentTypes.totalElements + 1)

        val nftType = types.content.first()
        println(nftType)
        println(service.queryNFTTypeDetail(nftType.id))

        // 现在可以发布nft, 查询它 最后再干掉他。
        val currentNFT = service.queryNFT(typeClassId = nftType.id)
        val nftName = RandomStringUtils.randomAlphabetic(63)
        assertThat(service.issueNFT(nftType.id, nftName))
            .`as`("发行NFT 必须成功")
            .isNotNull
        // 现在我们继续查询，直到有为止。
        while (true) {
            if (service.queryNFT(typeClassId = nftType.id).totalElements >= 1) {
                break
            }
            Thread.sleep(1000)
        }
        assertThat(service.queryNFT(typeClassId = nftType.id).totalElements)
            .isEqualTo(currentNFT.totalElements + 1)

        val nft = service.queryNFT(typeClassId = nftType.id).content.first()

        println(service.queryNFTDetail(nft))
        assertThat(service.deleteNFT(nft))
            .`as`("删除必须成功")
            .isNotNull

    }

    private fun <T> printPage(page: Page<T>) {
        println(page.totalElements)
        println(page.totalPages)
        println(page.size)
        println(page.number)
        println(page.content)
    }
}