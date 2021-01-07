package np.com.mkishor.androidtestexample.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import np.com.mkishor.androidtestexample.databinding.ItemImageBinding
import javax.inject.Inject


/**
 * @author:  Kishor Mainali
 * @date:  06/01/2021 12:01
 * @email:  mainalikishor@outlook.com
 *
 */
class ImageAdapter @Inject constructor(private val glide: RequestManager) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {


    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var images: List<String>
        get() = differ.currentList
        set(value) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder.from(parent)

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        var url = images[position]
        holder.binding.apply {
            glide.load(url).into(this.ivShoppingImage)

            this.root.setOnClickListener {
                onItemClickListener?.let {
                    it(url)
                }
            }


        }

    }

    override fun getItemCount(): Int = images.size


    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemCLickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }


    class ImageViewHolder(val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {


        companion object {
            fun from(view: ViewGroup): ImageViewHolder {
                val layoutInflater = LayoutInflater.from(view.context)
                val binding = ItemImageBinding.inflate(layoutInflater, view, false)
                return ImageViewHolder(binding)
            }
        }
    }


}