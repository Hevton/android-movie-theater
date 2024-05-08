package woowacourse.movie

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.model.Cinema
import woowacourse.movie.seat.TheaterSeatContract
import woowacourse.movie.seat.TheaterSeatPresenter

@ExtendWith(MockKExtension::class)
class SeatPresenterTest {
    @RelaxedMockK
    private lateinit var view: TheaterSeatContract.View

    @RelaxedMockK
    private lateinit var cinema: Cinema

    private lateinit var presenter: TheaterSeatPresenter

    @BeforeEach
    fun setUp() {
        presenter = TheaterSeatPresenter(view, 2, cinema)
    }

    @Test
    fun `좌석 선택 시 배경색 변경 테스트`() {
        // given
        val seatId = "A1"
        val expectedColor = "#FF0000"
        val colorSlot = slot<String>()
        every { view.setSeatBackground(seatId, capture(colorSlot)) } just runs
        // when
        presenter.toggleSeatSelection(seatId)
        // then
        verify(exactly = 1) { view.setSeatBackground(seatId, colorSlot.captured) }
        colorSlot.captured shouldBe expectedColor
    }

    @Test
    fun `좌석 선택시 가격 적용 테스트`() {
        // given
        val seatId = "A1"
        val expectedPrice = 10_000
        val priceSlot = slot<Int>()
        every { view.updateTotalPrice(capture(priceSlot)) } just runs
        // when
        presenter.toggleSeatSelection(seatId)
        // then
        verify(exactly = 1) { view.updateTotalPrice(priceSlot.captured) }
        priceSlot.captured shouldBe expectedPrice
    }

    @Test
    fun `티켓수가 2일 때, 좌석은 2만큼 선택할 수 있다`() {
        // given
        val seatId1 = "A1"
        val seatId2 = "A2"
        val redColor = "#FF0000"
        val seatOneColorSlot = slot<String>()
        val seatTwoColorSlot = slot<String>()
        every { view.setSeatBackground(seatId1, capture(seatOneColorSlot)) } just runs
        every { view.setSeatBackground(seatId2, capture(seatTwoColorSlot)) } just runs
        // when
        presenter.toggleSeatSelection(seatId1)
        presenter.toggleSeatSelection(seatId2)
        // then
        verify(exactly = 1) { view.setSeatBackground(seatId1, seatOneColorSlot.captured) }
        verify(exactly = 1) { view.setSeatBackground(seatId2, seatTwoColorSlot.captured) }
        seatOneColorSlot.captured shouldBe redColor
        seatTwoColorSlot.captured shouldBe redColor
    }

    @Test
    fun `티켓수가 2개 일 때, 좌석은 3개 이상 선택할 수 없다`() {
        // given
        val seatId1 = "A1"
        val seatId2 = "A2"
        val seatId3 = "A3"
        val redColor = "#FF0000"
        every { view.setSeatBackground(seatId1, redColor) } just runs
        every { view.setSeatBackground(seatId2, redColor) } just runs
        every { view.setSeatBackground(seatId3, redColor) } just runs
        // when
        presenter.toggleSeatSelection(seatId1)
        presenter.toggleSeatSelection(seatId2)
        presenter.toggleSeatSelection(seatId3)
        // then
        verify(exactly = 1) { view.setSeatBackground(seatId1, redColor) }
        verify(exactly = 1) { view.setSeatBackground(seatId2, redColor) }
        verify(exactly = 0) { view.setSeatBackground(seatId3, redColor) }
    }

    @Test
    fun `빈 좌석 선택하고, 재선택하면 빈 좌석으로 돌아가는지 테스트`() {
        // given
        val seatId = "A1"
        val whiteColor = "#FFFFFF"
        val redColor = "#FF0000"
        val colorSlot = slot<String>()
        every { view.setSeatBackground(seatId, capture(colorSlot)) } just runs
        // when
        presenter.toggleSeatSelection(seatId) // Select
        presenter.toggleSeatSelection(seatId) // Deselect
        // then
        verify(exactly = 1) { view.setSeatBackground(seatId, redColor) }
        verify(exactly = 1) { view.setSeatBackground(seatId, whiteColor) }
        colorSlot.captured shouldBe whiteColor
    }
}
