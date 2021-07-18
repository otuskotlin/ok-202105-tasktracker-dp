package homework.easy

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

suspend fun findNumberInList(toFind: Int, numbers: List<Int>): Int = coroutineScope {
    withContext(Dispatchers.Default) {
        delay(2000L)
        numbers.firstOrNull { it == toFind } ?: -1
    }
}


