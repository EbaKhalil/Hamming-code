import java.util.ArrayList;
public class HammingCode {
    //check if data is valid, 1- size = 8 , 2- it is binary string
    public static boolean isValidData(String data){
        if(data.length()!=8)
            return false;
        for(int i=0;i<data.length();i++)
            if(data.charAt(i)!='1' && data.charAt(i)!='0')
                return false;
        return true;
    }
    //find the transmitted data with ?
    public static String findFirstTransmittedData(String data){
        ArrayList<Character> firstTransmittedData = new ArrayList<Character>();
        for(int i=0;i<data.length();i++)
            firstTransmittedData.add(data.charAt(i));
        //add ? at locations 0,1,3,7
        firstTransmittedData.add(0,'?');
        firstTransmittedData.add(1,'?');
        firstTransmittedData.add(3,'?');
        firstTransmittedData.add(7,'?');
        //re-store the array list in string builder
        String str = "";
        for(int i=0;i<firstTransmittedData.size();i++)
            str += firstTransmittedData.get(i);
        return str;
    }
    //find P1
    public static String getP1(String transmittedData) {
        return ""+ transmittedData.charAt(0)+transmittedData.charAt(2)+transmittedData.charAt(4)
                +transmittedData.charAt(6)+transmittedData.charAt(8)+transmittedData.charAt(10);
    }
    //find P2
    public static String getP2(String transmittedData){
        return ""+transmittedData.charAt(1)+transmittedData.charAt(2)
                + transmittedData.charAt(5)+transmittedData.charAt(6)
                + transmittedData.charAt(9)+transmittedData.charAt(10);
    }
    //find P4
    public static String getP4(String transmittedData){
        return ""+transmittedData.charAt(3)+transmittedData.charAt(4)
                + transmittedData.charAt(5)+transmittedData.charAt(6)
                + transmittedData.charAt(11);
    }
    //find P8
    public static String getP8(String transmittedData){
        return ""+transmittedData.charAt(7)+transmittedData.charAt(8)
                + transmittedData.charAt(9)+transmittedData.charAt(10)
                + transmittedData.charAt(11);
    }
    //determine the parity bit
    public static char findParityBit(String transmittedData,String parityType){
        //first, count the number of ones
        int counter = 0;
        for(int i=0;i<transmittedData.length();i++)
            if(transmittedData.charAt(i)=='1')
                counter++;
        if(parityType.equals("ODD"))
        {
            if(counter%2 == 1)
                return '0';
            else
                return '1';
        }
        else if(parityType.equals("EVEN"))
        {
            if(counter%2 == 1)
                return '1';
            else
                return '0';
        }
        return 0;
    }
    //find the actual transmitted data (Hamming Code)
    public static String findActualTransmittedData(String data,String parityType){
        //find the first transmitted data in array list (to do remove and add)
        ArrayList<Character> AL = new ArrayList<Character>();
        for(int i=0;i<findFirstTransmittedData(data).length();i++)
            AL.add(findFirstTransmittedData(data).charAt(i));

        //first ?
        AL.remove(0);
        AL.add(0,findParityBit(getP1(findFirstTransmittedData(data)),parityType));
        //second ?
        AL.remove(1);
        AL.add(1,findParityBit(getP2(findFirstTransmittedData(data)),parityType));
        //third ?
        AL.remove(3);
        AL.add(3,findParityBit(getP4(findFirstTransmittedData(data)),parityType));
        //fourth ?
        AL.remove(7);
        AL.add(7,findParityBit(getP8(findFirstTransmittedData(data)),parityType));

        //re-store the result in string
        String str = "";
        for(int i=0;i<AL.size();i++)
            str += AL.get(i);
        return str;
    }
    //check if received data is valid, 1- size = 12, 2- it is binary string
    public static boolean isValidRD(String RD){
        if(RD.length()==8)
            return false;
        for(int i=0;i<RD.length();i++)
            if(RD.charAt(i)!='1' && RD.charAt(i)!='0')
                return false;
        return true;
    }
    //find the error
    public static int findError(String RD,String parityType){
        char P1 = findParityBit(getP1(RD),parityType);
        char P2 = findParityBit(getP2(RD),parityType);
        char P4 = findParityBit(getP4(RD),parityType);
        char P8 = findParityBit(getP8(RD),parityType);
        return Integer.parseInt(""+P1+P2+P4+P8,2);
    }
    //find the percentage of correcting error for single and double
    public static double getPercentage(String type){
        if(type.equals("Single"))
            return 50;
        else if(type.equals("Double"))
            return 62.5;
        return 0;
    }
}