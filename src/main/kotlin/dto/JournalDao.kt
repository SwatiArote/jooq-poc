package dto


import domain.Journal
import org.jooq.DSLContext
import schema.Tables.Companion.JOURNAL
import schema.Tables.Companion.JOU_ID
import schema.Tables.Companion.JOU_NAME


class JournalDao(private val dslContext: DSLContext) : BaseDao<Journal, Int> {

    override fun save(obj: Journal): Int {
        val jou_id = obj.jou_id?.let {
            dslContext.update(JOURNAL)
                      .set(JOU_NAME, obj.jou_name)
                      .where(JOU_ID.eq(obj.jou_id))
                      .execute()
        } ?: dslContext.use { ctx ->
            val maxId = ctx.fetchCount(JOURNAL)+1
            ctx.insertInto(JOURNAL, JOU_ID, JOU_NAME)
                .values(maxId,obj.jou_name)
                .execute()
        }
        return jou_id
    }

    fun findAll(): MutableList<Journal>{
        return dslContext.use { ctx ->
            ctx.select()
                .from(JOURNAL)
                .fetch()
                .into(Journal::class.java)
        }
    }

    override fun findById(id: Int): Journal? {
        return dslContext.use { ctx ->
            ctx.select()
                .from(JOURNAL)
                .where(JOU_ID.eq(id))
                .fetch()
                .firstOrNull()
                ?.into(Journal::class.java)
        }
    }

    override fun delete(id: Int): Int {
        return dslContext.use {
            ctx ->
            ctx.delete(JOURNAL)
                .where(JOU_ID.eq(id))
                .execute()
        }
     }
 }
