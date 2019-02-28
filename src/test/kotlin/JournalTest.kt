import App.Companion.JOURNAL
import domain.Journal
import dto.JournalDao
import org.assertj.core.api.Assertions
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.junit.After

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.sql.DriverManager

class JournalTest{

    lateinit var dslContext: DSLContext

    @BeforeEach
    fun setUp(){
        val conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "san6685", "postgres")
        dslContext = DSL.using(conn)
    }
    companion object {
        val jou = Journal(null, "Springer Journal")
    }

    @After
    fun cleanUpContext() {
            dslContext.close()
    }

    @AfterEach
    fun cleanUp(){
        dslContext.deleteFrom(JOURNAL).execute()
    }

    @Test
    fun `should insert a record in Journal table`(){
        val dao = JournalDao(dslContext)
        println("Data before inserting new journal: ")
        dao.findAll().forEach{ println(it)}
        dao.save(jou)
        println("Data after inserting new journal: ")
        dao.findAll().forEach{
            println(it)
            Assertions.assertThat(it.jou_name).isEqualTo(jou.jou_name)
        }

    }

    @Test
    fun `should update a record in Subjects table`(){
        val dao = JournalDao(dslContext)
        val jouId = dao.save(jou)
        val updatedJournal = Journal(jouId, "BMC Journal")
        dao.save(updatedJournal).apply {
            Assertions.assertThat(this).isEqualTo(jouId)
        }
        dao.findAll().forEach{
            println(it)
            Assertions.assertThat(it.jou_name).isEqualTo(updatedJournal.jou_name)
        }
    }

    @Test
    fun `should fetch subject based on valid id`(){
        val dao = JournalDao(dslContext)
        dao.save(jou).apply {
            dao.findById(this).apply {
                Assertions.assertThat(this).isNotNull
                Assertions.assertThat(this!!.jou_name).isEqualTo(jou.jou_name)
            }
        }
    }

    @Test
    fun `should not fetch subject for invalid id`(){
        val dao = JournalDao(dslContext)
        dao.findById(2).apply { Assertions.assertThat(this).isNull() }
    }


    @Test
    fun `should delete subject for valid id`(){
        val dao = JournalDao(dslContext)
         dao.save(jou).apply {
            dao.delete(this).apply {
                Assertions.assertThat(this).isEqualTo(1)
            }
        }
    }

    @Test
    fun `should not delete subject for non existent id`() {
        val dao = JournalDao(dslContext)
        dao.delete(10).apply {
            Assertions.assertThat(this).isEqualTo(0)
        }
    }
}