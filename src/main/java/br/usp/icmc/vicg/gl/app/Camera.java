
package br.usp.icmc.vicg.gl.app;

import java.awt.AWTException;
import java.awt.Robot;

/**
 *
 * @author Léo
 */

public class Camera {
 
    private float ang;
    private float posX;
    private float posY;
    private float posZ;
    private float lX;
    private float lY;
    private float lZ;
    private float sen;
    private float senx;
    private float sendesc;
    private float rotX;
    private float rotY;
    private float X;
    private float Z;
    private int jump;
    private float flag;
    private int state;
    Robot robot;
    
        public Camera() throws AWTException{
            
            ang = 90;
            posX = 0.0f;
            posZ = 1f;
            posY = 0.0f;
            flag = 0;
            lX = 0;
            lY = 0;
            lZ = 0;
            sen = 0;
            senx = 0;
            sendesc = 0;
            jump = 0;
            robot = new Robot();
            
            
        }
        
        public void display(Pipeline pipeline){
            
               
            lX = ((float)Math.sin(rotX))*((float)Math.cos(rotY));
            lY = ((float)Math.sin(rotY));
            lZ = ((float)Math.cos(rotX))*((float)Math.cos(rotY));
      
             pipeline.getMatrix(Pipeline.MatrixType.VIEW).loadIdentity();
             pipeline.getMatrix(Pipeline.MatrixType.VIEW).lookAt(
                posX, posY + ((float) Math.sin(sen))/50, posZ,
                posX + lX , posY + lY, posZ + lZ ,
                0, 1, 0);
            
            System.out.println("posX   " + posX + "lookx    " + (posX + lX));
            System.out.println("posY   " + posY + "lookY    " + (posY + lY));
            System.out.println("posZ   " + posZ + "lookZ    " + (posZ + lZ));
            System.out.println("ang  " + sen );
            
            robot.mouseMove(1280/2, 720/2);
            pipeline.getMatrix(Pipeline.MatrixType.VIEW).bind();
            
            if(jump == 1)
            {
                if(posY < 0.99 && flag == 0)
                {
                    
                    posY += ((float) Math.sin(senx))/10;
                    senx += 0.2f;
                    if(state == 1)//pulo pra frente
                    {
                       posX += ((float) Math.cos(Math.toRadians(ang)))/100 + lX/10;
                       posZ -= ((float) Math.sin(Math.toRadians(ang)))/100 - lZ/10;
                    }
                    
                    if(state == 2)//pulo pra tras
                    {
                        posX += ((float) Math.cos(Math.toRadians(ang))/100 - lX/10);
                        posZ += ((float) Math.sin(Math.toRadians(ang))/100  - lZ/10 );
                    }
                    
                    if(state == 3)//pulo left
                    {
                        posZ -= ((float) Math.cos(Math.toRadians(ang))/100 + lX/10);
                        posX -= ((float) Math.sin(Math.toRadians(ang))/100 - lZ/10);
                    }
                    if(state == 4)
                    {
                        posZ -= ((float) Math.cos(Math.toRadians(ang))/100 - lX/10);
                        posX -= ((float) Math.sin(Math.toRadians(ang))/100 + lZ/10);
                    }

                }
                else{
                    
                posY -= ((float) Math.sin(sendesc))/10;
                sendesc += 0.2f;
                    if(state == 1)
                    {
                       posX += ((float) Math.cos(Math.toRadians(ang)))/100 + lX/10;
                       posZ -= ((float) Math.sin(Math.toRadians(ang)))/100 - lZ/10;
                    }
                    if(state == 2)
                    {
                        posX += ((float) Math.cos(Math.toRadians(ang))/100 - lX/10);
                        posZ += ((float) Math.sin(Math.toRadians(ang))/100  - lZ/10 );
                    }
                    if(state == 3)
                    {
                        posZ -= ((float) Math.cos(Math.toRadians(ang))/100 + lX/10);
                        posX -= ((float) Math.sin(Math.toRadians(ang))/100 - lZ/10);
                    }
                    if(state == 4)
                    {
                        posZ -= ((float) Math.cos(Math.toRadians(ang))/100 - lX/10);
                        posX -= ((float) Math.sin(Math.toRadians(ang))/100 + lZ/10);
                    }

                flag = 1;
                if(posY <= 0.3)
                {
                    jump = 0;
                    senx = 0;
                    sendesc = 0;
                }
              }   
            }
           
        }
        
