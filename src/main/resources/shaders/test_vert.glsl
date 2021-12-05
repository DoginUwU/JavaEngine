#version 430 core

layout (location = 0) in vec3 InPosition;
layout (location = 1) in vec3 InColor;
layout (location = 2) in vec2 TexCord;
layout (location = 3) in vec3 VertexNormal;

uniform mat4 ModelViewProjection;
uniform mat4 NormalMatrix;

out vec2 OutTexCord;
out vec3 Color;
out vec3 OutVertexNormal;

void main() {
    OutTexCord = TexCord;
    Color = InColor;
    OutVertexNormal = vec3(NormalMatrix * vec4(VertexNormal, 0));

    gl_Position = ModelViewProjection * vec4(InPosition, 1.0);
}