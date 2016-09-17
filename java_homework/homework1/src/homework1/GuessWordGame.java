package homework1;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class GuessWordGame {

	public static void main(String[] args) {
		String[] wordlist={"ascendancy","bastardization","capitulate","demonstrative","entrenched",
				"firebrand","gregarious","hagiographic","impecunious","juxtapose","lampoon"};
		int wordnumber = (int) Math.round(Math.random() * 10 + 1) - 1;  
		int wordlength = wordlist[wordnumber].length();
		String word = wordlist[wordnumber];

		StringBuffer answer = new StringBuffer();
		for(int i=0;i<wordlength;i++){//构造初始答案，全部为------
			answer.append("-"); 
		} 
		
		Scanner scan=new Scanner(System.in);

		
        while(true){    //创造一个死循环，让用户一直猜，但是里面要有跳出循环的语句
            System.out.println("请输入你猜测的字母：");
            String str=scan.next().toLowerCase();//获取用户输入，并转换成小写
            if(word.indexOf(str, 0) != -1){//说明word中有该字母
	            for(int i = word.indexOf(str, 0) ;i != -1 ;){//在word中还有该字母时，则继续进行replace
	            	answer.replace(i,i+1,str);
	            	System.out.println("猜中该字母，目前答案为");
	            	System.out.println(answer);
	            	i = word.indexOf(str, i+1);
	            }
            }
            else {
            	System.out.println("该单词中没有该字母，请重新尝试");
            }
            
            if(answer.indexOf("-", 0) == -1) {System.out.println("猜完了！真棒！");break;}//在answer中没有-的时候说明匹配完了
                                
                      
        }

	}

}
