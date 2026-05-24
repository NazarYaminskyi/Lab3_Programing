package com.example.gymapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gymapp.database.DatabaseClient;
import com.example.gymapp.database.VisitorDao;
import com.example.gymapp.model.Visitor;

public class VisitorDetailsActivity extends AppCompatActivity {

    private EditText editFirstName, editLastName, editPhone, editEmail, editTrainerId;;
    private Button buttonSave;
    private Button buttonBack;
    private Visitor visitor;
    private VisitorDao visitorDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_details);

        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editPhone = findViewById(R.id.editPhone);
        editEmail = findViewById(R.id.editEmail);
        editTrainerId = findViewById(R.id.editTrainerId);

        buttonSave = findViewById(R.id.buttonSave);
        buttonBack = findViewById(R.id.buttonBack);

        visitorDao = DatabaseClient.getInstance(this)
                .getAppDatabase()
                .visitorDao();

        long visitorId = getIntent().getLongExtra("visitorId", -1);

        visitor = visitorDao.getById(visitorId);

        if (visitor == null) {
            Toast.makeText(this, "Visitor not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        editFirstName.setText(visitor.getFirstName());
        editLastName.setText(visitor.getLastName());
        editPhone.setText(visitor.getPhone());
        editEmail.setText(visitor.getEmail());
        if (visitor.getTrainerId() != null) {
            editTrainerId.setText(String.valueOf(visitor.getTrainerId()));
        }

        buttonSave.setOnClickListener(v -> saveVisitor());

        buttonBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void saveVisitor() {

        try {

            String firstName = editFirstName.getText().toString().trim();
            String lastName = editLastName.getText().toString().trim();
            String phone = editPhone.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String trainerInput = editTrainerId.getText().toString().trim();
            if (firstName.isEmpty() || lastName.isEmpty()
                    || phone.isEmpty() || email.isEmpty()) {

                Toast.makeText(this,
                        "All fields are required",
                        Toast.LENGTH_SHORT).show();

                return;
            }

            if (trainerInput.isEmpty()) {
                visitor.setTrainerId(null);
            } else {
                try {
                    long trainerId = Long.parseLong(trainerInput);
                    visitor.setTrainerId(trainerId);

                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Trainer ID must be a number", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            visitor.setFirstName(firstName);
            visitor.setLastName(lastName);
            visitor.setPhone(phone);
            visitor.setEmail(email);

            visitorDao.update(visitor);

            Toast.makeText(this,
                    "Visitor updated",
                    Toast.LENGTH_SHORT).show();

            finish();

        } catch (Exception e) {

            Toast.makeText(this,
                    e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}