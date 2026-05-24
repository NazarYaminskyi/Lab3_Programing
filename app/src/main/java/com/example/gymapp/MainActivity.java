package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymapp.adapter.VisitorAdapter;
import com.example.gymapp.database.DatabaseClient;
import com.example.gymapp.database.VisitorDao;
import com.example.gymapp.model.Visitor;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editFirstName;
    private EditText editLastName;
    private EditText editPhone;
    private EditText editEmail;
    private EditText editSearch;

    private Button buttonAdd;
    private Button buttonSearch;

    private RecyclerView recyclerView;

    private VisitorAdapter adapter;
    private VisitorDao visitorDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initDatabase();
        initRecyclerView();

        loadVisitors();

        buttonAdd.setOnClickListener(v -> addVisitor());

        buttonSearch.setOnClickListener(v -> searchVisitorById());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadVisitors();
    }

    private void initViews() {
        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editPhone = findViewById(R.id.editPhone);
        editEmail = findViewById(R.id.editEmail);
        editSearch = findViewById(R.id.editSearch);

        buttonAdd = findViewById(R.id.buttonAddVisitor);
        buttonSearch = findViewById(R.id.buttonSearch);

        recyclerView = findViewById(R.id.recyclerVisitors);
    }

    private void initDatabase() {
        visitorDao = DatabaseClient.getInstance(this)
                .getAppDatabase()
                .visitorDao();
    }

    private void initRecyclerView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new VisitorAdapter(
                Collections.emptyList(),
                new VisitorAdapter.OnVisitorActionListener() {
                    @Override
                    public void onDeleteClick(Visitor visitor) {

                        try {
                            visitorDao.delete(visitor);

                            Toast.makeText(
                                    MainActivity.this,
                                    "Visitor deleted",
                                    Toast.LENGTH_SHORT
                            ).show();

                            loadVisitors();

                        } catch (Exception e) {

                            Toast.makeText(
                                    MainActivity.this,
                                    "Error deleting visitor",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onManageClick(Visitor visitor) {

                        Intent intent = new Intent(
                                MainActivity.this,
                                VisitorDetailsActivity.class
                        );

                        intent.putExtra("visitorId", visitor.getId());

                        startActivity(intent);
                    }
                });

        recyclerView.setAdapter(adapter);
    }

    private void loadVisitors() {

        List<Visitor> visitors = visitorDao.getAll();

        adapter.setVisitors(visitors);
    }

    private void addVisitor() {

        String firstName = editFirstName.getText().toString().trim();
        String lastName = editLastName.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();
        String email = editEmail.getText().toString().trim();

        if (firstName.isEmpty()
                || lastName.isEmpty()
                || phone.isEmpty()
                || email.isEmpty()) {

            Toast.makeText(
                    this,
                    "All fields are required",
                    Toast.LENGTH_SHORT
            ).show();

            return;

        }

        try {
            Visitor visitor = new Visitor();

            visitor.setFirstName(firstName);
            visitor.setLastName(lastName);
            visitor.setPhone(phone);
            visitor.setEmail(email);

            visitorDao.insert(visitor);

            Toast.makeText(
                    this,
                    "Visitor added",
                    Toast.LENGTH_SHORT
            ).show();

            clearFields();

            loadVisitors();

        } catch (Exception e) {

            Toast.makeText(
                    this,
                    e.getMessage(),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void searchVisitorById() {

        String input = editSearch.getText().toString().trim();

        if (input.isEmpty()) {

            loadVisitors();

            return;
        }

        try {

            long id = Long.parseLong(input);

            Visitor visitor = visitorDao.getById(id);

            if (visitor == null) {

                Toast.makeText(
                        this,
                        "No visitor found",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            adapter.setVisitors(
                    Collections.singletonList(visitor)
            );

        } catch (NumberFormatException e) {

            Toast.makeText(
                    this,
                    "Invalid ID",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void clearFields() {

        editFirstName.setText("");
        editLastName.setText("");
        editPhone.setText("");
        editEmail.setText("");
    }
}