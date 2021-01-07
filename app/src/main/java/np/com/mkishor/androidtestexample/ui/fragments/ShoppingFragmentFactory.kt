package np.com.mkishor.androidtestexample.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import np.com.mkishor.androidtestexample.adapters.ImageAdapter
import javax.inject.Inject


/**
 * @author:  Kishor Mainali
 * @date:  06/01/2021 12:16
 * @email:  mainalikishor@outlook.com
 *
 */
class ShoppingFragmentFactory @Inject constructor(
    private val imageAdapter: ImageAdapter,
    private val glide: RequestManager
) : FragmentFactory() {


    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when (className) {
            ImagePickFragment::class.java.name -> ImagePickFragment(imageAdapter)
            AddShoppingItemFragment::class.java.name -> AddShoppingItemFragment(glide)
            else -> return super.instantiate(classLoader, className)
        }

    }
}