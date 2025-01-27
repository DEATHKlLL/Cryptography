class Sbox{,xmc nkkcxm
    public static void main(String[] args){
        String[] hexa={"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
        operation obj= new operation();
        for(int a=0;a<16;a++){
            for(int b=0;b<16;b++){
                if(a==0 && b==0){
                    System.out.print("00 ");
                }
                else{
                obj.MI(String.join("",hexa[a],hexa[b]));
                System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
}
}
class operation{
    public void MI(String find){
        String[] hexa={"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
        for(int a=0;a<16;a++){
            for(int b=0;b<16;b++){
                
                String v2s=String.join("",hexa[a],hexa[b]);
                String v1 = fixbi(Integer.parseInt(find, 16),8);
                String v2 = fixbi(Integer.parseInt(v2s, 16),8);
                int[] p1=stoi(v1);
                int[] p2=stoi(v2);
                int[] product={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
                for(int i=0;i<8;i++){
                    for(int j=0;j<8;j++){
                        product[i+j]= (product[i+j] + p1[i]*p2[j])%2;
                    }
                }
                
                int[] modulo={0,0,0,0,0,0,1,0,0,0,1,1,0,1,1};
                int[] producttempi= product;
                int temp1;
                int producttemp=Integer.parseInt(itos(producttempi), 2);
        while(true){
                    int dis1=dis(modulo);
                    int dis2=dis(producttempi);
                    if(producttemp == 1){
                        System.out.print(v2s);
                        break;
                    }
                    else if(dis1 < dis2 || dis2 < 0){
                        break;
                    }
                    temp1=Integer.parseInt(itos(modulo), 2) << (dis1-dis2);
                    producttemp= temp1 ^ producttemp;
                    producttempi=stoi(fixbi(producttemp,15));
                    
            }
            }
        }
}
    public int[] stoi(String x){
        int v[]=new int[x.length()];
        for(int i=0;i<x.length();i++){
            if(x.substring(i,i+1).equals("0")){
                v[i]=0;
            }
            else{
                v[i]=1;  
            }
        }
        return v;
    }
    public int dis(int[] x){
        for(int i=0;i<15;i++){
            if(x[i]==1){
                return i;
            }
        }
        return -1;

    }
    public String itos(int[] x){
        String y="";
        for(int i=0 ; i<x.length;i++)
        {
            y += String.valueOf(x[i]);
        }
        return y;
    }
    public String fixbi(int x,int len){
        String bi = Integer.toBinaryString(x);
        int Zeros = len - bi.length();
        StringBuilder binaryStringBuilder = new StringBuilder();
        for (int i = 0; i < Zeros; i++) {
            binaryStringBuilder.append('0');
        }
        binaryStringBuilder.append(bi);
        return binaryStringBuilder.toString();
    }
}
