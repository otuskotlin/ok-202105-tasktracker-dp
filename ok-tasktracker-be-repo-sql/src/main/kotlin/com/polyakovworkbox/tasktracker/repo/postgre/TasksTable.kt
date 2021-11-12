package com.polyakovworkbox.tasktracker.repo.postgre

import com.polyakovworkbox.tasktracker.backend.common.models.general.Percent
import com.polyakovworkbox.tasktracker.backend.common.models.task.Description
import com.polyakovworkbox.tasktracker.backend.common.models.task.DueTime
import com.polyakovworkbox.tasktracker.backend.common.models.task.Measurability
import com.polyakovworkbox.tasktracker.backend.common.models.task.Name
import com.polyakovworkbox.tasktracker.backend.common.models.task.OwnerId
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
    val ownerId = varchar("ownerId", 128)
    val name = varchar("name", 128)
    val description = varchar("description", 1024)
    var attainabilityDescription = varchar("attainabilityDescription", 1024)
    var relevanceDescription = varchar("relevanceDescription", 1024)
    var measurabilityDescription = varchar("measurabilityDescription", 1024)
    var progress = integer("progress")
    var dueTime = datetime("dueTime")
    var parent = reference("parent_id", id).nullable()
    var children = reference("child_id", id).nullable()

    override val primaryKey = PrimaryKey(id)

    fun from(res: InsertStatement<Number>) = Task(
        id = TaskId(res[id]),
        ownerId = OwnerId(res[ownerId]),
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
        children = if(res[children] != null) listOf(TaskIdReference(res[children])) else emptyList()
    )

    fun from(res: ResultRow) = Task(
        id = TaskId(res[id]),
        ownerId = OwnerId(res[ownerId]),
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
        children = if(res[children] != null) listOf(TaskIdReference(res[children])) else emptyList()
    )

}