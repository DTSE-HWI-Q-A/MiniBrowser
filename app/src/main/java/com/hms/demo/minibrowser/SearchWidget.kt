package com.hms.demo.minibrowser

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.hms.demo.minibrowser.databinding.SearchBinding
import com.huawei.hms.searchkit.SearchKitInstance
import com.huawei.hms.searchkit.utils.Language
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class SearchWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), TextWatcher {
    private val binding: SearchBinding

    companion object {
        const val EXPIRED_TOKEN = "SK-AuthenticationExpired"
    }

    var hasSuggestions: Boolean = false
    private var lang: Language
    private val suggestionsAdapter: SuggestionsAdapter = SuggestionsAdapter()

    init {
        val layoutInflater = LayoutInflater.from(context)
        binding = SearchBinding.inflate(layoutInflater, this, true)
        binding.apply {
            searchBar.textSearch.addTextChangedListener(this@SearchWidget)
            recycler.adapter = suggestionsAdapter
        }
        lang = getLang()
    }

    private fun getLang(): Language {
        return when (Locale.getDefault().language) {
            "es" -> Language.SPANISH
            "fr" -> Language.FRENCH
            "de" -> Language.GERMAN
            "it" -> Language.ITALIAN
            "pt" -> Language.PORTUGUESE
            else -> Language.ENGLISH
        }
    }

    override fun afterTextChanged(s: Editable?) {
        if (s.toString().isEmpty()) {
            handler.post {
                suggestionsAdapter.suggestions.clear()
                suggestionsAdapter.notifyDataSetChanged()
                binding.recycler.visibility = View.GONE
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        s?.let {
            CoroutineScope(Dispatchers.IO).launch {
                getSuggestions(s)
            }

        }

    }

    private fun getSuggestions(input: CharSequence) {
        Log.e("Suggest", input.toString())

        val response = SearchKitInstance.instance.searchHelper.suggest(input.toString(), lang)
        if (response != null) {
            response.code?.let {
                Log.e("response", response.toString())
                when (it) {
                    EXPIRED_TOKEN -> {
                        SearchKitInstance.instance.refreshToken(context)
                    }
                }
                return
            }
            handler.post {
                suggestionsAdapter.suggestions.clear()
                suggestionsAdapter.suggestions.addAll(response.suggestions)
                suggestionsAdapter.notifyDataSetChanged()
                binding.recycler.visibility = View.VISIBLE
                hasSuggestions = true
            }
        }
    }

    fun clearSuggestions() {
        suggestionsAdapter.suggestions.clear()
        suggestionsAdapter.notifyDataSetChanged()
        binding.recycler.visibility = View.GONE
        hasSuggestions = false

    }
}