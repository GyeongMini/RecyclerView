package com.example.tacademy.recyclerviewtest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import java.util.ArrayList;

/**
 *  백업용 클레스 이다.
 */
public class Main3Activity extends AppCompatActivity {

    RecyclerView recyclerview;
    AutoCompleteTextView msg_input;
    ArrayList<String> arrayList = new ArrayList<String>();
    String data[] =
            {
                    "1 맨체스터 유나이티드(이하 맨유)는 2일(이하 한국시간) 오전 5시 잉글랜드 맨체스터 올드 트래포드에서 열린 헐시티와의 '2016~17 시즌 잉글리시 프리미어리그' 23라운드 홈 경기에서 0-0 무승부를 거뒀다.",
                    "2 이날 무승부로 맨유는 11승9무3패를 기록, 승점 1점을 추가하는데 그쳤다. 승점 42점으로 리그 6위 유지. 같은 날 웨스트햄을 4-0으로 대파한 5위 맨체스터 시티(승점 46점)와의 승점 차는 4점으로 벌어졌다.",
                    "3 이날 무승부로 맨유는 최근 리그서 14경기 연속 무패 행진을 이어갔다. 하지만 최근 3경기 연속 무승부(리버풀-스토크시티-헐시티)를 거두며 만족스럽지는 못한 모습.",
                    "4 반면 최하위였던 헐시티 역시 승점 1점을 추가, 4승5무14패(승점 17점)를 올리며 선더랜드(승점 16점)를 20위로 내려앉히고 19위로 올라섰다.",
                    "5 이날 맨유는 즐라탄 이브라히모비치와 래쉬포드, 포그바를 비롯해 에레라, 므키타리안, 발렌시아, 캐릭, 브린트, 로호, 필 존스, 데헤아 골키퍼를 선발로 내세웠다. 이에 맞서 헐시티는 니아세와 마르코비치, 메일러, 에반드로 등이 선발 출전했다."
            };

    int index[] = new int[1000];


    MyAdapter myAdapter = new MyAdapter();
    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (String s : data) {
            arrayList.add(s);
        }


        // 콤퍼넌트 리소스를 자바 객체로 연결
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        msg_input = (AutoCompleteTextView) findViewById(R.id.msg_input);

        // 자동 완성 기능 추가
        msg_input.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, autoKeyword));

        // 뷰의 스타일(매니저) 정의, 선형, 그리드형, 높이가 불규칙한 그리드 형
        // 선형 그리드
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);

        // 고정 크기 그리드
        gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
//        recyclerview.setLayoutManager(gridLayoutManager);

        //가변 그리드
        staggeredGridLayoutManager
                = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
//        recyclerview.setLayoutManager(staggeredGridLayoutManager);

        // 채팅 관련
        // 데이터를 뒤집어서 표현할때(최신순), 서버에서온 데이터가 최신순이면 피요없음
        // FB로 데이터를 받아올때는 최싯누 처리라 곤란하다 ?
        // 마지막 데이터가 보이게 (staggeredGridLayoutManager 미지원)
        //데이터가 동적으로 바뀌면 적용이 안됨. 새로 세팅해야 함.
        linearLayoutManager.setStackFromEnd(true);
//        gridLayoutManager.setStackFromEnd(true);
        //특정 위치로 맞추기
        linearLayoutManager.scrollToPosition(data.length - 1);

        // 데이터 공급원인 아답터 연결
        recyclerview.setAdapter(myAdapter);
    }

    // 전송 버튼 누르면 호출
    public void onSend(View view) {
//        msg_input.setText("이상");

        // 1. 입력 데이터 추출
        String msg = msg_input.getText().toString();
        // 2. 서버 전송 => 여기서는 데이터 직접 추가
        arrayList.add(msg);
        index[arrayList.size()-1]=1;
        // 3. 화면 갱신
        myAdapter.notifyDataSetChanged();
        // 4. 리스트 가장 마지막으로 갱신
//        staggeredGridLayoutManager.scrollToPosition(arrayList.size() - 1);
        linearLayoutManager.scrollToPosition(arrayList.size() - 1);
        // 5. 입력값 제거
        msg_input.setText("");
        // 6. 키보드 내리기
        closeKeyword(this, msg_input);
    }

    public void closeKeyword(Context context, EditText editText) {
        InputMethodManager methodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        methodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);

    }


    // 아답터
    class MyAdapter extends RecyclerView.Adapter {

        // 데이터의 개수
        @Override
        public int getItemCount() {
            return arrayList.size(); //data.length;
        }

        // ViewHoledr 생성
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // xml -> view
            View itemView =
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.sendbird_view_group_user_message, parent, false);
//                            .inflate(R.layout.cell_cardview_layout, parent, false);

            return new PostHolder(itemView);
        }

        // ViewHoledr에 데이터를 설정(바인딩) 한다.
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            // 보이고자 하는 셀의 내용을 설정한다, 이안에 이벤트 등 다넣는다?
//            ((PostHolder) holder).bindOnPost(arrayList.get(position));   //data[position]);
            ((PostHolder) holder).bindOnPost(arrayList.get(position), index[position]);//((int) Math.random() * 10) % 2);   //data[position]);
        }

    }

    // 추천 키워드, 자동완성 UI를 직접 구성하거나, 내용을 가변시킬수 있다.
