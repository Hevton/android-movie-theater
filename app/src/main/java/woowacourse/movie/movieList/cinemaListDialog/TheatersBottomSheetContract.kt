package woowacourse.movie.movieList.cinemaListDialog

import woowacourse.movie.model.Cinema
import woowacourse.movie.model.theater.Theater

interface TheatersBottomSheetContract {
    interface View {
        fun showCinemas(cinemas: List<Cinema>)

        fun navigateToMovieDetail(cinema: Cinema)
    }

    interface Presenter {
        fun loadCinema(theater: Theater)

        fun selectCinema(cinema: Cinema)
    }
}
