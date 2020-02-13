package us.haodongandyushang.yushangmusic;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.elvishew.xlog.XLog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.haodongandyushang.yushangmusic.Adapters.SearchResultAdapter;
import us.haodongandyushang.yushangmusic.Models.SearchResult;

public class HomeActivity extends AppCompatActivity implements SearchResultAdapter.SearchResultViewHolder.ItemClickCallback {

    private AppBarConfiguration mAppBarConfiguration;
    private SearchView mSearchView;
    private NavController navController;

    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer player;

    @BindView(R.id.music_title)
    TextView musicTitle;
    @BindView(R.id.music_max)
    TextView musicMax;
    @BindView(R.id.music_description)
    TextView musicDescription;
    @BindView(R.id.music_now)
    TextView musicNow;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.playing_title)
    TextView playingTitle;

    @BindView(R.id.playing_img)
    SimpleDraweeView playingImg;

    @BindView(R.id.playing_button)
    ImageButton playButton;

    @BindView(R.id.replay_button)
    ImageButton replayButton;

    @BindView(R.id.pause_button)
    ImageButton pauseButton;

    @BindView(R.id.playing_info)
    NestedScrollView playingInfo;

    BottomSheetBehavior bottomSheetBehavior;

    @BindView(R.id.peek_card)
    CardView peekCard;
    boolean isPlaying = true;
    float total;


    List<ImageButton> imageButtons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) collapsingToolbar.getLayoutParams();


        FloatingActionButton fab = findViewById(R.id.fab);

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
        )
                .setDrawerLayout(drawer)
                .build();


        ButterKnife.bind(this);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        youTubePlayerView = findViewById(R.id.player);

        youTubePlayerView.getYouTubePlayerWhenReady(youTubePlayer -> player = youTubePlayer);
        youTubePlayerView.enableBackgroundPlayback(true);


        bottomSheetBehavior = BottomSheetBehavior.from(playingInfo);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP|
                                AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL|
                                AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS|
                                AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED

                        ); // list other flags here by |
                        collapsingToolbar.setLayoutParams(params);

                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        params.setScrollFlags(
                                AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                                        | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
                        | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED); // list other flags here by |
                        collapsingToolbar.setLayoutParams(params);


                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if (slideOffset > 0.5) {
                    peekCard.setVisibility(View.GONE);
                } else {
                    peekCard.setVisibility(View.VISIBLE);
                    peekCard.setAlpha((0.5f - slideOffset) * 2);
                    XLog.e(Float.toString(peekCard.getAlpha()));
                }
            }
        });

        playingInfo.setVisibility(View.GONE);
        imageButtons = new ArrayList<>();
        imageButtons.add(playButton);
        imageButtons.add(replayButton);
        imageButtons.add(pauseButton);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.app_bar_search).getActionView();

        if (searchManager != null) {
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getComponentName()));
        }

        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                searchView.setIconified(true);
                searchView.setIconified(true);


                Bundle query = new Bundle();

                query.putString("query", s);

                navController.navigate(R.id.nav_home, query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        searchView.setQueryHint("输入你喜欢的音乐");

        MenuItem.OnActionExpandListener expandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when action item collapses
                TransitionManager.beginDelayedTransition(findViewById(R.id.toolbar));
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                TransitionManager.beginDelayedTransition(findViewById(R.id.toolbar));
                return true;  // Return true to expand action view
            }
        };

        // Get the MenuItem for the action item
        MenuItem actionMenuItem = menu.findItem(R.id.app_bar_search);

        actionMenuItem.setOnActionExpandListener(expandListener);
        // Assign the listener to that action item

        // Any other things you have to do when creating the options menu...

        return true;


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        youTubePlayerView.release();
    }

    public String floatSecondToString(float f) {
        int s = (int) f % 60;
        int m = (int) f / 60;
        return pad2(Integer.toString(m)) + ":" + pad2(Integer.toString(s));
    }

    public String pad2(String string) {
        if (string.length() == 1) {
            return "0".concat(string);
        }
        return string;
    }

    @Override
    public void SearchResultClick(SearchResult searchResult) {

        player.loadVideo(searchResult.getVideoId(), 0);
        player.addListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onCurrentSecond(@NotNull YouTubePlayer youTubePlayer, float v) {
                float percentage = (v / total) * 100;
                progressBar.setProgress((int) percentage);
                musicNow.setText(floatSecondToString(v));
            }

            @Override
            public void onVideoDuration(@NotNull YouTubePlayer youTubePlayer, float v) {
                musicMax.setText(floatSecondToString(v));
                total = v;

            }

            @Override
            public void onStateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerState state) {
                hideAllButton();
                switch (state) {
                    case PAUSED:
                        playButton.setVisibility(View.VISIBLE);
                        break;
                    case PLAYING:
                        playingInfo.setVisibility(View.VISIBLE);
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        bottomSheetBehavior.setHideable(false);
                        pauseButton.setVisibility(View.VISIBLE);
                        break;
                    case ENDED:
                        replayButton.setVisibility(View.VISIBLE);
                        break;
                }
                super.onStateChange(youTubePlayer, state);
            }
        });
        musicTitle.setText(searchResult.getTitle());
        musicDescription.setText(searchResult.getDescription());
        playingTitle.setText(searchResult.getTitle());
        playingImg.setImageURI(searchResult.getThumbnail().getUrl());
        channelName.setText(searchResult.getChanneTitle());
        publishAt.setText(String.format("发布于:%s", searchResult.getPublishedAt().split("T")[0]));


    }
    @BindView(R.id.channel_name)
    TextView channelName;
    @BindView(R.id.publish_at)
    TextView publishAt;


    void hideAllButton() {
        for (ImageButton i : imageButtons) {
            i.setVisibility(View.GONE);
        }
    }


    @OnClick(R.id.playing_button)
    void playButtonClick(View v) {
        player.play();

    }

    @OnClick(R.id.pause_button)
    void pauseButtonClick(View v) {
        player.pause();
    }

    @OnClick(R.id.replay_button)
    void replayButtonClick(View v) {
        player.seekTo(0);
        player.play();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
