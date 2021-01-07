package np.com.mkishor.androidtestexample.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import np.com.mkishor.androidtestexample.adapters.ShoppingItemAdapter
import np.com.mkishor.androidtestexample.databinding.FragmentShoppingItemBinding
import np.com.mkishor.androidtestexample.ui.viewmodels.ShoppingViewModel
import javax.inject.Inject


/**
 * @author:  Kishor Mainali
 * @date:  05/01/2021 16:21
 * @email:  mainalikishor@outlook.com
 *
 */

@AndroidEntryPoint
class ShoppingItemFragment @Inject constructor(
    val shoppingItemAdapter: ShoppingItemAdapter,
    var viewModel: ShoppingViewModel? = null

) : Fragment() {

    private var _binding: FragmentShoppingItemBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            viewModel ?: ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingItemBinding.inflate(inflater, container, false)

        subscribeToObservers()
        setUpRecyclerView()


        binding.fabAddShoppingItem.setOnClickListener {
            findNavController().navigate(
                ShoppingItemFragmentDirections.actionShoppingItemFragmentToAddShoppingItemFragment()
            )
        }

        return binding.root
    }


    private val itemTouchCallBack =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = true


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.layoutPosition
                val item = shoppingItemAdapter.shoppingItems[position]
                viewModel?.deleteShoppingItem(item)
                Snackbar.make(binding.root, "Successfully Deleted Item", Snackbar.LENGTH_LONG)
                    .apply {
                        setAction("Undo") {
                            viewModel?.insertShoppingItemIntoDb(item)
                        }
                        show()
                    }

            }


        }

    private fun subscribeToObservers() {
        var priceText: String? = null
        viewModel?.shoppingItems?.observe(viewLifecycleOwner, {
            shoppingItemAdapter.shoppingItems = it
        })
        viewModel?.totalPrice?.observe(viewLifecycleOwner, {
            val price = it ?: 0f
            priceText = "Total Price: Rs. $price"

        })
        binding.tvShoppingItemPrice.text = priceText
    }


    private fun setUpRecyclerView() {

        binding.rvShoppingItems.apply {
            adapter = shoppingItemAdapter
            layoutManager = LinearLayoutManager(requireContext())
            ItemTouchHelper(itemTouchCallBack).attachToRecyclerView(this)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}