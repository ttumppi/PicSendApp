package com.example.PicSend;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class WaitForDesktopSignal extends Fragment {




    private OnImageCommandReceived _onCommandReceived;

    public WaitForDesktopSignal() {
        // Required empty public constructor
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wait_for_desktop_signal, container, false);
        // Initialize views or perform other setup here
        return rootView;
    }











}