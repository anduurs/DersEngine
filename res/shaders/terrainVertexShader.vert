#version 400 core

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out vec2 out_TexCoords;
out vec3 out_Normal;
out vec3 toLightVector;
out vec3 toCameraVector;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

uniform vec3 lightPosition;

void main(){
	vec4 worldPos = transformationMatrix * vec4(position, 1.0);
	out_TexCoords = textureCoords * 40.0;
	out_Normal = (transformationMatrix * vec4(normal, 0.0)).xyz;
	toLightVector = lightPosition - worldPos.xyz;
	toCameraVector = (inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPos.xyz;
	gl_Position = projectionMatrix * viewMatrix * worldPos;
}