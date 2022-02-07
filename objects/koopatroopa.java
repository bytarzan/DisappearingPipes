import org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.*;

public class koopatroopa {
	private float SIZE = 1.0f; 
	private VertexArray mesh; 
	private Texture texture; 
	
	private themath position = new themath(); 
	private float rot; 
	private float delta; 
	
	public koopatroopa() {
		float[] vertices = new float[] {  
				-SIZE / 2.0f, -SIZE / 2.0f, 0.1f,
				-SIZE / 2.0f, SIZE / 2.0f, 0.1f,
				SIZE / 2.0f, SIZE / 2.0f, 0.1f,
				SIZE / 2.0f, -SIZE / 2.0f, 0.1f,
				
		}; 
		
		byte[] indices = new byte[] {
			0, 1, 2,
			2, 3, 0 
		}; 
		
		float[] textureCoordinates = new float[] {
				0, 1, 
				0, 0, 
				1, 0,
				1, 1
		}; 
		
		mesh = new VertexArray(vertices, indices, textureCoordinates); 
		texture = new Texture("res/koopatroopa.png"); 
	}
	
	public void update() {

		position.y -= delta; 
		if (Input.isKeyDown(GLFW_KEY_SPACE)) {
			delta = -0.15f; 
		} else {
			delta += 0.03f; 
		}
		rot = -delta * 10.0f; 
	}
	
	
	public void fall() {
		delta = -0.15f; 
	} 
	
	public void render() {
		Shader.koopatroopa.enable();
		Shader.koopatroopa.setUniformMat4f("ml_matrix", matrixmath.translate(position).multiply(matrixmath.rotate(rot)));
		texture.bind();
		mesh.render();
		Shader.koopatroopa.disable();
		
	}
	
	public float getY() {
		return position.y; 
	}
	
	public float getSize() {
		return SIZE; 
	}
}
