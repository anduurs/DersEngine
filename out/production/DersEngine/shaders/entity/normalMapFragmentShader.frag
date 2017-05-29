#version 330 core

const int MAX_POINT_LIGHTS = 4;
const int MAX_SPOT_LIGHTS = 4;

out vec4 fragColor;

in VS_Data{
    vec3 position;
    vec3 tangent;
	vec2 textureCoords;
	vec3 cameraViewPosition;
	float fogFactor;
	vec3 directionalLightPosition;
	vec3 pointLightPositions[MAX_POINT_LIGHTS];
} fs_in;

struct Material{
	sampler2D diffuseMap;
	sampler2D specularMap;
	sampler2D normalMap;
	float useSpecularMap;
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
};

struct PointLight{
	Light light;
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

uniform vec3 skyColor;
uniform vec3 ambientLight;

vec4 calculateLight(vec3 lightColor, vec3 lightDirection, float lightIntensity, vec3 normal, vec4 textureColor, vec4 specularMapColor){
	float diffuseFactor = max(dot(normal, lightDirection), 0.0);

	vec3 viewDirection = normalize(fs_in.cameraViewPosition - fs_in.position);
	vec3 halfwayDirection = normalize(lightDirection + viewDirection);

	float normalizationFactor = ((material.shininess + 2.0) / 8.0);
	float specularFactor = pow(max(dot(normal, halfwayDirection), 0.0), material.shininess) * normalizationFactor;

	vec4 diffuseLight  =  vec4(lightColor, 1.0) * lightIntensity * diffuseFactor * vec4(material.baseColor, 1.0) * textureColor;
	vec4 specularLight = vec4(0.0);

	if(material.useSpecularMap == 1.0)
		specularLight = vec4(lightColor, 1.0) * lightIntensity * specularFactor * vec4(material.specular, 1.0) * specularMapColor.r;
	else specularLight =  vec4(lightColor, 1.0) * lightIntensity * specularFactor * vec4(material.specular, 1.0);

	return diffuseLight + specularLight;
}

vec4 calculateDirectionalLight(DirectionalLight directionalLight, vec3 normal, vec4 textureColor, vec4 specularMapColor){
	vec3 lightDirection = normalize(fs_in.position - fs_in.directionalLightPosition);
	return calculateLight(directionalLight.light.color, -lightDirection, directionalLight.light.intensity, normal, textureColor, specularMapColor);
}

vec4 calculatePointLight(PointLight pointLight, vec3 normal, vec4 textureColor, vec4 specularMapColor, int index){
	vec3 lightDirection = fs_in.position - fs_in.pointLightPositions[index];
	float distance = length(lightDirection);

	if(distance > pointLight.range)
		return vec4(0.0);

	vec4 lightColor = calculateLight(pointLight.light.color, normalize(-lightDirection), pointLight.light.intensity, normal, textureColor, specularMapColor);

	float constant = pointLight.attenuation.x;
	float linear = pointLight.attenuation.y * distance;
	float quadratic = pointLight.attenuation.z * distance * distance;

	float attenuationFactor = 1.0 / (constant + linear + quadratic + 0.0001);

	return lightColor * attenuationFactor;
}

void main(){
	vec4 textureColor = texture(material.diffuseMap, fs_in.textureCoords);
	vec4 specularMapColor = texture(material.specularMap, fs_in.textureCoords);

	if(textureColor.a < 0.5)
		discard;

	vec3 normal = normalize((2.0 * texture(material.normalMap, fs_in.textureCoords).rgb - 1.0));

	vec4 emissive = vec4(material.emissive, 1.0) * textureColor;
	vec4 ambient = vec4(ambientLight, 1.0);

	vec4 totalShade = ambient + emissive;

	if(directionalLight.light.intensity > 0)
		totalShade += calculateDirectionalLight(directionalLight, normal, textureColor, specularMapColor);

	for(int i = 0; i < MAX_POINT_LIGHTS; i++)
		if(pointLights[i].light.intensity > 0)
			totalShade += calculatePointLight(pointLights[i], normal, textureColor, specularMapColor, i);

	fragColor = mix(vec4(skyColor, 1.0), totalShade, fs_in.fogFactor);
	//fragColor = vec4(fs_in.tangent, 1.0);
}