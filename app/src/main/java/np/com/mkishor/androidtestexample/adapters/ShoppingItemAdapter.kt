package np.com.mkishor.androidtestexample.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import np.com.mkishor.androidtestexample.data.local.ShoppingItem
import np.com.mkishor.androidtestexample.databinding.ItemShoppingBinding
import javax.inject.Inject


/**
 * @author:  Kishor Mainali
 * @date:  07/01/2021 12:11
 * @email:  mainalikishor@outlook.com
 *
 */
class ShoppingItemAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<ShoppingItemAdapter.ShoppingViewHolder>() {


    private val diffCallback = object : DiffUtil.ItemCallback<ShoppingItem>() {
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean =
            oldItem == newItem

    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var shoppingItems: List<ShoppingItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder =
        ShoppingViewHolder.from(parent)

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        var item = shoppingItems[position]
        holder.binding.apply {
            glide.load(item.imageUrl).into(this.ivShoppingImage)

            this.root.setOnClickListener {
                onItemClickListener?.let {
                    it(item.imageUrl)
                }
            }
        }

    }

    override fun getItemCount(): Int = shoppingItems.size


    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemCLickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }


    class ShoppingViewHolder(val binding: ItemShoppingBinding) :
        RecyclerView.ViewHolder(binding.root) {


        companion object {
            fun from(view: ViewGroup): ShoppingViewHolder {
                val layoutInflater = LayoutInflater.from(view.context)
                val binding = ItemShoppingBinding.inflate(layoutInflater, view, false)
                return ShoppingViewHolder(binding)
            }
        }
    }


}