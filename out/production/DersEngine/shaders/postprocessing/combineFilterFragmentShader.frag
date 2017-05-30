#version 330 core

in vec2 textureCoords;

out vec4 fragColor;

uniform sampler2D colorTexture;
uniform sampler2D highlightTexture;
const float contrast = 0.3;
void main(void){
    vec4 sceneColor = texture(colorTexture, textureCoords);
    vec4 highlightColor = texture(highlightTexture, textureCoords);
    float glowFactor = 2.0;
    fragColor = sceneColor + highlightColor * glowFactor;
    fragColor.rgb = (fragColor.rgb - 0.5) * (1.0 + contrast) + 0.5;
}