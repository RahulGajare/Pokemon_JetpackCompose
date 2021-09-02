package com.rg.pokemon.di

import com.rg.pokemon.`interface`.PokiApi
import com.rg.pokemon.constants.Constants
import com.rg.pokemon.constants.Constants.BASE_URL
import com.rg.pokemon.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun pokemonRepository(api: PokiApi): PokemonRepository {
        return PokemonRepository(api)
    }

    @Singleton
    @Provides
    fun providePokemonApi() : PokiApi{
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokiApi::class.java)
    }
}