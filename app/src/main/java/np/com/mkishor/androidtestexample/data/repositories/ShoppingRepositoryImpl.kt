package np.com.mkishor.androidtestexample.data.repositories

import androidx.lifecycle.LiveData
import np.com.mkishor.androidtestexample.data.local.ShoppingDao
import np.com.mkishor.androidtestexample.data.local.ShoppingItem
import np.com.mkishor.androidtestexample.data.remote.PixalBayApi
import np.com.mkishor.androidtestexample.data.remote.models.ImageModel
import np.com.mkishor.androidtestexample.repositories.ShoppingRepository
import np.com.mkishor.androidtestexample.util.Resource
import javax.inject.Inject


/**
 * @author:  Kishor Mainali
 * @date:  05/01/2021 15:57
 * @email:  mainalikishor@outlook.com
 *
 */
class ShoppingRepositoryImpl @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixalBayApi: PixalBayApi
) : ShoppingRepository {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) =
        shoppingDao.insertShoppingItem(shoppingItem)

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) =
        shoppingDao.deleteShoppingItem(shoppingItem)

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> =
        shoppingDao.observeAllShoppingItems()

    override fun observeTotalPrice(): LiveData<Float> = shoppingDao.observeTotalPrice()

    override suspend fun searchForImage(imageQuery: String): Resource<ImageModel> {

        return try {
            val response = pixalBayApi.searchForImage(searchQuery = imageQuery)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.Success(data = it)
                } ?: Resource.Error(message = "Unknown Error")
            } else {
                Resource.Error(message = "Unknown Error")
            }

        } catch (e: Exception) {
            Resource.Error(message = e.localizedMessage)
        }
    }

}