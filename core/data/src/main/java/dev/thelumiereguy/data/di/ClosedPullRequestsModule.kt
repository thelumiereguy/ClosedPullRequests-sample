/*
 * Created by Piyush Pradeepkumar on 25/06/22, 11:07 AM
 */

package dev.thelumiereguy.data.di

import android.content.Context
import android.os.Build
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import dev.thelumiereguy.data.BuildConfig
import dev.thelumiereguy.data.local.PullRequestsDatabase
import dev.thelumiereguy.data.local.dao.ClosedPRDao
import dev.thelumiereguy.data.network.ClosedPRsApi
import dev.thelumiereguy.data.network.ClosedPRsApiImpl
import dev.thelumiereguy.data.repo.ClosedPRsRepo
import dev.thelumiereguy.data.repo.ClosedPRsRepoImpl
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class ClosedPullRequestsModule {

    @Binds
    @ViewModelScoped
    abstract fun bindClosedPRRepo(
        closedPRsRepoImpl: ClosedPRsRepoImpl
    ): ClosedPRsRepo

    @Binds
    abstract fun bindClosedPRApi(
        closedPRsApi: ClosedPRsApiImpl
    ): ClosedPRsApi

}

@Module
@InstallIn(SingletonComponent::class)
internal class ClosedPullRequestsDataModule {

    @Provides
    fun providePullRequestsDatabase(@ApplicationContext context: Context): PullRequestsDatabase {
        return Room.databaseBuilder(
            context,
            PullRequestsDatabase::class.java,
            "pull_requests"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideClosedPRDao(db: PullRequestsDatabase): ClosedPRDao {
        return db.closedPRDao()
    }


    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(httpLoggingInterceptor)
                }
            }
            .build()


    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
        }
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    }

    companion object {
        private const val BASE_URL = "https://api.github.com/"
    }
}
