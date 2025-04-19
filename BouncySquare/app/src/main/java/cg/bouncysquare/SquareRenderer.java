package cg.bouncysquare;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;
import android.opengl.GLSurfaceView;
import java.lang.Math;

public class SquareRenderer implements GLSurfaceView.Renderer{
    private Square mSquare;

    private float mTransY = 0.0f;

    public SquareRenderer()
    {
        mSquare = new Square();
    }

    public void onDrawFrame(GL10 gl)
    {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL10.GL_MODELVIEW);

        gl.glLoadIdentity();

        gl.glTranslatef(0.0f,(float)Math.sin(mTransY), -3.0f);

        mTransY += 0.075f;

        /* tell OpenGL to expect both vertex and color data */
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        mSquare.draw(gl);
    }

    /**
     * called whenever the screen changes size or is created at startup.
     */
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        /*
            glViewport merely permits you to specify the actual dimensions
            and placement of your OpenGL window.
        */
        gl.glViewport(0, 0, width, height);

        /*
        case, we
        switch to the GL_PROJECTION matrix, which is the one that projects the 3D scene to your 2D
        screen. glLoadIdentity resets the matrix to its initial values to erase any previous settings.
        */
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

        /*
        tell OpenGL ES to do what it thinks best by accepting certain
        trade-offs: usually speed vs. quality.
        */
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

        //the background is black.
        gl.glClearColor(0,0,0,1);

        gl.glEnable(GL10.GL_CULL_FACE);

        gl.glShadeModel(GL10.GL_SMOOTH);

        gl.glEnable(GL10.GL_DEPTH_TEST);
    }
}
