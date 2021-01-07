package np.com.mkishor.androidtestexample.repositories

import androidx.lifecycle.LiveData
import np.com.mkishor.androidtestexample.data.local.ShoppingItem
import np.com.mkishor.androidtestexample.data.remote.models.ImageModel
import np.com.mkishor.androidtestexample.util.Resource


/**
 * @author:  Kishor Mainali
 * @date:  05/01/2021 15:59
 * @email:  mainalikishor@outlook.com
 *
 */
interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>


    suspend fun searchForImage(imageQuery: String): Resource<ImageModel>
}