package engine.shader;

import engine.file.File;
import engine.log.Log;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    public int vertexShaderId, fragmentShaderId;
    public int programId;

    public Shader(String vertexShaderFile, String fragmentShaderFile) {
        String vertexShaderSource = File.ReadFile("shaders/" + vertexShaderFile);
        String fragmentShaderSource = File.ReadFile("shaders/" + fragmentShaderFile);

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
    }

    public void Update() {
        GL30.glUseProgram(programId);
    }

    public void CheckShader(int shaderID) {
        IntBuffer result = BufferUtils.createIntBuffer(1);
        glGetShaderiv(shaderID, GL_COMPILE_STATUS, result);

        if(result.get() == GL_FALSE)
            new Log(Log.LogEnum.ERROR, "Shader " + shaderID + " error!", true);
    }

    public void CompileShaders(String shaderSource, int shaderID) {
        glShaderSource(shaderID, shaderSource);
        glCompileShader(shaderID);

        CheckShader(shaderID);

        new Log(Log.LogEnum.SUCCESS, "Shader " + shaderID + " loaded!", false);
    }
}
