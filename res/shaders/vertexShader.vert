#version 400 core

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out vec2 out_TexCoords;
out vec3 out_Normal;
out vec3 toLightVector[4];
out vec3 toCameraVector;
out float visibility;

uniform mat4 modelMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

uniform vec3 lightPosition[4];
uniform float useFakeLighting;

uniform float numOfRows;
uniform vec2 offset;

const float density = 0.0035;
const float gradient = 2;

void main(){
	vec4 worldPos = modelMatrix * vec4(position, 1.0);
	out_TexCoords = (textureCoords / numOfRows) + offset;
	vec3 actualNormal = normal;
	
	if(useFakeLighting == 1.0){
		actualNormal = vec3(0.0, 1.0, 0.0);
	}
	
	out_Normal = (modelMatrix * vec4(actualNormal, 0.0)).xyz;
	
	for(int i = 0; i < 4 ; i++){
		toLightVector[i] = lightPosition[i] - worldPos.xyz;
	}
	
	toCameraVector = (inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPos.xyz;
	
	vec4 positionRelativeToCamera = viewMatrix * worldPos;
	
	float distance = length(positionRelativeToCamera.xyz);
	visibility = exp(-pow((distance * density), gradient));
	visibility = clamp(visibility, 0.0, 1.0);
	
	gl_Position = projectionMatrix * positionRelativeToCamera;
}