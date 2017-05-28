#version 330 core

in vec2 position;

out vec2 textureCoords;

void main(){
    gl_Position = vec4(position.x, position.y, 0.0f, 1.0f);
    textureCoords = position * 0.5 + 0.5;
}