#version 400 core

in vec2 out_TexCoords;
in vec3 out_Normal;
in vec3 toCameraVector;
in float visibility;

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
};

//struct PointLight{
//	vec3 attenuation;
	
//	vec3 ambient;
//	vec3 diffuse;
//	vec3 specular;
//}

uniform Material material;
uniform DirectionalLight directionalLight;
//uniform PointLight pointLights[0];

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



void main(){
	vec3 normal = normalize(out_Normal);
	vec3 viewDirection = normalize(toCameraVector);
	
	vec4 textureColor = texture(textureSampler, out_TexCoords);
	
	if(textureColor.a < 0.5){
		discard;
	}
	
	vec3 resultingShade = calculateDirectionalLight(directionalLight, normal, viewDirection, textureColor);
	
	//for(int i = 0; i < 0; i++){
		//resultingShade += calculatePointLight(pointLights[i], normal, viewDirection, textureColor, i);
	//}
	
	outColor = vec4(resultingShade, 1.0);
	//outColor = mix(vec4(skyColor, 1.0), vec4(resultingShade, 1.0), visibility);
}