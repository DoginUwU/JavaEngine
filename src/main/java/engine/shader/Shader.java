package engine.shader;

import engine.Engine;
import engine.file.File;
import engine.gamemanager.GameManager;
import engine.light.Light;
import engine.log.Log;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    public int vertexShaderId, fragmentShaderId;
    public int programId;
    public String shaderName;

    public Shader(String shaderName) {
        this.shaderName = shaderName;

        String vertexShaderSource = File.ReadFile("shaders/" + shaderName + "_vert.glsl");
        String fragmentShaderSource = File.ReadFile("shaders/" + shaderName + "_frag.glsl");

        if(vertexShaderSource.isEmpty() || fragmentShaderSource.isEmpty())
            new Log(Log.LogEnum.ERROR, "Shaders is empty", true);

        vertexShaderId = glCreateShader(GL_VERTEX_SHADER);
        fragmentShaderId = glCreateShader(GL_FRAGMENT_SHADER);

        CompileShaders(vertexShaderSource, vertexShaderId);
        CompileShaders(fragmentShaderSource, fragmentShaderId);

        programId = glCreateProgram();

        glAttachShader(programId, vertexShaderId);
        glAttachShader(programId, fragmentShaderId);
        glLinkProgram(programId);

        IntBuffer result = BufferUtils.createIntBuffer(1);
        glGetProgramiv(programId, GL_LINK_STATUS, result);

        if(result.get() == GL_FALSE)
            new Log(Log.LogEnum.LINK, "Program error", true);

        glDetachShader(programId, vertexShaderId);
        glDetachShader(programId, fragmentShaderId);
        glDeleteShader(vertexShaderId);
        glDeleteShader(fragmentShaderId);

        GameManager.shaders.add(this);
    }

    public void Update() {
        GL30.glUseProgram(programId);
    }

    public void CheckShader(int shaderID) {
        IntBuffer result = BufferUtils.createIntBuffer(1);
        glGetShaderiv(shaderID, GL_COMPILE_STATUS, result);

        if(result.get() == GL_FALSE) {
            IntBuffer infoLength = BufferUtils.createIntBuffer(1);
            glGetShaderiv(shaderID, GL_INFO_LOG_LENGTH, infoLength);

            if(infoLength.get() == 0) return;

            String log = glGetShaderInfoLog(shaderID);

            new Log(Log.LogEnum.ERROR, "Shader " + shaderName + " error! [" + log + "]", true);
        }
    }

    public void CompileShaders(String shaderSource, int shaderID) {
        glShaderSource(shaderID, shaderSource);
        glCompileShader(shaderID);

        CheckShader(shaderID);

        new Log(Log.LogEnum.SUCCESS, "Shader " + shaderName + " loaded!", false);
    }

    public void setUniform(String uniformName, int value) {
        int location = glGetUniformLocation(programId, uniformName);
        glUniform1i(location, value);
    }

    public void setUniform(String uniformName, float value) {
        int location = glGetUniformLocation(programId, uniformName);
        glUniform1f(location, value);
    }

    public void setUniform(String uniformName, Vector3f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            int location = glGetUniformLocation(programId, uniformName);
            glUniform3fv(location, value.get(stack.mallocFloat(3)));
        }
    }

    public void setUniform(String uniformName, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            int location = glGetUniformLocation(programId, uniformName);
            glUniformMatrix4fv(location, false, value.get(stack.mallocFloat(16)));
        }
    }

    public void setUniform(String uniformName, Light light) {
        setUniform(uniformName + "_direction", light.direction.x);
        setUniform(uniformName + "_intensity", light.intensity);
    }
}
