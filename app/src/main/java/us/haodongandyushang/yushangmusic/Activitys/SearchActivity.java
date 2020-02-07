package us.haodongandyushang.yushangmusic.Activitys;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import us.haodongandyushang.yushangmusic.BaseActivity;
import us.haodongandyushang.yushangmusic.R;

public class SearchActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

        }
    }
}
