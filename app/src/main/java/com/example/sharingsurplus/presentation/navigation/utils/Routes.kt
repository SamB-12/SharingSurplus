package com.example.sharingsurplus.presentation.navigation.utils

sealed class Routes(val route: String) {
    object Login : Routes("login")
    object Register : Routes("register")
    object AuthenticationGraph : Routes("authenticationGraph")

    ///////////////////////////////////////////////////////
    object Home : Routes("home")
    //////////////////////////////////////////////////////
    object Profile : Routes("profile")
    object ProfileInfo : Routes("profileInfo")
    object KarmaPoints : Routes("karmaPoints")

    object EditProfile : Routes("editProfile")
    object AboutUs : Routes("aboutUs")
    object ViewMyProduce : Routes("viewMyProduce")
    object EditAndDeleteProduce : Routes("editAndDeleteProduce")
    object ProfileGraph : Routes("profileGraph")

    //////////////////////////////////////////////////////

    object MainMenu : Routes("mainMenu")

    //////////////////////////////////////////////////////

    object CommunityForum : Routes("communityForum")

    //////////////////////////////////////////////////////

    object Requests : Routes("requests")
    object RequestReceived : Routes("requestReceived")
    object RequestSent : Routes("requestSent")

    //////////////////////////////////////////////////////

    object AddProduce: Routes("addProduce")

    object ViewAndRequestProduce: Routes("viewAndRequestProduce")

    object ViewMap: Routes("viewMap")


}