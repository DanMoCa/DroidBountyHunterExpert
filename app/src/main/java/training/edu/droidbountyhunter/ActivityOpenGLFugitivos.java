package training.edu.droidbountyhunter;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import training.edu.utils.SimpleRender;

/**
 * Created by Dan14z on 08/09/2017.
 */

public class ActivityOpenGLFugitivos extends AppCompatActivity {

    private GLSurfaceView surfaceView;
    public static String foto;
    public static float distorsion;
    public static String defaultValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foto = getIntent().getStringExtra("foto");
        distorsion = Float.parseFloat(getIntent().getStringExtra("distorsion"));
        defaultValue = getIntent().getStringExtra("default");

        surfaceView = new GLSurfaceView(this);
        surfaceView.setRenderer(new SimpleRender(this));
        setContentView(surfaceView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        surfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        surfaceView.onPause();
    }
}
