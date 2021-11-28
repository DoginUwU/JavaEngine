package engine.mesh;

import engine.texture.Texture;

public class Mesh {
    private int vao;
    private int vertices;
    private Texture texture;

    public Mesh(int vao, int vertex, Texture texture) {
        this.vao = vao;
        this.vertices = vertex;
        this.texture = texture;
    }

    public int getVaoID() {
        return vao;
    }
    public int getVertexCount() {
        return vertices;
    }
    public Texture getTexture() { return texture; }
}
