
import com.fasterxml.jackson.databind.ObjectMapper
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.BaseMessage
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ReadTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.SearchFilter
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.SearchTasksRequest
import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class SerializationTest {

    @Test
    fun `serialization test`() {
        val mapper = ObjectMapper()
        val obj = ReadTaskRequest(id = "1", requestId = "request-id")
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

    @Test
    fun `date time serialization test`() {
        val dateTimeNow = LocalDateTime.now().toString()
        val mapper = ObjectMapper()
        val obj = SearchTasksRequest(searchFilter = SearchFilter(dueTimeFilter = dateTimeNow))
        val jsonStr = mapper.writeValueAsString(obj)
        println("JSON: $jsonStr")
        assertTrue("must contain dueTimeFilter = $dateTimeNow") {
            jsonStr.contains(Regex("""\s*"dueTimeFilter":\s*"$dateTimeNow"\s*"""))
        }
        assertTrue("must contain messageType = SearchTasksRequest") {
            jsonStr.contains(Regex("""\s*"messageType":\s*"${obj::class.java.simpleName}"\s*"""))
        }

        val deserializedObj = mapper.readValue(jsonStr, BaseMessage::class.java)
        assertEquals(SearchTasksRequest::class.java, deserializedObj::class.java)
        assertEquals(obj, deserializedObj)
    }

}