package com.innerken.frontend.Aaden.utils.Alert

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.afollestad.materialdialogs.callbacks.onShow
import com.afollestad.materialdialogs.list.listItems
import us.haodongandyushang.yushangmusic.utils.Alert.AlertCallBack
import java.util.*
import kotlin.concurrent.timerTask

class SweetAlert {
    companion object {
        @JvmStatic
        fun showErrorWithInfo(info: String, context: Context, runnable: Runnable) {
            MaterialDialog(context).show {
                title(text = "Opps")
                message(text = info)
                cancelOnTouchOutside(false)
                onShow { dialog ->
                    run {
                        val timer = Timer()
                        timer.schedule(timerTask { dialog.dismiss() }, 2000)
                    }
                }
                onDismiss {
                    runnable.run();
                }
            }
        }



        @JvmStatic
        fun showErrorWithInfo(info: String, context: Context) {
            MaterialDialog(context).show {
                title(text = "Opps")
                message(text = info)
                onShow { dialog ->
                    run {

                        if (dialog.isShowing) {
                            val timer = Timer()
                            timer.schedule(timerTask { dialog.dismiss() }, 2000)
                        }

                    }
                }
            }
        }

        @JvmStatic
        fun showBottomSheet(info: String, context: Context, callBack: AlertCallBack, rid:Int) {
            MaterialDialog(context,BottomSheet()).show {
                title(text = info)
                listItems( rid) { dialog, index, text ->
                    run {
                        callBack.onCallBack(index);
                    }
                }
            }
        }

        @JvmStatic
        fun showBottomSheet(info: String, context: Context, callBack: AlertCallBack, items: List<String>) {
            MaterialDialog(context,BottomSheet()).show {
                title(text = info)
                listItems(items = items) { dialog, index, text ->
                    run {
                        callBack.onCallBack(index);
                    }
                }
            }
        }
    }

}
