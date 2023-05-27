package com.example.myapplicationforreal;

import static android.content.Intent.getIntent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.myapplicationforreal.databinding.ActivityChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.UUID;


public class HomePageFragment extends Fragment {

    ActivityChatBinding binding;
    String recieverId;
    DatabaseReference databaseReferenceSender,databaseReferenceReciever;
    String senderRoom,recieverRoom;
    MessageAdapter messageAdapter;
    ArrayList<String> s;
    ArrayAdapter arrayAdapter;
    int n=0;
    private static final String TAG = "Swipe Position";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  binding.getRoot();

        s=new ArrayList<String>();
        s.add("one");
        s.add("two");
        s.add("three");
        s.add("four");
        s.add("five");

        SwipeFlingAdapterView swipeFlingAdapterView=(SwipeFlingAdapterView) v.findViewById(R.id.card);
        arrayAdapter=new ArrayAdapter<String>(getActivity(),R.layout.details, R.id.textView,s);
        swipeFlingAdapterView.setAdapter(arrayAdapter);
        swipeFlingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter()
            {
                s.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object o)
            {
                Toast.makeText(getActivity(), "Left is swiped", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Left Swipe");
            }

            // binding.sendMessage.setOnClickListener(new View.OnClickListener()

            @Override
            public void onRightCardExit(Object o)
            {
                Toast.makeText(getActivity(), "Right is swiped", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Right Swipe");
                binding=ActivityChatBinding.inflate(getLayoutInflater());

                senderRoom= FirebaseAuth.getInstance().getUid()+recieverId;
                recieverRoom=recieverId + FirebaseAuth.getInstance().getUid();

                messageAdapter=new MessageAdapter(getActivity());
                binding.recycler.setAdapter(messageAdapter);
                binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

                databaseReferenceSender= FirebaseDatabase.getInstance().getReference("chats").child(senderRoom);
                databaseReferenceReciever= FirebaseDatabase.getInstance().getReference("chats").child(recieverRoom);

                String message=binding.messageEd.getText().toString();
                if (message.trim().length()>0){
                    sendMessage(message);

                }
            }
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    messageAdapter.clear();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        MessageModel messageModel=dataSnapshot.getValue(MessageModel.class);
                        messageAdapter.add(messageModel);

                    }
                }
            private void sendMessage(String message) {
                String messageId= UUID.randomUUID().toString();
                MessageModel messageModel=new MessageModel(messageId, FirebaseAuth.getInstance().getUid(),message);
                messageAdapter.add(messageModel);
                databaseReferenceSender
                        .child(messageId)
                        .setValue(messageModel);
                databaseReferenceReciever
                        .child(messageId)
                        .setValue(messageModel);
            }

            @Override
            public void onAdapterAboutToEmpty(int i) {

            }

            @Override
            public void onScroll(float v) {

            }
        });

        return v;
    }
}