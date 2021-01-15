package com.hms.demo.minibrowser

import androidx.lifecycle.ViewModel

class MainVM: ViewModel() {
    var navigator:MainNavigator?=null
    fun onSearchIntent(){
        navigator?.navigateToSearch()
    }

    fun homeRequest(){
        navigator?.navigateToHome()
    }

    interface MainNavigator{
        fun navigateToSearch()
        fun navigateToHome()
    }
}