package dto

interface BaseDao<T,R>{

    fun save(obj : T) : R

    fun findById(id : R) : T?

    fun delete(id : R) : Int

}