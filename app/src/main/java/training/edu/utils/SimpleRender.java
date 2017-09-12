package training.edu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import training.edu.droidbountyhunter.ActivityOpenGLFugitivos;
import training.edu.droidbountyhunter.R;

/**
 * Created by Dan14z on 08/09/2017.
 */

public class SimpleRender implements GLSurfaceView.Renderer {

    private Context context;
    private FloatBuffer vertexBuffer;
    private FloatBuffer textureBuffer;
    private ShortBuffer indexBuffer;
    private int carasLength;

    public SimpleRender(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //TODO auto-generated method to stub
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d("SURFACE CHANGED","textura modificada " + width + "x" + height);
        float positivo = ActivityOpenGLFugitivos.distorsion;
        float negativo = ActivityOpenGLFugitivos.distorsion * (-1.0f);

        float[] vertices = {
                negativo,1f,0f,
                -1f,-1f,0f,
                0f,-1f,0f,
                1f,-1f,0f,
                positivo,1f,0f
        };

        short[] caras = {
                0,1,2,
                0,2,4,
                4,2,3
        };

        carasLength = caras.length;
        float textura[] = {
                0f,0f,
                0f,1f,
                0.5f,1f,
                1f,1f,
                1f,0f
        };

        ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        vertexByteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = vertexByteBuffer.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        ByteBuffer textureByteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        textureByteBuffer.order(ByteOrder.nativeOrder());
        textureBuffer = textureByteBuffer.asFloatBuffer();
        textureBuffer.put(textura);
        textureBuffer.position(0);

        ByteBuffer faceBytebuffer = ByteBuffer.allocateDirect(vertices.length * 2);
        faceBytebuffer.order(ByteOrder.nativeOrder());
        indexBuffer = faceBytebuffer.asShortBuffer();
        indexBuffer.put(caras);
        indexBuffer.position(0);

        gl.glViewport(0,0,width,height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl,45.0f,(float)width/(float)height,0.1f,100.0f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0,0,-7);
        draw(gl);
    }

    public void cargarTextura(GL10 gl) {
        Bitmap bitmap;
        if(ActivityOpenGLFugitivos.defaultValue.equals("0")){
            bitmap = PictureTools.decodeSampledBitmapFromUri(ActivityOpenGLFugitivos.foto,200,200);
        }else{
            bitmap = BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher);
        }
        int[] textureIds = new int[1];
        gl.glGenTextures(1,textureIds,0);
        int textureId = textureIds[0];

        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glBindTexture(GL10.GL_TEXTURE_2D,textureId);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_LINEAR);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D,0,bitmap,0);
    }

    public void draw(GL10 gl){
        cargarTextura(gl);

        // Cotra las agujas del reloj
        gl.glFrontFace(GL10.GL_CCW);
        //Habilitar el buffer del arreglo de vertices para la escritura y cuales se usaran para el renderizado
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // Especifica la localizacion y el formato de los datos de un array de vertices a utilizar para el renderizado
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,vertexBuffer);
        // Habilita el buffer para la textura del grafico
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        // Señala donde se encuetnra el buffer del color
        gl.glTexCoordPointer(2,GL10.GL_FLOAT,0,textureBuffer);
        // Dibujar las superficies
        gl.glDrawElements(GL10.GL_TRIANGLES, carasLength, GL10.GL_UNSIGNED_SHORT, indexBuffer);
        // Desactiva el buffer de vertices
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        // Desactiva el buffer de color
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    }
}
