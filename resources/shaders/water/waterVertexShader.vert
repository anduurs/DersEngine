#version 330 core

in vec2 position;

out vec4 vertexClipSpacePosition;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main() {
    vertexClipSpacePosition = projectionMatrix * viewMatrix * modelMatrix * vec4(position.x, 0.0, position.y, 1.0);
	gl_Position = vertexClipSpacePosition;
}