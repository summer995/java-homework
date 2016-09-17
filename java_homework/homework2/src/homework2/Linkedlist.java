package homework2;

import java.util.Scanner;


class DATA2
{
    String key;                 //结点的关键字
  //  String name;
   // int age;
}
 
class CLType                                //定义链表结构
{
    DATA2 nodeData=new DATA2();
    CLType nextNode;
     
     
  //追加结点
    CLType CLAddEnd(CLType head,DATA2 nodeData)    
    {
        CLType node,htemp;
        if((node=new CLType())==null)
        {
            System.out.print("申请内存失败！\n");
            return null;                            //分配内存失败
        }
        else
        {
            node.nodeData=nodeData;                 //保存数据
            node.nextNode=null;                 //设置结点指针为空，即为表尾
            if(head==null)                          //头指针
            {
                head=node;
                return head;
            }
            htemp=head;
            while(htemp.nextNode!=null)             //查找链表的末尾
            {
                htemp=htemp.nextNode;
            }
            htemp.nextNode=node;
            return head;
        }
    }
 
     
    //添加头结点
    CLType CLAddFirst(CLType head,DATA2 nodeData)
    {
        CLType node;
        if((node=new CLType())==null)
        {
            System.out.print("申请内存失败！\n");
            return null;                            //分配内存失败
        }
        else
        {
            node.nodeData=nodeData;                 //保存数据
            node.nextNode=head;                     //指向头指针所指结点
            head=node;                              //头指针指向新增结点
            return head;
        }
    }
     
  //查找结点
    CLType CLFindNode(CLType head,String key)      
    {
        CLType htemp;
        htemp=head;                                 //保存链表头指针
        int pos=1;               //表示当前节点在真格链表中的位置
        int key1 = Integer.valueOf(key).intValue();
        while(htemp!=null)                                  //若结点有效，则进行查找
        {
            if(pos==key1)        //若结点关键字与传入关键字相同
            {
                return htemp;                       //返回该结点指针
            }
            htemp=htemp.nextNode;                   //处理下一结点
            pos++;
        }
        return null;                                //返回空指针
    }
    
    
    
    
    
    
    
    
  //插入结点
    CLType CLInsertNode(CLType head,String findkey,DATA2 nodeData)     
    {
        CLType node,nodetemp;   
        if((node=new CLType())==null)       //分配保存结点的内容
        {
            System.out.print("申请内存失败！\n");
            return null;                                //分配内存失败
        }
        node.nodeData=nodeData;                     //保存结点中的数据
        nodetemp=CLFindNode(head,findkey);
        if(nodetemp!=null)                                  //若找到要插入的结点
        {
            node.nextNode=nodetemp.nextNode;        //新插入结点指向关键结点的下一结点
            nodetemp.nextNode=node;             //设置关键结点指向新插入结点
        }
        else
        {
            System.out.print("未找到正确的插入位置！\n");
//            free(node);                               //释放内存
        }
        return head;                                //返回头指针
    }
 
    
    
    
    
    
    
    int CLDeleteNode(CLType head,String key)
    {
        CLType node,htemp;                      //node保存删除结点的前一结点
        htemp=head;
        node=head;
        while(htemp!=null)
        {
            if(htemp.nodeData.key.compareTo(key)==0)        //找到关键字，执行删除操作
            {
                node.nextNode=htemp.nextNode;       //使前一结点指向当前结点的下一结点
//                free(htemp);                          //释放内存
                CLDeleteNode(head,key);
                return 1;
            }
            else
            {
                node=htemp;                         //指向当前结点
                htemp=htemp.nextNode;               //指向下一结点
            }
         }
         return 0;                                  //未删除
    }
 
    
    
    
    
    
    int CLLength(CLType head)                       //计算链表长度
    {
        CLType htemp;
        int Len=0;
        htemp=head;
        while(htemp!=null)                                  //遍历整个链表
        {
            Len++;                              //累加结点数量
            htemp=htemp.nextNode;                   //处理下一结点
        }
        return Len;                                 //返回结点数量
    }
 
    
    
    
    
    
    void CLAllNode(CLType head)                     //遍历链表
    {
        CLType htemp;
        DATA2 nodeData;
        htemp=head;
        System.out.printf("当前链表共有%d个结点。链表所有数据如下：\n",CLLength(head));
        while(htemp!=null)                              //循环处理链表每个结点
        {
            nodeData=htemp.nodeData;                //获取结点数据
            System.out.printf("结点(%s)\n",nodeData.key);
            htemp=htemp.nextNode;                   //处理下一结点
        }
    }
   
}






 
public class Linkedlist {
 
    public static void main(String[] args) {
        CLType node, head=null;
        CLType CL=new CLType();
        CLType add;
        String key,findkey;
        Scanner input=new Scanner(System.in);
         
        System.out.print("链表测试。请输入链表中的要存储的数据（每输入一个数据请按一次回车，结束时请输入quit）:\n");
        do
        {
            DATA2 nodeData=new DATA2();
            nodeData.key=input.next();
           if(nodeData.key.equals("quit"))
            {
                break; //若输入0，则退出
            }
            else
            {
              //  nodeData.name=input.next();
              //  nodeData.age=input.nextInt();
                head=CL.CLAddEnd(head,nodeData);//在链表尾部添加结点
            }
        }while(true);  
        CL.CLAllNode(head);                             //显示所有结点
      
    
 
        System.out.print("\n演示删除结点，输入要删除的数据:");
          
        key=input.next();                               //输入删除结点关键字
        CL.CLDeleteNode(head,key);                  //调用删除结点函数
        CL.CLAllNode(head);                             //显示所有结点  
        
        
        System.out.print("\n演示添加结点，输入在第几个节点后添加节点:");
        findkey=input.next();                       //输入插入位置关键字
        System.out.print("\n输入插入结点的数据:");
        DATA2 nodeData=new DATA2();
        nodeData.key=input.next();       
        head=CL.CLInsertNode(head,findkey,nodeData);        //调用插入函数 
        CL.CLAllNode(head);                             //显示所有结点
 

 
    }
 
}