#version 400 core

in vec2 out_TexCoords;
in vec3 out_Normal;

out vec4 outColor;

uniform sampler2D textureSampler;

uniform vec3 lightColor;

void main(){
	outColor = texture(textureSampler, out_TexCoords);
}