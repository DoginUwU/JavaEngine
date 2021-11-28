#version 330 core

in vec3 Color;

out vec4 OutColor;

void main() {
    vec4 InitialColor = vec4(Color, 1);

    OutColor = InitialColor;
}