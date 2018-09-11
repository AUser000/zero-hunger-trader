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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zerohunger.zerohungertrader.model.Inventory;
import com.zerohunger.zerohungertrader.model.SpinnerItem;

import java.util.ArrayList;

public class AddNewItemActivity extends AppCompatActivity {

    ArrayList<SpinnerItem> spinnerItems;
    String itemId;
    String traderId;
    Spinner spinner;
    TextView priceText;
    TextView qutText;
    Button locationBtn;
    Button addBtn;
    double lat = 0.0;
    double lng = 0.0;
    Intent i;

    int price;
    int qut;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);
        initFirebase();


        spinner = findViewById(R.id.inventoryItemSpinner);
        priceText = findViewById(R.id.inventoryPriceText);
        qutText = findViewById(R.id.inventoryQuantityText);
        locationBtn = findViewById(R.id.locationBtn);
        addBtn = findViewById(R.id.inventoryAddButton);

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(AddNewItemActivity.this, MapsActivity.class);
                startActivityForResult(i ,1);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateUiData()) {
                    gatherUiData();
                    sumbitDataToFirebase();
                    clearUi();
                } else {
                    // Do nothing
                }
            }
        });


        getFirebaseItemData();
        collectLocationData();
    }

    private void gatherUiData() {
        getItemKey();
        getPrice();
        getQut();
    }

    // UI data gathering =================== //
    private void getItemKey() {
        itemId = (String)spinner.getSelectedItem();
        for (SpinnerItem spinnerItem : spinnerItems) {
            if(spinnerItem.getItemName().equals(itemId)) {
                itemId = spinnerItem.getKey();
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


    // validate UI data ================== //
    private boolean validateUiData() {
        boolean valid = false;
        if( lat != 0.0 && lng != 0.0 ){
            valid = true;
        }

        return valid;
    }


    private void initFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        traderId = FirebaseAuth.getInstance().getUid();
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



    // clear UI =========================== //
    private void clearUi() {
        priceText.setText("");
        qutText.setText("");
    }

    // read data on firebase ============== //
    private void sumbitDataToFirebase() {
        // TODO: fix this
        System.out.println("lat" + lat);
        System.out.println("lng" + lng);

        final Long startedTime = System.currentTimeMillis() / 1000L;
        Inventory inventory = new Inventory(
                traderId, itemId, price, qut, startedTime, lat, lng);
        // write into the database
        databaseReference.child("inventory").push().setValue(inventory);
        System.out.println(inventory.itemId);
        System.out.println(spinner.getSelectedItem());
    }


    private void collectLocationData() {

    }
//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                lat = (double)data.getDoubleExtra("LAT", 0.00);
                System.out.println("lat : " + lat);
                lng = (double)data.getDoubleExtra("LNG", 0.00);
                System.out.println("lat : " + lng);
            }
        }
    }

}
