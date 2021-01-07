package np.com.mkishor.androidtestexample.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import np.com.mkishor.androidtestexample.databinding.FragmentAddShoppingItemBinding
import np.com.mkishor.androidtestexample.ui.viewmodels.ShoppingViewModel
import np.com.mkishor.androidtestexample.util.Status
import javax.inject.Inject


/**
 * @author:  Kishor Mainali
 * @date:  05/01/2021 16:21
 * @email:  mainalikishor@outlook.com
 *
 */
@AndroidEntryPoint
class AddShoppingItemFragment @Inject constructor(
    val glide: RequestManager
) : Fragment() {

    private var _binding: FragmentAddShoppingItemBinding? = null
    private val binding get() = _binding!!


    lateinit var viewModel: ShoppingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddShoppingItemBinding.inflate(inflater, container, false)

        subscribeToObservers()

        binding.ivShoppingImage.setOnClickListener {
            findNavController().navigate(
                AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
            )
        }

        binding.btnAddShoppingItem.setOnClickListener {
            viewModel.insertShoppingItem(
                binding.etShoppingItemName.text.toString(),
                binding.etShoppingItemAmount.text.toString(),
                binding.etShoppingItemPrice.text.toString()
            )
        }


        val callback = object : OnBackPressedCallback(true) {

            override fun handleOnBackPressed() {
                viewModel.setCurrentImageUrl("")
                findNavController().popBackStack()
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)





        return binding.root
    }


    private fun subscribeToObservers() {
        viewModel.currentImageUrl.observe(viewLifecycleOwner, {
            glide.load(it).into(binding.ivShoppingImage)
        })

        viewModel.insertShoppingItemStatus.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        Snackbar.make(binding.root, "Added Shopping Item", Snackbar.LENGTH_SHORT)
                            .show()
                        findNavController().popBackStack()
                    }
                    Status.ERROR -> {
                        Snackbar.make(
                            binding.root,
                            result.message ?: "UnKnown Error",
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                        findNavController().popBackStack()
                    }
                    Status.LOADING -> {

                    }
                }

            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}