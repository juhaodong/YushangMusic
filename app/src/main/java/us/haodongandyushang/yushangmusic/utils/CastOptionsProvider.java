package us.haodongandyushang.yushangmusic.utils;

import android.content.Context;

import com.google.android.gms.cast.framework.OptionsProvider;
import com.google.android.gms.cast.framework.SessionProvider;

import java.util.List;


public class CastOptionsProvider implements OptionsProvider {
    public com.google.android.gms.cast.framework.CastOptions getCastOptions(Context appContext) {

        // Register you custom receiver on the Google Cast SDK Developer Console to get this ID.
        String receiverId = "59AC7B63";

        return new com.google.android.gms.cast.framework.CastOptions.Builder()
                .setReceiverApplicationId(receiverId)
                .build();
    }

    public List<SessionProvider> getAdditionalSessionProviders(Context context) {
        return null;
    }


}
