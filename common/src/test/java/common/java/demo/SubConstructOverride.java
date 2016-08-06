package common.java.demo;

/**
 *
 * @author acer
 * @version C10 2016年5月3日
 * @since SDP V300R003C10
 */
public class SubConstructOverride extends ConstructOverride
{
    public SubConstructOverride()
    {
        print();
    }
    
    @Override
    public void print()
    {
        System.out.println("sub print");
    }
    
    public static void main(String[] args)
    {
        ConstructOverride test = new SubConstructOverride();
    }

}
