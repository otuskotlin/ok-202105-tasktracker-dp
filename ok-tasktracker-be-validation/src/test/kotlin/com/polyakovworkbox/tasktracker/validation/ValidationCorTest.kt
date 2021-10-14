package com.polyakovworkbox.tasktracker.validation

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError
import com.polyakovworkbox.tasktracker.validation.cor.validation
import com.polyakovworkbox.tasktracker.validation.lib.ValidationResult
import com.polyakovworkbox.tasktracker.validation.lib.validators.StringNotEmptyValidator
import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.otus.otuskotlin.marketplace.common.cor.chain
import kotlin.test.assertEquals

class ValidationCorTest {

    @Test
    fun `test validation in cor`() {
        val context = BeContext()

        val chain = chain<BeContext> {
           validation {
                errorHandler { result: ValidationResult ->
                    if (!result.isSuccess) {
                        this.errors.addAll(result.errors.map { ApiError(message = it.message) })
                    }
                }

                validate<String> { validator(StringNotEmptyValidator()); on { x } }
                validate<String> { validator(StringNotEmptyValidator()); on { y } }
            }
        }

        runBlocking {
            assertEquals(context.errors.size, 0)
            chain.build().exec(context)
            assertEquals(context.errors.size, 2)
        }
    }
}