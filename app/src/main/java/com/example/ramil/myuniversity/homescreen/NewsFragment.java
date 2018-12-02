package com.example.ramil.myuniversity.homescreen;

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

import com.example.ramil.myuniversity.R;
import com.example.ramil.myuniversity.databinding.FragmentNewsBinding;
import com.example.ramil.myuniversity.databinding.ListItemNewsBinding;
import com.example.ramil.myuniversity.model.News;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class NewsFragment extends Fragment {

    private static final String TAG = "NewsFragment";
    private static final String NEWS_CHILD = "news";

    private FragmentNewsBinding fragmentBinding;
    private NewsClickHandlers handlers;
    private NewsCallbacks mCallbacks;

    private LinearLayoutManager mLinearLayoutManager;

    //Firebase vars
    private FirebaseRecyclerAdapter<News, NewsViewHolder> mNewsFirebaseAdapter;

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    public interface NewsCallbacks {
        void onNewsClicked(String url);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (NewsCallbacks) context;
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

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    private void initNewsRecycler() {
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        fragmentBinding.newsRecyclerView.setLayoutManager(mLinearLayoutManager);

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

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child(NEWS_CHILD)
                .limitToLast(10);

        FirebaseRecyclerOptions<News> options = new FirebaseRecyclerOptions.Builder<News>()
                        .setQuery(query, parser)
                        .build();

        // TODO impl-t partial loading

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
            mCallbacks.onNewsClicked(news.getLink());
        }
    }
}
