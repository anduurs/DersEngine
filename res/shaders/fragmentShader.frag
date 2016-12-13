#version 400 core

const int MAX_POINT_LIGHTS = 100;
const int MAX_SPOT_LIGHTS = 4;

in vec2 texCoords;
in vec3 vertexNormal;
in vec3 cameraViewDirection;
in float visibility;
in vec3 vertexPosition;

out vec4 outColor;

uniform vec3 skyColor;
uniform vec3 ambientLight;

struct Material{
	sampler2D diffuseMap;
	sampler2D specularMap;
	int useSpecularMap;
	vec3 baseColor;
	vec3 specular;
	vec3 emissive;
	float shininess;
};

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

uniform Material material;
uniform DirectionalLight directionalLight;
uniform PointLight pointLights[MAX_POINT_LIGHTS];
uniform SpotLight spotLights[MAX_SPOT_LIGHTS];

vec4 calculateLight(vec3 lightColor, vec3 lightDirection, float lightIntensity, vec3 normal, vec4 textureColor, vec4 specularMapColor){
	vec3 viewDirection = normalize(cameraViewDirection);
	
	float diffuseFactor = max(dot(normal, lightDirection), 0.0);
	
	vec3 reflectedLightDirection = reflect(-lightDirection, normal);
	float normalizationFactor = ((material.shininess + 2.0) / 8.0);
	float specularFactor = pow(max(dot(viewDirection, reflectedLightDirection), 0.0), material.shininess) * normalizationFactor;
	
	vec4 diffuseLight  =  vec4(lightColor, 1.0) * lightIntensity * diffuseFactor * vec4(material.baseColor, 1.0) * textureColor; 
	vec4 specularLight = vec4(0.0);
	
	if(material.useSpecularMap == 1)
		specularLight =  vec4(lightColor, 1.0) * lightIntensity * specularFactor * vec4(material.specular, 1.0) * specularMapColor.r;
	else specularLight =  vec4(lightColor, 1.0) * lightIntensity * specularFactor * vec4(material.specular, 1.0); 	

	return diffuseLight + specularLight;
}

vec4 calculateDirectionalLight(DirectionalLight directionalLight, vec3 normal, vec4 textureColor, vec4 specularMapColor){
	return calculateLight(directionalLight.light.color, normalize(-directionalLight.direction), 
							directionalLight.light.intensity, normal, textureColor, specularMapColor);
}

vec4 calculatePointLight(PointLight pointLight, vec3 normal, vec4 textureColor, vec4 specularMapColor){
	vec3 lightDirection = vertexPosition - pointLight.position;
	float distance = length(lightDirection);
	
	if(distance > pointLight.range)
		return vec4(0.0);
	
	vec4 lightColor = calculateLight(pointLight.light.color, normalize(-lightDirection), pointLight.light.intensity, normal, textureColor, specularMapColor);
	
	float constant = pointLight.attenuation.x;
	float linear = pointLight.attenuation.y * distance;
	float quadratic = pointLight.attenuation.z * distance * distance;
	
	float attenuationFactor = 1.0f / (constant + linear + quadratic + 0.0001);

	return lightColor * attenuationFactor;
}

vec4 calculateSpotLight(SpotLight light, vec3 normal, vec4 textureColor, vec4 specularMapColor){
	vec3 lightDirection = normalize(vertexPosition - light.pointLight.position);
	float spotFactor = dot(lightDirection, light.direction); 
	
	vec4 totalShade = vec4(0.0);
	float smoothnessFactor = 1.0 - ((1.0 - spotFactor) / (1.0 - light.cutOffAngle));
	
	if(spotFactor > light.cutOffAngle)
		totalShade = calculatePointLight(light.pointLight, normal, textureColor, specularMapColor) * smoothnessFactor;
	else totalShade = vec4(ambientLight, 1.0) * smoothnessFactor;
	
	return totalShade;
}

void main(){
	vec4 textureColor = texture(material.diffuseMap, texCoords);
	vec4 specularMapColor = texture(material.specularMap, texCoords);
	
	if(textureColor.a < 0.5)
		discard;
		
	vec3 normal = normalize(vertexNormal);
	
	vec4 emissive = vec4(material.emissive, 1.0) * textureColor; 
	vec4 ambient = vec4(ambientLight, 1.0);	
	
	vec4 totalShade = ambient + emissive;
	
	if(directionalLight.light.intensity > 0)
		totalShade += calculateDirectionalLight(directionalLight, normal, textureColor, specularMapColor);
	
	for(int i = 0; i < MAX_POINT_LIGHTS; i++)
		if(pointLights[i].light.intensity > 0)
			totalShade += calculatePointLight(pointLights[i], normal, textureColor, specularMapColor);
		
	for(int i = 0; i < MAX_SPOT_LIGHTS; i++)
		if(spotLights[i].pointLight.light.intensity > 0)
			totalShade += calculateSpotLight(spotLights[i], normal, textureColor, specularMapColor);
	
	outColor = mix(vec4(skyColor, 1.0), totalShade, visibility);
}