#version 330 core

in vec2 OutTexCord;
in vec3 Color;
in vec3 OutVertexNormal;

uniform sampler2D texture_sampler;
uniform int useColour;

// light
uniform vec3 light_position;
uniform vec3 light_color;
uniform vec3 light_ambient;

out vec4 OutColor;

float CalcDirectionalLightFactor(vec3 lightDirection, vec3 normal) {
    float factor = dot(normal, lightDirection);
    return max(factor, 0.0);
}

void main() {
    vec3 normal = normalize(OutVertexNormal);
    vec3 lightDirection = normalize(light_position);
    float diffuseFactor = CalcDirectionalLightFactor(lightDirection, normal);
    vec3 diffuseColor = diffuseFactor * light_color;
    vec3 ambientColor = light_ambient;
    vec3 color = diffuseColor * ambientColor;
    if (useColour == 1) {
        color = Color * color;
    } else {
        color = texture(texture_sampler, OutTexCord).rgb * color;
    }
    OutColor = vec4(color, 1.0);
}