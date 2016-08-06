package common.java.security;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * javac -d . TrustlessWriter.java 
 * java common.java.security.TrustlessWriter
 * 因为没有给policy中赋write权限所以会有权限错误
 * java -Djava.security.manager -Djava.security.policy=policyfile.txt common.java.security.TrustlessWriter
 */
public class TrustlessWriter
{
    public static void main(String[] args)
        throws IOException
    {
        File data = new File("D:\\work\\hill\\src\\test\\java\\common\\java\\security\\data.txt");
        FileWriter writer = new FileWriter(data);
        writer.write("test");
        writer.flush();
        writer.close();
        System.out.println("+++");
    }
}
