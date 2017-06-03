#version 330 core

in vec2 textureCoords;

out vec4 fragColor;

uniform sampler2D colorTexture;
uniform sampler2D highlightTexture;

const float contrast = 0.4;
const float glowFactor = 2.0;
const float exposure = 1.0;

void main(void){
     vec4 sceneColor = texture(colorTexture, textureCoords);
     vec4 bloomColor = texture(highlightTexture, textureCoords);

     sceneColor += bloomColor * glowFactor;

     fragColor = vec4(1.0) - exp(-sceneColor * exposure);
     fragColor.rgb = (fragColor.rgb - 0.5) * (1.0 + contrast) + 0.5;
}