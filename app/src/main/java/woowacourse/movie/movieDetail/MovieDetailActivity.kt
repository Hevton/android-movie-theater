package woowacourse.movie.movieDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.IntentCompat
import woowacourse.movie.ErrorActivity
import woowacourse.movie.R
import woowacourse.movie.base.BindingActivity
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.model.Cinema
import woowacourse.movie.seat.TheaterSeatActivity

class MovieDetailActivity :
    BindingActivity<ActivityMovieDetailBinding>(R.layout.activity_movie_detail),
    MovieDetailContract.View {
    private lateinit var presenter: MovieDetailContract.Presenter
    private lateinit var dateAdapter: ArrayAdapter<String>
    private lateinit var timeAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val cinema =
            IntentCompat.getSerializableExtra(intent, EXTRA_CINEMA, Cinema::class.java)
        if (cinema == null) {
            ErrorActivity.start(this)
            return
        }
        initView()
        presenter =
            MovieDetailPresenter(
                view = this@MovieDetailActivity,
                cinema,
            ).also { binding.presenter = it }
        setupEventListeners(cinema)
        presenter.loadDateRange()
    }

    override fun navigateToPurchaseConfirmation(intent: Intent) {
        startActivity(intent)
    }

    override fun onTicketCountChanged() {
        binding.invalidateAll()
    }

    override fun showTicketMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun updateDateAdapter(dates: List<String>) {
        dateAdapter.clear()
        dateAdapter.addAll(dates)
    }

    override fun updateTimeAdapter(times: List<String>) {
        timeAdapter.clear()
        timeAdapter.addAll(times)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    private fun initView() {
        dateAdapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                mutableListOf(),
            )
        binding.movieDateSpinner.adapter = dateAdapter
        binding.movieDateSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long,
                ) {
                    presenter.updateTimes()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        timeAdapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                mutableListOf(),
            )
        binding.movieTimeSpinner.adapter = timeAdapter
    }

    private fun setupEventListeners(cinema: Cinema) {
        binding.seatConfirmationButton.setOnClickListener {
            val intent =
                TheaterSeatActivity.newIntent(
                    this,
                    binding.quantityTextView.text.toString(),
                    cinema,
                    timeDate(),
                )
            navigateToPurchaseConfirmation(intent)
        }
    }

    private fun timeDate() =
        buildString {
            append(binding.movieDateSpinner.selectedItem.toString())
            append(DELIMITER)
            append(binding.movieTimeSpinner.selectedItem.toString())
        }

    companion object {
        const val DELIMITER = " "
        const val EXTRA_CINEMA = "cinema"

        fun newIntent(
            context: Context,
            cinema: Cinema,
        ): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_CINEMA, cinema)
            }
        }
    }
}
