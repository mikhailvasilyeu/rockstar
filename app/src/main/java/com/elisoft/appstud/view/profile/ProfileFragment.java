package com.elisoft.appstud.view.profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.elisoft.appstud.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author Hoang
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    /**
     * Singleton bookmarks fragment
     */
    private static ProfileFragment instance;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String FULL_NAME = "fullName";
    public static final String IMAGE_PATH = "image_Path";
    private static final int CAMERA_REQUEST = 1080;
    private ImageView ivAvatar;
    private ImageView ivButtonOk;
    private EditText edtFullName;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    boolean isTakePicture = false;

    /**
     * Get bookmarks fragment using singleton pattern (Only create new instance when needed)
     *
     * @return
     */
    public static ProfileFragment getInstance() {
        if (ProfileFragment.instance == null) {
            ProfileFragment.instance = new ProfileFragment();
        }
        return ProfileFragment.instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        ivAvatar = (ImageView) v.findViewById(R.id.iv_profile_avatar);
        ivButtonOk = (ImageView) v.findViewById(R.id.iv_ok);
        edtFullName = (EditText) v.findViewById(R.id.edt_full_name);
        ivAvatar.getLayoutParams().height = getHeightofScreen(getActivity()) / 2;
        ivAvatar.setOnClickListener(this);
        ivButtonOk.setOnClickListener(this);
        setUpUI();
        return v;
    }

    public void setUpUI() {
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        String fullName = sharedpreferences.getString(FULL_NAME, null);
        edtFullName.setText(fullName);
        try {
            String image_path = sharedpreferences.getString(IMAGE_PATH, null);
            Log.e("share", "" + image_path);
            File f = new File(image_path, "");
            Bitmap bm = BitmapFactory.decodeStream(new FileInputStream(f));
            ivAvatar.setImageBitmap(bm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_profile_avatar) {
            int frontCameraId = getCameraFontId();
            if (frontCameraId != -1) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", frontCameraId);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(getActivity(), "You don't have front camera", Toast.LENGTH_SHORT).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        } else if (v.getId() == R.id.iv_ok) {

            String name = edtFullName.getText().toString();
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(getActivity(), "Please fill in your name", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Profile was saved", Toast.LENGTH_SHORT).show();
                editor.putString(FULL_NAME, name);
                editor.commit();
                if (ivAvatar.getDrawable() != null && isTakePicture) {
                    String path = getOriginalImagePath();
                    editor.putString(IMAGE_PATH, path);
                    editor.commit();
                }
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ivAvatar.setImageBitmap(photo);
            isTakePicture = true;
            Log.e("", getOriginalImagePath());
        }
    }

    public int getCameraFontId() {
        int cameraId = -1;
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int camNo = 0; camNo < Camera.getNumberOfCameras(); camNo++) {
            Camera.CameraInfo camInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(camNo, camInfo);
            if (camInfo.facing == (Camera.CameraInfo.CAMERA_FACING_FRONT)) {
//                cam = Camera.open(camNo);
                cameraId = camNo;
                break;
            }
        }
        return cameraId;
    }

    public String getOriginalImagePath() {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, null);
        int column_index_data = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToLast();
        return cursor.getString(column_index_data);
    }

    public int getHeightofScreen(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        return height;
    }
}
