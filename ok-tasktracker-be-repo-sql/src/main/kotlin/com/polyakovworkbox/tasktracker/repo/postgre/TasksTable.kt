package com.polyakovworkbox.tasktracker.repo.postgre

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object TasksTable : Table("Tasks") {
    val id = uuid("id").uniqueIndex()
    val name = varchar("name", 128)
    val description = varchar("description", 1024)
    var attainabilityDescription = varchar("description", 1024)
    var relevanceDescription = varchar("description", 1024)
    var measurabilityDescription = varchar("description", 1024)
    var progress = byte("progress")
    var dueTime = datetime("dueTime")
    var parent = reference("parent_id", id)
    var children = reference("child_id", id)

    override val primaryKey = PrimaryKey(id)
}
