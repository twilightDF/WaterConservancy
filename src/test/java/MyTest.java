import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.jetlinks.protocol.message.upstream.DeviceMessageDecoder;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

public class MyTest {
    @Test
    public void test01(){
        int i = 554178326;
        String s = String.valueOf(i);
        System.out.println(decimalToBcd(s));
    }


    //10进制转BCD
    public static String decimalToBcd(String str){
        String b_num = "";
        for (int i = 0; i < str.length(); i++) {
            String b = Integer.toBinaryString(Integer.parseInt(str.valueOf(str.charAt(i))));

            int b_len = 4-b.length();

            for (int j = 0;j < b_len;j++){
                b = "0"+b;
            }
            b_num += b;
        }
        return b_num;
    }

    @Test
    public void test02() throws DecoderException {
        String s = "7e7e02000008007112342f000802 0049210817160618 03cdd1".replace(" ","");

        byte[] bytes = Hex.decodeHex(s);
        System.out.println(DeviceMessageDecoder.decode(bytes).toString());
    }

    @Test
    public void test03(){
        Calendar instance = Calendar.getInstance();
        int year = instance.get(Calendar.YEAR);
        int mon = instance.get(Calendar.MONTH);
        int day = instance.get(Calendar.DATE);
        int hours = instance.get(Calendar.HOUR);
        int min = instance.get(Calendar.MINUTE);
        int sec = instance.get(Calendar.SECOND);


        System.out.println(year);
        System.out.println(mon);
        System.out.println(day);
        System.out.println(hours);
        System.out.println(min);
        System.out.println(sec);
    }
}
