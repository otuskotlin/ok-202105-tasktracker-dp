package com.polyakovworkbox.tasktracker.common.handlers

import com.polyakovworkbox.tasktracker.common.cor.ICorChainDsl

import kotlin.test.Test

class CorBaseTest {
    @Test
    fun createCor() {

    }

    companion object {
        val chain = com.polyakovworkbox.tasktracker.common.cor.chain<TestContext> {
            worker {
                title = "Инициализация статуса"
                description = "При старте обработки цепочки, статус еще не установлен. Проверяем его"

                on { status == CorStatuses.NONE }
                handle { status = CorStatuses.RUNNING }
                except { status = CorStatuses.ERROR }
            }

            chain {
                on { status == CorStatuses.RUNNING }

                worker(
                    title = "Лямбда обработчик",
                    description = "Пример использования обработчика в виде лямбды"
                ) {
                    some += 4
                }

            }

            parallel {
                on {
                    some < 15
                }

                worker(title = "Increment some") {
                    some++
                }
            }
            printResult()

        }.build()

    }
}

private fun ICorChainDsl<TestContext>.printResult() = worker(title = "Print example") {
    println("some = $some")
}

data class TestContext(
    var status: CorStatuses = CorStatuses.NONE,
    var some: Int = Int.MIN_VALUE
) {

}

enum class CorStatuses {
    NONE,
    RUNNING,
    FAILING,
    DONE,
    ERROR
}
