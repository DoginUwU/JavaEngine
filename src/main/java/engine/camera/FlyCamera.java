package engine.camera;

import engine.Engine;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class FlyCamera {
    Vector3f location = new Vector3f( 0, 0, 5 );
    Vector3f direction = new Vector3f( 0, 0, -1 );
    Vector3f up = new Vector3f( 0, 1, 0 );

    float fieldOfView = Math.toRadians(45f);
    float aspectRatio = Engine.Width / Engine.Height;
    float near = 0.01f;
    float far = 1000.0f;

    float speed = 10.0f;
    float sensitivity = 0.1f;

    public Matrix4f GetViewProjection() {
        Matrix4f projection =  new Matrix4f().perspective(fieldOfView, aspectRatio, near, far);

        return projection.mul(GetView());
    }

    public Matrix4f GetView() {
        return new Matrix4f().lookAt(location, direction, up);
    }
}
