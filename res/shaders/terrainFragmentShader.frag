#version 400 core

const int MAX_POINT_LIGHTS = 4;
const int MAX_SPOT_LIGHTS = 4;

in vec2 out_TexCoords;
in vec3 out_Normal;
in vec3 toCameraVector;
in float visibility;
in vec3 worldFragPos;

out vec4 outColor;

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;

uniform vec3 skyColor;

struct DirectionalLight{
	vec3 direction;
	
	vec3 ambient;
	vec3 diffuse;
	vec3 specular;
	
	float intensity;
};

struct PointLight{
	vec3 position;
	vec3 attenuation;
	
	vec3 ambient;
	vec3 diffuse;
	vec3 specular;
	
	float intensity;
	float range;
};

struct SpotLight{
	PointLight pointLight;
	vec3 direction;
	float cutOffAngle;
};

uniform DirectionalLight directionalLight;
uniform PointLight pointLights[MAX_POINT_LIGHTS];
uniform SpotLight spotLights[MAX_SPOT_LIGHTS];

vec4 calculateDirectionalLight(DirectionalLight light, vec3 normal, vec4 textureColor){
	vec3 lightDirection = normalize(-light.direction);
	
	float diffuseFactor = max(dot(normal, lightDirection), 0.0);
	
	vec3 ambient  = light.ambient;
	vec3 diffuse  = light.diffuse * diffuseFactor;
	
	return (vec4(ambient, 1.0) + vec4(diffuse, 1.0) * textureColor) * light.intensity;
}

vec4 calculatePointLight(PointLight light, vec3 normal, vec4 textureColor){
	vec3 lightDirection = worldFragPos - light.position;
	float distance = length(lightDirection);
	
	if(distance > light.range)
		return vec4(0.0,0.0,0.0,0.0);
		
	lightDirection = normalize(lightDirection);
	
	float diffuseFactor = max(dot(normal, lightDirection), 0.0);
	float attenuationFactor = 1.0f / (light.attenuation.x + (light.attenuation.y * distance) + (light.attenuation.z * distance * distance) + 0.0001);
	
	vec3 ambient  = light.ambient;
	vec3 diffuse  = light.diffuse * diffuseFactor;
	
	ambient *= attenuationFactor;
	diffuse *= attenuationFactor;
	
	return (vec4(ambient, 1.0) + vec4(diffuse, 1.0) * textureColor) * light.intensity;
}

vec4 calculateSpotLight(SpotLight light, vec3 normal, vec4 textureColor){
	vec3 lightDirection = normalize(worldFragPos - light.pointLight.position);
	float spotFactor = dot(lightDirection, light.direction); 
	
	vec4 resultingShade = vec4(0.0);
	float smoothnessFactor = 1.0 - ((1.0 - spotFactor) / (1.0 - light.cutOffAngle));
	if(spotFactor < light.cutOffAngle){
		resultingShade = calculatePointLight(light.pointLight, normal, textureColor) * smoothnessFactor;
	}else resultingShade = vec4(light.pointLight.ambient, 1.0) * smoothnessFactor;
	
	return resultingShade;
}

void main(){
	vec4 blendMapColor = texture(blendMap, out_TexCoords);
	
	float backTextureAmount = 1 - (blendMapColor.r + blendMapColor.g + blendMapColor.b);
	vec2 tiledCoords = out_TexCoords * 40.0;
	
	vec4 backgroundTextureColor = texture(backgroundTexture, tiledCoords) * backTextureAmount;
	vec4 rTextureColor = texture(rTexture, tiledCoords) * blendMapColor.r;
	vec4 gTextureColor = texture(gTexture, tiledCoords) * blendMapColor.g;
	vec4 bTextureColor = texture(bTexture, tiledCoords) * blendMapColor.b;
	
	vec4 totalTextureColor = backgroundTextureColor + rTextureColor + gTextureColor + bTextureColor;

	vec3 normal = normalize(out_Normal);
	
	vec4 resultingShade = calculateDirectionalLight(directionalLight, normal, totalTextureColor);
	
	for(int i = 0; i < MAX_POINT_LIGHTS; i++)
		resultingShade += calculatePointLight(pointLights[i], normal, totalTextureColor);
		
	for(int i = 0; i < MAX_SPOT_LIGHTS; i++)
		resultingShade += calculateSpotLight(spotLights[i], normal, totalTextureColor);
	
	outColor = mix(vec4(skyColor, 1.0), resultingShade, visibility);
}