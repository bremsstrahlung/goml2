attribute vec2 position;
attribute vec2 texCoord;
uniform mat4 projection;
uniform mat4 modelView;
varying vec2 v_texCoord;
void main() {
  mat4 mvp = projection * modelView;
  v_texCoord = texCoord;
  gl_Position = mvp * vec4(position.xy, 0, 1);
}
