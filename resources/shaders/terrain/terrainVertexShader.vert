#version 330 core

const float density = 0.0035;
const float gradient = 2;

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out VS_Data{
    vec3 position;
	vec2 textureCoords;
	vec3 normal;
	vec3 cameraViewDirection;
	float fogFactor;
} vs_out;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

uniform float useFakeLighting;
uniform float numOfRows;

uniform vec2 offset;

uniform vec4 plane;

void main(){
	vec4 worldPosition = modelMatrix * vec4(position, 1.0);
	gl_ClipDistance[0] = dot(worldPosition, plane);
	vec4 viewSpacePosition = viewMatrix * worldPosition;

	vec3 actualNormal = normal;
	

	actualNormal = vec3(0.0, 1.0, 0.0);

    vs_out.position = worldPosition.xyz;
	vs_out.textureCoords = (textureCoords / numOfRows) + offset;
	vs_out.normal = normalize((modelMatrix * vec4(actualNormal, 0.0)).xyz);
	vs_out.cameraViewDirection = normalize((inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPosition.xyz);

	float distance = length(viewSpacePosition.xyz);
	float fogFactor = exp(-pow((distance * density), gradient));
	vs_out.fogFactor = 1.0f; //clamp(fogFactor, 0.0, 1.0);
	
	gl_Position = projectionMatrix * viewSpacePosition;
}