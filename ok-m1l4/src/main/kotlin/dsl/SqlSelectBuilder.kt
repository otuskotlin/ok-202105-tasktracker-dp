package dsl

import java.lang.IllegalArgumentException

@UserDsl
class WhereContext {
    var where = arrayOf<String>()
    var whereWithOr = ""

    infix fun String.eq(that : String) {
        where += "$this = '$that'"
    }

    infix fun String.eq(that : Int) {
        where += "$this = $that"
    }

    infix fun String.nonEq(that : Int) {
        where += "$this != $that"
    }

    infix fun String.nonEq(that : Any?) {
        where += "$this !is $that"
    }

    @UserDsl
    fun or(block: () -> Unit) {
        block()

        var orStatements = ""
        for (i in 1 until where.size) {
            orStatements += " or ${where[i]}"
        }

        whereWithOr = "(${where[0]}$orStatements)"
    }
}

@UserDsl
class SqlSelectBuilder {
    private var from = ""
    private var select = "*"
    private var where = ""

    @UserDsl
    fun from(from : String) {
        this.from = from
    }

    @UserDsl
    fun select(vararg select: String) {
        this.select = select.joinToString (", ")
    }

    @UserDsl
    fun where(block: WhereContext.() -> Unit) {
        val context = WhereContext().apply(block)
        where = if(context.where.size > 1) context.whereWithOr else context.where.first()
    }

    fun build() : String {
        if(from.isBlank()) throw IllegalArgumentException()
        return "select $select from $from" +
                if(where.isNotBlank()) " where $where" else ""
    }
}

@UserDsl
fun query(block: SqlSelectBuilder.() -> Unit) = SqlSelectBuilder().apply(block)

@DslMarker
annotation class UserDsl