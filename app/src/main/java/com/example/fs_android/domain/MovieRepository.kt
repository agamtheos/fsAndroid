//package com.example.fs_android.domain
//
//import com.example.fs_android.data.NetworkService
//import com.example.fs_android.data.response.BaseError
//import com.example.fs_android.data.response.mapToMovie
//import com.example.fs_android.domain.model.Movie
//import com.example.fs_android.utils.NetworkState
//import com.example.fs_android.utils.parseError
//
//class MovieRepository(private val service: NetworkService) {
//    suspend fun getMovies(page: Int): NetworkState<List<Movie>> {
//        return try {
//            val response = service.getNowPlaying(page)
//            if (response.isSuccessful){
//                val body = response.body()
//                if (body != null){
//                    body.data.mapToMovie()?.let {
//                        NetworkState.Success(it)
//                    } ?:
//                    run {
//                        NetworkState
//                            .Error(error = BaseError(error = "Null Response"))
//                    }
//                }else {
//                    parseError(response)
//                }
//            } else {
//                parseError(response)
//            }
//        }catch (e:Exception) {
//            NetworkState
//                .Error(error = e)
//        }
//    }
//}