#version 130

in vec3 position;
in vec2 texCoordIn;

out vec2 texCoord; 
out vec3 outColor;

uniform mat4 projection_matrix;

void main() 
{
	texCoord = texCoordIn;
	outColor = vec3(1,1,1.6);
	gl_Position = projection_matrix  * vec4(position, 1);
}