package com.example.jayso.wheelersslotbooking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userFeedbackReview;
    private Button SubmitFeed;

    public String review = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        hideSoftKeyboard();

        RatingBar rb = findViewById(R.id.Rating_bar);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(getApplicationContext(),String.valueOf(rating),Toast.LENGTH_LONG).show();
            }
        });

        userFeedbackReview = findViewById(R.id.et_review_feedback);


        findViewById(R.id.feedback_submit).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                review = userFeedbackReview.getText().toString();


                if (review.length() == 0) {
                    Toast.makeText(FeedbackActivity.this, "Please Insert First Name", Toast.LENGTH_SHORT).show();
                } else {
                    User_feedback();
                }
            }
        });

        Button btnback = findViewById(R.id.btn_back);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    private void User_feedback() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.UserRegi,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("Response", response);
                            Toast.makeText(FeedbackActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                           /* AlertDialog alertDialog = new AlertDialog.Builder(RegistrationActivity.this).create();
                            alertDialog.setTitle("Information");
                            alertDialog.setMessage("Registration Successfully!!!");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            startActivity(new Intent(RegistrationActivity.this, MapActivity.class));
                                            finish();
                                        }
                                    });
                            alertDialog.show();*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Error", e.getMessage());
                            Toast.makeText(FeedbackActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("U_FirstName",review );
                return params;
            }

        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onClick(View v) {
        if (v == userFeedbackReview) {
            User_feedback();

        }
    }
}
