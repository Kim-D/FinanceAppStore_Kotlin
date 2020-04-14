package kr.co.kimd.financeappstore.binding

import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingComponent

class FragmentDataBindingComponent(fragment: Fragment) : DataBindingComponent {
    private val adapter = FragmentBindingAdapters(fragment)

    override fun getFragmentBindingAdapters() = adapter
}