package com.example.tacademy.recyclerviewtest;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Tacademy on 2017-02-02.
 */

public class PostHolder extends RecyclerView.ViewHolder {

    TextView msg;
    TextView txt_left, txt_right;
    LinearLayout left_container, right_container;
    int type;
    public PostHolder(View itemView) {
        super(itemView);
        msg = (TextView)itemView.findViewById(R.id.msg);
        // 채팅UI
        txt_left = (TextView)itemView.findViewById(R.id.txt_left);
        txt_right = (TextView)itemView.findViewById(R.id.txt_right);
        left_container = (LinearLayout)itemView.findViewById(R.id.left_container);
        right_container = (LinearLayout)itemView.findViewById(R.id.right_container);
    }
    // 데이터를 설정하는 부분
    public void bindOnPost(String text){
        msg.setText(text);
    }

    public void bindOnPost(String text, int type){
        if( type == 1 ){ // me
            right_container.setVisibility(View.VISIBLE);
            left_container.setVisibility(View.GONE);
            txt_right.setText(text);
        }else{ // you
            left_container.setVisibility(View.VISIBLE);
            right_container.setVisibility(View.GONE);
            txt_left.setText(text);
        }
    }

}
