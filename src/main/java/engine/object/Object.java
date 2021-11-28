package engine.object;

import engine.Engine;
import engine.mesh.Mesh;
import engine.mesh.MeshLoader;
import engine.transform.Transform;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.*;

public class Object {
    public String name;
    public Mesh mesh;
    public Transform transform = new Transform();

    public Object(String name) {
        this.name = name;

        Start();
    }

    public void Start() {
        DrawTriangles();
    }

    public void DrawTriangles() {
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

    public void Update() {
        if(mesh == null) return;

        UpdatePosition();

        GL30.glBindVertexArray(mesh.getVaoID());

        GL20.glEnableVertexAttribArray(0);

        FloatBuffer pointer = BufferUtils.createFloatBuffer(1);

        GL20.glVertexAttribPointer(0, 0, GL_FLOAT, false, 0, pointer);

        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT,0);

        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    private void UpdatePosition() {
        Matrix4f viewProjection = Engine.camera.GetViewProjection();

        Matrix4f modelViewProjection = transform.GetViewProjection();

        int modelViewProjectionLoc = glGetUniformLocation(Engine.shader.programId, "ModelViewProjection");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            glUniformMatrix4fv(modelViewProjectionLoc, false, modelViewProjection.get(stack.mallocFloat(16)));
        }
    }
}