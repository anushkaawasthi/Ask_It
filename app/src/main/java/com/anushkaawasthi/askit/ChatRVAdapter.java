package com.anushkaawasthi.askit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatRVAdapter extends RecyclerView.Adapter{
    private ArrayList<ChatModel> messageModalArrayList;
    private Context context;

    public ChatRVAdapter(ArrayList<ChatModel> messageModalArrayList, Context context) {
        this.messageModalArrayList = messageModalArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch(viewType){
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_msg, parent, false);
                return new UserViewHolder(view);
            case 1:
                // below line we are inflating bot message layout.
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_msg, parent, false);
                return new botViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatModel chatModel =messageModalArrayList.get(position);
        switch (chatModel.getSender()){
            case "user":
                ((UserViewHolder)holder).userTV.setText(chatModel.getMessage());
                break;
            case "bot":
                ((botViewHolder)holder).botTV.setText(chatModel.getMessage());
                break;
        }
    }
    @Override
    public int getItemViewType(int position) {
        switch (messageModalArrayList.get(position).getSender()){
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }
    }
    @Override
    public int getItemCount() {
        return messageModalArrayList.size();
    }
    public static class UserViewHolder extends RecyclerView.ViewHolder {

        // creating a variable
        // for the text view.
        TextView userTV;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing with id.
            userTV = itemView.findViewById(R.id.idTVUser);
        }
    }
    public static class botViewHolder extends RecyclerView.ViewHolder{

        TextView botTV;
        public botViewHolder(@NonNull View itemView) {
            super(itemView);
            botTV=itemView.findViewById(R.id.idTVBot);
        }
    }
}
