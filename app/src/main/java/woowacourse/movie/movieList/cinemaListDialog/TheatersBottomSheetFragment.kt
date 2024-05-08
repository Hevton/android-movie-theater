package woowacourse.movie.movieList.cinemaListDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.BundleCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.ErrorActivity
import woowacourse.movie.databinding.FragmentBottomSheetTheatersBinding
import woowacourse.movie.model.Cinema
import woowacourse.movie.model.theater.Theater
import woowacourse.movie.movieDetail.MovieDetailActivity
import woowacourse.movie.movieList.MovieListFragment.Companion.THEATER_KEY

class TheatersBottomSheetFragment : BottomSheetDialogFragment(), TheatersBottomSheetContract.View {
    private var _binding: FragmentBottomSheetTheatersBinding? = null
    private val binding get() = _binding ?: error("error")
    private lateinit var adapter: CinemaAdapter

    private val presenter: TheatersBottomSheetContract.Presenter by lazy {
        TheatersBottomSheetPresenter(this)
    }
    private lateinit var theater: Theater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            BundleCompat.getSerializable(it, THEATER_KEY, Theater::class.java)
        }?.let {
            theater = it
        } ?: ErrorActivity.start(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBottomSheetTheatersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CinemaAdapter { presenter.selectCinema(it) }
        presenter.loadCinema(theater)
    }

    override fun navigateToMovieDetail(cinema: Cinema) {
        requireActivity().apply {
            startActivity(MovieDetailActivity.newIntent(this, cinema))
            dismiss()
        }
    }

    override fun showCinemas(cinemas: List<Cinema>) {
        binding.rvCinema.adapter = adapter
        adapter.submitList(cinemas)
    }

    companion object {
        val TAG: String? = this::class.java.canonicalName
    }
}
