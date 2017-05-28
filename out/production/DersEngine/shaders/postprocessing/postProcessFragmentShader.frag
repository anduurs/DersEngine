#version 330 core

in vec2 textureCoords;

out vec4 fragColor;

uniform sampler2D textureSampler;

void main(){
    vec3 hdrColor = texture(textureSampler, textureCoords).rgb;
    vec3 toneMapping = hdrColor / (hdrColor + vec3(1.0));
    fragColor = vec4(hdrColor, 1.0);


    //apply gamma correction
    //fragColor.rgb = pow(fragColor.rgb, vec3(1.0/2.0));
}