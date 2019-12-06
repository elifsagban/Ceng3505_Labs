package com.elif.mynotes;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elif.mynotes.dummy.DummyContent;
import com.elif.mynotes.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnNoteListInteractionListener}
 * interface.
 */
public class NoteFragment extends Fragment {
    private OnNoteListInteractionListener mListener;
    RecyclerView recyclerView;
    public NoteFragment() {}
    public static NoteFragment newInstance() {
        NoteFragment fragment = new NoteFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);
        if (view instanceof RecyclerView) {
            recyclerView = (RecyclerView) view;
        }
        Log.d("Fragment" , "OnCreateView");
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNoteListInteractionListener) {
            mListener = (OnNoteListInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNoteListInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    /**
     * Interface for listing note operations in the list
     */
    public interface OnNoteListInteractionListener {
        void onNoteSelected(Note item);
    }
    public void updateNotes(List<Note> notes){
        Log.d("Fragment" , "updateNotes");
        recyclerView.setLayoutManager(new
                LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new MyNoteRecyclerViewAdapter(notes, mListener));
    }
}