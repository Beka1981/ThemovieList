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
import retrofit2.HttpException

class ListScreenViewModel : ViewModel() {

    private val moviesMutableLiveData = MutableLiveData<ResponseState>()
    private val moviesRepository = MoviesRepository.getInstance()

    private val tvShowMutableLiveData = MutableLiveData<List<ItemList>>()
    private val tvShowRepository = TvShowRepository.getInstance()

    init {
        moviesMutableLiveData.postValue(Loading)
        viewModelScope.launch {

            try {
                moviesMutableLiveData.postValue(
                    Success(
                        data = moviesRepository.getMovies("843c612d1207fdec63f0e6a5fd426d68") // ეს api key კონსტანტად უნდა გაიტანო
                            .toDomainModel().results
                    )
                )
            } catch (e: HttpException) {
                moviesMutableLiveData.postValue(Error(e))
            }


        }
    }

    fun getMoviesLiveData(): LiveData<ResponseState> {
        return moviesMutableLiveData
    }

    fun getTvShowsLiveData(): LiveData<List<ItemList>> {
        return tvShowMutableLiveData
    }

    fun getPopularMovies() {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            try {
                moviesMutableLiveData.postValue(
                    Success(
                        data = moviesRepository.getMovies("843c612d1207fdec63f0e6a5fd426d68") // ეს api key კონსტანტად უნდა გაიტანო
                            .toDomainModel().results
                    )
                )
            } catch (e: HttpException) {
                moviesMutableLiveData.postValue(Error(e))
            }
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


