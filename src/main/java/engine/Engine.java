package engine;

import engine.camera.Camera;
import engine.camera.FlyCamera;
import engine.log.Log;
import engine.object.Object;
import engine.shader.Shader;
import engine.time.DeltaTime;
import engine.window.Window;
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
    public static DeltaTime deltaTime;

    public static Engine instance;

    // temp
    public static Camera camera = new Camera();

    public Engine () {
        Start();
    }

    public void Start() {
        instance = this;

        new Log(Log.LogEnum.LOG, "LWJGL " + Version.getVersion(), false);

        if (!glfwInit())
            new Log(Log.LogEnum.ERROR, "Unable to initialize GLFW", true);

        window = new Window("Engine");

        glfwMakeContextCurrent(window.GetWindow());
        glfwSwapInterval(1);

        GL.createCapabilities();
        glClearColor(0.5f, 0.79f, 1, 1);

        camera.setPosition(0, 1, 5);

        deltaTime = new DeltaTime();

        glEnable(GL_DEPTH_TEST);
    }

    public void Update() {
        deltaTime.Tick();

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        camera.Update();

        for(Object object : objects) {
            object.Update();
            GL30.glUseProgram(0);
        }

        GL30.glBindVertexArray(0);

        window.UpdateWindow();

        glfwPollEvents();
    }

    public void Exit() {
        window.Destroy();
        glfwTerminate();
    }

    public boolean EngineShouldClose() {
        return window.ShouldClose();
    }
}
