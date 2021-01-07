package np.com.mkishor.androidtestexample.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import np.com.mkishor.androidtestexample.data.local.ShoppingItem
import np.com.mkishor.androidtestexample.data.remote.models.ImageModel
import np.com.mkishor.androidtestexample.repositories.ShoppingRepository
import np.com.mkishor.androidtestexample.util.Constants.MAX_NAME_LENGTH
import np.com.mkishor.androidtestexample.util.Constants.MAX_PRICE_LENGTH
import np.com.mkishor.androidtestexample.util.Event
import np.com.mkishor.androidtestexample.util.Resource


/**
 * @author:  Kishor Mainali
 * @date:  05/01/2021 16:24
 * @email:  mainalikishor@outlook.com
 *
 */
class ShoppingViewModel @ViewModelInject constructor(
    private val repository: ShoppingRepository
) : ViewModel() {

    val shoppingItems = repository.observeAllShoppingItems()
    val totalPrice = repository.observeTotalPrice()

    private val _images = MutableLiveData<Event<Resource<ImageModel>>>()
    val images: LiveData<Event<Resource<ImageModel>>> = _images

    private val _currentImageUrl = MutableLiveData<String>()
    val currentImageUrl: LiveData<String> = _currentImageUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus: LiveData<Event<Resource<ShoppingItem>>> =
        _insertShoppingItemStatus


    fun setCurrentImageUrl(url: String) {
        _currentImageUrl.postValue(url)
    }

    private fun deleteShoppingItem(shoppingItem: ShoppingItem) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteShoppingItem(shoppingItem)
        }

    private fun insertShoppingItemIntoDb(shoppingItem: ShoppingItem) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertShoppingItem(shoppingItem)
        }

    fun insertShoppingItem(name: String, amountString: String, priceString: String) {

        if (name.isEmpty() || amountString.isEmpty() || priceString.isEmpty()) {
            _insertShoppingItemStatus.postValue(Event(Resource.Error(message = "Field Must Not be Empty")))
            return
        }
        if (name.length > MAX_NAME_LENGTH) {
            _insertShoppingItemStatus.postValue(Event(Resource.Error(message = "Name of the item must not exceed $MAX_NAME_LENGTH characters")))
            return
        }
        if (priceString.length > MAX_PRICE_LENGTH) {
            _insertShoppingItemStatus.postValue(Event(Resource.Error(message = "Price of the item must not exceed $MAX_PRICE_LENGTH characters")))
            return
        }

        val amount = try {
            amountString.toInt()
        } catch (e: Exception) {
            _insertShoppingItemStatus.postValue(Event(Resource.Error(message = "Please Enter Valid Amount")))
            return
        }

        val shoppingItem =
            ShoppingItem(name, amount, priceString.toFloat(), _currentImageUrl.value ?: "")
        insertShoppingItemIntoDb(shoppingItem)
        setCurrentImageUrl("")
        _insertShoppingItemStatus.postValue(Event(Resource.Success(shoppingItem)))

    }


    fun searchForImage(imageQuery: String) {

        if (imageQuery.isEmpty()) {
            return
        }
        _images.value = Event(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {

            val response = repository.searchForImage(imageQuery)
            _images.value = Event(response)

        }


    }


}