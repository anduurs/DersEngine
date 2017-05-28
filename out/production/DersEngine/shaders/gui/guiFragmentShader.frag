#version 330 core

in vec2 textureCoords;

out vec4 outColor;

uniform sampler2D textureSampler;
uniform vec3 color;
uniform float usingColor;

void main(){
	if(usingColor == 1)
		outColor = vec4(color, 1.0);
	else outColor = texture(textureSampler, textureCoords);
}