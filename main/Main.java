import static org.lwjgl.glfw.GLFW.*;

import static org.lwjgl.opengl.GL11.*; 
import static org.lwjgl.system.MemoryUtil.*;

import org.ietf.jgss.GSSContext;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL13.*; 
public class Main implements Runnable{
	private int width = 2560;
	private int height = 1440; 
	// new thread are ways of doing things concurrently 
	private Thread thread; 
	private boolean running = false; 
	private long window; 
	
	private Level level; 
	//game thread
	public void start() {
		running = true; 
		thread = new Thread(this, "Game");
		thread.start();
	}
	
	private void init() {
		if (!glfwInit()) {
			return; 
		}
		
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		window = glfwCreateWindow(width, height, "FLAPPY", NULL, NULL);
		if (window == NULL) {
			return; 
		}
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor()); 
		glfwSetWindowPos(window, (vidmode.width() - width ) / 2 , (vidmode.height() - height ) / 2 );
		
		glfwSetKeyCallback(window, new Input()); 
		
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		//call this before other open GL Methods 
		GL.createCapabilities(); 
		
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		glEnable(GL_DEPTH_TEST); 
		glActiveTexture(GL_TEXTURE1);
		System.out.println("OpenGL:" + glGetString(GL_VERSION));
		
		Shader.loadAll();
		
		
		//Always make sure to bind the shader! Even if calling from shader class 
		 
		matrixmath projection_mat = matrixmath.orthographic(-10.0f, 10.0f, -10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f, -1.0f, 1.0f); 
		Shader.BACKGROUND.setUniformMat4f("projection_mat", projection_mat); 
		Shader.BACKGROUND.setUniform1i("tex", 1); 
		Shader.koopatroopa.setUniformMat4f("projection_mat", projection_mat); 
		Shader.koopatroopa.setUniform1i("tex", 1); 
		Shader.Pipe.setUniformMat4f("projection_mat", projection_mat); 
		Shader.Pipe.setUniform1i("tex", 1);
		level = new Level(); 
		
	} 
	
	public void run() {
		init();
		long lastTime = System.nanoTime();
		double delta = 0.0; 
		double ns = 1000000000.0 / 60.0; 
		long timer = System.currentTimeMillis();
		int update = 0; 
		int frames = 0; 
		 
		while (running) {
			long now = System.nanoTime(); 
			delta += (now - lastTime) / ns; 
			lastTime = now; 
			if (delta >= 1.0) {
				update(); 
				update++; 
				delta--; 
			}

			render(); 
			frames++; 
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000; 
			
			}
		
		if (glfwWindowShouldClose(window)) {
			running = false;  
			}
		}
		
		glfwDestroyWindow(window);
		glfwTerminate();
	}
	
	private void update() {
		
		glfwPollEvents();
		level.update();
		 
	}
	
	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 
		level.render(); 
		glfwSwapBuffers(window);
	}
	
	public static void main(String[] args) {
		new Main().start(); 
		
		
		
		
	}

}
