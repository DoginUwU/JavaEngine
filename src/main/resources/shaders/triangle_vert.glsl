#version 330 core

layout (location = 0) in vec3 InPosition;

uniform mat4 ModelViewProjection;

void main() {
    gl_Position = ModelViewProjection * vec4(InPosition, 1.0);
}