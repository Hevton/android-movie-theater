package woowacourse.movie.purchaseConfirmation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.IntentCompat
import woowacourse.movie.ErrorActivity
import woowacourse.movie.R
import woowacourse.movie.base.BindingActivity
import woowacourse.movie.databinding.ActivityPurchaseConfirmationBinding
import woowacourse.movie.model.Cinema

class PurchaseConfirmationActivity :
    BindingActivity<ActivityPurchaseConfirmationBinding>(R.layout.activity_purchase_confirmation) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ticketPrice = intent.getStringExtra(EXTRA_TICKET_PRICE)
        val seatNumber = intent.getStringArrayExtra(EXTRA_SEAT_NUMBER)
        val timeDate = intent.getStringExtra(EXTRA_TIME_DATE)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val cinema =
            IntentCompat.getSerializableExtra(intent, EXTRA_CINEMA, Cinema::class.java)
                ?: return ErrorActivity.start(this)
        val movie = cinema.theater.movie
        binding.movieTitleConfirmation.text = movie.title.toString()
        binding.purchaseMovieRunningTime.text = movie.runningTime.toString()
        binding.reservedInformation.text =
            "일반 %s명 | %s | %s".format(
                seatNumber?.size,
                seatNumber?.joinToString(),
                cinema.cinemaName,
            )
        binding.ticketCharge.text = ticketPrice
        binding.movieTimeDate.text = timeDate
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    companion object {
        const val EXTRA_TICKET_PRICE = "ticketPrice"
        const val EXTRA_SEAT_NUMBER = "seatNumber"
        const val EXTRA_CINEMA = "cinema"
        const val EXTRA_TIME_DATE = "timeDate"

        fun newIntent(
            context: Context,
            ticketPrice: String,
            seatNumber: Array<String>,
            cinema: Cinema,
            timeDate: String,
        ): Intent {
            return Intent(context, PurchaseConfirmationActivity::class.java).apply {
                putExtra(EXTRA_TICKET_PRICE, ticketPrice)
                putExtra(EXTRA_SEAT_NUMBER, seatNumber)
                putExtra(EXTRA_CINEMA, cinema)
                putExtra(EXTRA_TIME_DATE, timeDate)
            }
        }
    }
}
