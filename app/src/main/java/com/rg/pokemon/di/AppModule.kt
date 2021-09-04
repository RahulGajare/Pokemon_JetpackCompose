package com.rg.pokemon.di

import com.rg.pokemon.PokeApi
import com.rg.pokemon.PokemonRepositoryInterface
import com.rg.pokemon.constants.Constants.BASE_URL
import com.rg.pokemon.repository.PokemonRepositoryImpl
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
    fun providePokemonRepository (pokeApi : PokeApi) : PokemonRepositoryInterface
    {
        return PokemonRepositoryImpl(pokeApi)
    }

    @Singleton
    @Provides
    fun providePokeApi(): PokeApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokeApi::class.java)
    }

    @Singleton
    @Provides
    fun provideString () : String
    {
        return "Hey look this is a Weird String"
    }

}