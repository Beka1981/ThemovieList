package ge.gogichaishvili.themovielist.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ge.gogichaishvili.themovielist.domain.model.ItemList
import ge.gogichaishvili.themovielist.domain.model.ItemTypesEnum
import ge.gogichaishvili.themovielist.domain.repository.MoviesRepository
import ge.gogichaishvili.themovielist.domain.repository.TvShowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListScreenViewModel : ViewModel() {

    private val moviesMutableLiveData = MutableLiveData<List<ItemList>>()
    private val moviesRepository = MoviesRepository.getInstance()

    private val tvShowMutableLiveData = MutableLiveData<List<ItemList>>()
    private val tvShowRepository = TvShowRepository.getInstance()

    init {
        viewModelScope.launch {
            moviesMutableLiveData.postValue(
                moviesRepository.getMovies("843c612d1207fdec63f0e6a5fd426d68") // ეს api key კონსტანტად უნდა გაიტანო
                    .toDomainModel().results
            )
        }
    }

    fun getMoviesLiveData(): LiveData<List<ItemList>> {
        return moviesMutableLiveData
    }

    fun getTvShowsLiveData(): LiveData<List<ItemList>> {
        return tvShowMutableLiveData
    }

    fun getPopularMovies() {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            moviesMutableLiveData.postValue(
                moviesRepository.getMovies("843c612d1207fdec63f0e6a5fd426d68")
                    .toDomainModel().results
            )
        }
    }

    fun getPopularTvShows() {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            tvShowMutableLiveData.postValue(
                tvShowRepository.getTvShows("843c612d1207fdec63f0e6a5fd426d68")
                    .toDomainModel().results
            )
        }
    }

    private var lastSelectedList = ItemTypesEnum.MOVIES

    fun saveLastSelectedList(listType: ItemTypesEnum) {
        lastSelectedList = listType
    }

    fun getLastSelectedList(): ItemTypesEnum {
        return lastSelectedList
    }

}


