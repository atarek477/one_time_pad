/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.codesecurity;

/**
 *
 * @author BLURAY
 */

import java.math.BigInteger;
import java.util.*;
import java.util.Objects;
import java.util.Scanner;
public class CodeSecurity {
    private static String hexToAscii(String hexStr) {   // function change from hex to ascii
    StringBuilder output = new StringBuilder("");
    
    for (int i = 0; i < hexStr.length(); i += 2) {
        String str = hexStr.substring(i, i + 2);
        output.append((char) Integer.parseInt(str, 16));
    }
    
    return output.toString();
}
  
//  private static String asciiToHex(String asciiStr) { 
//    char[] chars = asciiStr.toCharArray();
//    StringBuilder hex = new StringBuilder();
//    for (char ch : chars) {
//        hex.append(Integer.toHexString((int) ch));
//    }
//
//    return hex.toString();
//}
  
   public static String XOR(String str1, String str2) {        // function used for xoring between two hex 
        BigInteger hex1 = new BigInteger(str1, 16);
        BigInteger hex2 = new BigInteger(str2, 16);
        BigInteger xor = hex1.xor(hex2);

        String Hex = xor.toString(16);

        return Hex;
    }
   


    public static void main(String[] args) {
        
       // System.out.println("here");
        String key;
        String s1="20";  
        int r=0;
       String []cipher1= {"68AF0BEF7F39982DA975B5E6D06947E61C22748C94A2155CFCCC464DEAFB6F4844DB2D7312ED192B6B7251580C61D5A296964E824A16648B16B9",
                          "70A20FBD7E209324A979BFE2997A46E61B22749692EB1655FA995D46A9FA654F43C93F2114A21E3E227714580A6790B88BD74F9E09107D8B0EAC",
                          "6FA20DBA622CDD28EC68F0F0C16D41A7023778C29EB8455EFC894B46EDA96C46459E2D2A1CEF1239707F571604618CEB9DD85E955013628B0DAE",
                          "6FA20DBA6220893AA970A4B5CD664CE609286D8799B80010F68A0F56FAE868405BD72A2A51E118386E7214520E6994AC9D964E824A16648B16B9"
                         ,"71A80AAA6227DD20FB68A0E1D6695BA71C3864C285AE1445F09E4A50A9EA6B5B52D82B3F51E3192922645D5100769ABE8B965C89480F6F910BB3"
                         ,"7DA30ABD753A8E63FB70BEF1D66340BC0D24748D99EB065FEC804B03F9FB6F5F52D02A731CE31B24617F5B431C2496AA94DA1D865D17778109B3"
                         ,"75B34EA66369932CFD31A0E7D86D5DAF0F3171C283A44542FC805603FAE6664C5BC77E3C1FA204346F7B51421D6D96EB9DD85E955013628B0DAE",
                         "75E71DA771259163E774A6F0CB2E5BA3192378C283A30010EA8D4246A9F96B5A44C9312115A21823227B415A1B6D85A79D965C844A0C638C16B3"}; 
       
       String[][] ch1 = new String[8][(cipher1[1].length())];
       char[][] ch = new char[8][cipher1[1].length()];   
       String p,m;
       int flag=0;
       for (int i = 0; i < 8; i++) {                       // divide each string cipher to char of 2D Array to check the first char of each byte later  
       for (int j = 0; j < cipher1[1].length(); j++){    
            ch[i][j] = cipher1[i].charAt(j);
       }
        }
       int count1;
        int count2;
        char []x1=new char[2] ;
        char []x2=new char[2] ;
   // System.out.println("here "+ ch[1][0]);
    for (int j = 0; j < cipher1[1].length();j= j+2){  // check the first hex char of each btye 
        count1=0;
        count2=0;
     for (int i = 0; i < 8; i++) {
        
         
         //if hex  in {00110000 , 00110001 , 00110011 ,00111000 ,00111001, 01100001, 01100010}   group 1
         // increment counter1 to take threshold 
       if(ch[i][j]=='0'||ch[i][j]=='1'||ch[i][j]=='2'||ch[i][j]=='3'||ch[i][j]=='8'||ch[i][j]=='9'||ch[i][j]=='A'||ch[i][j]=='B')
       {  
           count1++;             
           x1[0]=ch[i][j];          // store byte in x1
           x1[1]=ch[i][j+1];       
           
       }
       else{     //if hex  in {00110100 , 00110101 ,00110110, 00110111 ,01100011,01100100, 01100101,01100110} group 2
                 // increment counter2 to take threshold 
           count2++;
       x2[0]=ch[i][j];           // store byte in x2 
        x2[1]=ch[i][j+1]; 
       
       }
       
       }
     
     if(count1<count2&&count1!=0){          // counter 1 < counter 2 and counter 1 not equal zero so we can dedicate space 
     String str1 = new String(x1);     
     //System.out.println("str1 "+str1 );
     key=XOR(str1,s1);                  //take byte which store in x1 and xor with hex of space "20"  to get key 
     for(int o=0 ;o<8;o++)
          {
          m= Character.toString( ch[o][r])+Character.toString( ch[o][r+1]);  
         ch1[o][r]=hexToAscii(XOR(m,key));         // xor key with byte in the same column to get other char
     }
     }
     else if(count1==count2){   // special case if counter 1 = counter 2  so we test both group to find which one satisfy condition      
     if(flag==0){
     String str2 = new String(x2);
     //System.out.println("str2 "+str2 );
     key=XOR(str2,s1);          // take byte which store in x2 and xor with hex of space "20"  to get key 
     for(int o=0 ;o<8;o++)
     {m= Character.toString( ch[o][r])+Character.toString( ch[o][r+1]);
         ch1[o][r]=hexToAscii(XOR(m,key));             // xor key with byte in the same column to get other char
     } 
    flag =1; 
     }
     else if (flag ==1)    // special case if counter 1 = counter 2  so we test both group to find which one satisfy condition      
     {
     String str1 = new String(x1);
     //System.out.println("str1 "+str1 );
     key=XOR(str1,s1);        // take byte which store in x1 and xor with hex of space "20"  to get key 
     for(int o=0 ;o<8;o++)
          {
          m= Character.toString( ch[o][r])+Character.toString( ch[o][r+1]);
         ch1[o][r]=hexToAscii(XOR(m,key));         // xor key with byte in the same column to get other char
     }
     
     }
     
     }
         
     else if(count2<count1&&count2!=0)        // counter 2 < counter 1 and counter 2 not equal zero so we can dedicate space
     { 
     String str2 = new String(x2);
     //System.out.println("str2 "+str2 );
     key=XOR(str2,s1);             // take byte which store in x2 and xor with hex of space "20"  to get key 
     for(int o=0 ;o<8;o++)
     {m= Character.toString( ch[o][r])+Character.toString( ch[o][r+1]);
         ch1[o][r]=hexToAscii(XOR(m,key));          // xor key with byte in the same column to get other char
     }
     }
     else if (count1==0||count2==0)   // if count1 =0 or count 2 = 0 so we canot dedicate space 
     {
     for(int o=0 ;o<8;o++)
     {ch1[o][r]="-";                 // print in each unknown column
     }
     }
     for(int o=0 ;o<8;o++){
     System.out.print(" "+ch1[o][r]);   // print output 
   
    }
     System.out.println("");
   // System.out.println("count "+r);
     r=r+2;
       }
   
    
    System.out.println("key after guessing the missing char :");
    System.out.println("1CC76ECF1049FD438911D095B90E29C66C501DE2F7CB653099EC2F2389890A2937BE5E537182774D021634366F04F5CBF8B63DE7296316E262C0");
    System.out.println("");
    System.out.println("");
    System.out.println("");
    
    // final output
     System.out.println("final output");
    System.out.println("m1 :the open design principle increases confidence in security");
    System.out.println("m2 :learning how to write source software is a necessaey skill");
    System.out.println("m3 :secure key exchange is needed for symmetric key encryption");
    System.out.println("m4 :security at the expense of usability could damage security");
    System.out.println("m5 :modern cryptography requires careful and rigorous analysis");
    System.out.println("m6 :address randomization could prevent malicious cell attacks");
    System.out.println("m7 :it is not practical to rely solely on symmetric encryption");
    System.out.println("m8 :i shall never reuse the same password on multiple accounts");
    
    
    
    }
}
