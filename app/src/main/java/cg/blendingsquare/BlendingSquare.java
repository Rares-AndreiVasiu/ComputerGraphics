package cg.blendingsquare;

import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.content.Context;
import android.graphics.*;
import android.opengl.*;
public class BlendingSquare {
    private final FloatBuffer mFVertexBuffer;
    private final ByteBuffer mColorBuffer;
    private final ByteBuffer mIndexBuffer;
    public FloatBuffer mTextureBuffer;
    private int[] textures = new int[1];
    public int mTexture0, mTexture1;
    public BlendingSquare(){
        float vertices[] =
                {
                        -1.0f, -1.0f,
                        1.0f, -1.0f,
                        -1.0f, 1.0f,
                        1.0f, 1.0f
                };
        byte maxColor=(byte)255;

        byte colors[] =
                {
                        0, 0, 0, maxColor,
                        maxColor, 0, 0, maxColor,
                        0, 0, 0, maxColor,
                        maxColor, 0, 0, maxColor,
                };

        byte indices[] =
                {
                        0, 3, 1,
                        0, 2, 3
                };
        float[] textureCoords =
                {
                        0.0f , 2.0f,
                        2.0f , 2.0f,
                        0.0f , 0.0f,
                        2.0f , 0.0f
                };
        byte squareColorsYMCA[] =
                {
                        1, 1, 0, 1,
                        0, 1, 1, 1,
                        0, 0, 0, 1,
                        1, 0, 1, 1
                };
        byte squareColorsRGBA[] =
                {
                        1, 0, 0, 1,
                        0, 1, 0, 1,
                        0, 0, 1, 1,
                        1, 1, 1, 1
                };

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);

        vbb.order(ByteOrder.nativeOrder());

        mFVertexBuffer = vbb.asFloatBuffer();

        mFVertexBuffer.put(vertices);

        mFVertexBuffer.position(0);

        mColorBuffer = ByteBuffer.allocateDirect(squareColorsYMCA.length);

        mColorBuffer.put(squareColorsYMCA);

        mColorBuffer.position(0);

        mIndexBuffer = ByteBuffer.allocateDirect(indices.length);

        mIndexBuffer.put(indices);

        mIndexBuffer.position(0);

        ByteBuffer tbb = ByteBuffer.allocateDirect(textureCoords.length * 4);

        tbb.order(ByteOrder.nativeOrder());

        mTextureBuffer = tbb.asFloatBuffer();

        mTextureBuffer.put(textureCoords);

        mTextureBuffer.position(0);
    }
    private int createTexture(GL10 gl, Context context, int resourceId) {
        int[] textures = new int[1];

        gl.glGenTextures(1, textures, 0);

        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        InputStream is = context.getResources().openRawResource(resourceId);

        Bitmap bitmap;

        try {
            bitmap = BitmapFactory.decodeStream(is);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // Ignore
            }
        }

        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

        bitmap.recycle();

        return textures[0];
    }


    public void setTextures(GL10 gl, Context context, int resourceID0, int resourceID1) {
        mTexture0 = createTexture(gl, context, resourceID0);

        mTexture1 = createTexture(gl, context, resourceID1);
    }

    public void multiTexture(GL10 gl, int tex0, int tex1) {
        float combineParameter = GL10.GL_MODULATE;

        gl.glActiveTexture(GL10.GL_TEXTURE0);

        gl.glBindTexture(GL10.GL_TEXTURE_2D, tex0);

        gl.glActiveTexture(GL10.GL_TEXTURE1);

        gl.glBindTexture(GL10.GL_TEXTURE_2D, tex1);

        gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, combineParameter);
    }

    public void draw(GL10 gl) {

        gl.glFrontFace(GL11.GL_CW);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glVertexPointer(2, GL11.GL_FLOAT, 0, mFVertexBuffer);

        gl.glEnable(GL10.GL_TEXTURE_2D);

        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);

        //multiTexture(gl, mTexture0, mTexture1);

        gl.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_BYTE, mIndexBuffer);

        gl.glFrontFace(GL11.GL_CCW);

    }

}
