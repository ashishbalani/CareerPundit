package inducesmile.com.CareerPundit;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import inducesmile.com.CareerPundit.LoginRegister.User;
import inducesmile.com.CareerPundit.LoginRegister.UserLocalStore;
import inducesmile.com.CareerPundit.R;



public class Profile extends Fragment implements View.OnClickListener {


    ImageButton cam,up;
    ImageView dp;
    User user;
    ImageView circleView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        UserLocalStore userLocalStore=new UserLocalStore(getActivity());
        user=userLocalStore.getLoggedInUser();
        circleView=(ImageView)getActivity().findViewById(R.id.circleView);
        dp=(ImageView) view.findViewById(R.id.dp);
        cam=(ImageButton)view.findViewById(R.id.camera);
        up=(ImageButton)view.findViewById(R.id.upload);
        cam.setOnClickListener(this);
        up.setOnClickListener(this);
        return view;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.camera:{
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
                break;
            }
            case R.id.upload:{
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(
                        Intent.createChooser(intent, "Select File"),
                        1);
            }

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 0) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail=Bitmap.createScaledBitmap(thumbnail,240,240,false);
                thumbnail.compress(Bitmap.CompressFormat.JPEG,90,bytes);
              File destination = new File(Environment.getExternalStorageDirectory(),
                        "/sdcard0/baidu" + ".jpg");

                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                dp.setImageBitmap(thumbnail);
                circleView.setImageBitmap(thumbnail);
            }
        else if(requestCode==1){
                Uri selectedImageUri = data.getData();
                String[] projection = { MediaStore.MediaColumns.DATA };
                CursorLoader cursorLoader = new CursorLoader(getActivity(),selectedImageUri, projection, null, null, null);
                Cursor cursor =cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);
                dp.setImageBitmap(bm);
                circleView.setImageBitmap(bm);

            }
    }




}
