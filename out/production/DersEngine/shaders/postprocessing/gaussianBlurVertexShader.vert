#version 330 core

in vec2 position;

out vec2 blurTextureCoords[11];

uniform float targetDimension;
uniform int horizontalBlur;

void main(void){
	gl_Position = vec4(position, 0.0, 1.0);

	vec2 textureCoords = position * 0.5 + 0.5;
	float pixelSize = 1.0 / targetDimension;

	if(horizontalBlur == 0){
	    for(int i = -5; i <= 5; i++){
            blurTextureCoords[i + 5] = textureCoords + vec2(0.0, pixelSize * i);
        }
    }else{
        for(int i = -5; i <= 5; i++){
            blurTextureCoords[i + 5] = textureCoords + vec2(pixelSize * i, 0.0);
        }
    }


}