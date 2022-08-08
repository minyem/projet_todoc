package com.cleanup.todoc.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.cleanup.todoc.R;
import com.cleanup.todoc.model.Task;

import java.util.Date;

import utils.Helper;

public class DetailTaskActivity extends FragmentActivity {
   Helper helper;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);

        Intent bundle = getIntent();
        int id = bundle.getIntExtra("id", 0);
        Task ts=helper.getoneTask(id);

        String nameTache = ts.getName();
        String nameProjet =ts.getProject().getName();
        Long dateR = ts.getCreationTimestamp();




        EditText name_task = findViewById(R.id.inom_tache);
        EditText date_task = findViewById(R.id.idate_creationtache);
        EditText heure_task = findViewById(R.id.iheure_creation);
        EditText name_projet = findViewById(R.id.inom_projet);

        Button modif = findViewById(R.id.modif);

        name_task.setText(nameTache);
        date_task.setText(Math.toIntExact(dateR));
        heure_task.setText(Math.toIntExact(dateR));
        name_projet.setText(nameProjet);


        modif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_task.setText(toString());
                date_task.setText(toString());
                heure_task.setText(toString());
                name_projet.setText(toString());
                Task ts1=new Task(id,Integer.valueOf(String.valueOf(name_projet)),String.valueOf(name_task),dateR);
                helper.upDateTask(ts1);
                finish();
            }
        });

    }
}