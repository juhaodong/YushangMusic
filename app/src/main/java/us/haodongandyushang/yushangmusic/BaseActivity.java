package us.haodongandyushang.yushangmusic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import us.haodongandyushang.yushangmusic.utils.Alert.LoadingDialog;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    public <T extends FragmentActivity> void gotoActivity(Class<T> clz, Bundle data) {
        Intent intent = new Intent(this, clz);
        intent.putExtras(data);
        startActivity(intent);
    }

    public <T extends Activity> void gotoActivity(Class<T> clz) {
        Intent intent = new Intent(this, clz);
        startActivity(intent);
    }
    LoadingDialog dialog;

    public void showDialog() {
        if (dialog == null) {
            dialog = new LoadingDialog(this, R.style.selectorDialog);
//            dialog = LoadingDialog.getInstance(this,R.style.selectorDialog);
        }
        dialog.showDialog(null);
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }



}
