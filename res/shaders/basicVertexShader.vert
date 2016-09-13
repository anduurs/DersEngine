#version 400 core

in vec3 position;
in vec2 textureCoords;

out vec2 out_TexCoords;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

uniform float numOfRows;
uniform vec2 offset;

void main(){
	out_TexCoords = (textureCoords / numOfRows) + offset;
	vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
	gl_Position = projectionMatrix * viewMatrix * worldPosition;
}