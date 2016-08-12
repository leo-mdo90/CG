/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.usp.icmc.vicg.gl.app;

import br.usp.icmc.vicg.gl.core.Light;
import br.usp.icmc.vicg.gl.matrix.Matrix4;
import br.usp.icmc.vicg.gl.util.Shader;
import br.usp.icmc.vicg.gl.util.ShaderFactory;
import javax.media.opengl.GL3;

/**
 *
 * @author paulovich
 */
public class Pipeline {

    private final Shader shader;
    private final Matrix4 modelMatrix;
    private final Matrix4 projectionMatrix;
    private final Matrix4 viewMatrix;
    private final Matrix4 modelviewMatrix;
    private final Matrix4 normalMatrix;
    private final Light light;

    public enum LightNumber {

        LIGHT0
    };

    public enum MatrixType {

        MODEL, VIEW, PROJECTION
    };

    public Pipeline() {
        // Carrega os shaders
        shader = ShaderFactory.getInstance(ShaderFactory.ShaderType.JWAVEFRONT_SHADER);
        modelMatrix = new Matrix4();
        projectionMatrix = new Matrix4();
        viewMatrix = new Matrix4();
        modelviewMatrix = new Matrix4();
        normalMatrix = new Matrix4();
        light = new Light();
    }

    public void init(GL3 gl) {
        //inicializa e ativa o shader
        shader.init(gl);
        shader.bind();

        //inicializa as matrizes ModelView e Projection
        projectionMatrix.init(gl, shader.getUniformLocation("u_projectionMatrix"));
        modelviewMatrix.init(gl, shader.getUniformLocation("u_modelviewMatrix"));
        normalMatrix.init(gl, shader.getUniformLocation("u_normalMatrix"));
        viewMatrix.init(gl, shader.getUniformLocation("u_viewMatrix"));
                
        //inicializa a luz
        light.init(gl, shader);
    }

    public Matrix4 getMatrix(MatrixType type) {
        if (type == MatrixType.MODEL) {
            return modelMatrix;
        } else if (type == MatrixType.VIEW) {
            return viewMatrix;
        } else if (type == MatrixType.PROJECTION) {
            return projectionMatrix;
        }
        return null;
    }

    public Shader getShader() {
        return shader;
    }

    public Light getLight(LightNumber number) {
        if (number == LightNumber.LIGHT0) {
            return light;
        }
        return null;
    }

    public void bind() {
        projectionMatrix.bind();
        modelviewMatrix.copy(viewMatrix).multiply(modelMatrix).bind();
        normalMatrix.copy(modelviewMatrix).bind();
        viewMatrix.bind();
        light.bind();
    }

}
