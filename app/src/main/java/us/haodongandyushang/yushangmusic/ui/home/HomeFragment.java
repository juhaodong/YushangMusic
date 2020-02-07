package us.haodongandyushang.yushangmusic.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.elvishew.xlog.XLog;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import us.haodongandyushang.yushangmusic.R;
import us.haodongandyushang.yushangmusic.utils.IKRequest;
import us.haodongandyushang.yushangmusic.utils.NetworkUtils;
import us.haodongandyushang.yushangmusic.utils.RequestType;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    @BindView(R.id.searchQuery)
    EditText searchQuery;

    @BindView(R.id.search_button)
    Button search;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);


        search.setOnClickListener(view -> {
            queryForResults(searchQuery.getText().toString());

        });


        return root;
    }

    public void queryForResults(String query) {

        HashMap<String, String> params = new HashMap<>();
        params.put("q", query);
        params.put("part", "snippet");
        params.put("order", "relevance");
        params.put("type", "video");
        params.put("key", "AIzaSyAUTTqf3U41RnP890AbWmJ1MjYMznarjCo");
        IKRequest i = new IKRequest().url("https://www.googleapis.com/youtube/v3/search").data(params).method(RequestType.GET)
                .callback((result -> {

                    JsonObject jsonObject=new JsonParser().parse(result).getAsJsonObject();
                    JsonArray items=jsonObject.getAsJsonArray("items");


                })).bulid();
        NetworkUtils.sendRequest(i);
    }


}