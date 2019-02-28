package domain

data class Journal(val jou_id: Int?,val jou_name: String)
data class ArticleTypes(val mst_id:Int?,val mst_name: String,val mst_jou_id: Int?)