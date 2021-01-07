package np.com.mkishor.androidtestexample.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import np.com.mkishor.androidtestexample.MainCoroutineRule
import np.com.mkishor.androidtestexample.getOrAwaitValueTest
import np.com.mkishor.androidtestexample.repositories.FakeShoppingRepository
import np.com.mkishor.androidtestexample.util.Constants
import np.com.mkishor.androidtestexample.util.Status
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * @author: Kishor Mainali
 * @date: 05/01/2021 16:43
 * @email: mainalikishor@outlook.com
 */
@ExperimentalCoroutinesApi
class ShoppingViewModelTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ShoppingViewModel


    @Before
    fun setup() {
        viewModel = ShoppingViewModel(FakeShoppingRepository())
    }


    @Test
    fun `insert shopping item with empty fields, returns error`() {
        viewModel.insertShoppingItem("name", "", "200")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)


    }

    @Test
    fun `insert shopping item with too long name , returns error`() {
        val string = buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH + 1) {
                append(1)
            }
        }
        viewModel.insertShoppingItem(string, "5", "200")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)


    }

    @Test
    fun `insert shopping item with too long price , returns error`() {
        val string = buildString {
            for (i in 1..Constants.MAX_PRICE_LENGTH + 1) {
                append(1)
            }
        }
        viewModel.insertShoppingItem("name", "5", string)

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)


    }

    @Test
    fun `insert shopping item with invalid amount, returns error`() {

        viewModel.insertShoppingItem("name", "523123123131231231231231231231231", "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun `insert shopping item with valid input, returns success`() {

        viewModel.insertShoppingItem("name", "5", "3.0")
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }


    @After
    fun teardown() {

    }


}