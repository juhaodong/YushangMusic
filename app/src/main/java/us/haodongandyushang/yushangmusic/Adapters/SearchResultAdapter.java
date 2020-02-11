package us.haodongandyushang.yushangmusic.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import us.haodongandyushang.yushangmusic.Models.SearchResult;
import us.haodongandyushang.yushangmusic.R;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {
    public SearchResultViewHolder.ItemClickCallback activity;

    public static class SearchResultViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_title)
        public TextView title;

        @BindView(R.id.thumbnail)
        public SimpleDraweeView thumbnail;

        @BindView(R.id.item_desc)
        TextView description;

        @BindView(R.id.play_button)
        Button button;

        public SearchResultViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }


        public interface ItemClickCallback{
            public void SearchResultClick(SearchResult searchResult);
        }
    }

    public SearchResultAdapter() {
        this.results = new JsonArray();
    }

    public SearchResultAdapter(JsonArray results) {
        this.results = results;
    }

    public JsonArray results;

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new SearchResultViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        try {
            JsonObject item = results.get(position).getAsJsonObject();
            SearchResult searchResult = new SearchResult(item);
            holder.title.setText(searchResult.getTitle());
            holder.thumbnail.setImageURI(searchResult.getThumbnail().getUrl());
            holder.description.setText(searchResult.getChanneTitle());
            holder.button.setOnClickListener(view -> activity.SearchResultClick(searchResult));

        } catch (JsonIOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public int getItemCount() {
        return results.size();

    }
}
