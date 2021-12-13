package engine.camera;

import engine.Engine;
import engine.input.Input;
import engine.log.Log;
import org.joml.*;
import org.joml.Math;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;

public class Camera {
    private final Matrix4f viewMatrix = new Matrix4f().identity();
    private final Vector3f position;
    private final Vector3f rotation;

    float fieldOfView = Math.toRadians(45f);
    public float aspectRatio = Engine.Width / Engine.Height;
    float near = 0.1f;
    float far = 1000.0f;

    float speed = 10f;
    float sensitivity = 0.1f;

    boolean enableMouseMovement = false;
    private Vector2f previousCursor = new Vector2f(0, 0);

    public Camera() {
        position = new Vector3f();
        rotation = new Vector3f();
    }

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public void movePosition(float offsetX, float offsetY, float offsetZ) {
        if ( offsetZ != 0 ) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
            position.z += (float)Math.cos(Math.toRadians(rotation.y)) * offsetZ;
        }
        if ( offsetX != 0) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
            position.z += (float)Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
        }
        position.y += offsetY;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
    }

    public void moveRotation(float offsetX, float offsetY, float offsetZ) {
        rotation.x += offsetX;
        rotation.y += offsetY;
        rotation.z += offsetZ;
    }

    public Matrix4f GetViewProjection() {
        Matrix4f projection = new Matrix4f().perspective(fieldOfView, aspectRatio, near, far);

        return projection.mul(GetView());
    }

    public Matrix4f GetView() {
        Vector3f cameraPos = getPosition();
        Vector3f rotation = getRotation();

        viewMatrix.identity();
        viewMatrix.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
                .rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
        viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        return viewMatrix;
    }

    public void Update() {
        UpdateInputs();
    }

    private void UpdateInputs() {
        if(Input.GetKeyDown(GLFW_KEY_W)) {
            movePosition(0, 0, -1 * (float)Engine.deltaTime.GetDeltaTime() * speed);
        }
        if(Input.GetKeyDown(GLFW_KEY_S)) {
            movePosition(0, 0, 1 * (float)Engine.deltaTime.GetDeltaTime() * speed);
        }
        if(Input.GetKeyDown(GLFW_KEY_A)) {
            movePosition((-1 * (float)Engine.deltaTime.GetDeltaTime() * speed), 0, 0);
        }
        if(Input.GetKeyDown(GLFW_KEY_D)) {
            movePosition((1 * (float)Engine.deltaTime.GetDeltaTime() * speed), 0, 0);
        }
        if(Input.GetKeyDown(GLFW_KEY_Z)) {
            movePosition(0,  1 * (float)Engine.deltaTime.GetDeltaTime() * speed, 0);
        }
        if(Input.GetKeyDown(GLFW_KEY_X)) {
            movePosition(0,  -1 * (float)Engine.deltaTime.GetDeltaTime() * speed, 0);
        }

        if(Input.GetMouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
            DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
            DoubleBuffer y = BufferUtils.createDoubleBuffer(1);

            glfwGetCursorPos(Engine.window.GetWindow(), x, y);

            previousCursor = new Vector2f((float)x.get(), (float)y.get());

            enableMouseMovement = true;

            glfwSetInputMode(Engine.window.GetWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        }
        if(Input.GetMouseButtonUp(GLFW_MOUSE_BUTTON_LEFT)) {
            enableMouseMovement = false;

            glfwSetInputMode(Engine.window.GetWindow(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        }

        glfwSetCursorPosCallback(Engine.window.GetWindow(), new GLFWCursorPosCallback() {
            @Override
            public void invoke(long l, double v, double v1) {
                MouseMotionCallback(l, v, v1);
            }
        });
    }

    public void MouseMotionCallback(long window, double x, double y) {
        if(!enableMouseMovement) return;

        Vector2f currentCursor = new Vector2f((float)x, (float)y);
        Vector2f deltaCursor = new Vector2f(currentCursor.x - previousCursor.x, currentCursor.y - previousCursor.y);

        moveRotation((float)(deltaCursor.y * sensitivity), (float)(deltaCursor.x * sensitivity), 0);

        previousCursor = currentCursor;
    }
}