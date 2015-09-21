#version 130

in vec3 position;
in vec2 texCoordIn;

out vec2 texCoord; 
out vec3 outColor;

uniform mat4 projection_matrix;
uniform mat4 view_matrix = mat4(1.0);
uniform mat4 model_matrix; 

uniform int is_Static;

void main(){
	texCoord = texCoordIn;
	outColor = vec3(1,1,1.6);
	
	if(is_Static == 1){
		gl_Position = projection_matrix * model_matrix * vec4(position, 1);
	}else {
		gl_Position = projection_matrix * vec4(position, 1);
	}
}