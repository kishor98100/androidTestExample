package np.com.mkishor.androidtestexample.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import np.com.mkishor.androidtestexample.R
import np.com.mkishor.androidtestexample.adapters.ShoppingItemAdapter
import np.com.mkishor.androidtestexample.data.local.ShoppingItem
import np.com.mkishor.androidtestexample.getOrAwaitValue
import np.com.mkishor.androidtestexample.launchFragmentInHiltContainer
import np.com.mkishor.androidtestexample.ui.viewmodels.ShoppingViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject


/**
 * @author: Kishor Mainali
 * @date: 06/01/2021 11:34
 * @email: mainalikishor@outlook.com
 */

@ExperimentalCoroutinesApi
@HiltAndroidTest
@MediumTest
class ShoppingItemFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var testFragmentFactory: TestShoppingFragmentFactory


    @Before
    fun setup() {
        hiltRule.inject()
    }


    @Test
    fun clickAtAddShoppingItemButton_navigateToAddShoppingItemFragment() {

        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<ShoppingItemFragment> {

            Navigation.setViewNavController(requireView(), navController)

        }

        onView(withId(R.id.fabAddShoppingItem)).perform(click())

        verify(navController).navigate(
            ShoppingItemFragmentDirections.actionShoppingItemFragmentToAddShoppingItemFragment()
        )

    }


    @Test
    fun swipeShoppingItem_deleteItemFromDb() {
        val shoppingItem = ShoppingItem("TEST", 5, 20f, "Test", id = 1)
        var testViewModel: ShoppingViewModel? = null
        launchFragmentInHiltContainer<ShoppingItemFragment>(fragmentFactory = testFragmentFactory) {
            testViewModel = viewModel
            viewModel?.insertShoppingItemIntoDb(shoppingItem)
        }

        onView(withId(R.id.rvShoppingItems)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ShoppingItemAdapter.ShoppingViewHolder>(
                0,
                swipeLeft()
            )
        )

        assertThat(testViewModel?.shoppingItems?.getOrAwaitValue()).isEmpty()

    }


}