#version 400 core

in vec2 out_TexCoords;
in vec3 out_Normal;
in vec3 toLightVector[4];
in vec3 toCameraVector;
in float visibility;

out vec4 outColor;

uniform sampler2D textureSampler;

uniform vec3 lightColor[4];
uniform vec3 attenuation[4];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;

void main(){
	vec3 unitNormal = normalize(out_Normal);
	vec3 unitCameraVector = normalize(toCameraVector);
	
	vec3 totalDiffuse = vec3(0.0);
	vec3 totalSpecular = vec3(0.0);
	
	for(int i = 0; i < 4; i++){
		float distance = length(toLightVector[i]);
		float attenuationFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
		vec3 unitLightVector = normalize(toLightVector[i]);
		
		float brightness = max(dot(unitNormal, unitLightVector), 0.0);
		vec3 lightDirection = -unitLightVector;
		vec3 reflectedLightDirection = reflect(lightDirection, unitNormal); 
		float specularFactor = max(dot(reflectedLightDirection, unitCameraVector), 0.0); 
		float dampedFactor = pow(specularFactor, shineDamper);
		
		if(attenuationFactor == 1){
			totalDiffuse = totalDiffuse + (brightness * lightColor[i]) * 1;
		}else {
			totalDiffuse = totalDiffuse + (brightness * lightColor[i]) / attenuationFactor;
		}
	
		totalSpecular = totalSpecular + (dampedFactor * reflectivity * lightColor[i]) / attenuationFactor;
	}
	
	float ambientFactor = 0.01;
	totalDiffuse = max(totalDiffuse, ambientFactor);
	
	vec4 textureColor = texture(textureSampler, out_TexCoords);
	if(textureColor.a < 0.5){
		discard;
	}
	
	outColor = mix(vec4(skyColor, 1.0), vec4(totalDiffuse, 1.0) * textureColor + vec4(totalSpecular, 1.0), visibility);
}