package woowacourse.movie.movieList.cinemaListDialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.databinding.ItemBottomSheetTheatersBinding
import woowacourse.movie.model.Cinema

class CinemaAdapter(private val onTheaterClicked: (Cinema) -> Unit) :
    ListAdapter<Cinema, TheaterViewHolder>(theaterComparator) {
    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        if (::inflater.isInitialized.not()) inflater = LayoutInflater.from(parent.context)
        val binding = ItemBottomSheetTheatersBinding.inflate(inflater, parent, false)

        return TheaterViewHolder(binding, onTheaterClicked)
    }

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    companion object {
        private val theaterComparator =
            object : DiffUtil.ItemCallback<Cinema>() {
                override fun areItemsTheSame(
                    oldItem: Cinema,
                    newItem: Cinema,
                ): Boolean {
                    return oldItem.cinemaName == newItem.cinemaName
                }

                override fun areContentsTheSame(
                    oldItem: Cinema,
                    newItem: Cinema,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
