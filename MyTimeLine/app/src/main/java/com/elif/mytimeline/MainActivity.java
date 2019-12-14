package com.elif.mytimeline;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.grpc.Context;

public class MainActivity extends AppCompatActivity {

    Button  btnPost;
    ListenerRegistration listenerRegistration;
    List<Post> posts = new ArrayList<>();
    public static final String TAG = "Timeline";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPost = (Button) findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PostActivity.class);
                startActivityForResult(intent, 1);
            }
        });



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        listenerRegistration = db.collection("notes").orderBy("date",
                Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e(TAG, "Error retrieving notes", e);
                    return;
                }
                posts.clear();
                posts.addAll(queryDocumentSnapshots.toObjects(Post.class));
                ListView listView = (ListView) findViewById(R.id.listView);
                PostAdapter adapter = new PostAdapter(posts, MainActivity.this);
                listView.setAdapter(adapter);
            }
        });
    }

    @Override
    protected void onDestroy() {
        listenerRegistration.remove();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode, data);
        Post post = new Post();
        post.setMessage(data.getCharSequenceExtra("msg").toString());
        Bitmap image = (Bitmap) data.getParcelableExtra("bitmap");

        DocumentReference docRef = FirebaseFirestore.getInstance().collection("posts").document();

        post.setImagePath("images/"+docRef.getId());
        post.setDate(new Timestamp(new Date()));

        final FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference ref = storage.getReference(post.getImagePath());
        final StorageReference reference = ref.child("images/");


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] bytes= baos.toByteArray();
        UploadTask uploadTask = reference.putBytes(bytes);
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) /
                        taskSnapshot.getTotalByteCount();
                System.out.println("Upload is " + progress + "% done");
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("Upload is paused");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
// Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            }
        });
//save to cloud firebase
        docRef.set(post);
    }
}