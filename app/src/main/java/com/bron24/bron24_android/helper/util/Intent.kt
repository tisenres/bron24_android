import android.content.Context
import android.content.Intent
import android.net.Uri
import com.bron24.bron24_android.components.toast.ToastManager
import com.bron24.bron24_android.components.toast.ToastType

fun openDialer(context: Context, phoneNumber: String) {
    val uri = Uri.parse("tel:" + phoneNumber)
    val i = Intent(Intent.ACTION_DIAL, uri)
    try {
        context.startActivity(i)
    } catch (s: Exception) {
        ToastManager.showToast("An error occurred", ToastType.ERROR)
    }
}
