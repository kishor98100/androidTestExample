package np.com.mkishor.androidtestexample.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import np.com.mkishor.androidtestexample.getOrAwaitValue
import np.com.mkishor.androidtestexample.launchFragmentInHiltContainer
import np.com.mkishor.androidtestexample.ui.fragments.ShoppingItemFragment
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


/**
 * @author:  Kishor Mainali
 * @date:  05/01/2021 11:57
 * @email:  mainalikishor@outlook.com
 *
 */

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ShoppingDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Inject
    @Named("testDb")
    lateinit var database: ShoppingItemDatabase

    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.shoppingDao()

    }

    @After
    fun teardown() {
        database.close()
    }


    @Test
    fun insertShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem(
            "name", 1, 1f, "url", id = 1
        )
        dao.insertShoppingItem(shoppingItem)
        val allItems = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allItems).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem(
            "name", 1, 1f, "url", id = 1
        )
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)
        val allItems = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allItems).doesNotContain(shoppingItem)
    }

    @Test
    fun observeTotalPriceSum() = runBlockingTest {
        val shoppingItem = ShoppingItem(
            "name", 1, 1f, "url", id = 1
        )
        val shoppingItem1 = ShoppingItem(
            "name1", 36, 10f, "url", id = 2
        )
        val shoppingItem2 = ShoppingItem(
            "name2", 25, 30f, "url", id = 3
        )
        val shoppingItem3 = ShoppingItem(
            "name3", 100, 25f, "url", id = 4
        )
        dao.insertShoppingItem(shoppingItem)
        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)
        val totalPriceSum = dao.observeTotalPrice().getOrAwaitValue()
        assertThat(totalPriceSum).isEqualTo(1 * 1f + 36 * 10f + 25 * 30f + 100 * 25f)
    }




}