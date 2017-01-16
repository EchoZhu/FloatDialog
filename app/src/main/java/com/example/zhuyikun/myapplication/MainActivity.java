package com.example.zhuyikun.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.zhuyikun.myapplication.Views.EmotionRainAnimation;
import com.example.zhuyikun.myapplication.Views.FloatDialog;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button mButton;
    private FrameLayout mEmotionRainContainer;
    private EmotionRainAnimation rainAnimation;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("正在上传图片,请稍后");

//        FloatDialog dialog = new FloatDialog();
//        dialog.setOutContext(this);
//        dialog.show(getSupportFragmentManager(), getLocalClassName());


//        mEmotionRainContainer = (FrameLayout) findViewById(R.id.emotion_rain_container);
//        rainAnimation = new EmotionRainAnimation(this);
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.MATCH_PARENT,
//                FrameLayout.LayoutParams.MATCH_PARENT);
//        rainAnimation.setLayoutParams(params);
//        mEmotionRainContainer.addView(rainAnimation);

        final long starTime = System.currentTimeMillis();
        mButton = (Button) findViewById(R.id.btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
//                rainAnimation.startAnimation();    ((System.currentTimeMillis() - starTime) % (1000 * 60)) / 1000
//                long userTime = ((System.currentTimeMillis() - starTime) % (1000 * 60)) / 1000;
//                Log.d("useTime",userTime+"");
                progressDialog.show();
                uploadPic();
//                if (!Settings.System.canWrite(MainActivity.this)) {
//                    Toast.makeText(MainActivity.this,"请在该设置页面勾选",Toast.LENGTH_LONG).show();
//                    Uri selfPackageUri = Uri.parse("package:"
//                            + MainActivity.this.getPackageName());
//                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
//                            selfPackageUri);
//                    startActivity(intent);
//                }
            }

            private void uploadPic() {
                //创建okHttpClient对象
                OkHttpClient mOkHttpClient = new OkHttpClient();
                RequestBody formBody = new FormEncodingBuilder()
                        .add("mission_id","123456")
                        .add("filename","filename.jpg")
                        .add("encode","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")
                        .build();
                //创建一个Request
                final Request request = new Request.Builder()
                        .url("http://139.224.34.135:8080/water/user.req?action=imageUpload")
                        .post(formBody)
                        .build();
                //new call
                Call call = mOkHttpClient.newCall(request);
                //请求加入调度
                call.enqueue(new Callback()
                {
                    @Override
                    public void onFailure(Request request, IOException e)
                    {
                        Looper.prepare();
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this,"fail",Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }

                    @Override
                    public void onResponse(final Response response) throws IOException
                    {
                        //String htmlStr =  response.body().string();
                        Looper.prepare();
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this,"success",Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                });
            }
        });
    }
}

