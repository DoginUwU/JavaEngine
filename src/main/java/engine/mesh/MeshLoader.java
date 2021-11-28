package engine.mesh;

import engine.log.Log;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MeshLoader {
    private static List<Integer> vaos = new ArrayList<Integer>();
    private static List<Integer> vbos = new ArrayList<Integer>();

    private static FloatBuffer createFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private static IntBuffer createIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private static void storeData(int attribute, int dimensions, float[] data) {
        int vbo = GL15.glGenBuffers(); //Creates a VBO ID
        vbos.add(vbo);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo); //Loads the current VBO to store the data
        FloatBuffer buffer = createFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attribute, dimensions, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); //Unloads the current VBO when done.
    }

    private static void bindIndices(int[] data) {
        int vbo = GL15.glGenBuffers();
        vbos.add(vbo);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo);
        IntBuffer buffer = createIntBuffer(data);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    public static Mesh createMesh(Vector3f[] positions, Vector3i[] indices, Vector3f[] colors) {
        float[] newPositions = new float[positions.length * 3];
        int[] newIndices = new int[indices.length * 3];
        float[] newColors = new float[colors.length * 3];

        for (int i = 0, x = 0; x < positions.length * 3; x += 3) {
            newPositions[x] = positions[i].x();
            newPositions[x + 1] = positions[i].y();
            newPositions[x + 2] = positions[i].z();
            i++;
        }

        for (int i = 0, x = 0; x < colors.length * 3; x += 3) {
            newColors[x] = colors[i].x();
            newColors[x + 1] = colors[i].y();
            newColors[x + 2] = colors[i].z();
            i++;
        }

        for (int i = 0, x = 0; x < indices.length * 3; x += 3) {
            newIndices[x] = indices[i].x();
            newIndices[x + 1] = indices[i].y();
            newIndices[x + 2] = indices[i].z();
            i++;
        }

        int vao = genVAO();
        storeData(0,3, newPositions);
        storeData(1,3, newColors);
        bindIndices(newIndices);
        GL30.glBindVertexArray(0);
        return new Mesh(vao, newIndices.length);
    }

    private static int genVAO() {
        int vao = GL30.glGenVertexArrays();
        vaos.add(vao);
        GL30.glBindVertexArray(vao);
        return vao;
    }
}
