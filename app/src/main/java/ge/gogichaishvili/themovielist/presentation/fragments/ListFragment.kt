package ge.gogichaishvili.themovielist.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ge.gogichaishvili.themovielist.R
import ge.gogichaishvili.themovielist.databinding.FragmentListBinding
import ge.gogichaishvili.themovielist.domain.model.ItemList
import ge.gogichaishvili.themovielist.domain.model.ItemTypesEnum
import ge.gogichaishvili.themovielist.presentation.adapters.ItemsAdapter
import ge.gogichaishvili.themovielist.presentation.viewmodels.Error
import ge.gogichaishvili.themovielist.presentation.viewmodels.ListScreenViewModel
import ge.gogichaishvili.themovielist.presentation.viewmodels.Loading
import ge.gogichaishvili.themovielist.presentation.viewmodels.Success
import retrofit2.HttpException

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ListScreenViewModel>()

    private lateinit var itemsAdapter: ItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //for details fragment back button //ამ ფრაგმენტის ვიუმოდელიც განადგურდება ერთი სქრინიც რომ გქონდეს წინ ნავიგაციაში.
        //მაგიტომ ასეთი რაღაცეები უფრო გლობალურად უნდა შეინახო მაგ. activity - ის ვიუმოდელი შეგიძლია გაუკეთო და იქ შეინახოო
        if (viewModel.getLastSelectedList() == ItemTypesEnum.MOVIES) {
            viewModel.getPopularMovies()
            binding.btnMovies.isEnabled = false
            binding.btnShows.isEnabled = true
        } else {
            viewModel.getPopularTvShows()
            binding.btnMovies.isEnabled = true
            binding.btnShows.isEnabled = false
        }

        setUpRecyclerView()

        viewModel.getMoviesLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is Error -> showError(it.exception)
                Loading -> showLoading()
                is Success -> showSuccess(it.data)
            }
        }

        viewModel.getTvShowsLiveData().observe(viewLifecycleOwner) {
            itemsAdapter.updateList(it)
        }

        binding.btnMovies.setOnClickListener {
            viewModel.getPopularMovies()
            viewModel.saveLastSelectedList(ItemTypesEnum.MOVIES)
            binding.btnMovies.isEnabled = false
            binding.btnShows.isEnabled = true
        }

        binding.btnShows.setOnClickListener {
            viewModel.getPopularTvShows()
            viewModel.saveLastSelectedList(ItemTypesEnum.SHOW)
            binding.btnMovies.isEnabled = true
            binding.btnShows.isEnabled = false
        }

    }

    private fun showSuccess(data: List<ItemList>) {
        binding.progressBar.isVisible = false
        itemsAdapter.updateList(data)
    }

    private fun showError(exception: HttpException) {
        binding.progressBar.isVisible = false
        Toast.makeText(requireContext(), exception.code().toString(), Toast.LENGTH_LONG).show()
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
    }

    private fun setUpRecyclerView() {
        itemsAdapter = ItemsAdapter(
            mutableListOf()
        ).apply {
            setOnItemCLickListener { itemList, i ->
                parentFragmentManager.beginTransaction().apply {
                    val detailsFragment =
                        DetailsFragment.newInstance(itemList.id, itemList.viewType.value)
                    addToBackStack("")
                    replace(R.id.flContent, detailsFragment)
                    commit()
                }
            }
        }
        binding.rvMovies.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvMovies.adapter = itemsAdapter
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}