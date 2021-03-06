package engine.transform;

import engine.Engine;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {
    private Matrix4f modelMatrix = new Matrix4f().identity();

    public Matrix4f GetMatrix() {
        return modelMatrix;
    }

    public Matrix4f GetViewProjection() {
        Matrix4f viewProjection = Engine.camera.GetViewProjection();

        return viewProjection.mul(GetMatrix());
    }

    public Vector3f GetPosition() {
        return new Vector3f(modelMatrix.m30(), modelMatrix.m31(), modelMatrix.m32());
    }

    public void SetPosition(Vector3f pos) {
        modelMatrix.translation(pos);
    }
}
