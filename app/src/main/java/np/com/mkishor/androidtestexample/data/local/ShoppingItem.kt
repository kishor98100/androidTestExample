package np.com.mkishor.androidtestexample.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * @author:  Kishor Mainali
 * @date:  05/01/2021 11:49
 * @email:  mainalikishor@outlook.com
 *
 */
@Entity(tableName = "shopping_items")
data class ShoppingItem(
    var name: String, var amount: Int,
    var price: Float,
    var imageUrl: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)