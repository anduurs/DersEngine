#version 400 core

const int MAX_POINT_LIGHTS = 3;

in vec2 out_TexCoords;
in vec3 out_Normal;
in vec3 toCameraVector;
in float visibility;
in vec3 worldFragPos;

out vec4 outColor;

uniform sampler2D textureSampler;
uniform vec3 skyColor;

struct Material{
	vec3 ambient;
	vec3 diffuse;
	vec3 specular;
	
	float shininess;
};

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
};

uniform Material material;
uniform DirectionalLight directionalLight;
uniform PointLight pointLights[MAX_POINT_LIGHTS];

vec3 calculateDirectionalLight(DirectionalLight light, vec3 normal, vec3 viewDirection, vec4 textureColor){
	vec3 lightDirection = normalize(-light.direction);
	
	float diffuseFactor = max(dot(normal, lightDirection), 0.0);
	
	vec3 reflectedLightDirection = reflect(-lightDirection, normal);
	float normalizationFactor = ((material.shininess + 2.0) / 8.0);
	float specularFactor = pow(max(dot(viewDirection, reflectedLightDirection), 0.0), material.shininess) * normalizationFactor;
	
	vec3 ambient  = light.ambient * material.ambient;
	vec3 diffuse  = light.diffuse * material.diffuse * diffuseFactor * textureColor.rgb;
	vec3 specular = light.specular * material.specular * specularFactor;
	
	return (ambient + diffuse + specular);
}

vec3 calculatePointLight(PointLight light, vec3 normal, vec3 viewDirection, vec4 textureColor){
	vec3 lightDirection = worldFragPos - light.position;
	float distance = length(lightDirection);
	lightDirection = normalize(lightDirection);
	
	float diffuseFactor = max(dot(normal, lightDirection), 0.0);
	
	vec3 reflectedLightDirection = reflect(-lightDirection, normal);
	float normalizationFactor = ((material.shininess + 2.0) / 8.0);
	float specularFactor = pow(max(dot(viewDirection, reflectedLightDirection), 0.0), material.shininess) * normalizationFactor;
	
	float attenuationFactor = 1.0f / (light.attenuation.x + (light.attenuation.y * distance) + (light.attenuation.z * distance * distance) + 0.0001);
	
	vec3 ambient  = light.ambient * material.ambient;
	vec3 diffuse  = light.diffuse * material.diffuse * diffuseFactor * textureColor.rgb;
	vec3 specular = light.specular * material.specular * specularFactor;
	
	ambient *= attenuationFactor;
	diffuse *= attenuationFactor;
	specular *= attenuationFactor;
	
	return (ambient + diffuse + specular);
}

void main(){
	vec3 normal = normalize(out_Normal);
	vec3 viewDirection = normalize(toCameraVector);
	
	vec4 textureColor = texture(textureSampler, out_TexCoords);
	
	if(textureColor.a < 0.5){
		discard;
	}
	
	vec3 resultingShade = calculateDirectionalLight(directionalLight, normal, viewDirection, textureColor);
	
	for(int i = 0; i < MAX_POINT_LIGHTS; i++)
		resultingShade += calculatePointLight(pointLights[i], normal, viewDirection, textureColor);
	
	outColor = mix(vec4(skyColor, 1.0), vec4(resultingShade, 1.0), visibility);
}