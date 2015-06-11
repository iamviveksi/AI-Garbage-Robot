import java.io.*;

    public class test {
        public static void main(String args[]) {
        String s = null;
        try {
            String[]callAndArgs = {"python", "net.py", "glue", "water", "sponge"}; //arguments
            Process p = Runtime.getRuntime().exec(callAndArgs);    
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            //output
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            System.exit(0);
        }

        catch (IOException e) {
            System.out.println("exception occured");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
