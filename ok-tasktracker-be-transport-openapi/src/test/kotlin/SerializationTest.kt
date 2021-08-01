
import com.fasterxml.jackson.databind.ObjectMapper
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.BaseMessage
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ReadTaskRequest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class SerializationTest {
    @Test
    fun simpleSerialization() {
        val mapper = ObjectMapper()
        val obj = ReadTaskRequest(id = "1")
        val jsonStr = mapper.writeValueAsString(obj)
        println("JSON: $jsonStr")
        assertTrue("must contain id = 1") {
            jsonStr.contains(Regex("""\s*"id":\s*"1"\s*"""))
        }
        assertTrue("must contain messageType = ReadTaskRequest") {
            jsonStr.contains(Regex("""\s*"messageType":\s*"${obj::class.java.simpleName}"\s*"""))
        }

        val deserializedObj = mapper.readValue(jsonStr, BaseMessage::class.java)
        assertEquals(ReadTaskRequest::class.java, deserializedObj::class.java)
        assertEquals(obj, deserializedObj)
    }
}