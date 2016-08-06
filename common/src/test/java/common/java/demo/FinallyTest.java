package common.java.demo;

/**
 * TODO help to find which code execute first;
 *
 * 先执行try中 return 中的语句但不返回，执行完finally后，将之前的结果返回。
 * 但如果在finally中也有return 则直接return finally中的数值
 * @author acer
 * @version C10 2016年4月24日
 * @since SDP V300R003C10
 */
public class FinallyTest
{
    public int get()
    {
        int x = 0;
        try
        {
            x += 2;
            return x;
        }
        catch (Exception e)
        {
            x += 2;
        }
        finally
        {
            x += 2;
            return x;
        }
    }
    
    public static void main(String[] args)
    {
        FinallyTest test = new FinallyTest();
        System.out.println(test.get());
        
    }

}
