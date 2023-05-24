package com.example.myapplicationforreal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ResumeFillActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, phoneEditText, degreeEditText, majorEditText;
    private Button submitButton;

    private DatabaseReference resumeRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_fill);

        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        degreeEditText = findViewById(R.id.degreeEditText);
        majorEditText = findViewById(R.id.majorEditText);
        submitButton = findViewById(R.id.submitButton);

        // Initialize the Firebase database reference
        resumeRef = FirebaseDatabase.getInstance().getReference("resumes");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered values
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String degree = degreeEditText.getText().toString();
                String major = majorEditText.getText().toString();

                // Create a new resume object
                Resume resume = new Resume(name, email, phone, degree, major);

                // Save the resume to Firebase
                String resumeId = resumeRef.push().getKey();
                resumeRef.child(resumeId).setValue(resume);

                // Clear the input fields
                nameEditText.setText("");
                emailEditText.setText("");
                phoneEditText.setText("");
                degreeEditText.setText("");
                majorEditText.setText("");
            }
        });
    }
}
