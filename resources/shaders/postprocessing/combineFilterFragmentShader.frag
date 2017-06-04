#version 330 core

in vec2 textureCoords;

out vec4 fragColor;

uniform sampler2D colorTexture;
uniform sampler2D highlightTexture;

uniform float exposure;
uniform float glowFactor;
uniform float contrast;

void main(void){
     vec4 sceneColor = texture(colorTexture, textureCoords);
     vec4 bloomColor = texture(highlightTexture, textureCoords);

     sceneColor += bloomColor * glowFactor;

     fragColor = vec4(1.0) - exp(-sceneColor * exposure);
     //fragColor.rgb = (fragColor.rgb - 0.5) * (1.0 + contrast) + 0.5;
}