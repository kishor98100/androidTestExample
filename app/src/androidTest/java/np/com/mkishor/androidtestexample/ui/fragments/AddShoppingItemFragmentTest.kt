package np.com.mkishor.androidtestexample.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import np.com.mkishor.androidtestexample.R
import np.com.mkishor.androidtestexample.data.local.ShoppingItem
import np.com.mkishor.androidtestexample.getOrAwaitValue
import np.com.mkishor.androidtestexample.launchFragmentInHiltContainer
import np.com.mkishor.androidtestexample.repositories.FakeShoppingRepositoryAndroidTest
import np.com.mkishor.androidtestexample.ui.viewmodels.ShoppingViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject


/**
 * @author: Kishor Mainali
 * @date: 06/01/2021 11:55
 * @email: mainalikishor@outlook.com
 */

@ExperimentalCoroutinesApi
@HiltAndroidTest
@MediumTest
class AddShoppingItemFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory


    @Before
    fun setup() {
        hiltRule.inject()
    }


    @Test
    fun pressBacButton_popBackStack() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        pressBack()

        verify(navController).popBackStack()


    }


    @Test
    fun pressImageButton_navigateToImagePickFragment() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.ivShoppingImage)).perform(click())
        verify(navController).navigate(
            AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
        )

    }

    @Test
    fun clickInsertIntoDb_shoppingItemInsertedIntoDb() {
        val testViewModel = ShoppingViewModel(FakeShoppingRepositoryAndroidTest())
        launchFragmentInHiltContainer<AddShoppingItemFragment>(fragmentFactory = fragmentFactory) {
            viewModel = testViewModel

        }

        val shoppingItem = ShoppingItem(
            "shopping item",
            5,
            24f,
            ""
        )


        onView(withId(R.id.etShoppingItemName)).perform(replaceText("shopping item"))
        onView(withId(R.id.etShoppingItemAmount)).perform(replaceText("5"))
        onView(withId(R.id.etShoppingItemPrice)).perform(replaceText("24.0"))
        onView(withId(R.id.btnAddShoppingItem)).perform(click())


        assertThat(testViewModel.shoppingItems.getOrAwaitValue()).contains(shoppingItem)


    }


}