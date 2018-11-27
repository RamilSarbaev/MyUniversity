package com.example.ramil.myuniversity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ramil.myuniversity.databinding.FragmentNewsBinding;
import com.example.ramil.myuniversity.databinding.ListItemNewsBinding;
import com.example.ramil.myuniversity.model.News;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewsFragment extends Fragment {

    private static final String TAG = "NewsFragment";
    private static final String NEWS_CHILD = "news";

    private FragmentNewsBinding fragmentBinding;
    private NewsClickHandlers handlers;

    private LinearLayoutManager mLinearLayoutManager;

    //Firebase vars
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<News, NewsViewHolder> mNewsFirebaseAdapter;

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_news, container, false);

        initNewsRecycler();

        handlers = new NewsClickHandlers(getActivity());

        return fragmentBinding.getRoot();
    }

    private void initNewsRecycler() {
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        fragmentBinding.newsRecyclerView.setLayoutManager(mLinearLayoutManager);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        SnapshotParser<News> parser = new SnapshotParser<News>() {
            @NonNull
            @Override
            public News parseSnapshot(@NonNull DataSnapshot snapshot) {
                News news = snapshot.getValue(News.class);

                if (news != null) {
                    news.setId(snapshot.getKey());
                }

                return news;
            }
        };

        DatabaseReference newsReference = mFirebaseDatabaseReference.child(NEWS_CHILD);

        FirebaseRecyclerOptions<News> options = new FirebaseRecyclerOptions.Builder<News>()
                        .setQuery(newsReference, parser)
                        .build();
        
        mNewsFirebaseAdapter = new FirebaseRecyclerAdapter<News, NewsViewHolder>(options) {
            @NonNull
            @Override
            public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                ListItemNewsBinding binding = DataBindingUtil
                        .inflate(inflater, R.layout.list_item_news, parent, false);

                return new NewsViewHolder(binding);
            }

            @Override
            protected void onBindViewHolder(@NonNull NewsViewHolder holder, int position,
                                            @NonNull News news) {
                Log.i(TAG, "onBindViewHolder: " + news.toString());
                fragmentBinding.newsProgressBar.setVisibility(View.INVISIBLE);

                holder.bind(news, handlers);
            }
        };

        mNewsFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                int messageCount = mNewsFirebaseAdapter.getItemCount();
                int lastVisiblePosition =
                        mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (messageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    fragmentBinding.newsRecyclerView.scrollToPosition(positionStart);
                }
            }
        });

        fragmentBinding.newsRecyclerView.setAdapter(mNewsFirebaseAdapter);
    }

    private static class NewsViewHolder extends RecyclerView.ViewHolder {

        private final ListItemNewsBinding listItemBinding;

        public NewsViewHolder(final ListItemNewsBinding binding) {
            super(binding.getRoot());

            listItemBinding = binding;
        }

        public void bind(News news, NewsClickHandlers handlers) {
            listItemBinding.setNews(news);
            listItemBinding.setHandlers(handlers);
            listItemBinding.executePendingBindings();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mNewsFirebaseAdapter.startListening();
    }

    @Override
    public void onStop() {
        mNewsFirebaseAdapter.stopListening();
        super.onStop();
    }

    public class NewsClickHandlers {

        Context mContext;

        public NewsClickHandlers(Context context) {
            mContext = context;
        }

        public void onNewsClicked(View view, News news) {
            Toast.makeText(mContext, "Link: " + news.getLink(), Toast.LENGTH_SHORT).show();

            // TODO impl-t loading news in the site's WebView by the news link
        }
    }
}
