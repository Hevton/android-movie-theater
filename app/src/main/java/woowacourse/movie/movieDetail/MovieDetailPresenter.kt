package woowacourse.movie.movieDetail

import woowacourse.movie.model.Cinema
import woowacourse.movie.model.movieInfo.MovieInfo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieDetailPresenter(
    private val view: MovieDetailContract.View,
    private val cinema: Cinema,
) : MovieDetailContract.Presenter {
    var ticketNum = 1

    val movie: MovieInfo = cinema.theater.movie

    override fun onTicketPlus() {
        if (ticketNum < 10) {
            ticketNum++
            view.onTicketCountChanged()
        } else {
            view.showTicketMessage("최대 티켓 수량에 도달했습니다.")
        }
    }

    override fun onTicketMinus() {
        if (ticketNum > 1) {
            ticketNum--
            view.onTicketCountChanged()
        } else {
            view.showTicketMessage("최소 티켓 수량입니다.")
        }
    }

    override fun updateTimes() {
        view.updateTimeAdapter(cinema.theater.times.map { it.toString() })
    }

    override fun loadDateRange() {
        val startDate = LocalDate.of(2024, 4, 1)
        val endDate = LocalDate.of(2024, 4, 30)
        val dates = mutableListOf<String>()
        var date = startDate
        while (!date.isAfter(endDate)) {
            dates.add(date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
            date = date.plusDays(1)
        }
        view.updateDateAdapter(dates)
    }
}
