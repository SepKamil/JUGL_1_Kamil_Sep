package org.yourorghere;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;



/**
 * JOGLZad1.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class JOGLZad1 implements GLEventListener {

    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new JOGLZad1());
        frame.add(canvas);
        frame.setSize(640, 480);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!
        
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
       /* // Reset the current matrix to the "identity"
        gl.glLoadIdentity();
        gl.glColor3f(0.4f,0.3f,0.5f);
        gl.glBegin(GL.GL_TRIANGLES);
            gl.glVertex3f(-1.0f, 1.0f, -6.0f);
            gl.glVertex3f(-2.0f,-1.0f, -6.0f);
            gl.glVertex3f( 0.0f,-1.0f, -6.0f);
        gl.glEnd();
        gl.glColor3f(0.2f,0.8f,0.3f);
        gl.glBegin(GL.GL_QUADS);
            gl.glVertex3f(1.0f, 1.0f, -6.0f);
            gl.glVertex3f(2.0f, 1.0f, -6.0f);
            gl.glVertex3f(2.0f, -1.0f, -6.0f);
            gl.glVertex3f(1.0f, -1.0f, -6.0f);
        gl.glEnd();
        
        gl.glColor3f(0.7f,0.1f,0.1f);
        gl.glBegin(GL.GL_TRIANGLES);
            gl.glVertex3f(-1.0f, 2.0f, -6.0f);
            gl.glVertex3f(-3.0f, 1.0f, -6.0f);
            gl.glVertex3f( 1.0f, 1.0f, -6.0f);
        gl.glEnd();
        gl.glColor3f(0.5f,0.4f,0.3f);
        gl.glBegin(GL.GL_QUADS);
            gl.glVertex3f(0.9f, 1.0f, -6.0f);
            gl.glVertex3f(-2.9f, 1.0f, -6.0f);
            gl.glVertex3f(-2.9f, -1.0f, -6.0f);
            gl.glVertex3f(0.9f, -1.0f, -6.0f);
        gl.glEnd();
        gl.glColor3f(0.01f,0.7f,0.3f);
        gl.glBegin(GL.GL_QUADS);
            gl.glVertex3f(0.0f, 0.7f, -5.9f);
            gl.glVertex3f(0.6f, 0.7f, -5.9f);
            gl.glVertex3f(0.6f, -1.0f, -5.9f);
            gl.glVertex3f(0.0f, -1.0f, -5.9f);
        gl.glEnd();
        */
        gl.glColor3f(0.01f,0.7f,0.3f);
        rysowanieDziwaka(gl);
        gl.glFlush();
    }

    public void rysowanieKolka(GL gl, float x, float y, float s){
        float kat, p, q;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex3f(x,y,-6.0f); //œrodek
        for(kat = 0.0f; kat < (2.0f*Math.PI);kat+=(Math.PI/32.0f))
        {
            p = x+s*(float)Math.sin(kat);
            q = y+s*(float)Math.cos(kat);
            gl.glVertex3f(p, q, -6.0f); //kolejne punkty
        }
    gl.glEnd();
    
    
    }
    
    public void rysowanieDziwaka(GL gl){
        float kat, p, q;
        gl.glBegin(GL.GL_LINE_STRIP);
        gl.glVertex3f(-1.0f,0.0f,-6.0f); //pocz¹tek
        for(kat = 0.0f; kat <= (3.0f*Math.PI);kat+=(Math.PI/32.0f))
        {
            p = 0.3f*kat-1.0f;
            q = 0.3f*(float)Math.sin(kat);
            gl.glVertex3f(p, q, -6.0f); //kolejne punkty
        }
        gl.glVertex3f((float) 0.3f*kat-1.0f, 0.3f*(float)Math.sin(3.0f*Math.PI)-1.0f, -6.0f);
        gl.glVertex3f((float) -1.0f, 0.3f*(float)Math.sin(3.0f*Math.PI)-1.0f, -6.0f);
        gl.glVertex3f((float) -1.0f, 0.3f*(float)Math.sin(3.0f*Math.PI), -6.0f);
    gl.glEnd();
    
    
    }
    
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}

