package engine.window;

import engine.Engine;
import engine.log.Log;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private long glfwWindow;

    public Window(CharSequence title) {
        glfwWindow = glfwCreateWindow(Engine.Width, Engine.Height, title, NULL, NULL);

        if(glfwWindow == NULL)
            new Log(Log.LogEnum.ERROR, "Failed to create the GLFW window", true);
    }

    public long GetWindow() {
        return glfwWindow;
    }

    public void UpdateWindow() {
        glfwSwapBuffers(glfwWindow);
    }

    public boolean ShouldClose() {
        return glfwWindowShouldClose(glfwWindow);
    }

    public void Destroy() {
        glfwDestroyWindow(glfwWindow);
    }
}
