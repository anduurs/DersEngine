#version 400 core

in vec2 out_TexCoords;
in vec3 out_Normal;
in vec3 toLightVector;
in vec3 toCameraVector;
in float visibility;

out vec4 outColor;

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;

uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;

void main(){
	vec4 blendMapColor = texture(blendMap, out_TexCoords);
	
	float backTextureAmount = 1 - (blendMapColor.r + blendMapColor.g + blendMapColor.b);
	vec2 tiledCoords = out_TexCoords * 40.0;
	
	vec4 backgroundTextureColor = texture(backgroundTexture, tiledCoords) * backTextureAmount;
	vec4 rTextureColor = texture(rTexture, tiledCoords) * blendMapColor.r;
	vec4 gTextureColor = texture(gTexture, tiledCoords) * blendMapColor.g;
	vec4 bTextureColor = texture(bTexture, tiledCoords) * blendMapColor.b;
	
	vec4 totalColor = backgroundTextureColor + rTextureColor + gTextureColor + bTextureColor;

	vec3 unitNormal = normalize(out_Normal);
	vec3 unitLightVector = normalize(toLightVector);
	
	float ambientFactor = 0.2;
	float brightness = max(dot(unitNormal, unitLightVector), ambientFactor);
	
	vec3 diffuse = brightness * lightColor;
	
	vec3 unitCameraVector = normalize(toCameraVector);
	vec3 lightDirection = -unitLightVector;
	vec3 reflectedLightDirection = reflect(lightDirection, unitNormal); 
	
	float specularFactor = max(dot(reflectedLightDirection, unitCameraVector), 0.0); 
	float dampedFactor = pow(specularFactor, shineDamper);
	vec3 finalSpecular = dampedFactor * reflectivity * lightColor;
	
	vec4 textureColor = totalColor;
	
	outColor = mix(vec4(skyColor, 1.0), vec4(diffuse, 1.0) * textureColor + vec4(finalSpecular, 1.0), visibility);
}