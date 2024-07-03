package org.test.news

import androidx.fragment.app.DialogFragment

open class BaseFragment : DialogFragment() {
    fun setTitle(title : String?){
        (requireActivity() as MainActivity).supportActionBar?.title = title
    }
}