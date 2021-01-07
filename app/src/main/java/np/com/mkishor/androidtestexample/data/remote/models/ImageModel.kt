package np.com.mkishor.androidtestexample.data.remote.models

import com.google.gson.annotations.SerializedName


/**
 * @author:  Kishor Mainali
 * @date:  05/01/2021 12:45
 * @email:  mainalikishor@outlook.com
 *
 */
data class ImageModel(
    @SerializedName("hits")
    val hits: List<ImageResultModel>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalHits")
    val totalHits: Int
)
