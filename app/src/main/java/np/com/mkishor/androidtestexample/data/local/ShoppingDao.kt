package np.com.mkishor.androidtestexample.data.local

import androidx.lifecycle.LiveData
import androidx.room.*


/**
 * @author:  Kishor Mainali
 * @date:  05/01/2021 11:51
 * @email:  mainalikishor@outlook.com
 *
 */
@Dao
interface ShoppingDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)


    @Query("SELECT * FROM shopping_items")
    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>


    @Query("SELECT SUM(price * amount)  FROM shopping_items")
    fun observeTotalPrice(): LiveData<Float>
}