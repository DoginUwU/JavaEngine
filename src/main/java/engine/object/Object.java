package engine.object;

import engine.Engine;
import engine.gamemanager.GameManager;
import engine.log.Log;
import engine.mesh.Mesh;
import engine.shader.Shader;
import engine.transform.Transform;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL20.*;

public abstract class Object {
    public String name;
    public Mesh mesh;
    public Transform transform = new Transform();
    private Shader shader;

    public Object(String name, String shaderName) {
        this.name = name;
        this.shader = GameManager.GetShaderByName(shaderName);

        if(shader == null)
            new Log(Log.LogEnum.ERROR, "Shader not founded!", true);

        Engine.objects.add(this);

        Start();
    }

    public void Start() {
        Draw();
    }

    public void Draw() {

    }

    public void Update() {
        shader.Update();
        UpdateMesh();
    }

    private void UpdateMesh() {
        if(mesh == null) return;

        UpdatePosition();
        UpdateGraphs();

        GL30.glBindVertexArray(mesh.getVaoID());

        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL20.glEnableVertexAttribArray(3);

        if(mesh.getTexture() != null) {
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, mesh.getTexture().getId());
        }

        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT,0);

        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    protected void UpdateGraphs() {

    }

    private void UpdatePosition() {
        Matrix4f modelViewProjection = transform.GetViewProjection();

        int modelViewProjectionLoc = glGetUniformLocation(shader.programId, "ModelViewProjection");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            glUniformMatrix4fv(modelViewProjectionLoc, false, modelViewProjection.get(stack.mallocFloat(16)));
        }
    }
}