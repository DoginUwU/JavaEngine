package game;

import engine.Engine;

public class main {
    public static void main(String[] args) {
        Engine engine = new Engine();

        new Terrain();

        while (!engine.EngineShouldClose()) {
            engine.Update();
        }

        engine.Exit();
    }
}
