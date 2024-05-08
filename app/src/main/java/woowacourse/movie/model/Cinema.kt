package woowacourse.movie.model

import woowacourse.movie.model.theater.Theater
import java.io.Serializable

data class Cinema(
    val cinemaName: String,
    val theater: Theater,
) : Serializable
