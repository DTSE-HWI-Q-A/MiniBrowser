package com.hms.demo.minibrowser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import com.hms.demo.minibrowser.databinding.MainBinding

class MainActivity : AppCompatActivity(),MainVM.MainNavigator{
    companion object{
        const val SEARCH_CODE=100
        const val HOME_PAGE="https://consumer.huawei.com/en/mobileservices/search/"
    }

    lateinit var binding:MainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=MainBinding.inflate(layoutInflater)
        val viewModel= ViewModelProviders.of(this).get(MainVM::class.java).apply {
            navigator=this@MainActivity
        }
        setContentView(binding.root)
        binding.viewModel=viewModel

        binding.textUrl.text = HOME_PAGE
        navigateToHome()
    }

    override fun navigateToSearch() {
        startActivityForResult(
            Intent(this,SearchingActivity::class.java),
            SEARCH_CODE)
    }

    override fun navigateToHome() {
        binding.webView.loadUrl(HOME_PAGE)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}