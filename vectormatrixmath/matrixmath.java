
import static java.lang.Math.*;

import java.nio.FloatBuffer;


public class matrixmath {
	public static final int SIZE = 4 * 4; 
	public float[] elements = new float[SIZE];
	
	public matrixmath() {
		
	}
	//make identity matrix 
	public static matrixmath identity() {
		matrixmath result = new matrixmath();
		//fills every element with 0 
		for (int i = 0; i < SIZE; i++) {
			result.elements[i] = 0.0f;			
		}
		// give attribute values for result object  
		result.elements[0] = 1.0f; 
		result.elements[5] = 1.0f;
		result.elements[10] = 1.0f;
		result.elements[15] = 1.0f; 
		
		return result; 
	}
	
	//multiply two matrices, row of second mat with column of first mat 
	public matrixmath multiply(matrixmath matrix) {
		matrixmath result = new matrixmath();
		
		for (int c = 0; c < 4; c++) {
			for (int r  = 0; r < 4; r++) {
				float sum = 0.0f;
				for (int c2 = 0; c2 < 4; c2++) {
					sum += this.elements[c2 + c * 4] * matrix.elements[r + c2 * 4];
				}
				result.elements[r + c * 4] = sum; 
			}
		}
		return result; 
	}
	// moves object around space 
	public static matrixmath translate(themath vector) {
		matrixmath result = identity();
		
		result.elements[12] = vector.x;
		result.elements[13] = vector.y;
		result.elements[14] = vector.z; 
		
		return result; 
	}
	// rotate about the z axis 
	public static matrixmath rotate(float angle) {
		matrixmath result = identity();
		//deg to rad
		float r = (float) toRadians(angle); 
		float cos = (float) cos(r);
		float sin = (float) sin (r);
		
		result.elements[0] = cos;
		result.elements[1] = sin;
		result.elements[4] = -sin;
		result.elements[5] = cos; 
		
		return result; 
	}
	
	public static matrixmath orthographic(float left, float right, float bottom, float top, float near, float far) {
		matrixmath result = identity();
		result.elements[0] = 2.0f / (right - left);
		result.elements[5] = 2.0f / (top - bottom);
		result.elements[10] = 2.0f / (near - far);
		result.elements[12] = (left + right) / (left - right);
		result.elements[13] = (bottom + top) / (bottom - top);
		result.elements[14] = (near + far) / (far - near); 
		
		return result; 
		
	}
	
	public FloatBuffer toFloatBuffer() {
		return BufferUtils.createFloatBuffer(elements); 
	}
	
}
