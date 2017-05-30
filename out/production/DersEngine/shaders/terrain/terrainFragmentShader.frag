#version 330 core

const int MAX_POINT_LIGHTS = 4;
const int MAX_SPOT_LIGHTS = 4;

out vec4 fragColor;

in VS_Data{
    vec3 position;
	vec2 textureCoords;
	vec3 normal;
	vec3 cameraViewDirection;
	float fogFactor;
} fs_in;

struct Material{
	sampler2D diffuseMap;
	sampler2D specularMap;
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

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;

uniform vec3 skyColor;
uniform vec3 ambientLight;

uniform int renderNormals;
uniform int renderTangents;
uniform int wireframeMode;

vec4 calculateLight(vec3 lightColor, vec3 lightDirection, float lightIntensity, vec3 normal, vec4 textureColor, vec4 specularMapColor){
    float diffuseFactor = max(dot(normal, lightDirection), 0.0);
	vec4 diffuseLight = vec4(lightColor, 1.0) * lightIntensity * diffuseFactor * vec4(material.baseColor, 1.0) * textureColor;

	vec4 specularLight = vec4(0.0, 0.0, 0.0, 1.0);

    if(material.shininess > 0.0){
        vec3 viewDirection = fs_in.cameraViewDirection;
        vec3 halfwayDirection = normalize(lightDirection + viewDirection);

        float normalizationFactor = ((material.shininess + 2.0) / 8.0);
        float specularFactor = pow(max(dot(normal, halfwayDirection), 0.0), material.shininess) * normalizationFactor;

        specularLight = vec4(lightColor, 1.0) * lightIntensity * specularFactor * vec4(material.specular, 1.0);

        if(material.useSpecularMap == 1.0){
            specularLight *= specularMapColor.r;
        }
    }

	return diffuseLight + specularLight;
}

vec4 calculateDirectionalLight(DirectionalLight directionalLight, vec3 normal, vec4 textureColor, vec4 specularMapColor){
	vec3 lightDirection = directionalLight.direction;
	return calculateLight(directionalLight.light.color, -lightDirection, directionalLight.light.intensity, normal, textureColor, specularMapColor);
}

vec4 calculatePointLight(PointLight pointLight, vec3 normal, vec4 textureColor, vec4 specularMapColor){
	vec3 lightDirection = fs_in.position - pointLight.position;
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

vec4 calculateSpotLight(SpotLight light, vec3 normal, vec4 textureColor, vec4 specularMapColor){
	vec3 lightDirection = normalize(fs_in.position - light.pointLight.position);

	float spotFactor = dot(lightDirection, light.direction);

	vec4 totalShade = vec4(0.0);
	float smoothnessFactor = 1.0 - ((1.0 - spotFactor) / (1.0 - light.cutOffAngle));
	
	if(spotFactor > light.cutOffAngle)
		totalShade = calculatePointLight(light.pointLight, normal, textureColor, specularMapColor) * smoothnessFactor;
	else totalShade = vec4(ambientLight, 1.0) * smoothnessFactor;
	
	return totalShade;
}

void main(){
	vec4 blendMapColor = texture(blendMap, fs_in.textureCoords);
	vec4 specularMapColor = texture(material.specularMap, fs_in.textureCoords);
	
	float backTextureAmount = 1 - (blendMapColor.r + blendMapColor.g + blendMapColor.b);
	vec2 tiledCoords = fs_in.textureCoords * 40.0;
	
	vec4 backgroundTextureColor = texture(backgroundTexture, tiledCoords) * backTextureAmount;
	vec4 rTextureColor = texture(rTexture, tiledCoords) * blendMapColor.r;
	vec4 gTextureColor = texture(gTexture, tiledCoords) * blendMapColor.g;
	vec4 bTextureColor = texture(bTexture, tiledCoords) * blendMapColor.b;
	
	vec4 totalTextureColor = backgroundTextureColor + rTextureColor + gTextureColor + bTextureColor;

	vec3 normal = fs_in.normal;

	vec4 totalShade = vec4(ambientLight, 1.0);
	
	if(directionalLight.light.intensity > 0)
		totalShade += calculateDirectionalLight(directionalLight, normal, totalTextureColor, specularMapColor);
	
	for(int i = 0; i < MAX_POINT_LIGHTS; i++)
		if(pointLights[i].light.intensity > 0)
			totalShade += calculatePointLight(pointLights[i], normal, totalTextureColor, specularMapColor);

	fragColor = mix(vec4(skyColor, 1.0), totalShade * totalTextureColor, fs_in.fogFactor);
}