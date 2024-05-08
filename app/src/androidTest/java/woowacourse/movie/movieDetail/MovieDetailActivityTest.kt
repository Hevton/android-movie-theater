package woowacourse.movie.movieDetail

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.model.Cinema
import woowacourse.movie.model.movieInfo.MovieDate
import woowacourse.movie.model.movieInfo.MovieInfo
import woowacourse.movie.model.movieInfo.RunningTime
import woowacourse.movie.model.movieInfo.Synopsis
import woowacourse.movie.model.movieInfo.Title
import woowacourse.movie.model.theater.Theater
import java.time.LocalDate
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class MovieDetailActivityTest {
    private val cinema
        get() =
            Cinema(
                "CGV",
                Theater(
                    MovieInfo(
                        Title("차람과 하디의 진지한 여행기 1"),
                        MovieDate(LocalDate.of(2024, 2, 25)),
                        RunningTime(230),
                        Synopsis("wow!"),
                    ),
                    times =
                        listOf(
                            LocalTime.of(10, 0),
                            LocalTime.of(14, 0),
                            LocalTime.of(18, 0),
                        ),
                    seats = mapOf(),
                ),
            )
    private val intent =
        Intent(
            ApplicationProvider.getApplicationContext(),
            MovieDetailActivity::class.java,
        ).apply {
            putExtra(
                "Cinema",
                cinema,
            )
        }

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule<MovieDetailActivity>(intent)

    @Test
    fun `초기값은1_플러스버튼클릭_티켓수량표시`() {
        Espresso.onView(withId(R.id.plus_button)).check(matches(isDisplayed()))

        Espresso.onView(withId(R.id.plus_button)).perform(click())
        Espresso.onView(withId(R.id.quantity_text_view)).check(matches(withText("2")))
    }

    @Test
    fun `좌석선택버튼표시_화면에보임`() {
        Espresso.onView(withId(R.id.seat_confirmation_button)).check(matches(isDisplayed()))
    }
}
