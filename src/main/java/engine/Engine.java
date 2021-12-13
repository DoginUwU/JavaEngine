package engine;

import engine.camera.Camera;
import engine.gamemanager.GameManager;
import engine.light.Light;
import engine.log.Log;
import engine.object.Object;
import engine.time.DeltaTime;
import engine.window.Window;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Engine {
    public static int Width = 800, Height = 600;
    public static DeltaTime deltaTime = new DeltaTime();

    // Empty
    public static Window window = null;
    public static Camera camera = null;

    // Temp
    public static Light light = new Light();

    public Engine () {
        if (!glfwInit())
            new Log(Log.LogEnum.ERROR, "Unable to initialize GLFW", true);
    }

    public void Start() {
        if(window == null || camera == null) new Log(Log.LogEnum.ERROR, "Unable to find window or camera component", true);

        glfwMakeContextCurrent(window.GetWindow());
        glfwSwapInterval(1);

        GL.createCapabilities();
        glClearColor(0.5f, 0.79f, 1, 1);

        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);

        glCullFace(GL_BACK);
        glDepthFunc(GL_LESS);

        window.OnWindowResize((Window.WindowInfo info) -> {
            int width =  window.GetDimensions()[0];
            int height =  window.GetDimensions()[1];

            camera.aspectRatio = width / height;
            glViewport(0, 0, width, height);

            return info;
        });
    }

    public void Update() {
        deltaTime.Tick();

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        camera.Update();

        for(Object object : GameManager.objects) {
            object.Update();
            GL30.glUseProgram(0);
        }

        GL30.glBindVertexArray(0);

        window.UpdateWindow();

        glfwPollEvents();
    }

    public boolean EngineShouldClose() {
        return window.ShouldClose();
    }

    public void Exit() {
        window.Destroy();

        glfwTerminate();
    }
}
