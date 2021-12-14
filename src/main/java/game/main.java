package game;

import engine.Engine;
import engine.camera.Camera;
import engine.shader.Shader;
import engine.window.Window;
import org.joml.Vector3f;

public class Main {
    public static void main(String[] args) {
        Engine engine = new Engine();

        Engine.window = new Window("Game OpenGL [Engine Development]");
        Engine.camera = new Camera();

        engine.Start();
        Start();

        while (!engine.EngineShouldClose()) {
            Update();
            engine.Update();
        }

        engine.Exit();
    }

    public static void Start() {
        new Shader("test");
        new Terrain();
        new Test().transform.SetPosition(new Vector3f(5, 5, 5));
    }

    public static void Update() {

    }
}
