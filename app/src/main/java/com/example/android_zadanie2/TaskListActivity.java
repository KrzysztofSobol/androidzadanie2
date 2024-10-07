package com.example.android_zadanie2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class TaskListActivity extends SingleFragmentActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected Fragment createFragment() {
        return fragmentManager.findFragmentById(R.id. fragment_container);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}