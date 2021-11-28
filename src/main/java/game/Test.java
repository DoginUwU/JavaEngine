package game;

import engine.mesh.MeshLoader;
import engine.object.Object;
import engine.texture.Texture;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class Test extends Object {
    public Test() {
        super("Test", "test");
    }

    @Override
    public void Draw() {
        int resolution = 10;

        Vector3f[] vertices = new Vector3f[(resolution + 1) * (resolution + 1)];
        Vector3i[] indices = new Vector3i[resolution * resolution * 2];
        Vector2f[] uvs = new Vector2f[vertices.length];

        uvs[0] = new Vector2f(0, 0);
        uvs[1] = new Vector2f(1, 0);
        uvs[2] = new Vector2f(1, 1);
        uvs[3] = new Vector2f(0, 1);

        Texture texture = new Texture();
        texture.loadTexture("textures/test.jpg");

        for (int i = 0, x = 0; x <= resolution; x++) {
            for (int z = 0; z <= resolution; z++) {
                vertices[i] = new Vector3f(x, 0, z);

                i++;
            }
        }

        for (int vert = 0, tris = 0, x = 0; x < resolution; x++) {
            for (int z = 0; z < resolution; z++) {
                indices[tris + 0] = new Vector3i( vert + 0, vert + resolution + 1, vert + 1 );
                indices[tris + 1] = new Vector3i( vert + 1, vert + resolution + 1, vert + resolution + 2 );

                vert++;
                tris += 2;
            }
        }

        for (int i = 0, x = 0; x <= resolution; x++) {
            for (int z = 0; z <= resolution; z++) {
                uvs[i] = new Vector2f((float)x / resolution, (float)z / resolution);
                i++;
            }
        }

        mesh = MeshLoader.createMesh(vertices, indices, new Vector3f[0], uvs, texture);
    }
}
