package woowacourse.movie.movieList.cinemaListDialog

import woowacourse.movie.model.Cinema
import woowacourse.movie.model.theater.Theater
import java.time.LocalTime

class TheatersBottomSheetPresenter(private val view: TheatersBottomSheetContract.View) :
    TheatersBottomSheetContract.Presenter {
    override fun loadCinema(theater: Theater) {
        val cinemas =
            listOf(
                Cinema(
                    "CGV",
                    theater.copy(
                        times =
                            listOf(
                                LocalTime.of(9, 0),
                                LocalTime.of(11, 0),
                                LocalTime.of(15, 0),
                            ),
                    ),
                ),
                Cinema(
                    "롯데시네마",
                    theater.copy(
                        times =
                            listOf(
                                LocalTime.of(13, 0),
                                LocalTime.of(15, 0),
                                LocalTime.of(17, 0),
                                LocalTime.of(19, 0),
                            ),
                    ),
                ),
                Cinema(
                    "메가 박스",
                    theater.copy(
                        times =
                            listOf(
                                LocalTime.of(20, 0),
                                LocalTime.of(22, 0),
                                LocalTime.of(23, 30),
                            ),
                    ),
                ),
            )
        view.showCinemas(cinemas)
    }

    override fun selectCinema(cinema: Cinema) {
        view.navigateToMovieDetail(cinema)
    }
}
