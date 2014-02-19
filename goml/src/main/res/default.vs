attribute vec3 position;
uniform mat4 projection;
uniform mat4 modelView;

void main() {
    mat4 mvp = projection * modelView;

    gl_Position = mvp * vec3(position.xyz, 1);
}