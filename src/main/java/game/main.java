package game;

import engine.Engine;
import engine.gamemanager.GameManager;
import engine.shader.Shader;
import org.joml.Vector3f;

public class main {
    public static void main(String[] args) {
        Engine engine = new Engine();

        new Shader("triangle");
        new Shader("test");

        new Terrain();
        new Test().transform.SetPosition(new Vector3f(10, 0, 10));

        while (!engine.EngineShouldClose()) {
            Update();
            engine.Update();
        }

        engine.Exit();
    }

    public static void Update() {
        GameManager.GetShaderByName("test").setUniform("texture_sampler", 0);
    }
}
