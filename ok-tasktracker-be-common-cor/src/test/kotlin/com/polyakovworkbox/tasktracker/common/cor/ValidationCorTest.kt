package com.polyakovworkbox.tasktracker.common.cor

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError
import com.polyakovworkbox.tasktracker.backend.common.models.general.CorStatus
import com.polyakovworkbox.tasktracker.common.ValidationResult
import com.polyakovworkbox.tasktracker.validators.StringNotEmptyValidator
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class ValidationCorTest {

    @Test
    fun `test validation in cor`() {
        val context = BeContext().apply {
            corStatus = CorStatus.RUNNING
        }

        val chain = chain<BeContext> {
           validation {
                errorHandler { result: ValidationResult ->
                    if (!result.isSuccess) {
                        this.errors.addAll(result.errors.map { ApiError(message = it.message) })
                    }
                }

                validate<String> { validator(StringNotEmptyValidator()); on { requestTask.description.description } }
                validate<String> { validator(StringNotEmptyValidator()); on { requestTask.relevanceDescription.description } }
            }
        }

        runBlocking {
            assertEquals(context.errors.size, 0)
            chain.build().exec(context)
            assertEquals(context.errors.size, 2)
        }
    }
}