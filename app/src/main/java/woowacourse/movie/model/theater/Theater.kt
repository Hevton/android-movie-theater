package woowacourse.movie.model.theater

import woowacourse.movie.model.movieInfo.MovieInfo
import java.io.Serializable
import java.time.LocalTime

data class Theater(
    val movie: MovieInfo,
    val times: List<LocalTime>,
    private val seats: Map<String, Seat>,
) : Serializable
