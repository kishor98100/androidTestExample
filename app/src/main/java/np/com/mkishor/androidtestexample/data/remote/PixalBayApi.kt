package np.com.mkishor.androidtestexample.data.remote

import np.com.mkishor.androidtestexample.BuildConfig
import np.com.mkishor.androidtestexample.data.remote.models.ImageModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * @author:  Kishor Mainali
 * @date:  05/01/2021 12:50
 * @email:  mainalikishor@outlook.com
 *
 */
interface PixalBayApi {


    @GET("/api/")
    suspend fun searchForImage(
        @Query("key") apiKey: String = BuildConfig.PIXAL_BAY_API_KEY,
        @Query("q") searchQuery: String,
        @Query("image_type") imageType: String = "photo"
    ): Response<ImageModel>

}