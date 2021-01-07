package np.com.mkishor.androidtestexample.util


/**
 * @author:  Kishor Mainali
 * @date:  05/01/2021 16:26
 * @email:  mainalikishor@outlook.com
 *
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set


    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content

}