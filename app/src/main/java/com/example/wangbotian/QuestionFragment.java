package com.example.wangbotian;

import android.os.Bundle;
import android.view.LayoutInflater;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import java.util.Date;

import cn.jiguang.imui.messages.*;

public class QuestionFragment extends Fragment implements View.OnClickListener {

    MessageList messageList;
    MsgListAdapter adapter;
    Button sendButton;

    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_question, container, false);

        sendButton = v.findViewById(R.id.send_button);
        sendButton.setOnClickListener(this);
        messageList = v.findViewById(R.id.msg_list);
        MsgListAdapter.HoldersConfig holdersConfig = new MsgListAdapter.HoldersConfig();
        adapter = new MsgListAdapter<>("0", holdersConfig , null);
        messageList.setAdapter(adapter);
                Message m = new Message("hello", 1);
                Author a = new Author("1", "a", null);
                adapter.addToStart(m, true);
       m = new Message("fuck you dame", 2);
      a = new Author("1", "a", null);
        adapter.addToStart(m, true);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_button:
                Message m = new Message("hello", 1);
                Author a = new Author("1", "a", null);
                adapter.addToStart(m, true);
        }
    }

}