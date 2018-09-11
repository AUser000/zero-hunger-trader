package com.zerohunger.zerohungertrader;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zerohunger.zerohungertrader.model.SpinnerItem;

import java.util.ArrayList;

public class AddNewItemActivity extends AppCompatActivity {

    ArrayList<SpinnerItem> spinnerItems;
    String spinnerSelectedItemKey;
    Spinner spinner;
    TextView priceText;
    TextView qutText;
    Button locationBtn;


    int price;
    int qut;
    //Lat lat;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);

        initUi();

        initFirebase();

        getFirebaseItemData();

        //updateSpinner();

        gatherUiData();

        collectLocationData();

        clearUi();

        //validateUiData();

        if(validateUiData()) {
            sumbitDataToFirebase();
        } else {
            // Do nothing
        }
    }

    private void gatherUiData() {
        getItemKey();
        getPrice();
        getQut();

    }

    private void initUi() {
        spinner = findViewById(R.id.inventoryItemSpinner);
        priceText = findViewById(R.id.inventoryPriceText);
        qutText = findViewById(R.id.inventoryQuantityText);
        locationBtn = findViewById(R.id.locationBtn);

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddNewItemActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });
    }

    // validate UI data ================== //
    private boolean validateUiData() {
        boolean valid = false;

        return valid;
    }


    private void initFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void updateSpinner() {
        ArrayList<String> spinnerItemNames = new ArrayList<String>();
        for (SpinnerItem spinnerItem: spinnerItems) {
            String name = spinnerItem.getItemName();
            spinnerItemNames.add(name);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                AddNewItemActivity.this,
                android.R.layout.simple_spinner_item,
                spinnerItemNames);

        adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);

        spinner.setAdapter(adapter);
    }

    private void getFirebaseItemData() {
        spinnerItems = new ArrayList<>();
        databaseReference.child("items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    String name = dataSnapshot1.child("name").getValue(String.class);
                    String key = dataSnapshot1.getKey();

                    SpinnerItem spinnerItem = new SpinnerItem();
                    spinnerItem.setItemName(name);
                    spinnerItem.setKey(key);
                    System.out.println(key);
                    System.out.println(name);
                    spinnerItems.add(spinnerItem);
                }
                updateSpinner();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddNewItemActivity.this, "database reading error! refresh activity", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // UI data gathering =================== //
    private void getItemKey() {
        String spinnerSelectedItem = (String)spinner.getSelectedItem();
        for (SpinnerItem spinnerItem : spinnerItems) {
            if(spinnerItem.getItemName().equals(spinnerSelectedItem)) {
                spinnerSelectedItemKey = spinnerItem.getKey();
            }
        }
    }

    private void getPrice() {
        try {
            price =  Integer.parseInt(priceText.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(AddNewItemActivity.this, "check Errors", Toast.LENGTH_SHORT).show();
        }

    }

    private void getQut() {
        try {
            qut = Integer.parseInt(qutText.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(AddNewItemActivity.this, "check Errors", Toast.LENGTH_SHORT).show();
        }
    }

    // clear UI =========================== //
    private void clearUi() {
        priceText.setText("");
        qutText.setText("");
    }

    // read data on firebase ============== //
    private void sumbitDataToFirebase() {
        // TODO: fix this
    }


    private void collectLocationData() {

    }

}
