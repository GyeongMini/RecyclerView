package com.example.tacademy.recyclerviewtest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.tacademy.recyclerviewtest.model.ChatMessage;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        onBasic();
        // 일단 글쓰기
        // =================================
        // 1. firebase의 database 객체 획득
        firebaseDatabase  = FirebaseDatabase.getInstance();
        // 2. database access할 수 있는 참조값(root) 획득
        databaseReference = firebaseDatabase.getReference();
        // 3. insert 적절한 경로( 가지를 하나 만들어서 )에다 메시지 입력
//        databaseReference.child("chat").push().setValue(new ChatMessage("삼다수","하이"))
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if( task.isSuccessful() ) {
//                            Log.i("chat", "등록완료");
//                        }else{
//                            Log.i("chat", "등록실패");
//                        }
//                    }
//                });
        // 4. select 등록된 데이터 가져오기
        databaseReference.child("chat").addChildEventListener(new ChildEventListener(){
            // 아이템을 검색하거나, 추가될때 호출( select, insert)
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // s는 전번 데이터의 키를 가르킨다(링크드 리스트)
//                Log.i("chat", dataSnapshot.toString()+"/"+s);
                // 파싱 : 데이터를 ChatMessage 틀에 넣어준다.
                ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                Log.i("CHAT",chatMessage.getUsername());
            }
            // 아이템의 변화가 감지되면 호출 ( update )
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }
            // 아이템이 삭제되면 호출( delete )

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }
            // 아이템의 순서가 변경되면 호출

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // =================================
    }

    public void onBasic() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
}
