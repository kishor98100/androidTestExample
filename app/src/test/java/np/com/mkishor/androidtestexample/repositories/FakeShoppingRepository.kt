package np.com.mkishor.androidtestexample.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import np.com.mkishor.androidtestexample.data.local.ShoppingItem
import np.com.mkishor.androidtestexample.data.remote.models.ImageModel
import np.com.mkishor.androidtestexample.util.Resource


/**
 * @author:  Kishor Mainali
 * @date:  05/01/2021 16:07
 * @email:  mainalikishor@outlook.com
 *
 */

class FakeShoppingRepository : ShoppingRepository {

    private val shoppingItems = mutableListOf<ShoppingItem>()
    private val observeableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)
    private val observeableTotalPrice = MutableLiveData<Float>()

    private var showReturnNetworkError = false


    fun setShouldReturnNetworkError(value: Boolean) {
        showReturnNetworkError = value
    }

    private fun refresh() {
        observeableShoppingItems.postValue(shoppingItems)
        observeableTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice(): Float {
        return shoppingItems.sumByDouble { it.price.toDouble() }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refresh()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refresh()

    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> = observeableShoppingItems

    override fun observeTotalPrice(): LiveData<Float> = observeableTotalPrice

    override suspend fun searchForImage(imageQuery: String): Resource<ImageModel> {
        return if (showReturnNetworkError) {
            Resource.Error(message = "Error")
        } else Resource.Success(ImageModel(listOf(), 0, 0))
    }

}