#version 330 core

in vec2 position;

out vec2 textureCoords;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main() {
	textureCoords = vec2(position.x/2.0 + 0.5, position.y/2.0 + 0.5);
	gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(position.x, 0.0, position.y, 1.0);
}