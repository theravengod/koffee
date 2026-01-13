package cheshire.kitty.koffee.di

import android.annotation.SuppressLint
import cheshire.kitty.koffee.BuildConfig
import cheshire.kitty.koffee.net.CoffeeApi
import cheshire.kitty.koffee.net.CoffeeApiImpl
import cheshire.kitty.koffee.other.DynamicSerializerFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import timber.log.Timber
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

const val IGNORE_SSL_ERRORS = false

@Module
@InstallIn(SingletonComponent::class)
object NetCallerModule {

    @Provides
    fun provideHttpClient(jsonParser: Json): HttpClient = HttpClient(OkHttp) {
        engine {
            config {
                followRedirects(true)
                connectTimeout(50, TimeUnit.SECONDS)
                callTimeout(50, TimeUnit.SECONDS)
                readTimeout(50, TimeUnit.SECONDS)

                @Suppress("KotlinConstantConditions", "SimplifyBooleanWithConstants")
                if (BuildConfig.DEBUG && IGNORE_SSL_ERRORS) {
                    ignoreSSLErrors()
                }
            }

        }

        install(ContentNegotiation) {
            json(jsonParser)
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Timber.d("KTor: %s", message)
                }
            }
            level = LogLevel.ALL
        }

        install(ResponseObserver) {
            onResponse { response ->
                Timber.d("HTTP status: ${response.status.value}")
            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }

    @Provides
    fun provideCoffeeApiService(httpClient: HttpClient, json: Json, dynamicSerializerFactory: DynamicSerializerFactory): CoffeeApi =
        CoffeeApiImpl(httpClient, json, dynamicSerializerFactory)

    private fun OkHttpClient.Builder.ignoreSSLErrors(): OkHttpClient.Builder {
        val naiveTrustManager = @SuppressLint("CustomX509TrustManager") object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate?>?, authType: String?) = Unit
            override fun checkServerTrusted(chain: Array<out X509Certificate?>?, authType: String?) = Unit
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        }
        val insecureSocketFactory = SSLContext.getInstance("TLSv1.2").apply {
            val trustAllCerts = arrayOf<TrustManager>(naiveTrustManager)
            init(null, trustAllCerts, SecureRandom())
        }.socketFactory

        return this.apply {
            sslSocketFactory(insecureSocketFactory, naiveTrustManager)
            hostnameVerifier { _, _ -> true }
        }
    }
}