package java第三次作业;

//package java��������ҵ;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;



public class JTreeFrame extends JFrame {

	private JTree jTree;
	private DefaultMutableTreeNode top;
	private File[] rootFiles;
	JLabel showpath,showsize,showchange;
    JPanel p;

	public JTreeFrame(String s) {
		
        //���ڵ�
		top = new DefaultMutableTreeNode("�ҵĵ���", true);
		

		addRootNode();

		jTree = new JTree(top);
		
		showpath=new JLabel("�ļ�·��");
		showsize=new JLabel("�ļ���С");
		showchange=new JLabel("�޸�ʱ��");
        
		p = new JPanel();
        p.add(showpath);
        p.add(showsize);
        p.add(showchange);	   
        
        getContentPane().add(p,BorderLayout.SOUTH);
		
		// jTree.add(top);
	//	jTree.setPreferredSize(new Dimension(600, 1200));
		jTree.setVisible(true);
		//�¼�����
		jTree.addTreeSelectionListener(new TreeSelectionListener(){

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				
				
				TreePath treePath=e.getPath();
				String filePath=getFilePath(treePath);
             
				File thisf=new File(filePath);
				FileObject file =new FileObject(thisf);
				File thisfile = file.getFile();
				
				
				JLabel showpath,showsize,showchange;
				JPanel p;
				    
				showpath=new JLabel("�ļ�·��");
				showsize=new JLabel("�ļ���С");
				showchange=new JLabel("�޸�ʱ��");
			        
				p = new JPanel();
				p.add(showpath);
				p.add(showsize);
				p.add(showchange);	   
				
				getContentPane().add(p,BorderLayout.SOUTH);
				getContentPane().validate();
			   //     this.setVisible(true);   
					
					
				//	this.setTitle("�����ڲ鿴���ļ��ǣ�"+thisfile.getName().toString());
				String path="�ļ�·��Ϊ:"+thisfile.getPath();
				String size="�ļ���СΪ:"+thisfile.length()/(1024*1024)+"MB";
				
					
				Calendar cal = Calendar.getInstance(); 
				long time = thisfile.lastModified(); 
				cal.setTimeInMillis(time); 			
				String change="���һ���޸�����Ϊ:"+cal.getTime().toLocaleString();
					

				showpath.setText(path);	     
				showsize.setText(size);	     
				showchange.setText(change);			        
			}
			
		});
		
		

		
		jTree.addTreeExpansionListener(new TreeExpansionAction());

		this.setSize(1000, 600);
		this.add(new JScrollPane(jTree));
		this.setVisible(true);
		this.setTitle(s);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
	//	this.getContentPane().add(jTree, BorderLayout.CENTER);
		getContentPane().validate();
		displayCenter();

	}
		
	public class TreeExpansionAction implements TreeExpansionListener {
	        public void treeExpanded(TreeExpansionEvent event) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
				for (int i = 0; i < node.getChildCount(); i++) {
		            DefaultMutableTreeNode node2 = (DefaultMutableTreeNode) node.getChildAt(i);
		            File file = (File) node2.getUserObject();           
		            		if (file.isDirectory()) {
		                    addChildNotes(node2, file);
		                }		            
		        }      	        	
	        }
	        public void treeCollapsed(TreeExpansionEvent event) {
	        }
	    }
	 


	// �Զ�������ӽڵ�
	public void addChildNotes(DefaultMutableTreeNode node, File currentFile) {
		
		File[] files = currentFile.listFiles();
        if (files != null) {
            for (File file : files) {
            	
     /*       	DefaultMutableTreeNode root = new DefaultMutableTreeNode(file.getName(), true);
            	System.out.println("path="+file.getPath()+";name="+file.getName());
    			addChildNotes(root, file);
    			node.add(root);*/
            	
                node.add(new DefaultMutableTreeNode(file));
            }
        }				
	}

	// ��������Ӵ��̸�Ŀ¼
	public void addRootNode() {
		// ��ȡ���Դ��̸�Ŀ¼
		rootFiles = File.listRoots();
		// �������Դ��̸�Ŀ¼
		for (int i = 0; i < rootFiles.length; i++) {
			// System.out.println("path="+file.getPath()+";name="+file.getName());
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("���ش���:("
					+ rootFiles[i].getPath().substring(0, 2) + ")", true);
			addChildNotes(root, rootFiles[i]);
			top.add(root);
		}

	}

	// ��������ʾ
	public void displayCenter() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int x = (toolkit.getScreenSize().width - this.getSize().width) / 2;
		int y = (toolkit.getScreenSize().height - this.getSize().height) / 2;
		this.setLocation(x, y);

	}

	//ͨ���ֽ�Treepath.getPath��ȡ�ļ�·��
	public String getFilePath(TreePath treePath){
		Object[] objs=treePath.getPath();
		StringBuilder filePath=new StringBuilder();
		//���Ը��ڵ�<�ҵĵ���>
        for(int i=1;i<objs.length;i++){
        	//������̸�Ŀ¼
        	if(i==1){
        		filePath.append(dealRoot(objs[i]));
        	}
        	//�м�Ŀ¼���ֶ����Ŀ¼�ָ�����\��
        	else if(i<objs.length-1)
        		filePath.append(objs[i]).append("\\");
        	else
        		filePath.append(objs[i]);
        	
        //	System.out.println("objs"+i+objs[i]);
        }
		return filePath.toString();
	}
	//������̸�Ŀ¼
	public String dealRoot(Object obj){
		String str=obj.toString();
		int index=str.indexOf("(");
		str=str.substring(index+1,str.length()-1)+"\\";
		return str;
	}
	
	//ͨ��TreePath.getParentPath()ʵ������׷��ƴ���ļ�Ŀ¼
	//ͨ���ֽ�Treepath�����ȡ�ļ�·��

	public static void main(String[] args) {
		new JTreeFrame("PB13209036 ���� ����ҵ");
	}
	
	public class FileObject {
        private File file;
        private boolean updated;
        public FileObject(File file) {
            this.file = file;
        }
        public File getFile() {
            return file;
        }
        public boolean isUpdated() {
            return updated;
        }
        public void setUpdated(boolean updated) {
            this.updated = updated;
        }
        public String toString() {
            return file.getName();
        }
    }

}




