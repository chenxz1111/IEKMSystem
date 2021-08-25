package com.example.wangbotian;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

public class QuestionFragment extends Fragment implements View.OnClickListener{

    MaterialCardView card_question;

    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question, container, false);
        card_question = v.findViewById(R.id.card_question);
        card_question.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_question:
                Intent intent = new Intent();
                intent.setClass(this.getActivity(), QuestionActivity.class);
                startActivity(intent);
                break;
        }
    }

}