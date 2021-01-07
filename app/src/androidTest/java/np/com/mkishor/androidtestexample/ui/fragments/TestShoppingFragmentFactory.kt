package np.com.mkishor.androidtestexample.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import np.com.mkishor.androidtestexample.adapters.ImageAdapter
import np.com.mkishor.androidtestexample.adapters.ShoppingItemAdapter
import np.com.mkishor.androidtestexample.repositories.FakeShoppingRepositoryAndroidTest
import np.com.mkishor.androidtestexample.ui.viewmodels.ShoppingViewModel
import javax.inject.Inject


/**
 * @author:  Kishor Mainali
 * @date:  06/01/2021 12:16
 * @email:  mainalikishor@outlook.com
 *
 */
class TestShoppingFragmentFactory @Inject constructor(
    private val imageAdapter: ImageAdapter,
    private val glide: RequestManager,
    private val shoppingItemAdapter: ShoppingItemAdapter
) : FragmentFactory() {


    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when (className) {
            ImagePickFragment::class.java.name -> ImagePickFragment(imageAdapter)
            AddShoppingItemFragment::class.java.name -> AddShoppingItemFragment(glide)
            ShoppingItemFragment::class.java.name -> ShoppingItemFragment(
                shoppingItemAdapter,
                viewModel = ShoppingViewModel(FakeShoppingRepositoryAndroidTest())
            )
            else -> return super.instantiate(classLoader, className)
        }

    }


}