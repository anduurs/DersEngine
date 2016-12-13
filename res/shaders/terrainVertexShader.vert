#version 400 core

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out vec2 texCoords;
out vec3 vertexNormal;
out vec3 cameraViewDirection;
out float visibility;
out vec3 vertexPosition;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

const float density = 0.0035;
const float gradient = 2;

void main(){
	vec4 worldPosition = modelMatrix * vec4(position, 1.0);
	texCoords = textureCoords;
	vertexNormal = (modelMatrix * vec4(normal, 0.0)).xyz;
	
	vertexPosition = worldPosition.xyz;

	cameraViewDirection = (inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPosition.xyz;
	
	vec4 positionRelativeToCamera = viewMatrix * worldPosition;
	
	float distance = length(positionRelativeToCamera.xyz);
	visibility = exp(-pow((distance * density), gradient));
	visibility = clamp(visibility, 0.0, 1.0);
	
	gl_Position = projectionMatrix * positionRelativeToCamera;
}