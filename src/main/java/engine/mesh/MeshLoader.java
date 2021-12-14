package engine.mesh;

import engine.file.File;
import engine.texture.Texture;
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

    public static Mesh createMesh(Vector3f[] positions, Vector3f[] normals, Vector3i[] indices, Vector3f[] colors, Vector2f[] textCords, Texture texture) {
        float[] newPositions = new float[positions.length * 3];
        int[] newIndices = new int[indices.length * 3];

        float[] newColors = new float[colors.length * 3];
        float[] newTextCords = new float[textCords.length * 2];

        float[] newNormals = new float[normals.length * 3];

        if(colors.length == 0) {
            newColors = new float[3];

            newColors[0] = 0;
            newColors[1] = 0;
            newColors[2] = 0;
        }
        if(textCords.length == 0) {
            newTextCords = new float[2];

            newTextCords[0] = 0;
            newTextCords[1] = 0;
        }

        for (int i = 0, x = 0; x < positions.length * 3; x += 3) {
            newPositions[x] = positions[i].x();
            newPositions[x + 1] = positions[i].y();
            newPositions[x + 2] = positions[i].z();
            i++;
        }

        for (int i = 0, x = 0; x < normals.length * 3; x += 3) {
            newNormals[x] = normals[i].x();
            newNormals[x + 1] = normals[i].y();
            newNormals[x + 2] = normals[i].z();
            i++;
        }

        for (int i = 0, x = 0; x < colors.length * 3; x += 3) {
            newColors[x] = colors[i].x();
            newColors[x + 1] = colors[i].y();
            newColors[x + 2] = colors[i].z();
            i++;
        }

        for (int i = 0, x = 0; x < textCords.length * 2; x += 2) {
            if(textCords[i] != null) {
                newTextCords[x] = textCords[i].x();
                newTextCords[x + 1] = textCords[i].y();
            }
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
        storeData(2,2, newTextCords);
        storeData(3,3, newNormals);

        bindIndices(newIndices);
        GL30.glBindVertexArray(0);

        return new Mesh(vao, newIndices.length, texture);
    }

    public static Mesh loadMesh(String fileName, Texture texture) {
        List<String> lines = File.readAllLines(fileName);

        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Face> faces = new ArrayList<>();

        for (String line : lines) {
            String[] tokens = line.split("\\s+");
            switch (tokens[0]) {
                case "v":
                    // Geometric vertex
                    Vector3f vec3f = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3]));
                    vertices.add(vec3f);
                    break;
                case "vt":
                    // Texture coordinate
                    Vector2f vec2f = new Vector2f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]));
                    textures.add(vec2f);
                    break;
                case "vn":
                    // Vertex normal
                    Vector3f vec3fNorm = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3]));
                    normals.add(vec3fNorm);
                    break;
                case "f":
                    Face face = new Face(tokens[1], tokens[2], tokens[3]);
                    faces.add(face);
                    break;
                default:
                    // Ignore other lines
                    break;
            }
        }
        return reorderLists(vertices, textures, normals, faces, texture);
    }

    private static Mesh reorderLists(List<Vector3f> posList, List<Vector2f> textCoordList,
                                     List<Vector3f> normList, List<Face> facesList, Texture texture) {

        List<Integer> indices = new ArrayList<>();
        // Create position array in the order it has been declared
        Vector3f[] posArr = new Vector3f[posList.size() ];
        int i = 0;
        for (Vector3f pos : posList) {
            posArr[i] = new Vector3f(pos.x, pos.y, pos.z);
            i++;
        }
        Vector2f[] textCoordArr = new Vector2f[posList.size()];
        Vector3f[] normArr = new Vector3f[posList.size()];

        for (Face face : facesList) {
            IdxGroup[] faceVertexIndices = face.getFaceVertexIndices();
            for (IdxGroup indValue : faceVertexIndices) {
                processFaceVertex(indValue, textCoordList, normList,
                        indices, textCoordArr, normArr);
            }
        }
        Vector3i[] indicesArr = new Vector3i[indices.size() / 3];

        for (int indice = 0, a = 0; a < indicesArr.length; a++) {
            indicesArr[a] = new Vector3i(indices.get(indice), indices.get(indice + 1), indices.get(indice + 2));
            indice += 3;
        }

        Mesh mesh = createMesh(posArr, normArr, indicesArr, new Vector3f[0], textCoordArr, texture);
        return mesh;
    }

    private static void processFaceVertex(IdxGroup indices, List<Vector2f> textCoordList,
                                          List<Vector3f> normList, List<Integer> indicesList,
                                          Vector2f[] texCoordArr, Vector3f[] normArr) {

        // Set index for vertex coordinates
        int posIndex = indices.idxPos;
        indicesList.add(posIndex);

        // Reorder texture coordinates
        if (indices.idxTextCoord >= 0) {
            Vector2f textCoord = textCoordList.get(indices.idxTextCoord);
            texCoordArr[posIndex] = new Vector2f(textCoord.x, textCoord.y);
        }
        if (indices.idxVecNormal >= 0) {
            // Reorder normal vectors
            Vector3f vecNorm = normList.get(indices.idxVecNormal);
            normArr[posIndex] = new Vector3f(vecNorm.x, vecNorm.y, vecNorm.z);
        }
    }

    private static int genVAO() {
        int vao = GL30.glGenVertexArrays();
        vaos.add(vao);
        GL30.glBindVertexArray(vao);
        return vao;
    }
}

class IdxGroup {

    public static final int NO_VALUE = -1;

    public int idxPos;

    public int idxTextCoord;

    public int idxVecNormal;

    public IdxGroup() {
        idxPos = NO_VALUE;
        idxTextCoord = NO_VALUE;
        idxVecNormal = NO_VALUE;
    }
}

class Face {

    /**
     * List of idxGroup groups for a face triangle (3 vertices per face).
     */
    private IdxGroup[] idxGroups = new IdxGroup[3];

    public Face(String v1, String v2, String v3) {
        idxGroups = new IdxGroup[3];
        // Parse the lines
        idxGroups[0] = parseLine(v1);
        idxGroups[1] = parseLine(v2);
        idxGroups[2] = parseLine(v3);
    }

    private IdxGroup parseLine(String line) {
        IdxGroup idxGroup = new IdxGroup();

        String[] lineTokens = line.split("/");
        int length = lineTokens.length;
        idxGroup.idxPos = Integer.parseInt(lineTokens[0]) - 1;
        if (length > 1) {
            // It can be empty if the obj does not define text coords
            String textCoord = lineTokens[1];
            idxGroup.idxTextCoord = textCoord.length() > 0 ? Integer.parseInt(textCoord) - 1 : IdxGroup.NO_VALUE;
            if (length > 2) {
                idxGroup.idxVecNormal = Integer.parseInt(lineTokens[2]) - 1;
            }
        }

        return idxGroup;
    }

    public IdxGroup[] getFaceVertexIndices() {
        return idxGroups;
    }
}