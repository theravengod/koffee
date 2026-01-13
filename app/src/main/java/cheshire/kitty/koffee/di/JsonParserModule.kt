package cheshire.kitty.koffee.di

import cheshire.kitty.koffee.other.DynamicSerializerFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
object JsonParserModule {
    @Provides
    fun provideJsonParser(): Json {
        return Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        }
    }

    @Provides
    fun provideDynamicSerializerFactory(): DynamicSerializerFactory {
        return DynamicSerializerFactory()
    }

}