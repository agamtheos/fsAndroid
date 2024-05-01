//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.fs_android.data.response.BaseError
//import com.example.fs_android.domain.MovieRepository
//import com.example.fs_android.domain.model.Movie
//import com.example.fs_android.utils.Error
//import com.example.fs_android.utils.Initiate
//import com.example.fs_android.utils.Loading
//import com.example.fs_android.utils.NetworkState
//import com.example.fs_android.utils.Success
//import com.example.fs_android.utils.UIState
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.async
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//class HomeViewModel(private val repository: MovieRepository): ViewModel() {
//    private val _movie = MutableStateFlow<UIState<List<Movie>>>(Initiate())
//    val  movie: StateFlow<UIState<List<Movie>>> = _movie
//
//    fun getMovie(page: Int = 1){
//        viewModelScope.launch( Dispatchers.Main ) {
//            _movie.value = Loading(true)
//            val process = async(Dispatchers.IO) {
//                repository.getMovies(page)
//            }
//            when (val state = process.await()){
//                is NetworkState.Success -> {
//                    _movie.value = Loading(false)
//                    _movie.value = Success(data = state.data)
//
//                }
//                is NetworkState.Error -> {
//                    _movie.value = Loading(false)
//                    _movie.value = Error((state.error as BaseError).error)
//                }
//            }
//        }
//    }
//
//    fun getListBatchMovie(page: Int = 1){
//        viewModelScope.launch(Dispatchers.Main) {
//            _movie.value = Loading(true)
//            val process = async(Dispatchers.IO) {
//                repository.getMovies(page)
//            }
//            when (val state = process.await()){
//                is NetworkState.Success -> {
//                    _movie.value = Loading(false)
//                    _movie.value = Success(data = state.data)
//                }
//                is NetworkState.Error -> {
//                    _movie.value = Loading(false)
//                    _movie.value = Error((state.error as BaseError).error)
//                }
//            }
//        }
//    }
//}