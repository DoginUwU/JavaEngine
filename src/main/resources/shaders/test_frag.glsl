#version 330 core

in vec2 OutTexCord;
in vec3 Color;
in vec3 OutVertexNormal;

uniform sampler2D texture_sampler;
uniform int useColour;

// light
uniform vec3 light_direction;
uniform float light_intensity;

out vec4 OutColor;

float CalcDirectionalLightFactor(vec3 lightDirection, vec3 normal) {
    float DiffuseFactor = dot(normalize(normal), -lightDirection);

    if (DiffuseFactor > 0) {
        return DiffuseFactor;
    }
    else {
        return 0.0;
    }
}

void main() {
    vec3 InitialColor = vec3(0, 0, 0);

    vec3 Normal = normalize(OutVertexNormal);

    if(useColour == 1) {
        InitialColor = Color;
    } else {
        InitialColor = texture(texture_sampler, OutTexCord).rgb;
    }

    vec3 FinalColor = InitialColor * light_intensity;

    OutColor = vec4(FinalColor, 1);
}