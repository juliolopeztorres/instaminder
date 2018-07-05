package oob.instagramapitest.Z_Deprecated;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import oob.instagramapitest.R;

public class GetImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_image);

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 999);
    }
}
