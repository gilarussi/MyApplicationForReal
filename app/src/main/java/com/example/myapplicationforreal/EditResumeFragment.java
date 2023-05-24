package com.example.myapplicationforreal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditResumeFragment extends Fragment {

    private EditText nameEditText, emailEditText, phoneEditText, degreeEditText, majorEditText;
    private Button updateButton;

    private DatabaseReference resumeRef;
    private String resumeId;

    public static EditResumeFragment newInstance(String resumeId) {
        EditResumeFragment fragment = new EditResumeFragment();
        Bundle args = new Bundle();
        args.putString("resumeId", resumeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            resumeId = getArguments().getString("resumeId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_resume, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameEditText = view.findViewById(R.id.nameEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        degreeEditText = view.findViewById(R.id.degreeEditText);
        majorEditText = view.findViewById(R.id.majorEditText);
        updateButton = view.findViewById(R.id.updateButton);

        // Initialize the Firebase database reference
        resumeRef = FirebaseDatabase.getInstance().getReference("resumes").child(resumeId);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the updated values
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String degree = degreeEditText.getText().toString();
                String major = majorEditText.getText().toString();

                // Update the resume in Firebase
                resumeRef.child("name").setValue(name);
                resumeRef.child("email").setValue(email);
                resumeRef.child("phone").setValue(phone);
                resumeRef.child("degree").setValue(degree);
                resumeRef.child("major").setValue(major);

                // Navigate back to the previous fragment or activity
                // ...
            }
        });
    }
}
