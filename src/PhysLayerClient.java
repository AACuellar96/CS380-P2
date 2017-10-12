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
                   if(br.read()>baseline) {
                       b[i] = 1;
                   }
                   else {
                       b[i] = 0;
                   }
               }
               String[] fiveBytes = new String[64];
                //Decode from NRZI
                //CURRENTLY CONTINUOUS
                for(int i=0;i<64;i++){
                    String byteString="";
                    for(int j=0;j<5;j++){
                        if(((i*5)+j)!=0){
                            if(b[(i*5)+j]!=b[(i*5)+j-1])
                                byteString+=1;
                            else
                                byteString+=0;
                        }
                        else{
                            if(b[0]!=0)
                                byteString+=1;
                            else
                                byteString+=0;
                        }
                    }
                    fiveBytes[i]=byteString;
                }
            //DECODE from 5B to 4B
           String binaryHolder="";
           for(int i=0;i<64;i+=1){
               binaryHolder+=fiveBtoFourB(fiveBytes[i]);
           }
           String hexStr="";
           for(int i=0;i<64;i++){
               int deci = Integer.parseInt(binaryHolder.substring(0+(4*i),4+(4*i)),2);
               hexStr+=Integer.toHexString(deci);
           }
           out.println(hexStr.toUpperCase());
           System.out.println(hexStr.toUpperCase());
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
            System.out.println(fiveB);
        }
        return "";
    }
}
