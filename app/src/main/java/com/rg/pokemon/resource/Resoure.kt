package com.rg.pokemon.resource

sealed class Resoure<T>(val data: T? = null, val message : String? = null) {
    class  Success<T> (data: T) : Resoure<T>(data)
    class Error<T> ( message: String ,data : T? = null) : Resoure<T>(data,message)
    class  Loading<T> (data: T? = null) : Resoure<T>(data)
}