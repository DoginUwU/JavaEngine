#version 330 core

in vec2 OutTexCord;
in vec3 Color;

uniform sampler2D texture_sampler;
uniform int useColour;

out vec4 OutColor;

void main() {
    vec4 InitialColor = vec4(0, 0, 0, 1);

    if(useColour == 1) {
        InitialColor = vec4(Color, 1);
    } else {
        InitialColor = texture(texture_sampler, OutTexCord);
    }

    OutColor = InitialColor;
}