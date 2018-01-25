attribute vec4 a_Position;
uniform mat4 u_Matrix;

//attribute 一般用于每个顶点各不相同的量，如顶点位置等
void main(){
      gl_Position=u_Matrix*a_Position;
}