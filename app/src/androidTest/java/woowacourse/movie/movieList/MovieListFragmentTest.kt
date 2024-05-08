package woowacourse.movie.movieList

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.model.movieInfo.MovieDate
import woowacourse.movie.model.movieInfo.MovieInfo
import woowacourse.movie.model.movieInfo.RunningTime
import woowacourse.movie.model.movieInfo.Synopsis
import woowacourse.movie.model.movieInfo.Title
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class MovieListFragmentTest {
    private val movie =
        MovieInfo(
            Title("차람과 하디의 진지한 여행기 1"),
            MovieDate(LocalDate.of(2024, 2, 25)),
            RunningTime(230),
            Synopsis("wow!"),
        )

    @Test
    @DisplayName("영화 목록이 화면에 보여지는지 테스트")
    fun test1() {
        launchFragmentInContainer<MovieListFragment>()

        val viewInteraction =
            onView(withId(R.id.rv_movies)).perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    ViewMatchers.hasDescendant(
                        withText(
                            movie.title.toString(),
                        ),
                    ),
                ).atPosition(0),
            )

        viewInteraction.check(matches(ViewMatchers.hasDescendant(withText(movie.title.toString()))))
    }
}
