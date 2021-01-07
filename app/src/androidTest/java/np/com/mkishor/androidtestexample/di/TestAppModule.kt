package np.com.mkishor.androidtestexample.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import np.com.mkishor.androidtestexample.data.local.ShoppingItemDatabase
import javax.inject.Named


/**
 * @author:  Kishor Mainali
 * @date:  05/01/2021 18:29
 * @email:  mainalikishor@outlook.com
 *
 */
@Module
@InstallIn(ApplicationComponent::class)
object TestAppModule {

    @Provides
    @Named("testDb")
    fun provideInMemoryDb(
        @ApplicationContext context: Context
    ) = Room.inMemoryDatabaseBuilder(context, ShoppingItemDatabase::class.java)
        .allowMainThreadQueries().build()


}