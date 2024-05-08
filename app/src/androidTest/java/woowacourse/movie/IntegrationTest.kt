package woowacourse.movie

import MovieAdapter
import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import woowacourse.movie.movieList.MovieListFragment

class IntegrationTest {
    @Test
    fun `좌석선택버튼클릭_좌석선택표시`() {
        Espresso.onView(ViewMatchers.withId(R.id.seat_confirmation_button)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.seatTable)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed(),
            ),
        )
    }

    @Test
    fun `다음화면에서_뒤로가기버튼클릭_영화디테일화면표시`() {
        Espresso.onView(ViewMatchers.withId(R.id.seat_confirmation_button)).perform(ViewActions.click())
        Espresso.pressBack()
        Espresso.onView(ViewMatchers.withId(R.id.scroll_view)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed(),
            ),
        )
    }

    @Test
    @DisplayName("영화 버튼 누르면 Dialog 보이는지 테스트")
    fun test2() {
        launchFragmentInContainer<MovieListFragment>()
        Espresso.onView(ViewMatchers.withId(R.id.rv_movies))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<MovieAdapter.MovieViewHolder>(
                        0,
                        clickItemWithId(R.id.movie_details_button),
                    ),
            )

        Espresso.onView(ViewMatchers.withId(R.id.rv_cinema))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    // reference: https://www.maskaravivek.com/post/working-with-recycler-views-in-espresso-tests/
    private fun clickItemWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(
                uiController: UiController,
                view: View,
            ) {
                val v = view.findViewById<View>(id) as View
                v.performClick()
            }
        }
    }
}
