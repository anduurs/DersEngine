#version 400 core

const int MAX_POINT_LIGHTS = 100;
const int MAX_SPOT_LIGHTS = 4;

in vec2 texCoords;
in vec3 vertexNormal;
in vec3 cameraViewDirection;
in float visibility;
in vec3 vertexPosition;

out vec4 outColor;

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;

uniform vec3 skyColor;
uniform vec3 ambientLight;

struct Light{
	vec3 color;
	float intensity;
};

struct DirectionalLight{
	Light light;
	vec3 direction;
};

struct PointLight{
	Light light;
	vec3 position;
	vec3 attenuation;
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

vec4 calculateLight(vec3 lightColor, vec3 lightDirection, float lightIntensity, vec3 normal, vec4 textureColor){
	float diffuseFactor = max(dot(normal, lightDirection), 0.0);
	float shininess = 0.0;
	
	vec3 viewDirection = normalize(cameraViewDirection);
	vec3 reflectedLightDirection = reflect(-lightDirection, normal);
	
	float normalizationFactor = ((shininess + 2.0) / 8.0);
	float specularFactor = pow(max(dot(viewDirection, reflectedLightDirection), 0.0), shininess) * normalizationFactor;
	
	vec4 diffuseLight  =  vec4(lightColor, 1.0) * lightIntensity * diffuseFactor; 
	vec4 specularLight =  vec4(lightColor, 1.0) * lightIntensity * specularFactor;
	
	return diffuseLight + specularLight;
}

vec4 calculateDirectionalLight(DirectionalLight directionalLight, vec3 normal, vec4 textureColor){
	return calculateLight(directionalLight.light.color, normalize(-directionalLight.direction), directionalLight.light.intensity, normal, textureColor);
}

vec4 calculatePointLight(PointLight pointLight, vec3 normal, vec4 textureColor){
	vec3 lightDirection = vertexPosition - pointLight.position;
	float distance = length(lightDirection);
	
	if(distance > pointLight.range)
		return vec4(0.0);
	
	vec4 lightColor = calculateLight(pointLight.light.color, normalize(-lightDirection), pointLight.light.intensity, normal, textureColor);
	
	float constant = pointLight.attenuation.x;
	float linear = pointLight.attenuation.y * distance;
	float quadratic = pointLight.attenuation.z * distance * distance;
	
	float attenuationFactor = 1.0f / (constant + linear + quadratic + 0.0001);

	return lightColor * attenuationFactor;
}

vec4 calculateSpotLight(SpotLight light, vec3 normal, vec4 textureColor){
	vec3 lightDirection = normalize(vertexPosition - light.pointLight.position);
	float spotFactor = dot(lightDirection, light.direction); 
	
	vec4 totalShade = vec4(0.0);
	float smoothnessFactor = 1.0 - ((1.0 - spotFactor) / (1.0 - light.cutOffAngle));
	
	if(spotFactor > light.cutOffAngle)
		totalShade = calculatePointLight(light.pointLight, normal, textureColor) * smoothnessFactor;
	else totalShade = vec4(ambientLight, 1.0) * smoothnessFactor;
	
	return totalShade;
}

void main(){
	vec4 blendMapColor = texture(blendMap, texCoords);
	
	float backTextureAmount = 1 - (blendMapColor.r + blendMapColor.g + blendMapColor.b);
	vec2 tiledCoords = texCoords * 40.0;
	
	vec4 backgroundTextureColor = texture(backgroundTexture, tiledCoords) * backTextureAmount;
	vec4 rTextureColor = texture(rTexture, tiledCoords) * blendMapColor.r;
	vec4 gTextureColor = texture(gTexture, tiledCoords) * blendMapColor.g;
	vec4 bTextureColor = texture(bTexture, tiledCoords) * blendMapColor.b;
	
	vec4 totalTextureColor = backgroundTextureColor + rTextureColor + gTextureColor + bTextureColor;

	vec3 normal = normalize(vertexNormal);

	vec4 totalShade = vec4(ambientLight, 1.0);
	
	if(directionalLight.light.intensity > 0)
		totalShade += calculateDirectionalLight(directionalLight, normal, totalTextureColor);
	
	for(int i = 0; i < MAX_POINT_LIGHTS; i++)
		if(pointLights[i].light.intensity > 0)
			totalShade += calculatePointLight(pointLights[i], normal, totalTextureColor);
		
	for(int i = 0; i < MAX_SPOT_LIGHTS; i++)
		if(spotLights[i].pointLight.light.intensity > 0)
			totalShade += calculateSpotLight(spotLights[i], normal, totalTextureColor);
	
	outColor = mix(vec4(skyColor, 1.0), totalShade * totalTextureColor, visibility);
}