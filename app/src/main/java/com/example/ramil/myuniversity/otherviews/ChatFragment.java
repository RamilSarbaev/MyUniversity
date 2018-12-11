package com.example.ramil.myuniversity.otherviews;

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
import com.example.ramil.myuniversity.databinding.FragmentChatBinding;
import com.example.ramil.myuniversity.databinding.ListItemMessageBinding;
import com.example.ramil.myuniversity.homescreen.ProfileActivity;
import com.example.ramil.myuniversity.model.Message;
import com.example.ramil.myuniversity.model.User;
import com.example.ramil.myuniversity.utils.DateUtil;
import com.example.ramil.myuniversity.utils.FirebaseUtil;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ChatFragment extends Fragment {

    private static final String TAG = "ChatFragment";
    private static final String ARG_USER = "user";
    private static final String CHAT_CHILD = "chat";
    private static final String MESSAGES_CHILD = "messages";

    private FragmentChatBinding mFragmentBinding;
    private ChatHandlers mChatHandlers;
    private LinearLayoutManager mLinearLayoutManager;

    private User mUser;

    private DatabaseReference mDatabaseReference;
    private FirebaseRecyclerAdapter<Message, MessageViewHolder> mMessageFirebaseAdapter;
    private FirebaseUtil mFirebaseUtil;

    public static ChatFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);

        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = getUserFromArgs();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mFragmentBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_chat, container, false);
        mChatHandlers = new ChatHandlers();
        mFragmentBinding.setHandlers(mChatHandlers);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseUtil = new FirebaseUtil(getActivity());
        mFirebaseUtil.addUserToGroupChat(mUser);

        initRecyclerView();

        return mFragmentBinding.getRoot();
    }

    private User getUserFromArgs() {
        Bundle args = getArguments();
        if (args != null) {
            return args.getParcelable(ARG_USER);
        } else {
            return null;
        }
    }

    private void initRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setStackFromEnd(true);
        mFragmentBinding.messagesRecyclerView.setLayoutManager(mLinearLayoutManager);

        SnapshotParser<Message> parser = new SnapshotParser<Message>() {
            @NonNull
            @Override
            public Message parseSnapshot(@NonNull DataSnapshot snapshot) {
                return snapshot.getValue(Message.class);
            }
        };

        Query query = mDatabaseReference
                .child(CHAT_CHILD)
                .child(mUser.getGroup())
                .child(MESSAGES_CHILD);

        FirebaseRecyclerOptions<Message> options = new FirebaseRecyclerOptions.Builder<Message>()
                .setQuery(query, parser)
                .build();

        mMessageFirebaseAdapter = new FirebaseRecyclerAdapter<Message,
                MessageViewHolder>(options) {
            @NonNull
            @Override
            public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                ListItemMessageBinding binding = DataBindingUtil
                        .inflate(inflater, R.layout.list_item_message, parent, false);

                return new MessageViewHolder(binding);
            }

            @Override
            protected void onBindViewHolder(@NonNull final MessageViewHolder holder, int position,
                                            @NonNull final Message message) {
                Log.i(TAG, "onBindViewHolder: " + message.toString());

                DatabaseReference messageReference = FirebaseDatabase.getInstance().getReference();
                messageReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = mFirebaseUtil.getUser(dataSnapshot, message.getUid());
                        holder.bind(message, user, mChatHandlers);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w(TAG, "Failed to read value.", databaseError.toException());
                    }
                });
            }

            @Override
            public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
                super.onAttachedToRecyclerView(recyclerView);
                mFragmentBinding.chatProgressBar.setVisibility(View.INVISIBLE);
            }
        };

        mMessageFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                int messagesCount = mMessageFirebaseAdapter.getItemCount();
                int lastVisiblePosition =
                        mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (messagesCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    mFragmentBinding.messagesRecyclerView.scrollToPosition(positionStart);
                }
            }
        });

        mFragmentBinding.messagesRecyclerView.setAdapter(mMessageFirebaseAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mMessageFirebaseAdapter.startListening();
    }

    @Override
    public void onStop() {
        mMessageFirebaseAdapter.stopListening();
        super.onStop();
    }

    private static class MessageViewHolder extends RecyclerView.ViewHolder {

        private ListItemMessageBinding listItemBinding;

        public MessageViewHolder(ListItemMessageBinding binding) {
            super(binding.getRoot());

            listItemBinding = binding;
        }

        public void bind(Message message, User user, ChatHandlers handlers) {
            listItemBinding.setMessage(message);
            listItemBinding.setUser(user);
            listItemBinding.setHandlers(handlers);
            listItemBinding.executePendingBindings();
        }
    }

    public class ChatHandlers {

        public void onSendClicked(View view) {
            String text = mFragmentBinding.messageEditText.getText().toString();

            if (text.isEmpty())
                return;

            String date = DateUtil.getCurrentDate();

            Message message = new Message(mUser.getUid(), text, date);

            mDatabaseReference.child(CHAT_CHILD)
                    .child(mUser.getGroup())
                    .child(MESSAGES_CHILD)
                    .push().setValue(message);

            mFragmentBinding.messageEditText.setText("");
        }

        public void onUsersPhotoClicked(View view, User user) {
            startActivity(ProfileActivity.newIntent(getActivity(), user));
        }
    }
}
