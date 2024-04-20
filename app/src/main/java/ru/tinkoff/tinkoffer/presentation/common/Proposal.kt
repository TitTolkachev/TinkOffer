package ru.tinkoff.tinkoffer.presentation.common

data class Proposal(
    val id: String,
    val text: String,
    val author: UserShort,
    val status: ProposalStatus,
    val userVoice: Boolean? = null,
    val goodUsers: List<UserShort>,
    val badUsers: List<UserShort>,
    val voiceEnabled: Boolean,
    val link: String,
    val draftComment: String?,
    val canBeVoiceCanceled: Boolean,
)