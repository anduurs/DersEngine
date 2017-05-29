#version 330 core

in vec2 textureCoords;

out vec4 fragColor;

uniform sampler2D textureSampler;

const float contrast = 0.3;

void main(){
    fragColor = texture(textureSampler, textureCoords);
    fragColor.rgb = (fragColor.rgb - 0.5) * (1.0 + contrast) + 0.5;
}