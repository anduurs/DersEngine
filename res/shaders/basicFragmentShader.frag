#version 400 core

in vec2 out_TexCoords;

out vec4 outColor;

uniform sampler2D textureSampler;

void main(){
	vec4 textureColor = texture(textureSampler, out_TexCoords);
	
	if(textureColor.a < 0.5){
		discard;
	}
	
	outColor = textureColor;
}