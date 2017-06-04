#version 330 core

in vec4 vertexClipSpacePosition;

layout (location = 0) out vec4 fragColor;
layout (location = 1) out vec4 brightColor;

uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;

void main() {
    //perform perspective division to transform the vertex clipspace pos to normalized device coords
    vec2 NDC = vertexClipSpacePosition.xy / vertexClipSpacePosition.w;
    //then convert the ndc pos to screen space so we can sample any point on the water quad
    //which will in turn allow us to sample the reflection and refraction textures correctly
    vec2 screenSpaceCoords = NDC / 2.0 + 0.5;

    vec4 reflectionColor = texture(reflectionTexture, vec2(screenSpaceCoords.x, -screenSpaceCoords.y));
    vec4 refractionColor = texture(refractionTexture, vec2(screenSpaceCoords.x, screenSpaceCoords.y));

    brightColor = vec4(0.0);
	fragColor = mix(reflectionColor, refractionColor, 0.5);
}