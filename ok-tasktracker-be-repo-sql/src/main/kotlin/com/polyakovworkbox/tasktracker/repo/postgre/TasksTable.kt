package com.polyakovworkbox.tasktracker.repo.postgre

import com.polyakovworkbox.tasktracker.backend.common.models.general.Percent
import com.polyakovworkbox.tasktracker.backend.common.models.task.Description
import com.polyakovworkbox.tasktracker.backend.common.models.task.DueTime
import com.polyakovworkbox.tasktracker.backend.common.models.task.Measurability
import com.polyakovworkbox.tasktracker.backend.common.models.task.Name
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskId
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskIdReference
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.statements.InsertStatement
import java.time.ZoneId

object TasksTable : Table("Tasks") {
    val id = uuid("id").uniqueIndex()
    val name = varchar("name", 128)
    val description = varchar("description", 1024)
    var attainabilityDescription = varchar("description", 1024)
    var relevanceDescription = varchar("description", 1024)
    var measurabilityDescription = varchar("description", 1024)
    var progress = integer("progress")
    var dueTime = datetime("dueTime")
    var parent = reference("parent_id", id)
    var children = reference("child_id", id)

    override val primaryKey = PrimaryKey(id)

    fun from(res: InsertStatement<Number>) = Task(
        id = TaskId(res[id]),
        name = Name(res[name]),
        description = Description(res[description]),
        attainabilityDescription = Description(res[attainabilityDescription]),
        relevanceDescription = Description(res[relevanceDescription]),
        measurability = Measurability(
            Description(res[attainabilityDescription]),
            Percent(res[progress])
        ),
        dueTime = DueTime(res[dueTime].atZone(ZoneId.of("UTC+03:00")).toInstant()),
        parent = TaskIdReference(res[parent]),
        children = listOf(TaskIdReference(res[children]))
    )

    fun from(res: ResultRow) = Task(
        id = TaskId(res[id]),
        name = Name(res[name]),
        description = Description(res[description]),
        attainabilityDescription = Description(res[attainabilityDescription]),
        relevanceDescription = Description(res[relevanceDescription]),
        measurability = Measurability(
            Description(res[attainabilityDescription]),
            Percent(res[progress])
        ),
        dueTime = DueTime(res[dueTime].atZone(ZoneId.of("UTC+03:00")).toInstant()),
        parent = TaskIdReference(res[parent]),
        children = listOf(TaskIdReference(res[children]))
    )

}