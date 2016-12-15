#version 400 core

in vec3 position;
in vec2 textureCoords;
in vec3 normal;
in vec3 tangent;

out vec2 texCoords;
out vec3 vertexNormal;
out vec3 cameraViewDirection;
out float visibility;
out vec3 vertexPosition;
out vec3 vertexTangent;

uniform mat4 modelMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

uniform float useFakeLighting;
uniform float numOfRows;

uniform vec2 offset;

const float density = 0.0035;
const float gradient = 2;

void main(){
	vec4 worldPosition = modelMatrix * vec4(position, 1.0);

	texCoords = (textureCoords / numOfRows) + offset;
	vec3 actualNormal = normal;
	
	if(useFakeLighting == 1.0)
		actualNormal = vec3(0.0, 1.0, 0.0);
	
	vertexPosition = worldPosition.xyz;
	vertexNormal = (modelMatrix * vec4(actualNormal, 0.0)).xyz;
	vertexTangent = (modelMatrix * vec4(tangent, 0.0)).xyz;
	
	cameraViewDirection = (inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPosition.xyz;
	
	vec4 positionRelativeToCamera = viewMatrix * worldPosition;
	
	float distance = length(positionRelativeToCamera.xyz);
	visibility = exp(-pow((distance * density), gradient));
	visibility = clamp(visibility, 0.0, 1.0);
	
	gl_Position = projectionMatrix * positionRelativeToCamera;
}