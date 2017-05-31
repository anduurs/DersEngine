#version 330 core

out vec4 fragColor;

in vec2 blurTextureCoords[11];

uniform sampler2D originalTexture;

uniform float weights[] = {0.0093, 0.028002, 0.065984,
                           0.121703, 0.175713, 0.198596,
                           0.175713, 0.121703, 0.065984,
                           0.028002, 0.0093};

void main(void){
	fragColor = vec4(0.0);

	for(int i = 0; i < 11; i++){
        fragColor += texture(originalTexture, blurTextureCoords[i]) * weights[i];
	}
}