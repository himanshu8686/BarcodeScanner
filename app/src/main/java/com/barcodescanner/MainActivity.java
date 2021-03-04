package com.barcodescanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.barcodescanner.barcodeParser.ParseData;
import com.barcodescanner.barcodereader.barcodeService.BarcodeCaptureActivity;
import com.barcodescanner.models.BoardingPassModel;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_scan;
    private Button btn_pick_from_gallery;
    private BoardingPassModel barcodeResult;
    private static final int ACTION_IMAGE_CAPTURE = 9001;
    private static final int ACTION_IMAGE_SELECT = 9002;
    public static final int ALL_PERMISSIONS = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_scan = findViewById(R.id.btn_scan);
        btn_pick_from_gallery = findViewById(R.id.btn_pick_from_gallery);
        btn_scan.setOnClickListener(this);
        btn_pick_from_gallery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scan: {
                openBarcodeActivity();
                break;
            }
            case R.id.btn_pick_from_gallery: {
                pickPhotoClicked();
                break;
            }
        }
    }

    /**
     * This method is responsible for the picking up boarding pass from the gallery
     */
    public void pickPhotoClicked() {
        if (checkingPermissionIsEnabledOrNot()) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, ACTION_IMAGE_SELECT);
        } else {
            requestMultiplePermissions();
        }
    }

    /**
     * opens camera and get data from barcode
     */
    private void openBarcodeActivity() {
        // launch barcode activity if permission is granted.

        if (checkingPermissionIsEnabledOrNot()) {
            Intent intent = new Intent(this, BarcodeCaptureActivity.class);
            intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
            intent.putExtra(BarcodeCaptureActivity.UseFlash, false);
            startActivityForResult(intent, ACTION_IMAGE_CAPTURE);
        } else {
            requestMultiplePermissions();
        }


    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ACTION_IMAGE_CAPTURE: {
                if (resultCode == CommonStatusCodes.SUCCESS) {
                    if (data != null) {
                        Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                        // Create code
                        Log.e("Raw", barcode.rawValue);
                        parseBarcodeToModel(barcode);
//                        try {
//                            if (boardingPassModel != null) {
//                                Log.d("Get value : ", boardingPassModel.toString());
//                                Gson gson = new GsonBuilder().setPrettyPrinting().create();
//                                System.out.println("to JSON value" + gson.toJson(boardingPassModel));
//
//                                Intent intent = new Intent(this, BoardingPassDetailsActivity.class);
//                                intent.putExtra("getObject", gson.toJson(boardingPassModel));
//                                startActivity(intent);
//                            }
//                        } catch (NullPointerException e) {
//                            Toast.makeText(this, "please try again", Toast.LENGTH_SHORT).show();
//                        }
                    } else {
                        Toast.makeText(this, "Barcode not captured", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
            case ACTION_IMAGE_SELECT: {
                if (resultCode == CommonStatusCodes.SUCCESS || resultCode == CommonStatusCodes.SUCCESS_CACHE) {
                    if (data != null) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                            scanBarcodeWithBitmap(bitmap);
                        } catch (FileNotFoundException e) {
                            Toast.makeText(this, this.getResources().getString(R.string.something_went_Wrong), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        } catch (IOException e) {
                            Toast.makeText(this, this.getResources().getString(R.string.something_went_Wrong), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                } else if (resultCode == CommonStatusCodes.CANCELED) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    /**
     * @param bitmap is the picked image from the gallery.
     *               This method will read the barcodes of format pdf 417 and parse the barcode to Human readable
     *               form.
     */
    private void scanBarcodeWithBitmap(Bitmap bitmap) {
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        BarcodeDetector detector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.PDF417)
                .build();

        SparseArray<Barcode> barcodes = detector.detect(frame);

        if (barcodes.size() > 0 && barcodes != null) {
//            for (int i = 0; i < barcodes.size(); i++) {
//                Log.d("Array of barcode", "Value: " +i+ barcodes.valueAt(i).rawValue + "----" + barcodes.valueAt(i).displayValue);
//                Toast.makeText(this, barcodes.valueAt(i).rawValue, Toast.LENGTH_SHORT).show();
//            }
            Barcode barcode = barcodes.valueAt(0);
            parseBarcodeToModel(barcode);
        } else {
            Toast.makeText(this, "Invalid boarding pass", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param barcode is the scanned barcode which is need to be parsed into Human
     *                readable format if barcode is successfully parsed then it navigate to the other screen
     */
    private void parseBarcodeToModel(Barcode barcode) {
        try {
//           barcodeResult =  ParseData.parse("M1SOLLE/JOSUHUA       EQHSLJX ATLMEMDL 0254 006Y28C      10C3JIJI7O4M28C", this);
            barcodeResult = ParseData.parse(barcode.rawValue, this);

            if (barcodeResult != null && barcodeResult.getValid()) {

                // Do other stuffs whatever you want to do
                Intent intent = new Intent(this, BoardingPassDetailsActivity.class);
                intent.putExtra("GETKEY", new GsonBuilder().setPrettyPrinting().create().toJson(barcodeResult));
                startActivity(intent);
            } else {
                Toast.makeText(this, this.getResources().getString(R.string.invalid_boarding_pass), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "parse error", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *  request the permissions
     */
    private void requestMultiplePermissions() {
        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET,

                }, ALL_PERMISSIONS);
    }

    /**
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case ALL_PERMISSIONS:
                if (grantResults.length > 0) {
                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadExternalStoragePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean WriteExternalStoragePermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean Internet = grantResults[3] == PackageManager.PERMISSION_GRANTED;

                    if (!(CameraPermission && ReadExternalStoragePermission && WriteExternalStoragePermission && Internet))
                    {
                        Toast.makeText(MainActivity.this, "Permission Denied! Please grant the permission to continue.", Toast.LENGTH_LONG).show();
                    }
                }

                break;
        }
    }

    /**
     *  Method for checking if permission is granted or not
     * @return
     */
    public boolean checkingPermissionIsEnabledOrNot() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int ForthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ForthPermissionResult == PackageManager.PERMISSION_GRANTED;
    }


}
