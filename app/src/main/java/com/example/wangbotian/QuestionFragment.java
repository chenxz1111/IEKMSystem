package com.example.wangbotian;

import android.os.Bundle;
import android.view.LayoutInflater;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

import cn.jiguang.imui.messages.*;

public class QuestionFragment extends Fragment implements View.OnClickListener {

    MessageList messageList;
    MsgListAdapter adapter;
    Button sendButton;
    TextInputEditText sendText;

    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question, container, false);

        sendButton = v.findViewById(R.id.send_button);
        sendButton.setOnClickListener(this);
        sendText = v.findViewById(R.id.send_text);

        messageList = v.findViewById(R.id.msg_list);
        MsgListAdapter.HoldersConfig holdersConfig = new MsgListAdapter.HoldersConfig();
        adapter = new MsgListAdapter<>("0", holdersConfig , null);
        messageList.setAdapter(adapter);
        adapter.addToStart(new Message("请在下方输入需要询问的问题", 0), true);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_button:
                String question = sendText.getText().toString();
                if (!question.equals("")){
                    adapter.addToStart(new Message(question, 1), true);
                    sendText.setText("");
                    adapter.addToStart(new Message(replyQuestion(question), 2), true);
                }

        }
    }

    private String replyQuestion(String question){
          return "知道了";
    }

}