package com.elif.mytimeline;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class PostAdapter extends BaseAdapter {

    List<Post> posts;
    private LayoutInflater inflater;
    Context context;


    public PostAdapter(List<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View rowView;
        rowView = inflater.inflate(R.layout.row,null);
        EditText txtMessage = rowView.findViewById(R.id.txtMessage);
        TextView txtLocation = rowView.findViewById(R.id.txtLocation);
        final ImageView imageView = rowView.findViewById(R.id.imageView);
        Post post = posts.get(position);
        txtMessage.setText(post.getMessage());
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference pathReference= storageRef.child(post.getImagePath());


        Log.d(MainActivity.TAG, "Image path = " + post.getImagePath());
        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("Storage", uri.toString());
                Glide.with(context).load(uri).into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(MainActivity.TAG, "Error", e);
            }
        });
        return rowView;
    }


}
