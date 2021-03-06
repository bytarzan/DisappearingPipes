import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import static org.lwjgl.opengl.GL11.*; 
public class Texture {

	private int width, height;
	private int ID; 
	
	public Texture(String path) {
		ID = load(path); 
	}
	
	// responsible for loading image and extracting pixels from image. Returns an integer (ID)
	private int load(String path) {
		int[] pixels = null;
		try {
			BufferedImage image = ImageIO.read(new FileInputStream(path)); 
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int[] data = new int[width * height];
		for (int i = 0; i < width * height; i++) { 
			int a = (pixels[i] & 0xff000000) >> 24;
		    int r = (pixels[i] & 0xff0000) >> 16; 
		    int g = (pixels[i] & 0xff00) >> 8; 
		    int b = (pixels[i] & 0xff) >> 0; 
		    // least significant bit is r, reads from r to a 
		    data[i] = a << 24 | b << 16 | g << 8 | r; 
		    
		}
		int tex = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, tex);
		// GL_NEAREST For sharp resolution, goes to nearest pixel and doesn't blur pixels
		//GL_LINEAR linearly interpolates pixels
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
		return tex; 
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, ID);

	}
	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);

	}
}
