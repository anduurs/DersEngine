#version 330 core

in vec2 position;

out vec4 vertexClipSpacePosition;
out vec2 textureCoords;
out vec3 toCameraVector;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

uniform vec3 cameraPosition;

void main() {
    vec4 worldPosition = modelMatrix * vec4(position.x, 0.0, position.y, 1.0);
    vertexClipSpacePosition = projectionMatrix * viewMatrix * worldPosition;
    gl_Position = vertexClipSpacePosition;
    toCameraVector = cameraPosition - worldPosition.xyz;
    textureCoords = vec2(position.x / 2.0 + 0.5, position.y / 2.0 + 0.5) * 6.0;
}