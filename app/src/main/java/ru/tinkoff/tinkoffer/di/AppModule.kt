package ru.tinkoff.tinkoffer.di

import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.tinkoff.tinkoffer.data.local.PrefsDataStore
import ru.tinkoff.tinkoffer.data.rest.UserRestApi
import ru.tinkoff.tinkoffer.data.utils.TokenInterceptor
import ru.tinkoff.tinkoffer.presentation.MainViewModel
import ru.tinkoff.tinkoffer.presentation.screen.home.HomeViewModel
import ru.tinkoff.tinkoffer.presentation.screen.profile.ProfileViewModel
import ru.tinkoff.tinkoffer.presentation.screen.projectlist.ProjectListViewModel
import ru.tinkoff.tinkoffer.presentation.screen.projectsettings.ProjectSettingsViewModel
import ru.tinkoff.tinkoffer.presentation.screen.proposal.ProposalViewModel
import ru.tinkoff.tinkoffer.presentation.screen.signin.SignInViewModel
import java.util.concurrent.TimeUnit

val appModule = module {

    // Prefs DataStore
    single {
        PrefsDataStore(androidApplication().applicationContext)
    }

    // --------------------------------------------------------
    // ViewModel
    viewModel { MainViewModel(get()) }
    viewModel { SignInViewModel(get(), get(), get()) }
    viewModel { HomeViewModel() }
    viewModel { ProfileViewModel() }
    viewModel { ProjectSettingsViewModel() }
    viewModel { ProposalViewModel() }
    viewModel { ProjectListViewModel() }


    // Rest
    single { Gson() }

    single<Retrofit.Builder> {
        Retrofit.Builder()
            .baseUrl("http://79.174.94.50:8080/api/")
            .addConverterFactory(GsonConverterFactory.create(get()))
    }

    single {
        TokenInterceptor(get())
    }

    single<OkHttpClient> {
        val tokenInterceptor: TokenInterceptor = get()

        val builder: OkHttpClient.Builder =
            OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .callTimeout(120, TimeUnit.SECONDS)

        builder
            .addInterceptor(tokenInterceptor)
            .build()
    }

    single<Retrofit> {
        val builder: Retrofit.Builder = get()
        val client: OkHttpClient = get()
        builder.client(client).build()
    }

    single { get<Retrofit>().create(UserRestApi::class.java) }



}