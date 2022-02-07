#version 330 core

layout (location = 0) out vec4 color;

in DATA
{
	vec2 tc;
}

fs_in;

uniform sampler2D tex;
uniform int top;
vec2 Tc = vec2(fs_in.tc.x, fs_in.tc.y);
void main() {

	 if (top != 0) {
		 Tc.y = 1 - Tc.y;
	 }
	color = texture(tex, fs_in.tc);
	if (color.w < 1.0)
		discard;


}
