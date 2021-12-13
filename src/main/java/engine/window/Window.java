package engine.window;

import engine.Engine;
import engine.log.Log;

import java.util.function.Function;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    public WindowInfo windowInfo = new WindowInfo();
    private long glfwWindow;

    public Window(CharSequence title) {
        windowInfo.width = Engine.Width;
        windowInfo.height = Engine.Height;
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

    public void OnWindowResize(Function<WindowInfo, WindowInfo> callback) {
        glfwSetFramebufferSizeCallback(glfwWindow, (long window, int nWidth, int nHeight) -> {
            windowInfo.width = nWidth;
            windowInfo.height = nHeight;

            callback.apply(windowInfo);
        });
    }

    public int[] GetDimensions() {
        int[] t = new int[2];
        t[0] = windowInfo.width;
        t[1] = windowInfo.height;

        return t;
    }

    public boolean ShouldClose() {
        return glfwWindowShouldClose(glfwWindow);
    }

    public void Destroy() {
        glfwDestroyWindow(glfwWindow);
    }

    public class WindowInfo {
        int width;
        int height;
    };
}