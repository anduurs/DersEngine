#version 400 core

in vec2 out_TexCoords;
in vec3 out_Normal;
in vec3 toLightVector;

out vec4 outColor;

uniform sampler2D textureSampler;

uniform vec3 lightColor;

void main(){
	vec3 unitNormal = normalize(out_Normal);
	vec3 unitLightVector = normalize(toLightVector);
	
	float nDotl = dot(unitNormal, unitLightVector);
	float brightness = max(nDotl, 0.0);
	vec3 diffuse = brightness * lightColor;
	
	outColor = vec4(diffuse, 1.0) * texture(textureSampler, out_TexCoords);
}