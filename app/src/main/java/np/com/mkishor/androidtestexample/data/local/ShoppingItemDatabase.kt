package np.com.mkishor.androidtestexample.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


/**
 * @author:  Kishor Mainali
 * @date:  05/01/2021 11:54
 * @email:  mainalikishor@outlook.com
 *
 */
@Database(
    entities = [ShoppingItem::class],
    version = 1,
    exportSchema = false
)
abstract class ShoppingItemDatabase : RoomDatabase() {
    abstract fun shoppingDao(): ShoppingDao
}