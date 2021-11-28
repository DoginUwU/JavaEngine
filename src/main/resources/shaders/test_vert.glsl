#version 430 core

layout (location = 0) in vec3 InPosition;
layout (location = 1) in vec3 InColor;
layout (location = 2) in vec2 TexCord;

uniform mat4 ModelViewProjection;

out vec2 OutTexCord;
out vec3 Color;

void main() {
    OutTexCord = TexCord;
    Color = InColor;

    gl_Position = ModelViewProjection * vec4(InPosition, 1.0);
}