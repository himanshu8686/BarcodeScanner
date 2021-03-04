package com.barcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.barcodescanner.models.BoardingPassModel;
import com.google.gson.Gson;

public class BoardingPassDetailsActivity extends AppCompatActivity {

    private TextView tv_full_name, tv_from_city, tv_to_city, tv_carrier_code, tv_compartment_code,
            tv_dof, tv_flight_no,
            tv_seat_no, tv_seat_class;

    private BoardingPassModel boardingPassModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boarding_pass_details);

        tv_full_name = findViewById(R.id.tv_full_name);
        tv_from_city = findViewById(R.id.tv_from_city);
        tv_to_city = findViewById(R.id.tv_to_city);
        tv_carrier_code = findViewById(R.id.tv_carrier_code);
        tv_compartment_code = findViewById(R.id.tv_compartment_code);
        tv_dof = findViewById(R.id.tv_dof);
        tv_flight_no = findViewById(R.id.tv_flight_no);
        tv_seat_no = findViewById(R.id.tv_seat_no);
        tv_seat_class = findViewById(R.id.tv_seat_class);

        String barcodeResultString = getIntent().getStringExtra("GETKEY");

        try {
            boardingPassModel = new Gson().fromJson(barcodeResultString, BoardingPassModel.class);
            tv_full_name.setText(boardingPassModel.getFullName());
            tv_from_city.setText(boardingPassModel.getFromCity());
            tv_to_city.setText(boardingPassModel.getToCity());
            tv_carrier_code.setText(boardingPassModel.getCarrierCode());
            tv_compartment_code.setText(boardingPassModel.getCompartmentCode());
            tv_dof.setText(boardingPassModel.getFlightDate());
            tv_flight_no.setText(boardingPassModel.getFlightNumber());
            tv_seat_no.setText(boardingPassModel.getSeatNumber());
            tv_seat_class.setText(boardingPassModel.getSeatingClass());

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }

        Log.e("Model ==>0", boardingPassModel.toString());
    }
}