package ru.tinkoff.tinkoffer.di

import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.tinkoff.tinkoffer.data.local.PrefsDataStore
import ru.tinkoff.tinkoffer.data.rest.CommentRestApi
import ru.tinkoff.tinkoffer.data.rest.DraftRestApi
import ru.tinkoff.tinkoffer.data.rest.ProjectRestApi
import ru.tinkoff.tinkoffer.data.rest.ProposalRestApi
import ru.tinkoff.tinkoffer.data.rest.UserRestApi
import ru.tinkoff.tinkoffer.data.utils.TokenInterceptor
import ru.tinkoff.tinkoffer.presentation.MainViewModel
import ru.tinkoff.tinkoffer.presentation.screen.createproject.CreateProjectViewModel
import ru.tinkoff.tinkoffer.presentation.screen.drafts.DraftsViewModel
import ru.tinkoff.tinkoffer.presentation.screen.home.HomeViewModel
import ru.tinkoff.tinkoffer.presentation.screen.profile.ProfileViewModel
import ru.tinkoff.tinkoffer.presentation.screen.projectlist.ProjectListViewModel
import ru.tinkoff.tinkoffer.presentation.screen.projectsettings.ProjectSettingsViewModel
import ru.tinkoff.tinkoffer.presentation.screen.projectusers.ProjectUsersViewModel
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
    viewModel { HomeViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { ProjectSettingsViewModel(get(), get()) }
    viewModel { ProposalViewModel(get(), get(), get(), get()) }
    viewModel { ProjectListViewModel(get()) }
    viewModel { CreateProjectViewModel(get()) }
    viewModel { ProjectUsersViewModel(get(), get(), get()) }
    viewModel { DraftsViewModel(get(), get()) }


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
    single { get<Retrofit>().create(ProposalRestApi::class.java) }
    single { get<Retrofit>().create(DraftRestApi::class.java) }
    single { get<Retrofit>().create(CommentRestApi::class.java) }
    single { get<Retrofit>().create(ProjectRestApi::class.java) }
}