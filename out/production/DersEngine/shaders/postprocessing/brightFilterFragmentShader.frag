#version 330 core

in vec2 textureCoords;

out vec4 fragColor;

uniform sampler2D colorTexture;

void main(void){
    vec4 color = texture(colorTexture, textureCoords);
    //use luma conversion to calculate the brightness
    float brightness = (color.r * 0.2126) + (color.g * 0.7152) + (color.b * 0.0722);
    fragColor = color * brightness;
}