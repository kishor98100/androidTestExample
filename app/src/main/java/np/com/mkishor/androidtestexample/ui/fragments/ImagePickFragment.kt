package np.com.mkishor.androidtestexample.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import np.com.mkishor.androidtestexample.adapters.ImageAdapter
import np.com.mkishor.androidtestexample.databinding.FragmentImagePickBinding
import np.com.mkishor.androidtestexample.ui.viewmodels.ShoppingViewModel
import np.com.mkishor.androidtestexample.util.Constants.GRID_SPAN_COUNT
import javax.inject.Inject


/**
 * @author:  Kishor Mainali
 * @date:  05/01/2021 16:21
 * @email:  mainalikishor@outlook.com
 *
 */
@AndroidEntryPoint
class ImagePickFragment @Inject constructor(
    val imageAdapter: ImageAdapter
) : Fragment() {


    private var _binding: FragmentImagePickBinding? = null
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

        _binding = FragmentImagePickBinding.inflate(inflater, container, false)

        setUpRecyclerView()

        imageAdapter.setOnItemCLickListener {
            findNavController().popBackStack()
            viewModel.setCurrentImageUrl(it)

        }

        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.rvImages.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(requireContext(), GRID_SPAN_COUNT)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}