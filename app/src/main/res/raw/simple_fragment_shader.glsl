precision mediump float;
uniform vec4 u_Color;

//uniform一般用于同一组顶点组成的相同的量,如光源位置,一组颜色等
void main(){
    gl_FragColor=u_Color;
}