
package br.usp.icmc.vicg.gl.app;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;

import com.jogamp.opengl.util.AnimatorBase;
import com.jogamp.opengl.util.FPSAnimator;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.player.Player;
import javazoom.jl.decoder.JavaLayerException;


public class App extends KeyAdapter implements GLEventListener,MouseMotionListener,MouseListener {

    private final Pipeline pipeline;
    private final Scene scene;
    private final Camera camera;
    
    private float alpha;
    private float beta;
    private float delta;
    private float mouseX;
    private float mouseY;
    private float Z;
    private float X;
    private float currentmouseX;
    private float currentmouseY;
    private float rotY;
    private float rotX;
    private int state;
    Robot robot;
    private float mousespeed;
    Timer cursorTimer;
    private int jump;
    
    
   
    

    /**
     *
     */
    public App() throws AWTException {
        
        pipeline = new Pipeline();
        scene = new Scene(pipeline);
        delta = 2;
        jump = 0;
        camera = new Camera();
        robot = new Robot();
        mousespeed = 0.005f;
        rotX = 0;
        rotY = 0;
        state = 0;
        
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        // Get pipeline
        GL3 gl = drawable.getGL().getGL3();

        // Print OpenGL version
        System.out.println("OpenGL Version: " + gl.glGetString(GL.GL_VERSION) + "\n");

        gl.glClearColor(0.5f, 0.5f, 0.5f, 0.0f);
        gl.glClearDepth(1.0f);

        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL.GL_CULL_FACE);

        //inicializa o pipeline
        pipeline.init(gl);
        try {
            //inicializa a cena
            scene.init(gl);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        // Recupera o pipeline
        GL3 gl = drawable.getGL().getGL3();

        // Limpa o frame buffer com a cor definida
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);

       pipeline.getMatrix(Pipeline.MatrixType.PROJECTION).loadIdentity();
       pipeline.getMatrix(Pipeline.MatrixType.PROJECTION).perspective(45.0f, 16/9f, 0.1f, 2500.0f);
             
       camera.display(pipeline);
       scene.display(gl);

        // Força execução das operações declaradas
        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        scene.dispose();
    }

    @Override
     
    //public void Mousemovement()
    public void keyPressed(KeyEvent e) {
        
        switch (e.getKeyCode()) {
    
             
            case KeyEvent.VK_W://gira sobre o eixo-x
                
                camera.andarfrente(pipeline);
                camera.display(pipeline);
                state = 1;
                break;
                
            case KeyEvent.VK_S://gira sobre o eixo-x
                
                camera.andartras(pipeline);
                camera.display(pipeline);
                state = 2;
                break;
            case KeyEvent.VK_A://gira sobre o eixo-y
                
                camera.left(pipeline);
                camera.display(pipeline);
                state = 3;
                break;
                
            case KeyEvent.VK_D://gira sobre o eixo-y
              
               camera.right(pipeline);
               camera.display(pipeline);
               state = 4;
               break;
                
           case KeyEvent.VK_SPACE:
               
              camera.jump();
              camera.setState(state);
              camera.display(pipeline);
              state = 0;
              break; 
        }
    }
    
         
    public static void main(String[] args) throws AWTException, FileNotFoundException, JavaLayerException, InterruptedException {
        // Get GL3 profile (to work with OpenGL 4.0)
        GLProfile profile = GLProfile.get(GLProfile.GL3);

        // Configurations
        GLCapabilities glcaps = new GLCapabilities(profile);
        glcaps.setDoubleBuffered(true);
        glcaps.setHardwareAccelerated(true);

        // Create canvas
        GLCanvas glCanvas = new GLCanvas(glcaps);

        // Add listener to panel
        
        App listener = new App();
        glCanvas.addGLEventListener(listener);
        glCanvas.addKeyListener(listener);
        glCanvas.addMouseMotionListener(listener);
        glCanvas.addMouseListener(listener);

        Frame frame = new Frame("Passeio no paraíso");
        frame.setSize(1280,720);
        frame.add(glCanvas);
        frame.addKeyListener(listener);
        frame.addMouseMotionListener(listener);
        frame.addMouseListener(listener);
        final AnimatorBase animator = new FPSAnimator(glCanvas, 60);
        
        FileInputStream sound_background = new FileInputStream(".\\data\\Sounds\\background.mp3");
        Player playMp3 = new Player(sound_background);
       
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }

                }).start();
            }

        });
        frame.setVisible(true);
        animator.start();
        
        Thread C = new Thread();
        C.sleep(19000);
        playMp3.play();
    }
    
    
    

    @Override
    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
         
        
        currentmouseX = e.getXOnScreen();
        currentmouseY = e.getYOnScreen();
        
        mouseX = -currentmouseX + 1280/2;
        mouseY = -currentmouseY + 720/2;
        
        rotX += mouseX * mousespeed;
        rotY += mouseY * mousespeed;
        
         if(rotY >= 90)
         { 
             rotY = 90;
         }
         else if(rotY <= -90)
         {
             rotY = -90;
         }
         
         if(rotX >= 90)
         {
             rotX = 90;
         }
         else if(rotX <= -90)
         {
             rotX = -90;
         }
        
         camera.setRotX(rotX);
         camera.setRotY(rotY);
         camera.display(pipeline);
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
       X = camera.getX();
       Z = camera.getZ();
       scene.setX(X);
       scene.setZ(Z);
       
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setX(float X) {
        this.X = X;
    }

    public void setZ(float Z) {
        this.Z = Z;
    }
}
