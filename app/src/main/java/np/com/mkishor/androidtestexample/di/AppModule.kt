package np.com.mkishor.androidtestexample.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import np.com.mkishor.androidtestexample.R
import np.com.mkishor.androidtestexample.data.local.ShoppingDao
import np.com.mkishor.androidtestexample.data.local.ShoppingItemDatabase
import np.com.mkishor.androidtestexample.data.remote.PixalBayApi
import np.com.mkishor.androidtestexample.data.repositories.ShoppingRepositoryImpl
import np.com.mkishor.androidtestexample.repositories.ShoppingRepository
import np.com.mkishor.androidtestexample.util.Constants.BASE_URL
import np.com.mkishor.androidtestexample.util.Constants.DATABASE_NAME
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 * @author:  Kishor Mainali
 * @date:  05/01/2021 12:54
 * @email:  mainalikishor@outlook.com
 *
 */
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {


    /**
     * ROOM DATABASE
     * */

    @Singleton
    @Provides
    fun provideShoppingDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ShoppingItemDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration().build()


    @Singleton
    @Provides
    fun provideShoppingDao(database: ShoppingItemDatabase) = database.shoppingDao()


    /**
     *
     * RETROFIT
     *
     * */

    @Singleton
    @Provides
    fun providePixalBayApi(): PixalBayApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PixalBayApi::class.java)


    /**
     * SHOPPING REPOSITORY
     * */

    @Singleton
    @Provides
    fun provideShoppingRepository(dao: ShoppingDao, api: PixalBayApi): ShoppingRepository =
        ShoppingRepositoryImpl(dao, api)


    /**
     * GLIDE
     * */

    @Singleton
    @Provides
    fun provideGlideInstance(@ApplicationContext context: Context): RequestManager =
        Glide.with(context).setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_image).error(R.drawable.ic_image)
        )


}