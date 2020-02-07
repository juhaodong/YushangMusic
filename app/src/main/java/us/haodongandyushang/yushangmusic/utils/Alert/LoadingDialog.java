package us.haodongandyushang.yushangmusic.utils.Alert;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import us.haodongandyushang.yushangmusic.R;


public class LoadingDialog extends Dialog implements DialogInterface.OnDismissListener {
    private LayoutInflater inflater;
    private OnDismissListener listener;

    public LoadingDialog(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        inflater = LayoutInflater.from(context);
    }


    //private int[] pageID = {R.drawable.loding0, R.drawable.loding1, R.drawable.loding2, R.drawable.loding3};

    public void showDialog(OnDismissListener listener) {
        if (this != null && this.isShowing()) {
            dismiss();
        }


        int current_page = 0;
        View contentview = inflater.inflate(R.layout.item_progress, null);
        setContentView(contentview);
        setCancelable(false);
//        imageView = (ImageView) contentview.findViewById(R.id.loading_imag);
        setOnDismissListener(this);
        this.listener = listener;
        show();
//        handler.sendEmptyMessage(0);
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
//        handler.removeCallbacks(runnable);
        if (listener != null) {
            listener.onDismiss(dialog);
        }
    }
}
