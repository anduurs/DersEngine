#version 330 core

const int MAX_POINT_LIGHTS = 4;
const int MAX_SPOT_LIGHTS = 4;

const float density = 0.0035;
const float gradient = 2;

in vec3 position;
in vec2 textureCoords;
in vec3 normal;
in vec3 tangent;

out VS_Data{
    vec3 position;
	vec2 textureCoords;
	vec3 cameraViewPosition;
	float fogFactor;
	vec3 directionalLightDirection;
	vec3 pointLightPositions[MAX_POINT_LIGHTS];
} vs_out;

uniform vec3 directionalLightDirection;
uniform vec3 pointLightPositions[MAX_POINT_LIGHTS];

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

	vs_out.textureCoords = (textureCoords / numOfRows) + offset;
	vec3 actualNormal = normal;

	if(useFakeLighting == 1.0)
		actualNormal = vec3(0.0, 1.0, 0.0);

    vec3 finalNormal = normalize((modelMatrix * vec4(actualNormal, 0.0)).xyz);
    vec3 vertexTangent = normalize((modelMatrix * vec4(tangent, 0.0)).xyz);
    vec3 finalTangent =  normalize(vertexTangent - finalNormal * dot(vertexTangent, finalNormal));

    vec3 vertexBiTangent = normalize(cross(finalNormal, finalTangent));
    mat3 toTangentSpaceMatrix = transpose(mat3(finalTangent, vertexBiTangent, finalNormal));

    vs_out.position = toTangentSpaceMatrix * worldPosition.xyz;
    vs_out.cameraViewPosition = toTangentSpaceMatrix * ((inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz);
    vs_out.directionalLightDirection = toTangentSpaceMatrix * directionalLightDirection;

    for(int i = 0; i < MAX_POINT_LIGHTS; i++)
        vs_out.pointLightPositions[i] = toTangentSpaceMatrix * pointLightPositions[i];

	float distance = length(viewSpacePosition.xyz);
	float fogFactor = exp(-pow((distance * density), gradient));

	vs_out.fogFactor = 1.0f;//clamp(fogFactor, 0.0, 1.0);

	gl_Position = projectionMatrix * viewSpacePosition;
}