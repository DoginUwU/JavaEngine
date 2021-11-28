package game;

import engine.Engine;
import engine.shader.Shader;
import org.joml.Vector3f;

public class main {
    public static void main(String[] args) {
        Engine engine = new Engine();

        new Shader("test");

        new Terrain();
        new Test().transform.SetPosition(new Vector3f(5, 0, 5));

        while (!engine.EngineShouldClose()) {
            Update();
            engine.Update();
        }

        engine.Exit();
    }

    public static void Update() {

    }
}
