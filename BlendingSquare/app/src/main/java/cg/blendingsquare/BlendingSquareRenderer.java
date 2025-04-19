package cg.blendingsquare;

import android.content.Context;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

public class BlendingSquareRenderer implements GLSurfaceView.Renderer{
    private BlendingSquare mSquare, mSquare2;

    private Context context;
    private float mTransY = 0.0f;

    public BlendingSquareRenderer(Context context)
    {
        this.context = context;

        mSquare = new BlendingSquare();

        mSquare2 = new BlendingSquare();
    }

    public void onDrawFrame(GL10 gl)
    {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        gl.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL11.GL_MODELVIEW);

        gl.glEnable(GL10.GL_BLEND);

        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        //gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);
        //gl.glColorMask(true, false, true, true);

        gl.glLoadIdentity();

        gl.glTranslatef(0.0f,(float)Math.sin(mTransY), -3.0f);

        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

        gl.glBindTexture(GL10.GL_TEXTURE_2D, mSquare.mTexture0);  // Bind hedly.png

        mSquare.draw(gl);

        gl.glLoadIdentity();

        gl.glTranslatef((float)(Math.sin(mTransY)/2.0f),0.0f, -2.9f);

        gl.glColor4f(1.0f, 1.0f, 1.0f, 0.75f);

        gl.glBindTexture(GL10.GL_TEXTURE_2D, mSquare2.mTexture0);  // Bind goldengate.png

        mSquare2.draw(gl);

        mTransY += .075f;
    }

    /**
     * called whenever the screen changes size or is created at startup.
     */
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        float ratio = (float) width / height;
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
    }

    /**
     * called upon surface creation and deals mainly with initialization steps.
     */
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        gl.glDisable(GL10.GL_DITHER);

        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

        gl.glClearColor(0.25f,0.25f,0.25f,1);

        gl.glEnable(GL10.GL_CULL_FACE);

        gl.glShadeModel(GL10.GL_SMOOTH);

        gl.glEnable(GL10.GL_DEPTH_TEST);

        gl.glEnable(GL10.GL_TEXTURE_2D);

        int resid = R.drawable.hedly;

        int resid2 = R.drawable.truck;

        mSquare.setTextures(gl,this.context,resid,resid2);

        mSquare2.setTextures(gl,this.context,resid2,resid);
    }
}
