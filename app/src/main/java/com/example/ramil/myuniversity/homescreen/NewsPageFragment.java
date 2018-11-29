package com.example.ramil.myuniversity.homescreen;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.ramil.myuniversity.R;
import com.example.ramil.myuniversity.databinding.FragmentNewsPageBinding;

public class NewsPageFragment extends Fragment {

    private static final String ARG_URL = "news_page_url";

    private String mUrl;
    private FragmentNewsPageBinding mBinding;
    private WebView mWebView;
    private ProgressBar mProgressBar;

    public static NewsPageFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);

        NewsPageFragment fragment = new NewsPageFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrl = getArguments().getString(ARG_URL);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_news_page, container, false);

        initWebView();

        return mBinding.getRoot();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        mProgressBar = mBinding.progressBar;
        mProgressBar.setMax(100);

        mWebView = mBinding.webView;
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
        });
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(mUrl);
    }
}
