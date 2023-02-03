package com.tecraa.mycontact;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ContactAllViewHolder extends RecyclerView.ViewHolder {

    ImageView userImage;
    TextView userName,userPnone,userType;
    AppCompatButton userFavourite;

    CardView userCard;

    public ContactAllViewHolder(@NonNull View itemView) {
        super(itemView);
        userImage = itemView.findViewById(R.id.userImage);
        userName = itemView.findViewById(R.id.userName);
        userPnone = itemView.findViewById(R.id.userPnone);
        userType = itemView.findViewById(R.id.userType);
        userFavourite = itemView.findViewById(R.id.userFavourite);
        userCard = itemView.findViewById(R.id.userCard);
    }
}
