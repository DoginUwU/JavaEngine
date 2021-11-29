package engine.gamemanager;

import engine.object.Object;
import engine.shader.Shader;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    public static List<Shader> shaders = new ArrayList<Shader>();
    public static List<Object> objects = new ArrayList<Object>();

    public static Shader GetShaderByName(String name) {
        return shaders.stream().filter(i -> i.shaderName == name).findFirst().get();
    }
}
