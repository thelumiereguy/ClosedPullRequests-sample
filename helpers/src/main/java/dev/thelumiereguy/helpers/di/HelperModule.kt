package dev.thelumiereguy.helpers.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.thelumiereguy.helpers.framework.DispatcherProvider
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
class HelperModule {

    @Provides
    fun provideDispatcherProviderImpl(): DispatcherProvider {
        return object : DispatcherProvider {
            override val main = Dispatchers.Main
            override val default = Dispatchers.Default
            override val io = Dispatchers.IO
            override val unconfined = Dispatchers.Unconfined
        }
    }
}
