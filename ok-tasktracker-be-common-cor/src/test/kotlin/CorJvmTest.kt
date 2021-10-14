import kotlinx.coroutines.runBlocking
import com.polyakovworkbox.tasktracker.common.cor.CorBaseTest
import com.polyakovworkbox.tasktracker.common.cor.TestContext
import kotlin.test.Test
import kotlin.test.assertEquals

class CorJvmTest {

    @Test
    fun corTest() {
        val chain = CorBaseTest.chain
        val ctx = TestContext(some = 13)

        runBlocking { chain.exec(ctx) }

        assertEquals(17, ctx.some)
    }
}
