#version 330 core

in vec4 vertexClipSpacePosition;
in vec2 textureCoords;
in vec3 toCameraVector;

layout (location = 0) out vec4 fragColor;
layout (location = 1) out vec4 brightColor;

uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;
uniform sampler2D dudvMap;

uniform float moveFactor;

const float waveDistortionFactor = 0.02;

void main() {
    //perform perspective division to transform the vertex clipspace pos to normalized device coords
    vec2 NDC = vertexClipSpacePosition.xy / vertexClipSpacePosition.w;
    //then convert the ndc pos to screen space so we can sample any point on the water quad
    //which will in turn allow us to sample the reflection and refraction textures correctly
    vec2 screenSpaceCoords = NDC / 2.0 + 0.5;

    vec2 reflectionTextureCoords = vec2(screenSpaceCoords.x, -screenSpaceCoords.y);
    vec2 refractionTextureCoords = vec2(screenSpaceCoords.x, screenSpaceCoords.y);

    vec2 waveDistortion1 = (2.0 * texture(dudvMap, vec2(textureCoords.x + moveFactor, textureCoords.y)).rg - 1.0) * waveDistortionFactor;
    vec2 waveDistortion2 = (2.0 * texture(dudvMap, vec2(-textureCoords.x + moveFactor, textureCoords.y + moveFactor)).rg - 1.0) * waveDistortionFactor;

    vec2 totalWaveDistortion = waveDistortion1 + waveDistortion2;

    reflectionTextureCoords += totalWaveDistortion;
    refractionTextureCoords += totalWaveDistortion;

    reflectionTextureCoords.x = clamp(reflectionTextureCoords.x, 0.001, 0.999);
    reflectionTextureCoords.y = clamp(reflectionTextureCoords.y, -0.999, -0.001);

    refractionTextureCoords = clamp(refractionTextureCoords, 0.001, 0.999);

    vec4 reflectionColor = texture(reflectionTexture, reflectionTextureCoords);
    vec4 refractionColor = texture(refractionTexture, refractionTextureCoords);

    vec3 viewVector = normalize(toCameraVector);
    float refractiveFactor = dot(viewVector, vec3(0.0, 1.0, 0.0));
    refractiveFactor = pow(refractiveFactor, 5.0);

    brightColor = vec4(0.0);
	fragColor = mix(reflectionColor, refractionColor, refractiveFactor);
	fragColor = mix(fragColor, vec4(0.0, 0.3, 0.5, 1.0), 0.2);
}