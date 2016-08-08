#version 400 core

in vec2 out_TexCoords;
in vec3 out_Normal;
in vec3 toLightVector;
in vec3 toCameraVector;

out vec4 outColor;

uniform sampler2D textureSampler;

uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;

void main(){
	vec3 unitNormal = normalize(out_Normal);
	vec3 unitLightVector = normalize(toLightVector);
	
	float brightness = max(dot(unitNormal, unitLightVector), 0.1);
	vec3 diffuse = brightness * lightColor;
	
	vec3 unitCameraVector = normalize(toCameraVector);
	vec3 lightDirection = -unitLightVector;
	vec3 reflectedLightDirection = reflect(lightDirection, unitNormal); 
	
	float specularFactor = max(dot(reflectedLightDirection, unitCameraVector), 0.0); 
	float dampedFactor = pow(specularFactor, shineDamper);
	vec3 finalSpecular = dampedFactor * reflectivity * lightColor;
	
	vec4 textureColor = texture(textureSampler, out_TexCoords);
	if(textureColor.a < 0.5){
		discard;
	}
	
	outColor = vec4(diffuse, 1.0) * textureColor + vec4(finalSpecular, 1.0);
}