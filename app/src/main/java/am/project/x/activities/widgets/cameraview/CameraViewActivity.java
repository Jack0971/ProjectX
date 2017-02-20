package am.project.x.activities.widgets.cameraview;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import am.project.x.R;
import am.project.x.activities.BaseActivity;
import am.widget.cameraview.CameraView;
import am.widget.zxingscanview.ZxingForegroundView;

public class CameraViewActivity extends BaseActivity implements CameraView.OnCameraListener {

    private static final int PERMISSIONS_REQUEST_CAMERA = 106;
    private CameraView cvCamera;

    @Override
    protected int getContentViewLayoutResources() {
        return R.layout.activity_cameraview;
    }

    @Override
    protected void initResource(Bundle savedInstanceState) {
        setSupportActionBar(R.id.camera_toolbar);
        cvCamera = (CameraView) findViewById(R.id.camera_cv_camera);
        cvCamera.setOnCameraListener(this);
    }

    @Override
    public void onCameraPermissionDenied(CameraView cameraView) {
        // 缺少打开相机的权限
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSIONS_REQUEST_CAMERA);
    }

    @Override
    public void onOpenCameraError(int code) {
        // 相机无法打开
        Toast.makeText(this, "打开相机失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cvCamera.open();
                }
            }
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, CameraViewActivity.class);
        context.startActivity(starter);
    }
}