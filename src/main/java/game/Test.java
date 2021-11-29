package game;

import engine.mesh.MeshLoader;
import engine.object.Object;
import engine.texture.Texture;

public class Test extends Object {
    public Test() {
        super("Test", "test");
    }

    @Override
    public void Draw() {
        Texture texture = new Texture();
        texture.loadTexture("textures/test.jpg");

        mesh = MeshLoader.loadMesh("test.obj", texture);
    }
}
