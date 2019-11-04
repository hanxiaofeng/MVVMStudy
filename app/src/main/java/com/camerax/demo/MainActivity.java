package com.camerax.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraX;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {

    TextureView textureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textureView = findViewById(R.id.texture_view);

        applyPermission();
    }

    private void applyPermission() {

        AndPermission.with(this)
                .runtime()
                .permission(Permission.CAMERA)
                .onGranted(permissions -> {
                    startCamera();
                })
                .onDenied(permissions -> {
                    Toast.makeText(this, "未授权，无法使用！", Toast.LENGTH_SHORT).show();
                })
                .start();
    }

    /**
     * open camera
     */
    private void startCamera() {

        PreviewConfig.Builder builder = new PreviewConfig.Builder();
        PreviewConfig previewConfig = builder.setTargetResolution(new Size(640, 480)).build();

        Preview preview = new Preview(previewConfig);

        preview.setOnPreviewOutputUpdateListener(new Preview.OnPreviewOutputUpdateListener() {
            @Override
            public void onUpdated(@NonNull Preview.PreviewOutput output) {
                ViewGroup viewGroup = (ViewGroup) textureView.getParent();
                viewGroup.removeView(textureView);
                viewGroup.addView(textureView, 0);

                textureView.setSurfaceTexture(output.getSurfaceTexture());
                updateTransForm();
            }
        });

        CameraX.bindToLifecycle(this, preview);
    }

    private void updateTransForm() {
        Matrix matrix = new Matrix();

        float centerX = textureView.getWidth() / 2f;
        float centerY = textureView.getHeight() / 2f;

        int rotationDegress = 0;

        switch (textureView.getDisplay().getRotation()) {
            case Surface
                    .ROTATION_0:
                rotationDegress = 0;
                break;
            case Surface
                    .ROTATION_90:
                rotationDegress = 90;
                break;
            case Surface
                    .ROTATION_180:
                rotationDegress = 180;
                break;
            case Surface
                    .ROTATION_270:
                rotationDegress = 270;
                break;
        }

        matrix.postRotate(-rotationDegress, centerX, centerY);
        textureView.setTransform(matrix);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
