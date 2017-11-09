package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {
	// Variables
	public static final int PORT_NUMBER = 6013;
	public static ExecutorService fixedThreadPool;

	public static void main(String[] args) throws IOException, InterruptedException {
		
		// Setup connection and a new thread pool
		ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
		fixedThreadPool = Executors.newFixedThreadPool(2);
		
		// Continue accepting clients while running
		while(true) {
			Socket socket = serverSocket.accept();
			ThreadedServer serverThread = new ThreadedServer(socket);
			fixedThreadPool.execute(serverThread);
			
		}
	}
	
	// Runnable which is linked with a socket that is given and used in our fixedThreadPool
	public static class ThreadedServer implements Runnable {
		Socket socket;
		public ThreadedServer(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			InputStream inputStream;
			OutputStream outputStream;
			int readByte;
			
			try {
				inputStream = socket.getInputStream();
				outputStream = socket.getOutputStream();
				
				while((readByte = inputStream.read()) != -1) {
					System.out.println(readByte);
					outputStream.write(readByte);
				}
				
				System.out.flush();
				outputStream.flush();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
}