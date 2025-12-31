package cheshire.kitty.koffee.di

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
import timber.log.Timber
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetCallerModule {

    @Provides
    fun provideHttpClient(): HttpClient = HttpClient(OkHttp)  {
        engine {
            config {
                followRedirects(true)
                connectTimeout(50, TimeUnit.SECONDS)
                callTimeout(50, TimeUnit.SECONDS)
                readTimeout(50, TimeUnit.SECONDS)

                //if (BuildConfig.DEBUG) {
                /*val naiveTrustManager = @SuppressLint("CustomX509TrustManager")
                    object : X509TrustManager {
                        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                        override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) = Unit
                        override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) = Unit
                    }
                val insecureSocketFactory = SSLContext.getInstance("TLSv1.2").apply {
                    val trustAllCerts = arrayOf<TrustManager>(naiveTrustManager)
                    init(null, trustAllCerts, SecureRandom())
                }.socketFactory
                sslSocketFactory(insecureSocketFactory, naiveTrustManager)
                hostnameVerifier { _, _ -> true }*/
                //}
            }

        }

        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
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
}