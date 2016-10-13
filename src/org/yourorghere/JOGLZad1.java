package org.yourorghere;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    private static float xrot = 0.0f, yrot = 0.0f;

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
        frame.addKeyListener(new KeyListener()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_UP)
                xrot -= 1.0f;
                if(e.getKeyCode() == KeyEvent.VK_DOWN)
                xrot +=1.0f;
                if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                yrot += 1.0f;
                if(e.getKeyCode() == KeyEvent.VK_LEFT)
                yrot -=1.0f;
            }
            public void keyReleased(KeyEvent e){}
            public void keyTyped(KeyEvent e){}
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
       // gl.glEnable(GL.GL_CULL_FACE);
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
       
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -6.0f); //przesuniêcie o 6 jednostek
        gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f); //rotacja wokó³ osi X
        gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f); //rotacja wokó³ osi Y
        
        rysowanieMorgenszternu(gl);
        gl.glFlush();
    }
    
    public void rysowanieMorgenszternu(GL gl){
        float kat, p, q;
        float x=0.0f;
        float z=0.0f;
        float s=0.07f;
        /*gl.glColor3f(0.01f,0.7f,0.3f);
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glVertex3f(x,-0.4f,z); //œrodek
            for(kat = (float) (2.0f*Math.PI); kat >0.0f;kat-=(Math.PI/32.0f))
            {
                p = x+s*(float)Math.sin(kat);
                q = z+s*(float)Math.cos(kat);
                gl.glVertex3f(p, -0.4f, q); //kolejne punkty
            }*/
        gl.glEnd();//góra
        gl.glColor3f(0.4f,0.8f,0.5f);
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glVertex3f(x,0.4f,z);
            for(kat = 0.0f; kat < (2.0f*Math.PI);kat+=(Math.PI/32.0f))
            {
                p = x+s*(float)Math.sin(kat);
                q = z+s*(float)Math.cos(kat);
                gl.glVertex3f(p, -0.4f, q); //kolejne punkty
            }
        gl.glEnd();
        gl.glColor3f(0.4f,0.1f,0.8f);//dó³
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glVertex3f(x,-1.2f,z);
            for(kat = 0.0f; kat < (2.0f*Math.PI);kat+=(Math.PI/32.0f))
            {
                p = x+s*(float)Math.sin(kat);
                q = z+s*(float)Math.cos(kat);
                gl.glVertex3f(p, -0.4f, q); //kolejne punkty
            }
        gl.glEnd();
     
        gl.glColor3f(0.8f,0.2f,0.8f);//lewo
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glVertex3f(-0.8f,-0.4f,z);
            for(kat = 0.0f; kat < (2.0f*Math.PI);kat+=(Math.PI/32.0f))
            {
                p = x+s*(float)Math.sin(kat);
                q = z+s*(float)Math.cos(kat);
                gl.glVertex3f(0.0f, p-0.4f, q); //kolejne punkty
            }
        gl.glEnd();
        gl.glColor3f(0.2f,0.0f,0.3f);//prawo
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glVertex3f(0.8f,-0.4f,z);
            for(kat = (float) (2.0f*Math.PI); kat >0.0f;kat-=(Math.PI/32.0f))
            {
                p = x+s*(float)Math.sin(kat);
                q = z+s*(float)Math.cos(kat);
                gl.glVertex3f(0.0f, p-0.4f, q); //kolejne punkty
            }
        gl.glEnd();
        
 
        gl.glColor3f(0.0f,0.5f,0.9f);//przód
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glVertex3f(0.0f,-0.4f,0.8f);
            for(kat = 0.0f; kat < (2.0f*Math.PI);kat+=(Math.PI/32.0f))
            {
                p = x+s*(float)Math.sin(kat);
                q = z+s*(float)Math.cos(kat);
                gl.glVertex3f(q, p-0.4f, 0.0f); //kolejne punkty
            }
        gl.glEnd();
        
        gl.glColor3f(0.4f,0.9f,0.5f);//ty³
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glVertex3f(0.0f,-0.4f,-0.8f);
            for(kat = 0.0f; kat < (2.0f*Math.PI);kat+=(Math.PI/32.0f))
            {
                p = x+s*(float)Math.sin(kat);
                q = z+s*(float)Math.cos(kat);
                gl.glVertex3f(q, p-0.4f, 0.0f); //kolejne punkty
            }
        gl.glEnd();
        
        gl.glColor3f(0.1f,0.1f,0.1f);//ty³-lewo
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glVertex3f(0.6f,-0.4f,0.6f);
            for(kat = 0.0f; kat < (2.0f*Math.PI);kat+=(Math.PI/32.0f))
            {
                p = x+s*(float)Math.sin(kat);
                q = z+s*(float)Math.cos(kat);
                gl.glVertex3f(q, p-0.4f, 0.0f); //kolejne punkty
            }
        gl.glEnd();
        
        gl.glColor3f(0.5f,0.1f,0.1f);//ty³-prawo
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glVertex3f(-0.6f,-0.4f,0.6f);
            for(kat = 0.0f; kat < (2.0f*Math.PI);kat+=(Math.PI/32.0f))
            {
                p = x+s*(float)Math.sin(kat);
                q = z+s*(float)Math.cos(kat);
                gl.glVertex3f(q, p-0.4f, 0.0f); //kolejne punkty
            }
        gl.glEnd();
        
        gl.glColor3f(0.5f,0.1f,0.7f);//przód-prawo
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glVertex3f(0.6f,-0.4f,-0.6f);
            for(kat = 0.0f; kat < (2.0f*Math.PI);kat+=(Math.PI/32.0f))
            {
                p = x+s*(float)Math.sin(kat);
                q = z+s*(float)Math.cos(kat);
                gl.glVertex3f(q, p-0.4f, 0.0f); //kolejne punkty
            }
        gl.glEnd();
        
        gl.glColor3f(0.5f,0.7f,0.2f);//przód-lewo
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glVertex3f(-0.6f,-0.4f,-0.6f);
            for(kat = 0.0f; kat < (2.0f*Math.PI);kat+=(Math.PI/32.0f))
            {
                p = x+s*(float)Math.sin(kat);
                q = z+s*(float)Math.cos(kat);
                gl.glVertex3f(q, p-0.4f, 0.0f); //kolejne punkty
            }
        gl.glEnd();
        
        gl.glEnd();//góra-lewo-przód
        gl.glColor3f(0.4f,0.0f,0.5f);
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glVertex3f(x-0.4f,0.0f,z+0.4f);
            for(kat = 0.0f; kat < (2.0f*Math.PI);kat+=(Math.PI/32.0f))
            {
                p = x+s*(float)Math.sin(kat);
                q = z+s*(float)Math.cos(kat);
                gl.glVertex3f(p, -0.4f, q); //kolejne punkty
            }
       gl.glEnd();
       
       gl.glEnd();//góra-prawo-przód
        gl.glColor3f(0.1f,0.3f,0.5f);
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glVertex3f(x+0.4f,0.0f,z+0.4f);
            for(kat = 0.0f; kat < (2.0f*Math.PI);kat+=(Math.PI/32.0f))
            {
                p = x+s*(float)Math.sin(kat);
                q = z+s*(float)Math.cos(kat);
                gl.glVertex3f(p, -0.4f, q); //kolejne punkty
            }
       gl.glEnd();
       
       gl.glEnd();//góra-prawo-ty³
        gl.glColor3f(0.1f,0.3f,0.8f);
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glVertex3f(x+0.4f,0.0f,z-0.4f);
            for(kat = 0.0f; kat < (2.0f*Math.PI);kat+=(Math.PI/32.0f))
            {
                p = x+s*(float)Math.sin(kat);
                q = z+s*(float)Math.cos(kat);
                gl.glVertex3f(p, -0.4f, q); //kolejne punkty
            }
       gl.glEnd();
       
       gl.glEnd();//góra-lewo-ty³
        gl.glColor3f(0.9f,0.3f,0.2f);
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glVertex3f(x-0.4f,0.0f,z-0.4f);
            for(kat = 0.0f; kat < (2.0f*Math.PI);kat+=(Math.PI/32.0f))
            {
                p = x+s*(float)Math.sin(kat);
                q = z+s*(float)Math.cos(kat);
                gl.glVertex3f(p, -0.4f, q); //kolejne punkty
            }
       gl.glEnd();
       
        gl.glColor3f(0.1f,0.5f,0.2f);//dó³-lewo-przód
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glVertex3f(x-0.4f,-0.8f,z+0.4f);
            for(kat = 0.0f; kat < (2.0f*Math.PI);kat+=(Math.PI/32.0f))
            {
                p = x+s*(float)Math.sin(kat);
                q = z+s*(float)Math.cos(kat);
                gl.glVertex3f(p, -0.4f, q); //kolejne punkty
            }
        gl.glEnd();
        
        gl.glColor3f(0.1f,0.9f,0.9f);//dó³-prawo-przód
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glVertex3f(x+0.4f,-0.8f,z+0.4f);
            for(kat = 0.0f; kat < (2.0f*Math.PI);kat+=(Math.PI/32.0f))
            {
                p = x+s*(float)Math.sin(kat);
                q = z+s*(float)Math.cos(kat);
                gl.glVertex3f(p, -0.4f, q); //kolejne punkty
            }
        gl.glEnd();
        
        gl.glColor3f(0.9f,0.4f,0.0f);//dó³-prawo-ty³
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glVertex3f(x+0.4f,-0.8f,z-0.4f);
            for(kat = 0.0f; kat < (2.0f*Math.PI);kat+=(Math.PI/32.0f))
            {
                p = x+s*(float)Math.sin(kat);
                q = z+s*(float)Math.cos(kat);
                gl.glVertex3f(p, -0.4f, q); //kolejne punkty
            }
        gl.glEnd();
        
        gl.glColor3f(0.4f,0.4f,0.9f);//dó³-lewo-ty³
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glVertex3f(x-0.4f,-0.8f,z-0.4f);
            for(kat = 0.0f; kat < (2.0f*Math.PI);kat+=(Math.PI/32.0f))
            {
                p = x+s*(float)Math.sin(kat);
                q = z+s*(float)Math.cos(kat);
                gl.glVertex3f(p, -0.4f, q); //kolejne punkty
            }
        gl.glEnd();
    
    }
    public void rysowanieStozka(GL gl){
        float kat, p, q;
        float x=0.0f;
        float z=0.0f;
        float s=1.0f;
        gl.glColor3f(0.01f,0.7f,0.3f);
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glVertex3f(x,-1.0f,z); //œrodek
            for(kat = (float) (2.0f*Math.PI); kat >0.0f;kat-=(Math.PI/32.0f))
            {
                p = x+s*(float)Math.sin(kat);
                q = z+s*(float)Math.cos(kat);
                gl.glVertex3f(p, -1.0f, q); //kolejne punkty
            }
        gl.glEnd();
        gl.glColor3f(0.4f,0.3f,0.5f);
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glVertex3f(x,1.0f,z);
            for(kat = 0.0f; kat < (2.0f*Math.PI);kat+=(Math.PI/32.0f))
            {
                p = x+s*(float)Math.sin(kat);
                q = z+s*(float)Math.cos(kat);
                gl.glVertex3f(p, -1.0f, q); //kolejne punkty
            }
        gl.glEnd();
    }
    
    
    
    public void rysowanieWalca(GL gl){
        float kat, p, q;
        float x=0.0f;
        float z=0.0f;
        float s=1.0f;
        gl.glColor3f(0.01f,0.7f,0.3f);
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glVertex3f(x,-1.0f,z); //œrodek
            for(kat = (float) (2.0f*Math.PI); kat >0.0f;kat-=(Math.PI/32.0f))
            {
                p = x+s*(float)Math.sin(kat);
                q = z+s*(float)Math.cos(kat);
                gl.glVertex3f(p, -1.0f, q); //kolejne punkty
            }
        gl.glEnd();
        gl.glColor3f(0.01f,0.3f,0.5f);
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glVertex3f(x,2.0f,z); //œrodek
            for(kat = 0.0f; kat < (2.0f*Math.PI);kat+=(Math.PI/32.0f))
            {
                p = x+s*(float)Math.sin(kat);
                q = z+s*(float)Math.cos(kat);
                gl.glVertex3f(p, 2.0f, q); //kolejne punkty
            }
        gl.glEnd();
        gl.glColor3f(0.6f,0.3f,0.2f);
        gl.glBegin(GL.GL_QUAD_STRIP);
            for(kat = 0.0f; kat < (2.0f*Math.PI);kat+=(Math.PI/32.0f))
            {
                p = x+s*(float)Math.sin(kat);
                q = z+s*(float)Math.cos(kat);
                gl.glVertex3f(p, 2.0f, q); //kolejne punkty
                gl.glVertex3f(p, -1.0f, q); //kolejne punkty
            }
        gl.glEnd();
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
    
    public void rysowanieOstroslupa(GL gl){
        gl.glBegin(GL.GL_QUADS);//podstawa
            gl.glColor3f(0.1f,0.4f,1.0f);
            gl.glVertex3f(-1.0f,-1.0f,1.0f);
            gl.glVertex3f(-1.0f,-1.0f,-1.0f);
            gl.glVertex3f(1.0f,-1.0f,-1.0f);
            gl.glVertex3f(1.0f,-1.0f,1.0f);
        gl.glEnd();
        gl.glBegin(GL.GL_TRIANGLES);
            gl.glColor3f(0.3f,0.1f,0.5f);
            gl.glVertex3f(-1.0f,-1.0f,1.0f);
            gl.glVertex3f(1.0f,-1.0f,1.0f);
            gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glEnd();
        gl.glBegin(GL.GL_TRIANGLES);
            gl.glColor3f(0.6f,0.2f,0.8f);
            gl.glVertex3f(1.0f,-1.0f,1.0f);
            gl.glVertex3f(1.0f,-1.0f,-1.0f);
            gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glEnd();
        gl.glBegin(GL.GL_TRIANGLES);
            gl.glColor3f(0.2f,0.2f,0.2f);
            gl.glVertex3f(1.0f,-1.0f,-1.0f);
            gl.glVertex3f(-1.0f,-1.0f,-1.0f);
            gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glEnd();
        gl.glBegin(GL.GL_TRIANGLES);
            gl.glColor3f(0.1f,0.8f,0.3f);
            gl.glVertex3f(-1.0f,-1.0f,-1.0f);
            gl.glVertex3f(-1.0f,-1.0f,1.0f);
            gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glEnd();
            
    }
    
    public void rysowanieSzescianu(GL gl){
        gl.glBegin(GL.GL_QUADS);
            //œciana przednia
            gl.glColor3f(1.0f,0.0f,0.0f);
            gl.glVertex3f(-1.0f,-1.0f,1.0f);
            gl.glVertex3f(1.0f,-1.0f,1.0f);
            gl.glVertex3f(1.0f,1.0f,1.0f);
            gl.glVertex3f(-1.0f,1.0f,1.0f);
            //sciana tylnia
            gl.glColor3f(0.0f,1.0f,0.0f);
            gl.glVertex3f(-1.0f,1.0f,-1.0f);
            gl.glVertex3f(1.0f,1.0f,-1.0f);
            gl.glVertex3f(1.0f,-1.0f,-1.0f);
            gl.glVertex3f(-1.0f,-1.0f,-1.0f);
            //œciana lewa
            gl.glColor3f(0.0f,0.0f,1.0f);
            gl.glVertex3f(-1.0f,-1.0f,-1.0f);
            gl.glVertex3f(-1.0f,-1.0f,1.0f);
            gl.glVertex3f(-1.0f,1.0f,1.0f);
            gl.glVertex3f(-1.0f,1.0f,-1.0f);
            //œciana prawa
            gl.glColor3f(1.0f,1.0f,0.0f);
            gl.glVertex3f(1.0f,1.0f,-1.0f);
            gl.glVertex3f(1.0f,1.0f,1.0f);
            gl.glVertex3f(1.0f,-1.0f,1.0f);
            gl.glVertex3f(1.0f,-1.0f,-1.0f);
            //œciana dolna
            gl.glColor3f(1.0f,0.0f,1.0f);
            gl.glVertex3f(-1.0f,-1.0f,1.0f);
            gl.glVertex3f(-1.0f,-1.0f,-1.0f);
            gl.glVertex3f(1.0f,-1.0f,-1.0f);
            gl.glVertex3f(1.0f,-1.0f,1.0f);
            //œciana górna
            gl.glColor3f(0.1f,0.4f,1.0f);
            gl.glVertex3f(1.0f,1.0f,1.0f);
            gl.glVertex3f(1.0f,1.0f,-1.0f);
            gl.glVertex3f(-1.0f,1.0f,-1.0f);
            gl.glVertex3f(-1.0f,1.0f,1.0f);
        gl.glEnd();

    }
    
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}

