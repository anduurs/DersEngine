#version 400 core

in vec2 out_TexCoords;

uniform sampler2D textureSampler;

out vec4 outColor;

void main(){
	outColor = texture(textureSampler, out_TexCoords);
}