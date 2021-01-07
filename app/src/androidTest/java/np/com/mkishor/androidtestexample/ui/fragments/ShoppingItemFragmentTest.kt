package np.com.mkishor.androidtestexample.ui.fragments

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import np.com.mkishor.androidtestexample.R
import np.com.mkishor.androidtestexample.launchFragmentInHiltContainer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify


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
    var hiltRule = HiltAndroidRule(this)


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


}