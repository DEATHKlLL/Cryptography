import java.util.LinkedHashMap;

public class DES {
    static char[] encrypt(String plaintext,String key){
        operation des =new operation();
        // String subkey="000110110000001011101111111111000111000001110010";
        // System.out.println(des.ManglerFunction(plaintext.toCharArray(),subkey));
        // System.out.println(des.PC_1(des.TexttoBin(key)));
        LinkedHashMap<String,String> l = des.DC(des.PC_1(des.TexttoBin(key)));
        String x=String.valueOf(des.IN(des.TexttoBin(plaintext),0));
 
        String L=x.substring(0,32);
        String R=x.substring(32,64);
        char[] f=new char[32];
        char[] temp=new char[32];
        for(int i=1;i<17;i++){
            f=des.ManglerFunction(R.toCharArray(),des.PC_2(l.get("C"+i)+l.get("D"+i)));

            for(int j=0;j<32;j++){
                if(f[j]==L.toCharArray()[j]){
                    temp[j]='0';
                }
                else{
                    temp[j]='1';
                }
            }

            L=R;
            R=String.valueOf(temp);
        }
        return des.IN((R+L).toCharArray(),1);//error here
    }
    static void decrypt(char[] ciphertext,String key){
        operation des =new operation();
        LinkedHashMap<String,String> l = des.DC(des.PC_1(des.TexttoBin(key)));
        String x=String.valueOf(des.IN(ciphertext,0));
 
        String R=x.substring(0,32);
        String L=x.substring(32,64);
        char[] f=new char[32];
        char[] temp=new char[32];
        for(int i=16;i>0;i--){
            f=des.ManglerFunction(L.toCharArray(),des.PC_2(l.get("C"+i)+l.get("D"+i)));

            for(int j=0;j<32;j++){
                if(f[j]==R.toCharArray()[j]){
                    temp[j]='0';
                }
                else{
                    temp[j]='1';
                }
            }
            R=L;
            L=String.valueOf(temp);
        }
        System.out.print("plaintext after dycryption:-   ");
        System.out.println(des.IN((L+R).toCharArray(),1));

    }
    public static void main(String [] args){
        operation des =new operation();

        try{String key =args[1];
        String p=args[0];
        try{char[] CT=encrypt(p,key);
            System.out.print("cipher text after encryption:- ");
            System.out.println(CT);
            System.out.print("plaintext before encryption:-  ");
            System.out.println(des.TexttoBin(p));
            decrypt(CT,key);
        }
        catch(Exception e){
            System.out.println("the length of pass and key in parameter should be of 64 bytes means 8character length  ");
            System.out.println(e);
        }
        }
        catch(Exception e) {
            System.out.println("should be a parameter data :- java DES.java pass key");
        }    
    }
}
class operation{
    public char[] TexttoBin(String Text){
        StringBuilder textbin = new StringBuilder();
        for (char c : Text.toCharArray()){
            textbin.append(String.format("%8s",Integer.toBinaryString(c)).replace(" ","0"));
        }
        return textbin.toString().toCharArray();
    }
    public char[] IN(char[] plaintextbin,int x){
        int[][] permutation ={{58,50,42,34,26,18,10,2},
                              {60,52,44,36,28,20,12,4},
                              {62,54,46,38,30,22,14,6},
                              {64,56,48,40,32,24,16,8},
                              {57,49,41,33,25,17,9,1},
                              {59,51,43,35,27,19,11,3},
                              {61,53,45,37,29,21,13,5},
                              {63,55,47,39,31,23,15,7}};
        char[] result =new char[64];                     
        if(x==0){    
            int z=0;
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    result[z]=plaintextbin[permutation[i][j]-1];
                    z++;
                }    
            }
        }
        else{
            int z=0;
            for(int i=0 ; i<8;i++){
                for(int j=0;j<8;j++){
                    result[permutation[i][j]-1]=plaintextbin[z];
                    z++;
                }
            }

        }
        return result;
    }
    public char[] PC_1(char[] key){
        int[][] permutation ={{57, 49, 41, 33, 25, 17, 9},
        {1, 58, 50, 42, 34, 26, 18},
        {10, 2, 59, 51, 43, 35, 27},
        {19, 11, 3, 60, 52, 44, 36},
        {63, 55, 47, 39, 31, 23, 15},
        {7, 62, 54, 46, 38, 30, 22},
        {14, 6, 61, 53, 45, 37, 29},
        {21, 13, 5, 28, 20, 12, 4}};
        char[] result =new char[56];
        int z=0;
        for(int i=0;i<8;i++){
            for(int j=0;j<7;j++){
                result[z]=key[(permutation[i][j])-1];
                z++;
            }    
        }
        return result;
    }
    public char[] PC_2(String CD){
        char[] key = CD.toCharArray();
        int[][] permutation ={{14, 17, 11, 24, 1, 5},
            {3, 28, 15, 6, 21, 10},
            {23, 19, 12, 4, 26, 8},
            {16, 7, 27, 20, 13, 2},
            {41, 52, 31, 37, 47, 55},
            {30, 40, 51, 45, 33, 48},
            {44, 49, 39, 56, 34, 53},
            {46, 42, 50, 36, 29, 32}};
        char[] result =new char[48];
        int z=0;
        for(int i=0;i<8;i++){
            for(int j=0;j<6;j++){
                result[z]=key[(permutation[i][j])-1];
                z++;
            }    
        }
        return result;
    }
    public String shift(String key , int shift){
        char[] shiftkey =new char[28];
        for(int i=shift;i<28;i++){
            shiftkey[i-shift]=key.toCharArray()[i];
        }
        for(int i=0;i<shift;i++){
            shiftkey[i+28-shift]=key.toCharArray()[i];
        }
        return String.valueOf(shiftkey);
    }
    public LinkedHashMap<String,String> DC(char[] key){
        String keys = new String(key);
        LinkedHashMap<String, String> c =new LinkedHashMap<String,String>();
        c.put("C0",keys.substring(0,28));
        c.put("D0",keys.substring(28,56));
        for(int i=1;i<=16;i++){
            if(i==1 || i==2 || i==9 || i==16){
                c.put("D"+i,shift(c.get("D"+(i-1)),1)); 
                c.put("C"+i,shift(c.get("C"+(i-1)),1)); 
            }
            else{
                c.put("D"+i,shift(c.get("D"+(i-1)),2)); 
                c.put("C"+i,shift(c.get("C"+(i-1)),2));
            }
        }
        return c;
    }
    public char[] ManglerFunction(char [] R,char[] subkey){
        int[][] expantion ={{32, 1, 2, 3, 4, 5, 4, 5},
            {6, 7, 8, 9, 8, 9, 10, 11},
            {12, 13, 12, 13, 14, 15, 16, 17},
            {16, 17, 18, 19, 20, 21, 20, 21},
            {22, 23, 24, 25, 24, 25, 26, 27},
            {28, 29, 28, 29, 30, 31, 32, 1}};
            int[][][] Sbox={
                {{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}},
                {{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                 {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                 {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                 {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}},
                 {{10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                    {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                    {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                    {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}},
                {{7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                    {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                    {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                    {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}},
                    {{2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                        {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                        {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                        {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}},
                    {{12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                        {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                        {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                        {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}},
                        {{4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                            {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                            {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                            {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}},
                            {{13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                                {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                                {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                                {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}}
    };
        String[] NtoS={
            "0000","0001","0010","0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1101","1110","1111"
        };
        int[][] PBOX={{16,  7, 20, 21},
            {29, 12, 28, 17},
            {1, 15, 23, 26},
            {5, 18, 31, 10},
            {2,  8, 24, 14},
            {32, 27,  3,  9},
            {19, 13, 30,  6},
            {22, 11,  4, 25}};
        char[] result =new char[48];
        int z=0;
        for(int i=0;i<6;i++){
            for(int j=0;j<8;j++){
                result[z]=R[(expantion[i][j])-1];
                z++;
            }    
        }

        for(int i=0;i<48;i++){
            if(result[i]==subkey[i]){
                result[i]='0';
            }
            else{
                result[i]='1';
            }
        }

        String x="";
        String y="";
        String zz="";
        int zzz=0;
        for(int i=1;i<=48;i++){
            if(i%6==1 || i%6==0 ){
                x=x+ result[i-1];
                if(i%6==0){
                
                zz=zz + NtoS[Sbox[zzz][Integer.parseInt(x,2)][Integer.parseInt(y,2)]];
                zzz++;
                x="";
                y="";}
            }
            else{
                y=y + result[i-1];
            }
        }
        z=0;
        result= new char[32];
        for(int i=0;i<8;i++){
            for(int j=0;j<4;j++){
                result[z]=zz.toCharArray()[(PBOX[i][j])-1];
                z++;
            }    
        }
        return result;
    }
};

