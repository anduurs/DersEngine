#version 400 core

in vec3 position;
in vec2 textureCoords;
in vec3 normal;
in vec3 tangent;

out VS_Data{
	vec3 position;
	vec2 textureCoords;
	vec3 normal;
	vec3 tangent;
	vec3 cameraViewDirection;
	float visibility;
	float usingNormalMap;
	mat3 toTangentSpaceMatrix;
} vs_out;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

uniform float usingNormalMap;

const float density = 0.0035;
const float gradient = 2;

void main(){
	vec4 worldPosition = modelMatrix * vec4(position, 1.0);
	vec4 viewSpacePosition = viewMatrix * worldPosition;

	vs_out.usingNormalMap = usingNormalMap;

	vs_out.textureCoords = textureCoords;
	vs_out.normal = normalize((modelMatrix * vec4(normal, 0.0)).xyz);
	vs_out.position = worldPosition.xyz;
	
	vs_out.cameraViewDirection = (inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPosition.xyz;
	vs_out.tangent = normalize((modelMatrix * vec4(tangent, 0.0)).xyz);

	if(usingNormalMap == 1.0){
		vec3 vertexTangent = normalize((modelMatrix * vec4(tangent, 0.0)).xyz);
		//re-orthogonalize the tangent vector
		vertexTangent =  normalize(vertexTangent - vs_out.normal * dot(vertexTangent, vs_out.normal));
		vs_out.tangent = vertexTangent;
		vec3 vertexBiTangent = normalize(cross(vs_out.normal, vertexTangent));
		vs_out.toTangentSpaceMatrix = mat3(vs_out.tangent, vertexBiTangent, vs_out.normal);
		vs_out.cameraViewDirection = ((inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPosition.xyz) * vs_out.toTangentSpaceMatrix;
	}
	
	float distance = length(viewSpacePosition.xyz);
	float visibility = exp(-pow((distance * density), gradient));
	vs_out.visibility = clamp(visibility, 0.0, 1.0);
	
	gl_Position = projectionMatrix * viewSpacePosition;
}