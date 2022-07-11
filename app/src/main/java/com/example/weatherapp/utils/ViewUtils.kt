import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.show(text:String, actionText:String, action: View.OnClickListener){
    Snackbar.make(this, text, Snackbar.LENGTH_LONG).setAction(actionText,action).show()
}