package engine.shader;

import engine.file.File;
import engine.gamemanager.GameManager;
import engine.log.Log;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;

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
}
