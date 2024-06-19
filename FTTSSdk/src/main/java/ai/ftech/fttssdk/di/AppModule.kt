package ai.ftech.fttssdk.di

import android.content.Context
import ai.ftech.fttssdk.data.local.DataStoreService
import ai.ftech.fttssdk.data.local.IDataStore
import ai.ftech.fttssdk.data.remote.GatewayService
import ai.ftech.fttssdk.data.remote.TTSService
import ai.ftech.fttssdk.data.repositories.IGatewayAPI
import ai.ftech.fttssdk.data.repositories.ITTSAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGatewayApi(): IGatewayAPI {
        return GatewayService().create(IGatewayAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideTTSApi(datastore: IDataStore): ITTSAPI {
        return TTSService(datastore).create(ITTSAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideDataStore(
        @ApplicationContext app: Context
    ): IDataStore = DataStoreService(app)

}