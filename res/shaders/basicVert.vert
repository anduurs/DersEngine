#version 400 core

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out vec2 out_TexCoords;
out vec3 out_Normal;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

uniform vec3 lightPosition;

void main(){
	out_TexCoords = textureCoords;
	gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
}