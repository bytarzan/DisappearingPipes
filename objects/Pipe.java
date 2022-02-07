

public class Pipe {
// pipes are static 
	private themath position = new themath();
	private matrixmath ml_matrix; 
	
	private static float width = 1.5f, height = 9.0f; 
	private static Texture texture; 
	private static VertexArray mesh; 

	public static void create() {
		float[] vertices = new float[] {  
				0.0f, 0.0f, 0.1f,
				0.0f, height, 0.1f,
				width, height, 0.1f,
				width,  0.0f, 0.1f
				
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
		texture = new Texture("res/pipe.png"); 
	}
	
	public Pipe (float x, float y) {
		position.x = x; 
		position.y = y; 
		ml_matrix = matrixmath.translate(position); 
	}
	
	public float getX() {
		return position.x; 
	}
	
	public float getY() {
		return position.y; 
	}
	
	public matrixmath getModelMatrix() {
		return ml_matrix;  
	}
	public static VertexArray getMesh() {
		return mesh; 
	}
	
	public static Texture getTexture() {
		return texture; 
	}
	
	public static float getWidth() {
		return width; 
	}
	
	public static float getHeight() {
		return  height; 
	}
	}


