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
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;


/**
 * SimpleJOGL.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel)
 * <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class JOGLZad1 implements GLEventListener {

    //statyczne pola okre?laj?ce rotacj? wokó? osi X i Y
    private static float xrot = 0.0f, yrot = 0.0f;
    public static float ambientLight[] = {0.3f, 0.3f, 0.3f, 1.0f};//swiat?o otaczajšce
    public static float diffuseLight[] = {0.7f, 0.7f, 0.7f, 1.0f};//?wiat?o rozproszone
    public static float specular[] = {1.0f, 1.0f, 1.0f, 1.0f}; //?wiat?o odbite
    public static float lightPos[] = {0.0f, 12.0f, 9.0f, 1.0f};//pozycja ?wiat?a
    public static float lightPos2[] = {-12.0f, 2.0f, 0.0f, 1.0f};//pozycja ?wiat?a
    public static Koparka kop;
    public static int czas=0;
    static BufferedImage image1 = null,image2 = null, image3=null;
    static Texture t1 = null, t2 = null, t3=null;
    public static float x=0.0f, z=0.0f, kat=0.0f;

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
        //Obs?uga klawiszy strza?ek
        frame.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    przesun(1.5f);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    przesun(-1.5f);
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    kat += 2.0f;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    kat -= 2.0f;
                }
                if (e.getKeyChar() == ']') {
                    xrot -= 2.0f;
                }
                if (e.getKeyChar() == '[') {
                    xrot += 2.0f;
                }
                if (e.getKeyChar() == 'q') {
                    ambientLight = new float[]{ambientLight[0] - 0.1f, ambientLight[0] - 0.1f, ambientLight[0] - 0.1f, 1};
                }
                if (e.getKeyChar() == 'w') {
                    ambientLight = new float[]{ambientLight[0] + 0.1f, ambientLight[0] + 0.1f, ambientLight[0] + 0.1f, 1};
                }
                if (e.getKeyChar() == 'a') {
                    diffuseLight = new float[]{diffuseLight[0] - 0.1f, diffuseLight[0] - 0.1f, diffuseLight[0] - 0.1f, 1};
                }
                if (e.getKeyChar() == 's') {
                    diffuseLight = new float[]{diffuseLight[0] + 0.1f, diffuseLight[0] + 0.1f, diffuseLight[0] + 0.1f, 1};
                }
                if (e.getKeyChar() == 'z') {
                    specular = new float[]{specular[0] - 0.1f, specular[0] - 0.1f, specular[0] - 0.1f, 1};
                }
                if (e.getKeyChar() == 'x') {
                    specular = new float[]{specular[0] + 0.1f, specular[0] + 0.1f, specular[0] + 0.1f, 1};
                }
                if (e.getKeyChar() == 'n') {
                    lightPos[3] = 0;
                }
                if (e.getKeyChar() == 'm') {
                    lightPos[3] = 1;
                }
                if (e.getKeyChar() == 'l') {
                    lightPos[0] = lightPos[0]+4f;
                }
                if (e.getKeyChar() == 'k') {
                    lightPos[0] = lightPos[0]-4f;
                }
                if (e.getKeyChar() == 'o') {
                    lightPos2[0] = lightPos2[0]+4f;
                }
                if (e.getKeyChar() == 'p') {
                    lightPos2[0] = lightPos2[0]-4f;
                }
                if(e.getKeyChar() == '1')
                    kop.zmienKat1(3.0f);

                if(e.getKeyChar() == '2')
                    kop.zmienKat1(-3.0f);
                
                if(e.getKeyChar() == '3')
                    kop.zmienKat2(3.0f);

                if(e.getKeyChar() == '4')
                    kop.zmienKat2(-3.0f);
                
                if(e.getKeyChar() == '5')
                    kop.zmienKat3(3.0f);

                if(e.getKeyChar() == '6')
                    kop.zmienKat3(-3.0f);
                
                if(e.getKeyChar() == '7')
                    kop.zmienKat4(3.0f);

                if(e.getKeyChar() == '8')
                    kop.zmienKat4(-3.0f);
                            

                

            
                }
            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
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
        kop = new Koparka();

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);

        //warto?ci sk?adowe o?wietlenia i koordynaty ?ród?a ?wiat?a
        //(czwarty parametr okre?la odleg?o?? ?ród?a:
        //0.0f-niesko?czona; 1.0f-okre?lona przez pozosta?e parametry)
        gl.glEnable(GL.GL_LIGHTING); //uaktywnienie o?wietlenia
        //ustawienie parametrów ?ród?a ?wiat?a nr. 0
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, ambientLight, 1); //swiat?o otaczajšce
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuseLight, 1); //?wiat?o rozproszone
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, specular, 1); //?wiat?o odbite
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, lightPos, 1); //pozycja ?wiat?a
        gl.glEnable(GL.GL_LIGHT0); //uaktywnienie ?ród?a ?wiat?a nr. 0

        gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, ambientLight, 1); //swiat?o otaczajšce
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, diffuseLight, 1); //?wiat?o rozproszone
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, specular, 1); //?wiat?o odbite
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, lightPos2, 1); //pozycja ?wiat?a
        //gl.glEnable(GL.GL_LIGHT1); //uaktywnienie ?ród?a ?wiat?a nr. 0

        gl.glEnable(GL.GL_COLOR_MATERIAL); //uaktywnienie ?ledzenia kolorów
        gl.glEnable(GL.GL_TEXTURE_2D);
        //kolory b?dš ustalane za pomocš glColor
        gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);
        //Ustawienie jasno?ci i odblaskowo?ci obiektów
        float specref[] = {1.0f, 1.0f, 1.0f, 1.0f}; //parametry odblaskowo?ci
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specref, 0);

        gl.glMateriali(GL.GL_FRONT, GL.GL_SHININESS, 128);

        gl.glEnable(GL.GL_DEPTH_TEST);
        // Setup the drawing area and shading mode
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
        try
        {
            image1 = ImageIO.read(getClass().getResourceAsStream("/bok.jpg"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/trawa.jpg"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/niebo.jpg"));
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, exc.toString());
            return;
        }

        t1 = TextureIO.newTexture(image1, false);
        t2 = TextureIO.newTexture(image2, false);
        t3 = TextureIO.newTexture(image3, false);

        gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_BLEND | GL.GL_MODULATE);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
        
        

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
        glu.gluPerspective(90.0f, h, 2.0, 300.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, ambientLight, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuseLight, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, specular, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, lightPos, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, ambientLight, 1); //swiat?o otaczajšce
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, diffuseLight, 1); //?wiat?o rozproszone
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, specular, 1); //?wiat?o odbite
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, lightPos2, 1); //pozycja ?wiat?a

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
       //gl.glTranslatef(0.0f, 96.0f, -99.0f); //przesuni?cie o 6 jednostek
        gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f); //rotacja wokó? osi X
        gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f); //rotacja wokó? osi Y

        gl.glFlush();
        rysujScene(gl,t1, t2, t3);
        
 
    }
    void drzewo(GL gl) {
        gl.glColor3f(0.14f,0.55f,0.13f);
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, -1.0f);
        gl.glScalef(0.4f, 0.4f, 0.4f);
        stozek(gl);
        gl.glTranslatef(0.0f, 0.0f, +1.8f);
        gl.glScalef(1.4f, 1.4f, 1.4f);
        stozek(gl);
        gl.glTranslatef(0.0f, 0.0f, +1.3f);
        gl.glScalef(1.2f, 1.2f, 1.2f);
        stozek(gl);
        gl.glTranslatef(0.0f, 0.0f, 1.0f);
        gl.glColor3f(0.55f,0.27f,0.08f);
        gl.glScalef(0.6f, 0.6f, 1.0f);
        walec(gl);
        gl.glPopMatrix();
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
    public void rysujDrzewo(GL gl){
        gl.glColor3f(0.14f,0.55f,0.13f);
        gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        gl.glTranslatef(0.0f,0.0f,-1.0f);
        stozek(gl);
        gl.glTranslatef(0.0f,0.0f,1.0f);
        gl.glScalef(1.2f, 1.2f, 1.2f);
        stozek(gl);
        gl.glTranslatef(0.0f,0.0f,1.0f);
        gl.glScalef(1.2f, 1.2f, 1.2f);
        stozek(gl);
        gl.glTranslatef(0.0f,0.0f,1.0f);
        gl.glColor3f(0.55f,0.27f,0.08f);
        walec(gl);
    }
    public void walec(GL gl) {
//wywo³ujemy automatyczne normalizowanie normalnych
//bo operacja skalowania je zniekszta³ci
        gl.glEnable(GL.GL_NORMALIZE);
        float x, y, kat;
        gl.glBegin(GL.GL_QUAD_STRIP);
        for (kat = 0.0f; kat < (2.0f * Math.PI); kat += (Math.PI / 32.0f)) {
            x = 0.5f * (float) Math.sin(kat);
            y = 0.5f * (float) Math.cos(kat);
            gl.glNormal3f((float) Math.sin(kat), (float) Math.cos(kat), 0.0f);
            gl.glVertex3f(x, y, -1.0f);
            gl.glVertex3f(x, y, 0.0f);
        }
        gl.glEnd();
        gl.glNormal3f(0.0f, 0.0f, -1.0f);
        x = y = kat = 0.0f;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex3f(0.0f, 0.0f, -1.0f); //srodek kola
        for (kat = 0.0f; kat < (2.0f * Math.PI); kat += (Math.PI / 32.0f)) {
            x = 0.5f * (float) Math.sin(kat);
            y = 0.5f * (float) Math.cos(kat);
            gl.glVertex3f(x, y, -1.0f);
        }
        gl.glEnd();
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        x = y = kat = 0.0f;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex3f(0.0f, 0.0f, 0.0f); //srodek kola
        for (kat = 2.0f * (float) Math.PI; kat > 0.0f; kat -= (Math.PI / 32.0f)) {
            x = 0.5f * (float) Math.sin(kat);
            y = 0.5f * (float) Math.cos(kat);
            gl.glVertex3f(x, y, 0.0f);
        }
        gl.glEnd();
    }

    public void stozek(GL gl) {
//wywo³ujemy automatyczne normalizowanie normalnych
        gl.glEnable(GL.GL_NORMALIZE);
        float x, y, kat;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex3f(0.0f, 0.0f, -2.0f); //wierzcholek stozka
        for (kat = 0.0f; kat < (2.0f * Math.PI); kat += (Math.PI / 32.0f)) {
            x = (float) Math.sin(kat);
            y = (float) Math.cos(kat);
            gl.glNormal3f((float) Math.sin(kat), (float) Math.cos(kat), -2.0f);
            gl.glVertex3f(x, y, 0.0f);
        }
        gl.glEnd();
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3f(0.0f, 0.0f, 0.0f); //srodek kola
        for (kat = 2.0f * (float) Math.PI; kat > 0.0f; kat -= (Math.PI / 32.0f)) {
            x = (float) Math.sin(kat);
            y = (float) Math.cos(kat);
            gl.glVertex3f(x, y, 0.0f);
        }
        gl.glEnd();
    }

    
    
  /*  public void rysowanieWalca(GL gl){
        float kat, p, q;
        float x=0.0f;
        float z=0.0f;
        float s=1.0f;
        gl.glEnable(GL.GL_NORMALIZE);
        gl.glColor3f(0.01f,0.7f,0.3f);
        gl.glBegin(GL.GL_TRIANGLE_FAN);
                p = x+s*(float)Math.sin(Math.PI*(31/32));
                q = z+s*(float)Math.cos(Math.PI*(31/32));
                float[] podstawa1={x,-1.0f,z,
                                0.0f,-1.0f,1.0f,
                                p,-1.0f,q};
                float[] norm1=WyznaczNormalna(podstawa1, 0, 3, 6);
                gl.glNormal3fv(norm1,0);
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
                p = x+s*(float)Math.sin(Math.PI*(1/32));
                q = z+s*(float)Math.cos(Math.PI*(1/32));
                float[] podstawa2={x,2.0f,z,
                                0.0f,2.0f,1.0f,
                                p,2.0f,q};
                float[] norm2=WyznaczNormalna(podstawa2, 0, 3, 6);
                gl.glNormal3fv(norm2,0);
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
            float[] kraw1={0.0f,2.0f,1.0f,
                           0.0f,-1.0f,1.0f,
                           0.0f,0.0f,0.0f};
            float[] norm3=WyznaczNormalna(kraw1, 0, 3, 6);
            gl.glNormal3fv(norm3,0);
            float prev[]={0.0f,2.0f,1.0f};
            for(kat = 0.0f; kat < (2.0f*Math.PI);kat+=(Math.PI/12.0f))
            {
                p = x+s*(float)Math.sin(kat);
                q = z+s*(float)Math.cos(kat);
                float[] kraw={p,2.0f,q,
                           p,-1.0f,q,
                           prev[0],prev[1],prev[2]};
                float[] norm=WyznaczNormalna(kraw, 0, 3, 6);
                gl.glNormal3fv(norm,0);
                prev[0]=p;
                prev[2]=q;
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
    */
    public void rysowanieOstroslupa(GL gl){
        
        gl.glBindTexture(GL.GL_TEXTURE_2D, t1.getTextureObject());
        gl.glBegin(GL.GL_QUADS);//podstawa

            float[] podstawa={-1.0f,-1.0f,1.0f,
                                -1.0f,-1.0f,1.0f,
                                1.0f,-1.0f,-1.0f};
            float[] norm1=WyznaczNormalna(podstawa, 0, 3, 6);
            
            
            gl.glNormal3fv(norm1,0);
            //gl.glColor3f(0.1f,0.4f,1.0f);
            gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f,-1.0f,1.0f);
            gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f,-1.0f,-1.0f);
            gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(1.0f,-1.0f,-1.0f);
            gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(1.0f,-1.0f,1.0f);
        gl.glEnd();
        gl.glBindTexture(GL.GL_TEXTURE_2D, t2.getTextureObject());
        gl.glBegin(GL.GL_TRIANGLES);
            float[] sciana1={-1.0f,-1.0f,1.0f,
                                1.0f,-1.0f,1.0f,
                                0.0f, 0.0f, 0.0f};
            float[] norm2=WyznaczNormalna(sciana1, 0, 3, 6);
            gl.glNormal3fv(norm2,0);
            //gl.glColor3f(0.3f,0.1f,0.5f);
            gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f,-1.0f,1.0f);
            gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(1.0f,-1.0f,1.0f);
            gl.glTexCoord2f(0.5f, 0.5f); gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glEnd();
        gl.glBegin(GL.GL_TRIANGLES);
            float[] sciana2={1.0f,-1.0f,1.0f,
                             1.0f,-1.0f,-1.0f,
                             0.0f, 0.0f, 0.0f};
            float[] norm3=WyznaczNormalna(sciana2, 0, 3, 6);
            gl.glNormal3fv(norm3,0);
            //gl.glColor3f(0.6f,0.2f,0.8f);
            gl.glVertex3f(1.0f,-1.0f,1.0f);
            gl.glVertex3f(1.0f,-1.0f,-1.0f);
            gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glEnd();
        gl.glBegin(GL.GL_TRIANGLES);
            float[] sciana3={1.0f,-1.0f,-1.0f,
                             -1.0f,-1.0f,-1.0f,
                             0.0f, 0.0f, 0.0f};
            float[] norm4=WyznaczNormalna(sciana3, 0, 3, 6);
            gl.glNormal3fv(norm4,0);
            //gl.glColor3f(0.2f,0.2f,0.2f);
            gl.glVertex3f(1.0f,-1.0f,-1.0f);
            gl.glVertex3f(-1.0f,-1.0f,-1.0f);
            gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glEnd();
        gl.glBegin(GL.GL_TRIANGLES);
            float[] sciana4={-1.0f,-1.0f,-1.0f,
                             -1.0f,-1.0f,1.0f,
                             0.0f, 0.0f, 0.0f};
            float[] norm5=WyznaczNormalna(sciana4, 0, 3, 6);
            gl.glNormal3fv(norm5,0);
           // gl.glColor3f(0.1f,0.8f,0.3f);
            gl.glVertex3f(-1.0f,-1.0f,-1.0f);
            gl.glVertex3f(-1.0f,-1.0f,1.0f);
            gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glEnd();
            
    }
    
    public float[] WyznaczNormalna(float[] punkty, int ind1, int ind2, int ind3) {
         float[] norm = new float[3];
         float[] wektor0 = new float[3];
         float[] wektor1 = new float[3];
 
         for (int i = 0; i < 3; i++) {
             wektor0[i] = punkty[i + ind1] - punkty[i + ind2];
             wektor1[i] = punkty[i + ind2] - punkty[i + ind3];
         }
 
         norm[0] = wektor0[1] * wektor1[2] - wektor0[2] * wektor1[1];
         norm[1] = wektor0[2] * wektor1[0] - wektor0[0] * wektor1[2];
         norm[2] = wektor0[0] * wektor1[1] - wektor0[1] * wektor1[0];
         float d
                 = (float) Math.sqrt((norm[0] * norm[0]) + (norm[1] * norm[1]) + (norm[2] * norm[2]));
         if (d == 0.0f) {
             d = 1.0f;
         }
         norm[0] /= d;
         norm[1] /= d;
         norm[2] /= d;
 
         return norm;
     }
    
    public static void przesun(float d){
        if(x-d*Math.sin(kat*(Math.PI/180.0f))<97 && x-d*Math.sin(kat*(Math.PI/180.0f))>-97){
            x-=d*Math.sin(kat*(Math.PI/180.0f));
        }
        if(z+d*Math.cos(kat*(Math.PI/180.0f)) < 187 && z+d*Math.cos(kat*(Math.PI/180.0f)) > -7){
            z+=d*Math.cos(kat*(Math.PI/180.0f));
        }
        
    }
    
    public void rysujScene(GL gl, Texture t1, Texture t2, Texture t3) {

        //gl.glRotatef(kat, 1.0f, 0.0f, 0.0f); //rotacja wokó? osi X
        
        gl.glPushMatrix();
        kop.Rysuj(gl, x, z, kat);
        gl.glPopMatrix();
        gl.glRotatef(kat, 0.0f, 1.0f, 0.0f); //rotacja wokó? osi Y
        gl.glTranslatef(x, 98.0f, -90.0f+z);
        
//szescian
        gl.glColor3f(1.0f, 1.0f, 1.0f);
//za³adowanie tekstury wczytanej wczeœniej z pliku krajobraz.bmp
        gl.glBindTexture(GL.GL_TEXTURE_2D, t1.getTextureObject());
        gl.glBegin(GL.GL_QUADS);
//œciana przednia
        gl.glNormal3f(0.0f, 0.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-100.0f, 100.0f, 100.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(100.0f, 100.0f, 100.0f);
        gl.glTexCoord2f(0.0f, 0.9f);
        gl.glVertex3f(100.0f, -100.0f, 100.0f);
        gl.glTexCoord2f(1.0f, 0.9f);
        gl.glVertex3f(-100.0f, -100.0f, 100.0f);
//œciana tylnia
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 0.9f);
        gl.glVertex3f(-100.0f, -100.0f, -100.0f);
        gl.glTexCoord2f(0.0f, 0.9f);
        gl.glVertex3f(100.0f, -100.0f, -100.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(100.0f, 100.0f, -100.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-100.0f, 100.0f, -100.0f);
//œciana lewa
        gl.glNormal3f(1.0f, 0.0f, 0.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-100.0f, 100.0f, -100.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-100.0f, 100.0f, 100.0f);
        gl.glTexCoord2f(1.0f, 0.9f);
        gl.glVertex3f(-100.0f, -100.0f, 100.0f);
        gl.glTexCoord2f(0.0f, 0.9f);
        gl.glVertex3f(-100.0f, -100.0f, -100.0f);
//œciana prawa
        gl.glNormal3f(-1.0f, 0.0f, 0.0f);
        gl.glTexCoord2f(0.0f, 0.9f);
        gl.glVertex3f(100.0f, -100.0f, -100.0f);
        gl.glTexCoord2f(1.0f, 0.9f);
        gl.glVertex3f(100.0f, -100.0f, 100.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(100.0f, 100.0f, 100.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(100.0f, 100.0f, -100.0f);
        gl.glEnd();

//œciana dolna
//za³adowanie tekstury wczytanej wczeœniej z pliku niebo.bmp
        gl.glBindTexture(GL.GL_TEXTURE_2D, t2.getTextureObject());
        //ustawienia aby tekstura siê powiela³a
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        gl.glBegin(GL.GL_QUADS);
        gl.glNormal3f(0.0f, 1.0f, 0.0f);
 //koordynaty ustawienia 16 x 16 kwadratów powielonej tekstury na œcianie dolnej
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(100.0f, -100.0f, 100.0f);
        gl.glTexCoord2f(0.0f, 32.0f);
        gl.glVertex3f(100.0f, -100.0f, -100.0f);
        gl.glTexCoord2f(32.0f, 32.0f);
        gl.glVertex3f(-100.0f, -100.0f, -100.0f);
        gl.glTexCoord2f(32.0f, 0.0f);
        gl.glVertex3f(-100.0f, -100.0f, 100.0f);
        gl.glEnd();

 //œciana gorna
//za³adowanie tekstury wczytanej wczeœniej z pliku trawa.bmp
        gl.glBindTexture(GL.GL_TEXTURE_2D, t3.getTextureObject());
        gl.glBegin(GL.GL_QUADS);
        gl.glNormal3f(0.0f, -1.0f, 0.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-100.0f, 100.0f, 100.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-100.0f, 100.0f, -100.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(100.0f, 100.0f, -100.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(100.0f, 100.0f, 100.0f);
        gl.glEnd();
        
        
    }
    public void rysowanieSzescianu(GL gl){
        float[] sciana1={-1.0f,-1.0f,1.0f,
                        1.0f,-1.0f,1.0f,
                        1.0f,1.0f,1.0f};
        gl.glBindTexture(GL.GL_TEXTURE_2D, t1.getTextureObject());
        gl.glBegin(GL.GL_QUADS);
            //œciana przednia
            float[] norm1=WyznaczNormalna(sciana1, 0, 3, 6);
            gl.glNormal3fv(norm1,0);
            //gl.glColor3f(1.0f,0.0f,0.0f);
            gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f,-1.0f,1.0f);
            gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(1.0f,-1.0f,1.0f);
            gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(1.0f,1.0f,1.0f);
            gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f,1.0f,1.0f);
            
        gl.glEnd();
        gl.glBindTexture(GL.GL_TEXTURE_2D, t1.getTextureObject());
        gl.glBegin(GL.GL_QUADS);
          
            float[] sciana2={-1.0f,1.0f,-1.0f,
                        1.0f,1.0f,-1.0f,
                        1.0f,-1.0f,-1.0f};
            float[] norm2=WyznaczNormalna(sciana2, 0, 3, 6);
            gl.glNormal3fv(norm2,0);
            //sciana tylnia
            //gl.glColor3f(0.0f,1.0f,0.0f);
                         
            gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f,1.0f,-1.0f);
            gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(1.0f,1.0f,-1.0f);
            gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(1.0f,-1.0f,-1.0f);
            gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f,-1.0f,-1.0f);
            //œciana lewa
            float[] sciana3={-1.0f,-1.0f,-1.0f,
                        -1.0f,-1.0f,1.0f,
                        -1.0f,1.0f,1.0f};
            float[] norm3=WyznaczNormalna(sciana3, 0, 3, 6);
            gl.glNormal3fv(norm3,0);
            //gl.glColor3f(0.0f,0.0f,1.0f);
            gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f,-1.0f,-1.0f);
            gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f,-1.0f,1.0f);
            gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f,1.0f,1.0f);
            gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f,1.0f,-1.0f);
            //œciana prawa
            
        gl.glEnd();
        gl.glBindTexture(GL.GL_TEXTURE_2D, t2.getTextureObject());
        gl.glBegin(GL.GL_QUADS);
            float[] sciana4={1.0f,1.0f,-1.0f,
                        1.0f,1.0f,1.0f,
                        1.0f,-1.0f,1.0f};
            float[] norm4=WyznaczNormalna(sciana4, 0, 3, 6);
            gl.glNormal3fv(norm4,0);
            
            //gl.glColor3f(1.0f,1.0f,0.0f);
            gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(1.0f,1.0f,-1.0f);
            gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(1.0f,1.0f,1.0f);
            gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(1.0f,-1.0f,1.0f);
            gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(1.0f,-1.0f,-1.0f);
            //œciana dolna
            float[] sciana5={-1.0f,-1.0f,1.0f,
                        -1.0f,-1.0f,-1.0f,
                        1.0f,-1.0f,-1.0f};
            float[] norm5=WyznaczNormalna(sciana5, 0, 3, 6);
            gl.glNormal3fv(norm5,0);
            
           // gl.glColor3f(1.0f,0.0f,1.0f);
            gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f,-1.0f,1.0f);
            gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f,-1.0f,-1.0f);
            gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(1.0f,-1.0f,-1.0f);
            gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(1.0f,-1.0f,1.0f);
            //œciana górna
            float[] sciana6={1.0f,1.0f,1.0f,
                        1.0f,1.0f,-1.0f,
                        -1.0f,1.0f,-1.0f};
            float[] norm6=WyznaczNormalna(sciana6, 0, 3, 6);
            gl.glNormal3fv(norm6,0);
            
            //gl.glColor3f(0.1f,0.4f,1.0f);
            gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(1.0f,1.0f,1.0f);
            gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(1.0f,1.0f,-1.0f);
            gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f,1.0f,-1.0f);
            gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f,1.0f,1.0f);
        gl.glEnd();

    }


    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

