import org.jooq.Field
import org.jooq.Record
import org.jooq.Table
import org.jooq.impl.DSL.field
import org.jooq.impl.DSL.table


class App{

    companion object{
        val JOURNAL: Table<Record> = table("journal")
        val JOU_ID: Field<Int> = field("jou_id", Int::class.java)
        val JOU_NAME: Field<String> = field("jou_name", String::class.java)

        val ARTICLETYPES: Table<Record> = table("articletypes")
        val MST_ID: Field<Int> = field("mst_id", Int::class.java)
        val MST_JOU_ID: Field<Int> = field("mst_jou_id", Int::class.java)
        val MST_NAME: Field<String> = field("mst_name", String::class.java)
    }
}
fun main(args: Array<String>) {

}

