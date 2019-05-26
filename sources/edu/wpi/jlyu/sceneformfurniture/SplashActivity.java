package edu.wpi.jlyu.sceneformfurniture;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    /* renamed from: edu.wpi.jlyu.sceneformfurniture.SplashActivity$1 */
    class C00371 implements Runnable {
        C00371() {
        }

        public void run() {
            SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
            SplashActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0032R.layout.activity_splash);
        new Handler().postDelayed(new C00371(), 2500);
    }
}
