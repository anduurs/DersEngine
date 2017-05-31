#version 330 core

in vec2 textureCoords;

out vec4 fragColor;

uniform sampler2D colorTexture;

void main(void){
    vec4 color = texture(colorTexture, textureCoords);
    //use luma conversion to calculate the brightness
    float brightness = dot(color.rgb, vec3(0.2126, 0.7152, 0.0722));
    fragColor = color * brightness;
}