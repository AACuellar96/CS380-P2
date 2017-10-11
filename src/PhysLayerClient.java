import java.io.*;
import java.net.Socket;
public final class PhysLayerClient {
    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket("18.221.102.182",38002)) {
            System.out.println("Connected to server.");
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            BufferedReader brIS = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            double baseline =0;
            for(int i=0;i<64;i++){
                baseline+=br.read();
            }
            baseline/=64;
           int[] b = new int[320];
            System.out.println("Established baseline is "+baseline);
          for(int i=0;i<320;i++){
               if(baseline<br.read()) {
                   b[i] = 1;
               }
               else {
                   b[i] = 0;
               }
           }
           // NEED TO DECODE FROM NRZI FIRST
           String[] binaryHolder = new String[64];
            int place=0;
           for(int i=0;i<320;i+=5){
               binaryHolder[place]=fiveBtoFourB(""+b[i]+b[i+1]+b[i+2]+b[i+3]+b[i+4]);
                place++;
           }

           if(br.read()==1)
                System.out.println("Response good");
            else
                System.out.println("Response bad");
            is.close();
            isr.close();
            br.close();
            brIS.close();
            socket.close();
            System.out.println("Disconnected from server.");
        }
    }
    public static String fiveBtoFourB(String fiveB){
        if(fiveB.length()==5) {
            if (fiveB.equals("11110"))
                return "0000";
            else if (fiveB.equals("01001"))
                return "0001";
            else if (fiveB.equals("10100"))
                return "0010";
            else if (fiveB.equals("10101"))
                return "0011";
            else if (fiveB.equals("01010"))
                return "0100";
            else if (fiveB.equals("01011"))
                return "0101";
            else if (fiveB.equals("01110"))
                return "0110";
            else if (fiveB.equals("01111"))
                return "0111";
            else if (fiveB.equals("10010"))
                return "1000";
            else if (fiveB.equals("10011"))
                return "1001";
            else if (fiveB.equals("10110"))
                return "1010";
            else if (fiveB.equals("10111"))
                return "1011";
            else if (fiveB.equals("11010"))
                return "1100";
            else if (fiveB.equals("11011"))
                return "1101";
            else if (fiveB.equals("11100"))
                return "1110";
            else if (fiveB.equals("11101"))
                return "1111";
        }
        return "";
    }
}
