#version 400 core

in vec2 position;

uniform mat4 modelViewMatrix;
uniform mat4 projectionMatrix;

void main(){
	gl_Position = projectionMatrix * modelViewMatrix * vec4(position, 0.0, 1.0);
}