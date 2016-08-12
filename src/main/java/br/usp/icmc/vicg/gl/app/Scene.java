/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.icmc.vicg.gl.app;

import br.usp.icmc.vicg.gl.core.Material;
import br.usp.icmc.vicg.gl.model.Square;
import br.usp.icmc.vicg.gl.model.WiredCube;
import br.usp.icmc.vicg.gl.model.jwavefront.JWavefrontModel;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GL3;

/**
 *
 * @author 
 */
public class Scene {
    
    private float X;
    private float Z;
    private float est1;
    private float est2;
    private float cont;
    private final Pipeline pipeline;
    private final Square floor;
    private final JWavefrontModel skybox;
    private final JWavefrontModel skybox1;
    private final JWavefrontModel skybox2;
    private final JWavefrontModel chaopedra;
    private final JWavefrontModel grama;
    private final JWavefrontModel tree;
    private final JWavefrontModel tree1;
    private final Material mat = new Material();
    private final WiredCube cube;

    public Scene(Pipeline pipeline) {
        
        this.pipeline = pipeline;
        chaopedra = new JWavefrontModel(new File("./data/stone/Files/cenariocerto.obj"));
        skybox = new JWavefrontModel(new File("./data/skydia/skyboxnight2.obj"));
        skybox1 = new JWavefrontModel(new File("./data/skydomenoite/skyboxnight.obj"));
        skybox2 = new JWavefrontModel(new File("./data/skydomenoite/skyboxnight2.obj"));
        grama =  new JWavefrontModel(new File("./data/pedras/pedratipo2.obj"));
        tree = new JWavefrontModel(new File("./data/stone/Files/treelad.obj"));
        tree1 = new JWavefrontModel(new File("./data/stone/Files/treelad.obj"));
        floor = new Square();
        est1 = 0;
        est2 = 0;
        cube = new WiredCube();
    }

    public void init(GL3 gl) throws IOException {
        try {
            
            
            skybox.init(gl, pipeline.getShader());
            skybox.unitize();
            skybox.dump();
            
            skybox1.init(gl, pipeline.getShader());
            skybox1.unitize();
            skybox1.dump();
            
            skybox2.init(gl, pipeline.getShader());
            skybox2.unitize();
            skybox2.dump();
            
            chaopedra.init(gl, pipeline.getShader());
            chaopedra.unitize();
            chaopedra.dump();
            
            grama.init(gl, pipeline.getShader());
            grama.unitize();
            grama.dump();
            
           
            
        } catch (IOException ex) {
            Logger.getLogger(Scene.class.getName()).log(Level.SEVERE, null, ex);
        }

        floor.init(gl, pipeline.getShader());
        cube.init(gl, pipeline.getShader());
  

        //init the light
        pipeline.getLight(Pipeline.LightNumber.LIGHT0).setPosition(new float[]{0.0f, 0.0f, 2.0f, 0.0f});
        pipeline.getLight(Pipeline.LightNumber.LIGHT0).setAmbientColor(new float[]{0.3f, 0.4f, 0.2f, 1.0f});
        pipeline.getLight(Pipeline.LightNumber.LIGHT0).setDiffuseColor(new float[]{0.5f, 0.5f, 0.5f, 1.0f});
        pipeline.getLight(Pipeline.LightNumber.LIGHT0).setSpecularColor(new float[]{0.7f, 0.7f, 0.7f, 1.0f});

        mat.init(gl, pipeline.getShader());
        mat.setDiffuseColor(new float[]{0.75f, 0.75f, 0.75f, 1.0f});
        mat.setSpecularColor(new float[]{0.0f, 0.0f, 0.0f, 1.0f});
    }

    public void display(GL3 gl) {
        
        
         if((X <10.13 && X > 6.7) && Z < -3.96 && Z > -5.3){
            System.out.println("Clicou");
            if(est1 == 0)
            {
                est1 = 1; skybox.dispose(); X = 0; Z = 0;
            }
            else if(est1 == 1)
            {
                est1 = 2; skybox1.dispose(); X = 0; Z = 0;
            }
            else if(est1 == 2)
            {
                est1 = 0; skybox2.dispose(); X = 0; Z = 0;
            }
            
        }  
      
        if(est1 == 0){
        pipeline.getMatrix(Pipeline.MatrixType.MODEL).loadIdentity();
        pipeline.getMatrix(Pipeline.MatrixType.MODEL).translate(0f, -1.6f,0f);
        pipeline.getMatrix(Pipeline.MatrixType.MODEL).scale(500f, 500f, 500f);
        pipeline.bind();
        skybox.draw();
        }
       
        if(est1 == 1){
           
           pipeline.getMatrix(Pipeline.MatrixType.MODEL).loadIdentity();
           pipeline.getMatrix(Pipeline.MatrixType.MODEL).translate(0f, -1.6f,0f);
           pipeline.getMatrix(Pipeline.MatrixType.MODEL).scale(500f, 500f, 500f);
           pipeline.bind();
           skybox1.draw();
        }
        
        if(est1 == 2){
           
           pipeline.getMatrix(Pipeline.MatrixType.MODEL).loadIdentity();
           pipeline.getMatrix(Pipeline.MatrixType.MODEL).translate(0f, -1.6f,0f);
           pipeline.getMatrix(Pipeline.MatrixType.MODEL).scale(500f, 500f, 500f);
           pipeline.bind();
           skybox2.draw();
        }
            
        
        System.out.println("EST  " + est1);
        pipeline.getMatrix(Pipeline.MatrixType.MODEL).loadIdentity();
        pipeline.getMatrix(Pipeline.MatrixType.MODEL).translate(2.4f, 1.7f,0f);
        pipeline.getMatrix(Pipeline.MatrixType.MODEL).scale(10f, 10f, 10f);
        pipeline.bind();
        chaopedra.draw();
        
        pipeline.getMatrix(Pipeline.MatrixType.MODEL).loadIdentity();
        pipeline.getMatrix(Pipeline.MatrixType.MODEL).translate(-1f, 1f,-7f);
        pipeline.getMatrix(Pipeline.MatrixType.MODEL).scale(3f, 3f, 3f);
        pipeline.bind();
        //grama.draw();
        
        
        
       pipeline.getMatrix(Pipeline.MatrixType.MODEL).loadIdentity();
       pipeline.getMatrix(Pipeline.MatrixType.MODEL).translate(0, -0.5f, 0);
       pipeline.getMatrix(Pipeline.MatrixType.MODEL).scale(1, 1, 1);
       pipeline.getMatrix(Pipeline.MatrixType.MODEL).rotate(-90, 1, 0, 0);
       pipeline.bind();

        mat.bind();
        //floor.bind();
       // floor.draw(GL.GL_TRIANGLE_FAN);

        pipeline.getMatrix(Pipeline.MatrixType.MODEL).loadIdentity();
        pipeline.getMatrix(Pipeline.MatrixType.MODEL).translate(0.0f, 0.0f, 1.0f);
        pipeline.getMatrix(Pipeline.MatrixType.MODEL).scale(0.1f, 0.1f, 0.1f);
        pipeline.bind();

        mat.bind();
        cube.bind();
        cube.draw(GL.GL_LINES);
    }

    public void dispose() {
       
        floor.dispose();
        skybox.dispose();
        chaopedra.dispose();
        grama.dispose();
        skybox1.dispose();
        skybox2.dispose();
        
        
    }

    public void setX(float X) {
        this.X = X;
    }

    public void setZ(float Z) {
        this.Z = Z;
    }
    
    

    
    
    
    

}
