package schema

import org.jooq.Field
import org.jooq.Record
import org.jooq.Table
import org.jooq.impl.DSL

class Tables {
    companion object{
        val JOURNAL: Table<Record> = DSL.table("journal")
        val JOU_ID: Field<Int> = DSL.field("jou_id", Int::class.java)
        val JOU_NAME: Field<String> = DSL.field("jou_name", String::class.java)

        val ARTICLETYPES: Table<Record> = DSL.table("articletypes")
        val MST_ID: Field<Int> = DSL.field("mst_id", Int::class.java)
        val MST_JOU_ID: Field<Int> = DSL.field("mst_jou_id", Int::class.java)
        val MST_NAME: Field<String> = DSL.field("mst_name", String::class.java)
    }
}