package homework.hard

import homework.hard.dto.Dictionary
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import java.io.File

fun main() {
    val dictionaryApi = DictionaryApi()
    val words = FileReader.readFile().split(" ", "\n").toSet()

    val dictionaries = findWords(dictionaryApi, words, Locale.EN)

    dictionaries.map { dictionary ->
        print("For word ${dictionary.word} i found examples: ")
        println(dictionary.meanings.map { definition -> definition.definitions.map { it.example } })
    }
}

private fun findWords(dictionaryApi: DictionaryApi, words: Set<String>, locale: Locale) = runBlocking {
    val deferredList = mutableListOf<Deferred<Dictionary>>()
    words.forEach {
        deferredList.add(dictionaryApi.findWordAsync(locale, it, this.coroutineContext))
    }

    deferredList.map {
        it.await()
    }
}
object FileReader {
    fun readFile(): String =
        File(this::class.java.classLoader.getResource("words.txt")?.toURI() ?: throw RuntimeException("Can't read file")).readText()
}
