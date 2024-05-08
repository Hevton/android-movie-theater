package woowacourse.movie.movieDetail

import android.content.Intent

interface MovieDetailContract {
    interface View {
        fun navigateToPurchaseConfirmation(intent: Intent)

        fun onTicketCountChanged()

        fun showTicketMessage(message: String)

        fun updateDateAdapter(dates: List<String>)

        fun updateTimeAdapter(times: List<String>)
    }

    interface Presenter {
        fun onTicketPlus()

        fun onTicketMinus()

        fun updateTimes()

        fun loadDateRange()
    }
}
