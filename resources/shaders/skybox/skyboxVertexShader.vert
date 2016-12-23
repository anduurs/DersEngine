#version 400 core

in vec3 position;
out vec3 textureCoords;

uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main(){
	textureCoords = position;
	gl_Position = projectionMatrix * viewMatrix * vec4(position, 1.0);
}