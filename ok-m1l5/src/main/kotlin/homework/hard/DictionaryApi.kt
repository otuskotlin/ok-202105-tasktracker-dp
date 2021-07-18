package homework.hard

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import homework.hard.dto.Dictionary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import okhttp3.Response
import kotlin.coroutines.CoroutineContext

class DictionaryApi(
    private val objectMapper: ObjectMapper = jacksonObjectMapper()
) {

    fun findWordAsync(locale: Locale, word: String, context: CoroutineContext): Deferred<Dictionary> {
        return CoroutineScope(context).async(Dispatchers.IO) {
            val url = "$DICTIONARY_API/${locale.code}/$word"
            println("Searching $url")
            getBody(HttpClient.get(url).execute()).first()
        }
    }

    private fun getBody(response: Response): List<Dictionary> {
        if (!response.isSuccessful) {
            throw RuntimeException("Not found word")
        }

        return response.body?.let {
            objectMapper.readValue(it.string())
        } ?: throw RuntimeException("Body is null by some reason")
    }
}