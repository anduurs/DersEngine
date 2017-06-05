#version 330 core

in vec4 vertexClipSpacePosition;
in vec2 textureCoords;
in vec3 cameraViewDirection;
in vec3 lightDirection;

layout (location = 0) out vec4 fragColor;
layout (location = 1) out vec4 brightColor;

uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;
uniform sampler2D dudvMap;
uniform sampler2D normalMap;
uniform sampler2D depthMap;

uniform float moveFactor;

const float waveDistortionFactor = 0.04;
const float shininess = 20.0;
const float reflectivity = 0.5;
uniform vec3 lightColor;

vec4 calculateSpecularLight(vec3 lightColor, vec3 lightDirection, vec3 viewDirection, vec3 normal, float waterDepth){
    vec3 reflectedLight = reflect(lightDirection, normal);
    float specularFactor = pow(max(dot(reflectedLight, viewDirection), 0.0), shininess);
    return vec4(lightColor, 1.0) * specularFactor * reflectivity * clamp(waterDepth / 5.0, 0.0, 1.0);
}

float convertToLinearDepth(float depth, float nearPlane, float farPlane){
    return 2.0 * nearPlane * farPlane / (farPlane + nearPlane - (2.0 * depth - 1.0) * (farPlane - nearPlane));
}

void main() {
    //perform perspective division to transform the vertex clipspace pos to normalized device coords
    vec2 NDC = vertexClipSpacePosition.xy / vertexClipSpacePosition.w;
    //then convert the ndc pos to screen space so we can sample any point on the water quad
    //which will in turn allow us to sample the reflection and refraction textures correctly
    vec2 screenSpaceCoords = NDC / 2.0 + 0.5;

    vec2 reflectionTextureCoords = vec2(screenSpaceCoords.x, -screenSpaceCoords.y);
    vec2 refractionTextureCoords = vec2(screenSpaceCoords.x, screenSpaceCoords.y);

    float nearClipPlane = 0.1;
    float farClipPlane = 10000.0;
    float depth = texture(depthMap, refractionTextureCoords).r;
    //calculates the distance from the camera to the terrain under the water
    float floorDistance = convertToLinearDepth(depth, nearClipPlane, farClipPlane);
    //calculates the distance from the camera to the water surface
    float waterDistance = convertToLinearDepth(gl_FragCoord.z, nearClipPlane, farClipPlane);

    float waterDepth = floorDistance - waterDistance;

    vec2 waveDistortionTexCoords = texture(dudvMap, vec2(textureCoords.x + moveFactor, textureCoords.y)).rg * 0.1;
    waveDistortionTexCoords = textureCoords + vec2(waveDistortionTexCoords.x, waveDistortionTexCoords.y + moveFactor);
    vec2 totalWaveDistortion = (texture(dudvMap, waveDistortionTexCoords).rg * 2.0 - 1.0) * waveDistortionFactor *
    clamp(waterDepth / 20.0, 0.0, 1.0);

    reflectionTextureCoords += totalWaveDistortion;
    refractionTextureCoords += totalWaveDistortion;

    reflectionTextureCoords.x = clamp(reflectionTextureCoords.x, 0.001, 0.999);
    reflectionTextureCoords.y = clamp(reflectionTextureCoords.y, -0.999, -0.001);

    refractionTextureCoords = clamp(refractionTextureCoords, 0.001, 0.999);

    vec4 reflectionColor = texture(reflectionTexture, reflectionTextureCoords);
    vec4 refractionColor = texture(refractionTexture, refractionTextureCoords);

    vec4 normalMapColor = texture(normalMap, waveDistortionTexCoords);
    vec3 normal = vec3(normalMapColor.r * 2.0 - 1, normalMapColor.b * 3.0, normalMapColor.g * 2.0 - 1.0);
    normal = normalize(normal);

    vec3 viewDirection = normalize(cameraViewDirection);
    float refractiveFactor = dot(viewDirection, normal);
    refractiveFactor = clamp(pow(refractiveFactor, 5.0), 0.0, 1.0);

    vec4 specularLight = calculateSpecularLight(lightColor, normalize(lightDirection), viewDirection, normal, waterDepth);

    brightColor = vec4(0.0);

	fragColor = mix(reflectionColor, refractionColor, refractiveFactor);
	fragColor = mix(fragColor, vec4(0.0, 0.3, 0.7, 1.0), 0.2) + specularLight;
	fragColor.a = clamp(waterDepth / 5.0, 0.0, 1.0);
}