package com.example.wangbotian;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wenchao.cardstack.CardStack;


public class EntityExamFragment extends Fragment {

    CardsDataAdapter cardAdapter;
    CardStack cardStack;

    public EntityExamFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_entity_exam, container, false);

        cardStack = v.findViewById(R.id.card_stack);
        cardStack.setContentResource(R.layout.exam_card);
        cardStack.setStackMargin(20);
        JSONArray dataArray = JSON.parseArray(((EntityActivity) this.getActivity()).entityExam.getString("data"));
        cardAdapter = new CardsDataAdapter(this.getActivity().getApplicationContext(),0, 0);
        for (int i = 0; i < dataArray.size(); i++){
            if (dataArray.getString(i).indexOf("A.") > 0)
            cardAdapter.add(dataArray.getString(i));
        }
        cardStack.setAdapter(cardAdapter);
        return v;
    }
}