import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	private ServerSocket mServer;
	
	Server(){
		try {
			mServer = new ServerSocket(8088);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Server s = new Server();
				s.service();
				
			}
		}).start();
	}
	
	private void service(){
		Socket socket;
		InputStream input = null;
		while(true){
			try {
				socket = mServer.accept();
				input = socket.getInputStream();
				byte[] buffer = new byte[1024 * 1024];
				input.read(buffer);
				String repuset = new String(buffer,"utf-8");
				System.out.println(repuset);
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					if(input != null){
						input.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
