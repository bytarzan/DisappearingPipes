import org.joml.Random;
import org.lwjgl.util.xxhash.XXH128Canonical;

public class Level {
	
	private VertexArray background; 
	private Texture backgroundTexture; 
	private int xScroll = 0; 
	private int map = 0; 
	private koopatroopa koopatroopa; 
	private Pipe[] pipes = new Pipe[10]; 
	private Random random = new Random(); 
	
	private float OFFSET = 5.0f; 
	
	private boolean control = true; 
	private int index = 0; 
	public Level() {
		float[] vertices = new float[] {  
				-10.0f, -10.0f * 9.0f / 16.0f, 0.0f,
				-10.0f, 10.0f * 9.0f / 16.0f, 0.0f,
				0.0f, 10.0f * 9.0f / 16.0f, 0.0f,
				0.0f, -10.0f * 9.0f / 16.0f, 0.0f,

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
		
		background = new VertexArray(vertices, indices, textureCoordinates); 
		backgroundTexture = new Texture("res/BACKGROUND.png"); 
		createPipes(); 
		koopatroopa = new koopatroopa(); 
		
	}
	
	private void createPipes() {
		Pipe.create();
		for (int i = 0; i < 5 * 2; i+=2) {
			pipes[i] = new Pipe(OFFSET + index * 3.0f, random.nextFloat() * 4.0f); 
			pipes[i+1] = new Pipe(pipes[i].getX(), pipes[i].getY() - 13.0f); 
			index += 2; 
		}
	}
	
	private void updatePipes() {
		
			pipes[index % 10] = new Pipe(OFFSET + index * 3.0f, random.nextFloat() * 4.0f);  
			pipes[(index + 1) % 10] = new Pipe(pipes[index % 10].getX(), pipes[index % 10].getY() - 13.0f); 
			index += 2; 
		
		}

	public void update() {
		if (control) {
		xScroll -= 2; 
		if (-xScroll > 600 && -xScroll % 330 == 0) {
			map ++; 
			
		}
		if (-xScroll % 120 == 0) {
			updatePipes();
		}
		}
		koopatroopa.update();
		
		if (control && collision()) {
			//player lost control of the bird
			koopatroopa.fall(); 
			control = false; 
			
		}
	}
	
	private void renderPipes() {
		Shader.Pipe.enable(); 
		Shader.Pipe.setUniformMat4f("vw_matrix", matrixmath.translate(new themath(xScroll * 0.05f, 0.0f, 0.0f)));
		Pipe.getTexture().bind();
		Pipe.getMesh().bind(); 
		
		for (int i = 0; i < 5 * 2; i++) {
			Shader.Pipe.setUniformMat4f("ml_matrix", pipes[i].getModelMatrix());
			Shader.Pipe.setUniform1i("top", i % 2 == 0 ? 1 : 0);
			Pipe.getMesh().draw(); 
		}
		
		Pipe.getMesh().unbind(); 
		Pipe.getTexture().unbind();
	}
	
	private boolean collision() {
		for (int i = 0; i < 10; i++) {
			float koopax = -xScroll * 0.05f; 
			float koopay = koopatroopa.getY(); 
			float pipex = pipes[i].getX(); 
			float pipey = pipes[i].getY(); 
			
			float kx0 = koopax - koopatroopa.getSize() / 2.0f; 
			float kx1 = koopax + koopatroopa.getSize() / 2.0f; 
			float ky0 = koopay - koopatroopa.getSize() / 2.0f; 
			float ky1 = koopay + koopatroopa.getSize() / 2.0f; 
			
			float px0 = pipex; 
			float px1 = pipex + Pipe.getWidth(); 
			float py0 = pipey; 
			float py1 = pipey + Pipe.getHeight(); 
			
			if (kx1 > px0 && kx0 < px1) {
				if (ky1 > py0 && ky0 < py1) {
					return true; 
				}
			}

		}
		return false; 
	}
	public void render() {
		backgroundTexture.bind(); 
		Shader.BACKGROUND.enable();
		background.bind(); 
		
		for (int i = map; i < map + 4; i++) {
			Shader.BACKGROUND.setUniformMat4f("vw_matrix", matrixmath.translate(new themath(i * 10 + xScroll * 0.03f, 0.0f, 0.0f)));
			background.draw(); 
		}  
		
		background.render();
		renderPipes(); 
		Shader.BACKGROUND.disable(); 
		backgroundTexture.unbind(); 
		koopatroopa.render(); 
		
	}
}
