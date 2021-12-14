package engine.camera;

public class FlyCamera {
    /* Vector3f location = new Vector3f( 0, 0, 5 );
    Vector3f direction = new Vector3f( 0, 0, 1 );
    Vector3f up = new Vector3f( 0, 1, 0 );

    float fieldOfView = Math.toRadians(45f);
    float aspectRatio = Engine.Width / Engine.Height;
    float near = 1;
    float far = 1000.0f;

    float speed = 10f;
    float sensitivity = 0.1f;

    public Matrix4f GetViewProjection() {
        Matrix4f projection = new Matrix4f().perspective(fieldOfView, aspectRatio, near, far);

        return projection.mul(GetView());
    }

    public Matrix4f GetView() {
        return new Matrix4f().identity().lookAt(location, direction, up);
    }

    public void Update() {
        UpdateInputs();
    }

    public void MoveForward(float amount) {
        Vector3f normalize = location.normalize(direction);

        location.add(normalize.mul(amount).mul(-speed));
    }
    public void MoveRight(float amount) {
        Vector3f right =  location.normalize(direction.cross(up));

        new Log(Log.LogEnum.LOG, right.toString(), false);

        location.add(right.mul(amount).mul(speed));
    }
    public void Look(float yaw, float pitch) {
        yaw *= sensitivity;
        pitch *= sensitivity;

        Matrix4f identity = new Matrix4f().identity();

        Matrix4f yawRotation = new Matrix4f().rotate(Math.toRadians(yaw), up, identity);
        // Matrix4f pitchRotation = new Matrix4f().rotate(Math.toRadians(pitch), up, identity);

        new Log(Log.LogEnum.LOG, yawRotation.toString(), false);
    }

    private void UpdateInputs() {
        if(Input.GetKeyDown(GLFW_KEY_W)) {
            MoveForward(1 * (float)Engine.deltaTime.GetDeltaTime());
            Look(10, 0);
        }
        if(Input.GetKeyDown(GLFW_KEY_S)) {
            MoveForward(-1 * (float)Engine.deltaTime.GetDeltaTime());
        }
        if(Input.GetKeyDown(GLFW_KEY_A)) {
            MoveRight(-1 * (float)Engine.deltaTime.GetDeltaTime());
        }
        if(Input.GetKeyDown(GLFW_KEY_D)) {
            MoveRight(1 * (float)Engine.deltaTime.GetDeltaTime());
        }
    } */
}
