package ge.gogichaishvili.themovielist.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ge.gogichaishvili.themovielist.R
import ge.gogichaishvili.themovielist.databinding.FragmentDetailsBinding
import ge.gogichaishvili.themovielist.domain.model.ItemTypesEnum
import ge.gogichaishvili.themovielist.presentation.adapters.DetailsPagerAdapter
import ge.gogichaishvili.themovielist.presentation.adapters.GenresAdapter
import ge.gogichaishvili.themovielist.presentation.viewmodels.DetailsViewModel

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DetailsViewModel>()

    private lateinit var genreAdapter: GenresAdapter
    private lateinit var detailsPagerAdapter: DetailsPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        setUpViewPager()
        val itemId = requireArguments().getInt(KEY_ITEM_NAME)
        val itemType = requireArguments().getInt(KEY_ITEM_TYPE)

        if (itemType == ItemTypesEnum.MOVIES.value) {
            viewModel.getMovieDetails(itemId)
            viewModel.getMovieGenres()
        } else {
            viewModel.getTvShowDetails(itemId)
            viewModel.getTvShowGenres()
        }

        viewModel.getMoviesDetailsLiveData().observe(viewLifecycleOwner) {
            binding.tvMovieName.text = it.originalTitle
            binding.ivLargePoster.setImageURI("https://image.tmdb.org/t/p/w500${it.backdropPath}")
            binding.tvMovieDescription.text = it.overview
        }

        viewModel.getTvShowsDetailsLiveData().observe(viewLifecycleOwner) {
            binding.tvMovieName.text = it.originalTitle
            binding.ivLargePoster.setImageURI("https://image.tmdb.org/t/p/w500${it.backdropPath}")
            binding.tvMovieDescription.text = it.overview
        }

        viewModel.getMoviesGenresLiveData().observe(viewLifecycleOwner) {
            genreAdapter.updateAll(it.genres)
        }

        viewModel.getTvShowsGenresLiveData().observe(viewLifecycleOwner) {
            genreAdapter.updateAll(it.genres)
        }

        binding.btnBack.setOnClickListener {
            if (parentFragmentManager.backStackEntryCount > 0) { //nice check
                parentFragmentManager.popBackStackImmediate()
            }
        }

    }

    private fun setUpViewPager() {
        detailsPagerAdapter = DetailsPagerAdapter(parentFragmentManager, lifecycle)
        binding.viewPager.adapter = detailsPagerAdapter
        binding.viewPager.setPageTransformer(ZoomOutPageTransformer())

        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager
        ) { tab: TabLayout.Tab, postion: Int ->
            tab.text = when (postion) {
                0 -> getString(R.string.first_tab_name)
                1 -> getString(R.string.second_tab_name)
                else -> ""
            }
        }.attach()
    }

    private fun setUpRecyclerView() {
        genreAdapter = GenresAdapter(
            mutableListOf()
        ).apply {
            setOnItemCLickListener {
                Toast.makeText(requireContext(), it.name.toString(), Toast.LENGTH_SHORT).show()

                //ზედმეტი ფუნქციონალი
            }
        }
        binding.rvMovies.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvMovies.adapter = genreAdapter
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val KEY_ITEM_NAME = "KEY_ITEM_NAME"
        const val KEY_ITEM_TYPE = "KEY_ITEM_TYPE"

        //მომავალში კიდე რომ დაემატოს არრგუმენტები ჯობია რომ ერთი data კლასი გააკეთო სადაც ამ ყველა არგუმენტს დავრაპავ DetailsDataModel
        fun newInstance(listId: Int, itemType: Int): DetailsFragment {
            return DetailsFragment().apply {
                arguments = bundleOf(KEY_ITEM_NAME to listId, KEY_ITEM_TYPE to itemType)
            }
        }
    }
}