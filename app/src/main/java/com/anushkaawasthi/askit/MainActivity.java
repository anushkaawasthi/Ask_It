package com.anushkaawasthi.askit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private String TAG="MainActivity";
    private RecyclerView chatsRV;
    private ImageButton sendMsgIB;
    private EditText userMsgEdt;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";
    private ArrayList<ChatModel> chatModelArrayList;
    private ChatRVAdapter chatRVAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chatsRV = findViewById(R.id.idRVChats);
        sendMsgIB = findViewById(R.id.idIBSend);
        userMsgEdt = findViewById(R.id.idEdtMessage);
        chatModelArrayList = new ArrayList<>();
        chatRVAdapter = new ChatRVAdapter(chatModelArrayList,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        chatsRV.setLayoutManager(layoutManager);
        chatsRV.setAdapter(chatRVAdapter);

        sendMsgIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userMsgEdt.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"Please Enter ur Message",Toast.LENGTH_SHORT).show();
                    return;
                }
                getReponse(userMsgEdt.getText().toString());
                userMsgEdt.setText("");
            }
        });
    }
    private  void getReponse(String message){
        chatModelArrayList.add(new ChatModel(message,USER_KEY));
        chatRVAdapter.notifyDataSetChanged();
        String url ="get?bid=165664&key=xJs1gUhmneGcIFLQ&uid=[uid]&msg="+message;
        String BASE_URL ="http://api.brainshop.ai/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.brainshop.ai/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi  retrofitApi = retrofit.create(RetrofitApi.class);
        Call<MsgModel> call = retrofitApi.getMessage(url);

        call.enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {
                if(response.isSuccessful()){
                    MsgModel model = response.body();
                    chatModelArrayList.add(new ChatModel(model.getCnt(), BOT_KEY));
                    // Log.d(TAG,"message is "+model.getCtn());
                    chatRVAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MsgModel> call, Throwable t) {

                Log.d(TAG,"sorry"+t.getMessage());

                chatModelArrayList.add(new ChatModel("Please revert ur question",BOT_KEY));
                chatRVAdapter.notifyDataSetChanged();
            }
        });
    }
}