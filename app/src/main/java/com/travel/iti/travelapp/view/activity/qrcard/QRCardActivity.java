package com.travel.iti.travelapp.view.activity.qrcard;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.databinding.ActivityQrcardBinding;
import com.travel.iti.travelapp.repository.model.BookedPackage;
import com.travel.iti.travelapp.repository.model.PackagesPojo;

public class QRCardActivity extends AppCompatActivity {

    private PackagesPojo packagesPojo;
    private ActivityQrcardBinding binding;
    private ImageView qrCodeImage ;
    private BookedPackage bookedPackage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_qrcard);
        packagesPojo= (PackagesPojo) getIntent().getSerializableExtra("packageDetails");
        bookedPackage= (BookedPackage) getIntent().getSerializableExtra("bookedPackage");

        binding = DataBindingUtil.setContentView(QRCardActivity.this, R.layout.activity_qrcard);
        binding.setLifecycleOwner(this);
        binding.setPackageDetails(packagesPojo);


        qrCodeImage=findViewById(R.id.qrcodeImage);
        Bitmap bitmap = null;
        try {
            bitmap = TextToImageEncode(bookedPackage.getUserName() +" , "+packagesPojo.getPackageName()+" , no of adults " +bookedPackage.getNoOfAdults()
            +" , no of childs" + bookedPackage.getNoOfChildren());
        } catch (WriterException e) {
            e.printStackTrace();
        }
        qrCodeImage.setImageBitmap(bitmap);

    }



    public Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;

        try {
            bitMatrix=new MultiFormatWriter().encode(Value, BarcodeFormat.QR_CODE,70,70,null);

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth=bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];
        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(android.R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, bitMatrixWidth, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
