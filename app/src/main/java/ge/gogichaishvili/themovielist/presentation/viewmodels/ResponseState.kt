package ge.gogichaishvili.themovielist.presentation.viewmodels

import ge.gogichaishvili.themovielist.domain.model.ItemList
import retrofit2.HttpException
import java.lang.Exception

sealed class ResponseState
data class Success(val data: List<ItemList>) : ResponseState()
data class Error(val exception: HttpException) : ResponseState()
object Loading : ResponseState()
