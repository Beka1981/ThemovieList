package ge.gogichaishvili.themovielist.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ge.gogichaishvili.themovielist.presentation.fragments.FirstFragment
import ge.gogichaishvili.themovielist.presentation.fragments.SecondFragment


class DetailsPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private val fragmentsList = listOf(FirstFragment(), SecondFragment())

    override fun getItemCount(): Int {
        return fragmentsList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentsList[position]
    }
}