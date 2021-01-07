package np.com.mkishor.androidtestexample

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication


/**
 * @author:  Kishor Mainali
 * @date:  05/01/2021 18:25
 * @email:  mainalikishor@outlook.com
 *
 */
class HiltTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}