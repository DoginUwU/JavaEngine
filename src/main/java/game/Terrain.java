package game;

import engine.mesh.MeshLoader;
import engine.object.Object;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class Terrain extends Object {
    public Terrain() {
        super("Terrain");
    }

    @Override
    public void Draw() {
        int resolution = 10;

        Vector3f[] vertices = new Vector3f[(resolution + 1) * (resolution + 1)];
        Vector3i[] indices = new Vector3i[resolution * resolution * 2];
        Vector3f[] colors = new Vector3f[vertices.length];

        for (int i = 0, x = 0; x <= resolution; x++) {
            for (int z = 0; z <= resolution; z++) {
                vertices[i] = new Vector3f(x, 0, z);
                colors[i] = new Vector3f(0.46f, 0.81f, 0.36f);

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

        mesh = MeshLoader.createMesh(vertices, indices, colors);
    }
}
