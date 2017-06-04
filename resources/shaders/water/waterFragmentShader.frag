#version 330 core

in vec2 textureCoords;

layout (location = 0) out vec4 fragColor;
layout (location = 1) out vec4 brightColor;

void main() {
    brightColor = vec4(0.0);
	fragColor = vec4(0.0, 0.0, 1.0, 1.0);
}