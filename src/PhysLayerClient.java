import java.io.*;
import java.net.Socket;
public final class PhysLayerClient {
    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket("18.221.102.182",38002)) {
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            BufferedReader brIS = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            double baseline =0;
            for(int i=0;i<64;i++){
                baseline+=i;
            }
            baseline/=64;
            
            is.close();
            isr.close();
            br.close();
            brIS.close();
            socket.close();
        }
    }
}
