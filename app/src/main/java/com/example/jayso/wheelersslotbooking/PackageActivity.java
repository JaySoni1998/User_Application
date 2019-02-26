package com.example.jayso.wheelersslotbooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PackageActivity extends AppCompatActivity {
    ListView listView;
    TextView textView;
    String[] listItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);

        //back start
        Button btnback = findViewById(R.id.btn_back);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //back over
        listView=(ListView)findViewById(R.id.ListView);
        textView=(TextView)findViewById(R.id.textView);
        listItem = getResources().getStringArray(R.array.array_technology);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, listItem);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
//                String value = adapter.getItem(position);
//                Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();

                // package intant
                listView = (ListView) findViewById(R.id.ListView);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // TODO Auto-generated method stub
                        if (position == 0) {
                            Intent it = new Intent(PackageActivity.this,PackageTenDayActivity.class);
                            startActivity(it);
                            Toast.makeText(getApplicationContext(), "10 Day Package", Toast.LENGTH_SHORT).show();
                        } else if (position == 1) {
                            Intent it = new Intent(PackageActivity.this,PackageFifteenDayActivity.class);
                            startActivity(it);
                            Toast.makeText(getApplicationContext(), "15 Day Package", Toast.LENGTH_SHORT).show();
                        } else if (position == 2) {
                            Intent it = new Intent(PackageActivity.this,PackageThirtyDayActivity.class);
                            startActivity(it);
                            Toast.makeText(getApplicationContext(), "30 Day Package", Toast.LENGTH_SHORT).show();
                        } else if (position == 3) {
                            Intent it = new Intent(PackageActivity.this,PackageSixtyDayActivity.class);
                            startActivity(it);
                            Toast.makeText(getApplicationContext(), "60 Day Package", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

