package com.example.sharingsurplus.presentation.navigation.utils

sealed class Graphs(val graph:String) {
    object AuthenticationGraph : Graphs("authenticationGraph")
    object ProfileGraph : Graphs("profileGraph")
    object HomeGraph : Graphs("homeGraph")
    object CommunityGraph : Graphs("communityGraph")
    object RequestGraph : Graphs("requestGraph")
    object MainGraph : Graphs("mainGraph")
}