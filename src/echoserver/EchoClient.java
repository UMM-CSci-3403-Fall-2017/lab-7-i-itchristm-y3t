package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;



public class EchoClient {
	// Variables
	public static final int PORT_NUMBER = 6013;
	public static InputStream socketInputStream;
	public static OutputStream socketOutputStream;
	public static Socket socket;
	
	public static void main(String[] args) throws IOException {
		
		// Setup connection and streams
		socket = new Socket("localhost", PORT_NUMBER);
		socketInputStream = socket.getInputStream();
		socketOutputStream = socket.getOutputStream();
		
		// Create new userInputThread and userOutputThread runnables
		userInputThread input = new userInputThread();
		userOutputThread output = new userOutputThread();
		
		// Create two threads using the output and input runnables
		Thread outputThread = new Thread(output, "outputThread");
		Thread inputThread = new Thread(input, "inputThread");
		
		// Start both threads
		outputThread.start();
		inputThread.start();
	}

	
	// Runnable for getting the users input and sending it to the outputStream
	public static class userInputThread implements Runnable {
		
		@Override
		public void run() {
			int readByte;
			try {
				while((readByte = System.in.read()) != -1) {
					socketOutputStream.write(readByte);
				}
			
				socketOutputStream.flush();
				socket.shutdownOutput();
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	// Runnable for getting the users inputStream and sending it to the System.out
	public static class userOutputThread implements Runnable {

		@Override
		public void run() {
			int readByte;
			try {
				while((readByte = socketInputStream.read()) != -1) {
					System.out.write(readByte);
				}
				
				System.out.flush();
				socket.close();
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}
