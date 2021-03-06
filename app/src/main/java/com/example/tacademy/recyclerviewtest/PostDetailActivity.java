package com.example.tacademy.recyclerviewtest;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.recyclerviewtest.model.Comment;
import com.example.tacademy.recyclerviewtest.model.Post;
import com.example.tacademy.recyclerviewtest.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * 리뷰 클릭시 상세보기로 이동 하여 정보를 보여준다.
 * 글 상세보기 + 댓글 달기( 글 하나의 )
 */

public class PostDetailActivity extends BaseActivity {
    String postKey;
    EditText comment_input;
    RecyclerView detail_rv;
    DatabaseReference postReference, commentReference;
    ImageView profile, star;
    TextView nickName, star_count, title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        // ParentFragment 에서 보낸 키를 여기서 뽑는다.
        postKey = getIntent().getStringExtra("KEY");
        if (postKey == null) {
            Toast.makeText(this, "네트워크가 지연되고 있습니다. 잠시후에 다시 이용해 주세요...", Toast.LENGTH_SHORT).show();
            finish();
        }
        // 데이터 획득   - >    참조 획득
        postReference =
                FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child("posts")
                        .child(postKey);
        // 댓글 가지     - >    참조 획득
        commentReference = FirebaseDatabase.getInstance().getReference().child("post-comments").child(postKey);
        // 글 세팅
        comment_input   = (EditText) findViewById(R.id.comment_input);
        detail_rv       = (RecyclerView) findViewById(R.id.detail_rv);
        profile         = (ImageView) findViewById(R.id.profile);
        star            = (ImageView) findViewById(R.id.star);
        nickName        = (TextView) findViewById(R.id.nickName);
        star_count      = (TextView) findViewById(R.id.star_count);
        title           = (TextView) findViewById(R.id.title);
        content         = (TextView) findViewById(R.id.content);
        // 댓글 입력 받게 처리
        // 댓글을 쓰면 밑으로 리스팅(recyclerView), 아답터, 홀더, 커스텀뷰, 할일이 많네
        detail_rv.setLayoutManager(new LinearLayoutManager(this));
        showTop();
    }
    // 댓글 뿌리기
    // 댓글 입력 이벤트
    public void onComment(View view){
        final String comment_input_str = comment_input.getText().toString();
        if(TextUtils.isEmpty(comment_input_str) ) {
            comment_input.setError("댓글값이 없습니다");
            return ;
        }
        // 사용자 유효한지 체크
        FirebaseDatabase.getInstance().getReference().child("users")
                .child(getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        Comment comment = new Comment(getUid(), comment_input_str, user.getId());
                        // 글입력
                        commentReference.push().setValue(comment);
                        comment_input.setText("");

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }



    // 등록과 해제를 같이 해야 하기때문에 전역으로 올린다.
    ValueEventListener valueEventListener;
    ChildEventListener childEventListener;
    ComAdapter comAdapter;
    @Override
    protected void onStart() {
        super.onStart();
        // 이벤트 등록
        // 1. 작성자의 글을 가져온다.(1개)
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Post model = dataSnapshot.getValue(Post.class);
                nickName.setText(model.getAuthor());
                star_count.setText(""+model.getStar_count());
                title.setText(model.getTitle());
                content.setText(model.getContent());

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        postReference.addValueEventListener(valueEventListener);
        // 댓글을 뿌리는 아답터를 생성하여 리사이클 뷰에 세팅
        comAdapter = new ComAdapter(this, commentReference);
        detail_rv.setAdapter(comAdapter);
    }
    @Override
    protected void onStop() {
        super.onStop();
        // 이벤트 해제
        // 내글에 대한 이벤트
        if( valueEventListener != null){
            postReference.removeEventListener(valueEventListener);
        }
        // 댓글에 대한 이벤트
        comAdapter.closeListener();
    }

    // 뷰홀더 안에서만 쓰기위해서 안에서만 넣음
    class  ComViewHolder extends RecyclerView.ViewHolder{

        ImageView profile;
        TextView uid, comment;

        public ComViewHolder(View itemView) {
            super(itemView);

            profile = (ImageView)itemView.findViewById(R.id.profile);
            uid     = (TextView)itemView.findViewById(R.id.uid);
            comment = (TextView)itemView.findViewById(R.id.comment);
        }
    }
    // 아답터
    class ComAdapter extends RecyclerView.Adapter<ComViewHolder>{
        ArrayList<Comment> comments = new ArrayList<Comment>();
        DatabaseReference root;
        // 콘텍스트를 만들어줌
        Context context;
        public ComAdapter(Context context, DatabaseReference root){
            this.context = context;
            this.root = root;
            // 데이터 획득!!
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Comment comment = dataSnapshot.getValue(Comment.class);
                    comments.add(comment);
                    // x번째 데이터가 추가 되어 있다.
                    notifyItemInserted(comments.size()-1);

                }


                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            // 등록
            root.addChildEventListener(childEventListener);
        }
        public void closeListener(){
            root.removeEventListener(childEventListener); // 이벤트 해제
        }

        @Override
        public ComViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view =
            LayoutInflater.from(context)
                    .inflate(R.layout.cell_comment_layout,parent,false);
            return new ComViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ComViewHolder holder, int position) {
            // 데이터 획득
            Comment comment = comments.get(position);
            // 작성자
            holder.uid.setText(comment.getAuthor());
            // 댓글
            holder.comment.setText(comment.getComment());
        }

        @Override
        public int getItemCount() {
            return comments.size();
        }
    }

    public void showTop() {

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

