#version 330 core

in vec2 position;

out vec2 textureCoords;

uniform mat4 modelMatrix;

void main(){
	//calculate the texture coordinates based on the vertex position
	textureCoords = vec2((position.x + 1.0) / 2.0, 1 - (position.y + 1.0) / 2.0);
	gl_Position = modelMatrix * vec4(position, 0.0, 1.0);
}