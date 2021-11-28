#version 330 core

in vec2 OutTexCord;

uniform sampler2D texture_sampler;

out vec4 OutColor;

void main() {
    vec4 InitialColor = texture(texture_sampler, OutTexCord);

    OutColor = InitialColor;
}