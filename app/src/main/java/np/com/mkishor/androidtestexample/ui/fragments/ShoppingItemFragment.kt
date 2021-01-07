package np.com.mkishor.androidtestexample.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import np.com.mkishor.androidtestexample.databinding.FragmentShoppingItemBinding
import np.com.mkishor.androidtestexample.ui.viewmodels.ShoppingViewModel


/**
 * @author:  Kishor Mainali
 * @date:  05/01/2021 16:21
 * @email:  mainalikishor@outlook.com
 *
 */

@AndroidEntryPoint
class ShoppingItemFragment : Fragment() {

    private var _binding: FragmentShoppingItemBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ShoppingViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingItemBinding.inflate(inflater, container, false)



        binding.fabAddShoppingItem.setOnClickListener {
            findNavController().navigate(
                ShoppingItemFragmentDirections.actionShoppingItemFragmentToAddShoppingItemFragment()
            )
        }



        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}