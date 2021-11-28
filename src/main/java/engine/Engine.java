package engine;

import engine.camera.FlyCamera;
import engine.file.File;
import engine.log.Log;
import engine.object.Object;
import engine.shader.Shader;
import engine.window.Window;
import org.joml.Vector3f;
import org.lwjgl.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Engine {
    public static int Width = 800, Height = 600;
    public static Window window;
    public static List<Object> objects = new ArrayList<Object>();
    public static FlyCamera camera = new FlyCamera();

    // temp
    public static Shader shader;

    public void Start() {
        new Log(Log.LogEnum.LOG, "LWJGL " + Version.getVersion(), false);

        if (!glfwInit())
            new Log(Log.LogEnum.ERROR, "Unable to initialize GLFW", true);

        window = new Window("Engine");

        glfwMakeContextCurrent(window.GetWindow());
        glfwSwapInterval(1);

        GL.createCapabilities();
        glClearColor(0.5f, 0.79f, 1, 1);

        Object test = new Object("Triangle");

        objects.add(test);

        shader = new Shader("triangle_vert.glsl", "triangle_frag.glsl");

        while (!window.ShouldClose()) {
            Update();
        }
    }

    public void Update() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        GL30.glUseProgram(shader.programId);

        for(Object object : objects) {
            object.Update();
        }

        GL30.glBindVertexArray(0);
        GL30.glUseProgram(0);

        window.UpdateWindow();

        glfwPollEvents();
    }

    public void Exit() {
        window.Destroy();
        glfwTerminate();
    }
}