//    class MyKeywordAdaper extends BaseAdapter {
//        @Override
//        public int getCount() {
//            return 0;
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            return null;
//        }
//    }

    String autoKeyword[] =
            {
                    "이상해씨",
                    "이상해풀",
                    "이상해꽃",
                    "파이리",
                    "리자드",
                    "리자몽",
                    "꼬부기",
                    "어니부기",
                    "거북왕",
                    "캐터피",
                    "단데기",
                    "버터플",
                    "뿔충이",
                    "딱충이",
                    "독침붕",
                    "구구",
                    "피죤",
                    "피죤투",
                    "꼬렛",
                    "레트라",
                    "깨비참",
                    "깨비드릴조",
                    "아보",
                    "아보크",
                    "피카츄",
                    "라이츄",
                    "모래두지",
                    "고지",
                    "니드런♀",
                    "니드리나",
                    "니드퀸",
                    "니드런♂",
                    "니드리노",
                    "니드킹",
                    "삐삐",
                    "픽시",
                    "식스테일",
                    "나인테일",
                    "푸린",
                    "푸크린",
                    "주뱃",
                    "골뱃",
                    "뚜벅쵸",
                    "냄새꼬",
                    "라플레시아",
                    "파라스",
                    "파라섹트",
                    "콘팡",
                    "도나리",
                    "디그다",
                    "닥트리오",
                    "나옹",
                    "페르시온",
                    "고라파덕",
                    "골덕",
                    "망키",
                    "성원숭",
                    "가디",
                    "윈디",
                    "발챙이",
                    "슈륙챙이[16]",
                    "강챙이",
                    "캐이시",
                    "윤겔라",
                    "후딘",
                    "알통몬",
                    "근육몬",
                    "괴력몬",
                    "모다피",
                    "우츠동",
                    "우츠보트",
                    "왕눈해",
                    "독파리",
                    "꼬마돌",
                    "데구리",
                    "딱구리",
                    "포니타",
                    "날쌩마",
                    "야돈",
                    "야도란",
                    "코일",
                    "레어코일",
                    "파오리",
                    "두두",
                    "두트리오",
                    "쥬쥬",
                    "쥬레곤",
                    "질퍽이",
                    "질뻐기",
                    "셀러",
                    "파르셀",
                    "고오스",
                    "고우스트",
                    "팬텀",
                    "롱스톤",
                    "슬리프",
                    "슬리퍼",
                    "크랩",
                    "킹크랩",
                    "찌리리공",
                    "붐볼",
                    "아라리",
                    "나시",
                    "탕구리",
                    "텅구리",
                    "시라소몬",
                    "홍수몬",
                    "내루미",
                    "또가스",
                    "또도가스",
                    "뿔카노",
                    "코뿌리",
                    "럭키",
                    "덩쿠리",
                    "캥카",
                    "쏘드라",
                    "시드라",
                    "콘치",
                    "왕콘치",
                    "별가사리",
                    "아쿠스타",
                    "마임맨",
                    "스라크",
                    "루주라",
                    "에레브",
                    "마그마",
                    "쁘사이저",
                    "켄타로스",
                    "잉어킹",
                    "갸라도스",
                    "라프라스",
                    "메타몽",
                    "이브이",
                    "샤미드",
                    "쥬피썬더",
                    "부스터",
                    "폴리곤",
                    "암나이트",
                    "암스타",
                    "투구",
                    "투구푸스",
                    "프테라",
                    "잠만보",
                    "프리져",
                    "썬더",
                    "파이어",
                    "미뇽",
                    "신뇽",
                    "망나뇽",
                    "뮤츠",
                    "뮤",
            };


}
