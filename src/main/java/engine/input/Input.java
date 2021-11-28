package engine.input;

import engine.Engine;
import org.lwjgl.glfw.GLFW;

public class Input {
    public static boolean GetKeyDown(int key) {
        return GLFW.glfwGetKey(Engine.window.GetWindow(), key) == GLFW.GLFW_PRESS;
    }
    public static boolean GetKeyUp(int key) {
        return GLFW.glfwGetKey(Engine.window.GetWindow(), key) == GLFW.GLFW_RELEASE;
    }

    public static boolean GetMouseButtonDown(int button) {
        return GLFW.glfwGetMouseButton(Engine.window.GetWindow(), button) == GLFW.GLFW_PRESS;
    }
    public static boolean GetMouseButtonUp(int button) {
        return GLFW.glfwGetMouseButton(Engine.window.GetWindow(), button) == GLFW.GLFW_RELEASE;
    }
}
