package dto

import App.Companion.ARTICLETYPES
import App.Companion.JOURNAL
import App.Companion.JOU_ID
import App.Companion.MST_ID
import App.Companion.MST_JOU_ID
import App.Companion.MST_NAME
import domain.ArticleTypes
import org.jooq.DSLContext

class ArticleTypeDao(private val dslContext: DSLContext) : BaseDao<ArticleTypes, Int> {

    override fun save(obj: ArticleTypes ): Int {
        val mst_id = obj.mst_id?.let {
            dslContext.update(ARTICLETYPES)
                .set(MST_JOU_ID, obj.mst_jou_id)
                .set(MST_NAME, obj.mst_name)
                .where(MST_ID.eq(obj.mst_id))
                .execute()
        } ?: dslContext.use { ctx ->
            val maxId = ctx.fetchCount(ARTICLETYPES)+1
            ctx.insertInto(ARTICLETYPES, MST_ID, MST_NAME, MST_JOU_ID)
                .values(maxId,obj.mst_name,obj.mst_jou_id)
                .execute()
        }
        return mst_id
    }

    fun findAll(): MutableList<ArticleTypes>{
        return dslContext.use { ctx ->
            ctx.select()
                .from(ARTICLETYPES)
                .fetch()
                .into(ArticleTypes::class.java)
        }
    }

    override fun findById(id: Int): ArticleTypes? {
        return dslContext.use { ctx ->
            ctx.select()
                .from(ARTICLETYPES)
                .where(MST_ID.eq(id))
                .fetch()
                .firstOrNull()
                ?.into(ArticleTypes::class.java)
        }
    }

    override fun delete(id: Int): Int {
        return dslContext.use {
                ctx ->
            ctx.delete(ARTICLETYPES)
                .where(MST_ID.eq(id))
                .execute()
        }
    }

    fun getArticleTypesForJournalId(jouId: Int): MutableList<ArticleTypes>{
        return dslContext.use {
            ctx -> ctx.select(MST_ID, MST_NAME,MST_JOU_ID)
            .from(ARTICLETYPES)
            .join(JOURNAL)
            .on(JOU_ID.eq(MST_JOU_ID))
            .fetch()
            .into(ArticleTypes::class.java)
        }
    }

    fun saveArticleTypes(records1: List<ArticleTypes>){
        records1.forEach {
            save(it)
        }
    }


}