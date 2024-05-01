package com.example.fs_android.base

//import com.example.fs_android.domain.MovieRepository
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.fs_android.base.interceptor.HeaderInterceptor
import com.example.fs_android.data.NetworkService
import com.example.fs_android.domain.BatchRepository
import com.example.fs_android.domain.ChartRepository
import com.example.fs_android.domain.LoginRepository
import com.example.fs_android.presentation.factory.BatchViewModelFactory
import com.example.fs_android.presentation.factory.ChartViewModelFactory
import com.example.fs_android.presentation.factory.LoginViewModelFactory
import com.example.fs_android.utils.ApplicationProviderUtils
import com.example.fs_android.utils.DataStoreUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AppModule {

    private fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(15, TimeUnit.SECONDS)
        connectTimeout(15, TimeUnit.SECONDS)
        writeTimeout(15, TimeUnit.SECONDS)
        certificatePinner(SSLPinning.getPinnedCertificate())
//        addInterceptor(MockInterceptor())
        addInterceptor(ChuckerInterceptor(ApplicationProviderUtils.get()))
        addInterceptor(HeaderInterceptor(DataStoreUtils.get()))
        addInterceptor(HttpLoggingInterceptor { message ->
            Log.d("Http log", message)
        })
    }.build()

    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .baseUrl("http://10.0.2.2:3030/")
            .build()
    }

    private fun provideNetworkService(): NetworkService {
        return provideRetrofit().create(NetworkService::class.java)
    }

    private fun provideLoginRepository(): LoginRepository = LoginRepository(provideNetworkService())

    //    private fun provideMovieRepository(): MovieRepository = MovieRepository(provideNetworkService())
//
    private fun provideBatchRepository(): BatchRepository = BatchRepository(provideNetworkService())

    private fun provideChartRepository(): ChartRepository = ChartRepository(provideNetworkService())

    val loginViewModelFactory = LoginViewModelFactory(provideLoginRepository())

//    val homeViewModelFactory = HomeViewModelFactory(provideMovieRepository())

    val batchViewModelFactory = BatchViewModelFactory(provideBatchRepository())

    val chartViewModelFactory = ChartViewModelFactory(provideChartRepository())

}