        public void andarfrente(Pipeline pipeline){//CERTO
               
            if(posX < -7.5 && (posX + lX)< -7.5 || posZ > 9.1 && (posZ + lZ)> 9.1 //colisão
                  ||  posZ < -9.1 && (posZ + lZ)< -9.1 || posX > 8 && (posX + lX)> 8
                  || ((posX < 7.73 && posX > 6.4) && ((posX + lX) < 7.73 && (posX + lX) > 6.4))  
                  && ((posZ < -4.2 && posZ > -5) && ((posZ + lZ) < 7.8 && (posZ + lZ) > 7.0))
                  || ((posX > 6.32) && ((posX + lX) > 6.6 && (posX + lX) < 7.7)) && ((posZ < -2.5 && posZ > -3.5) 
                  && ((posZ + lZ) < -3.48 && (posZ + lZ) > -3.5)))
                   
               {
                   posX += 0;
                   posZ -= 0;
                   sen+= 0.2f;
               }
               else
               {
               posX += ((float) Math.cos(Math.toRadians(ang)))/200 + lX/20;
               posZ -= ((float) Math.sin(Math.toRadians(ang)))/200 - lZ/20;
               sen+= 0.2f;
               }
               
        }
        
        public void andartras(Pipeline pipeline){//CERTO
              
             if(posX < -7.5 && ((posX + lX) > -8.5) && ((posX + lX) < -8) //parede pedra ok
               || posZ < -8 && ((posZ + lZ) > -9) && ((posZ + lZ) < -8) //direita da parede pedra ok
               || posZ > 8 && ((posZ + lZ) > 8) && ((posZ + lZ) < 10.2) //esquerda da parede pedra ok
               || posX > 7 && ((posX + lX) > 7.5) && ((posX + lX) < 10))//paralela da parede pedra ok
             {
               
               posX += 0;
               posZ -= 0;
               sen+= 0.2f;
               
             }
             else 
             {
               posX += ((float) Math.cos(Math.toRadians(ang))/200 - lX/20);
               posZ += ((float) Math.sin(Math.toRadians(ang))/200  - lZ/20 );
               sen+= .2f; 
                   
             }
        }
        public void left(Pipeline pipeline){
            
               if(posX < -7.5 && (posX + lX)< -7.5 || posZ > 9.1 && (posZ + lZ)> 9.1 //colisão
                  ||  posZ < -9.1 && (posZ + lZ)< -9 || posX > 8 && (posX + lX)> 8
                  || ((posX < 7.73 && posX > 6.4) && ((posX + lX) < 7.73 && (posX + lX) > 6.4))  
                  && ((posZ < -4.2 && posZ > -5) && ((posZ + lZ) < -3.96 && (posZ + lZ) > -5.3))
                  || ((posX > 6.32) && ((posX + lX) > 6.6)) && ((posZ < -2.5 && posZ > -3.5) 
                  && ((posZ + lZ) < -3.45 && (posZ + lZ) > -4.1)))
             {
               
               posX += 0;
               posZ -= 0;
               sen+= 0.2f;
               
             }
             else 
              {
               posZ -= ((float) Math.cos(Math.toRadians(ang))/100 + lX/20);
               posX -= ((float) Math.sin(Math.toRadians(ang))/100 - lZ/20);
               sen+= .2f;
             }
        }
        
        public void right(Pipeline pipeline){
            
             if(posX < -7.5 && (posX + lX)< -7.5 || posZ > 9 && (posZ + lZ)> 9 //colisão
                  ||  posZ < -9.1 && (posZ + lZ)< -9 || posX > 8 && (posX + lX)> 8
                  || ((posX < 7.73 && posX > 6.4) && ((posX + lX) < 7.73 && (posX + lX) > 6.4))  
                  && ((posZ < -4.2 && posZ > -5) && ((posZ + lZ) < -3.96 && (posZ + lZ) > -5.3))
                  || ((posX > 6.32) && ((posX + lX) > 6.6)) && ((posZ < -2.5 && posZ > -3.5) 
                  && ((posZ + lZ) < -2.48 && (posZ + lZ) > -3.5)))
                   
               {
                   posX += 0;
                   posZ -= 0;
                   sen+= 0.2f;
               }
             else
             {
            
              posZ -= ((float) Math.cos(Math.toRadians(ang))/100 - lX/20);
              posX -= ((float) Math.sin(Math.toRadians(ang))/100 + lZ/20);
              sen+= .2f;
             }
             
             
        }
        
        
        public void jump()
        {       
                jump = 1;
                flag =0;
        }
        
         

    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

   
    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public void setState(int state) {
        this.state = state;
    }

    public float getX() {
        return X = posX + lX;
    }

    public float getZ() {
        return Z =  posZ + lZ;
    }
        
   
}


