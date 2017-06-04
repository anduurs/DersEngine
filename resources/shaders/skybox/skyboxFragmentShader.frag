#version 330 core

in vec3 textureCoords;

layout (location = 0) out vec4 fragColor;
layout (location = 1) out vec4 brightColor;

uniform samplerCube cubeMap;
uniform samplerCube cubeMap2;

uniform vec3 fogColor;

uniform float blendFactor;

const float lowerFogLimit = 0.0;
const float upperFogLimit = 30.0;

void main(){
	vec4 textureColor1 = texture(cubeMap, textureCoords);
	vec4 textureColor2 = texture(cubeMap2, textureCoords);

	vec4 finalTextureColor = mix(textureColor1, textureColor2, blendFactor);

	float factor = (textureCoords.y - lowerFogLimit) / (upperFogLimit - lowerFogLimit);
	factor = clamp(factor, 0.0, 1.0);

	//outColor = mix(vec4(fogColor, 1.0), finalTextureColor, factor);
	fragColor = finalTextureColor;
	brightColor = vec4(0.0);
}