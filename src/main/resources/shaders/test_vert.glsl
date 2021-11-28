#version 430 core

layout (location = 0) in vec3 InPosition;
layout (location = 2) in vec2 TexCord;

uniform mat4 ModelViewProjection;

out vec2 OutTexCord;

void main() {
    OutTexCord = TexCord;

    gl_Position = ModelViewProjection * vec4(InPosition, 1.0);
}