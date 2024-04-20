package ru.tinkoff.tinkoffer.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.tinkoff.tinkoffer.data.local.PrefsDataStore
import ru.tinkoff.tinkoffer.presentation.MainViewModel
import ru.tinkoff.tinkoffer.presentation.screen.home.HomeViewModel
import ru.tinkoff.tinkoffer.presentation.screen.profile.ProfileViewModel
import ru.tinkoff.tinkoffer.presentation.screen.projectsettings.ProjectSettingsViewModel
import ru.tinkoff.tinkoffer.presentation.screen.proposal.ProposalViewModel
import ru.tinkoff.tinkoffer.presentation.screen.signin.SignInViewModel

val appModule = module {

    // Prefs DataStore
    single {
        PrefsDataStore(androidApplication().applicationContext)
    }

    // --------------------------------------------------------
    // ViewModel
    viewModel { MainViewModel(get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { HomeViewModel() }
    viewModel { ProfileViewModel() }
    viewModel { ProjectSettingsViewModel() }
    viewModel { ProposalViewModel() }
}