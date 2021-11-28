package game;

import engine.log.Log;
import engine.mesh.MeshLoader;
import engine.object.Object;

public class Terrain extends Object {
    public Terrain() {
        super("Terrain");
    }

    @Override
    public void Draw() {
        float[] vertices = {
                -1, -1, 0f,
                1f, -1f, 0f,
                -1f, 1f, 0f,
                1f, 1f, 0f
        };

        int[] indices = { 0, 1, 2,
                2, 1, 3 };

        mesh = MeshLoader.createMesh(vertices, indices);
    }
}
