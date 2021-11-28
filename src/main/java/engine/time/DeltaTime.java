package engine.time;

import org.lwjgl.glfw.GLFW;

public class DeltaTime {
    private double previousTime = GLFW.glfwGetTime(), currentTime = GLFW.glfwGetTime();
    private double deltaTime = 0;

    public void Tick() {
        currentTime = GLFW.glfwGetTime();
        deltaTime = currentTime - previousTime;

        if (deltaTime > 0.0) {
            previousTime = currentTime;
        }
    }

    public double GetDeltaTime() {
        return deltaTime;
    }
}
