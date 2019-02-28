import App.Companion.ARTICLETYPES
import App.Companion.JOURNAL
import domain.ArticleTypes
import domain.Journal
import dto.ArticleTypeDao
import dto.JournalDao
import org.assertj.core.api.Assertions
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.junit.After
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.sql.DriverManager

class ArticleTypesTest{

    lateinit var dslContext: DSLContext

    @BeforeEach
    fun setUp(){
        val conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "san6685", "postgres")
        dslContext = DSL.using(conn)
    }
    companion object {
        val jou = Journal(null, "Nature Journal")

    }

    @After
    fun cleanUpContext() {
            dslContext.close()
    }

    @AfterEach
    fun cleanUp(){
        dslContext.deleteFrom(JOURNAL).execute()
        dslContext.deleteFrom(ARTICLETYPES).execute()
    }

    @Test
    fun `should insert a record in article type table`(){
        val journalDao = JournalDao(dslContext)
        val articelTypeDao = ArticleTypeDao(dslContext)

        val jouId = journalDao.save(jou)

        val mstAgainestJou = listOf<ArticleTypes>(ArticleTypes(null,"Research",jouId),ArticleTypes(null,"Review",jouId),ArticleTypes(null,"OriginalPaper",jouId))

        articelTypeDao.saveArticleTypes(mstAgainestJou)

        val articleTypesForJournalId: MutableList<ArticleTypes> = articelTypeDao.getArticleTypesForJournalId(jouId)

        articleTypesForJournalId.forEach{
            println(it)
            Assertions.assertThat(it.mst_jou_id).isEqualTo(jouId)
            Assertions.assertThat(it.mst_name).isEqualTo(mstAgainestJou.filter { rec->  rec.mst_name.equals(it.mst_name) }.first().mst_name)
        }

    }

}