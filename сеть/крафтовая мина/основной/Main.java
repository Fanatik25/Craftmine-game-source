package net.craftmine.main;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46C.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main{

    public static long window;

    public static void main(String[] args) {
        run();
    } 

    public static void run() {
        init();
        update();
        destroy();
    } 

    public static void init() {
        GLFWErrorCallback.createPrint(System.err).set();

	if ( !glfwInit() )
		throw new IllegalStateException("Unable to initialize GLFW");
	
	glfwDefaultWindowHints();
	glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
	glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

	window = glfwCreateWindow(1280, 720, "Craftmine", 0, 0);
	if ( window == 0)
		throw new RuntimeException("Failed to create the GLFW window");

	try ( MemoryStack stack = stackPush() ) {
		IntBuffer pWidth = stack.mallocInt(1); // int*
		IntBuffer pHeight = stack.mallocInt(1); // int*

		glfwGetWindowSize(window, pWidth, pHeight);

		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

		glfwSetWindowPos(
			window,
			(vidmode.width() - pWidth.get(0)) / 2,
			(vidmode.height() - pHeight.get(0)) / 2
		);
	} 

		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);

		glfwShowWindow(window);
    } 

    public static void update(){
        GL.createCapabilities();

	glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

	while ( !glfwWindowShouldClose(window) ) {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glfwSwapBuffers(window);

		glfwPollEvents();
	}
    } 

    public static void destroy(){

        glfwFreeCallbacks(window);
	glfwDestroyWindow(window);

	glfwTerminate();
	glfwSetErrorCallback(null).free();

    } 

} 
