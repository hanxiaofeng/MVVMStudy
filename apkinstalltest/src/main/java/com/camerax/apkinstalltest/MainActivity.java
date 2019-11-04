package com.camerax.apkinstalltest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_install).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyPermission();
            }
        });
    }

    private void applyPermission() {

        AndPermission.with(this)
                .runtime()
                .permission(Permission.READ_EXTERNAL_STORAGE,Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        install(MainActivity.this);
//                        installUseRoot(Environment.getExternalStorageDirectory()+"/test.apk");
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Toast.makeText(MainActivity.this, "权限未授予", Toast.LENGTH_SHORT).show();
                    }
                })
                .start();
    }


    /**
     * root 静默安装apk
     * @param filePath
     * @return
     */
    private boolean installUseRoot(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            throw new IllegalArgumentException("Please check apk file path!");
        } else {
            boolean result = false;
            Process process = null;
            OutputStream outputStream = null;
            BufferedReader errorStream = null;

            try {
                process = Runtime.getRuntime().exec("su");
                outputStream = process.getOutputStream();
                String command = "pm install -r " + filePath + "\n";
                outputStream.write(command.getBytes());
                outputStream.flush();
                outputStream.write("exit\n".getBytes());
                outputStream.flush();
                process.waitFor();
                errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                StringBuilder msg = new StringBuilder();

                String line;
                while((line = errorStream.readLine()) != null) {
                    msg.append(line);
                }

                Log.d("AutoInstaller", "install msg is " + msg);
                if (!msg.toString().contains("Failure")) {
                    result = true;
                }
            } catch (Exception var17) {
                Log.e("AutoInstaller", var17.getMessage(), var17);
            } finally {
                try {
                    if (outputStream != null) {
                        outputStream.close();
                    }

                    if (errorStream != null) {
                        errorStream.close();
                    }
                } catch (IOException var16) {
                    outputStream = null;
                    errorStream = null;
                    process.destroy();
                }

            }

            return result;
        }
    }

    /**
     * 通过隐式意图调用系统安装程序安装APK
     */
    public void install(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(MainActivity.this, "com.camerax.apkinstalltest.fileprovider", new File(Environment.getExternalStorageDirectory(), "test.apk"));
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "test.apk")), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

}
