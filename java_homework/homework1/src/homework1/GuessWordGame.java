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
		for(int i=0;i<wordlength;i++){//�����ʼ�𰸣�ȫ��Ϊ------
			answer.append("-"); 
		} 
		
		Scanner scan=new Scanner(System.in);

		
        while(true){    //����һ����ѭ�������û�һֱ�£���������Ҫ������ѭ�������
            System.out.println("��������²����ĸ��");
            String str=scan.next().toLowerCase();//��ȡ�û����룬��ת����Сд
            if(word.indexOf(str, 0) != -1){//˵��word���и���ĸ
	            for(int i = word.indexOf(str, 0) ;i != -1 ;){//��word�л��и���ĸʱ�����������replace
	            	answer.replace(i,i+1,str);
	            	System.out.println("���и���ĸ��Ŀǰ��Ϊ");
	            	System.out.println(answer);
	            	i = word.indexOf(str, i+1);
	            }
            }
            else {
            	System.out.println("�õ�����û�и���ĸ�������³���");
            }
            
            if(answer.indexOf("-", 0) == -1) {System.out.println("�����ˣ������");break;}//��answer��û��-��ʱ��˵��ƥ������
                                
                      
        }

	}

}
