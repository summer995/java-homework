package �����;


/*
**��ҳ�����������
**WebBrowser.java
*/
import java.awt.*; 
import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*; 
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileFilter; 
import javax.swing.filechooser.FileView; 
import java.io.*; 
import java.net.*; 
import java.util.*; 

public class WebBrowser extends JFrame implements HyperlinkListener,ActionListener{ 

    //����������������ʾ��ַ��
    JToolBar bar=new JToolBar (); 
    
    //������ҳ��ʾ����
    JTextField jurl = new JTextField (60); 
    JEditorPane jEditorPane1 = new JEditorPane ();
    JScrollPane scrollPane = new JScrollPane (jEditorPane1); 
    
    JFileChooser chooser=new JFileChooser (); 
    JFileChooser chooser1=new JFileChooser (); 
    String htmlSource; 
    JWindow window = new JWindow (WebBrowser.this); 
    
    JButton button2=new JButton ("���ڻ�ԭ");
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    
    //�����˵���
    JMenuBar jMenuBar1 = new JMenuBar(); 
    //�����˵���
    JMenu fileMenu = new JMenu ("�ļ�(F)"); 
    //�����˵���
    JMenuItem saveAsItem = new JMenuItem ("���Ϊ(A)...");
    JMenuItem exitItem=new JMenuItem ("�˳�(I)");
    
    JMenu editMenu=new JMenu ("�༭(E)");
    JMenuItem backItem=new JMenuItem ("����");
    JMenuItem forwardItem=new JMenuItem ("ǰ��");
    
    JMenu viewMenu=new JMenu ("��ͼ(V)");
    JMenuItem fullscreenItem=new JMenuItem ("ȫ��(U)"); 
    JMenuItem sourceItem=new JMenuItem ("�鿴Դ��(C)");
    JMenuItem reloadItem=new JMenuItem ("ˢ��(R)");

    //����������
    JToolBar toolBar = new JToolBar();
    //�����������еİ�ť���
    JButton picSave = new JButton("���Ϊ");
    JButton picBack = new JButton("����");
    JButton picForward = new JButton("ǰ��");
    JButton picView = new JButton("�鿴Դ����");
    JButton picExit = new JButton("�˳�");

    JLabel label=new JLabel ("��ַ"); 
    JButton button=new JButton ("ת��");
    
    Box adress=Box.createHorizontalBox ();    

    //ArrayList�������������ʷ��ַ
    private ArrayList history=new ArrayList();
    //���ͱ�������ʾ��ʷ��ַ�ķ���˳��
    private int historyIndex; 

    /**
    **���캯��
    **��ʼ��ͼ���û�����
    */
    public WebBrowser(){
        
        setTitle ("��ҳ�����"); 
        setResizable(false);
        setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    
        //ΪjEditorPane1����¼�����
        jEditorPane1.addHyperlinkListener (this);
        
        //Ϊ���fileMenu�����ȼ���F��
        fileMenu.setMnemonic('F'); 
        
        saveAsItem.setMnemonic ('S'); 
        //Ϊ�����Ϊ��������ÿ�ݼ�Ϊctrl+s
        saveAsItem.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_S,InputEvent.CTRL_MASK)); 
        
        exitItem.setMnemonic('Q'); 
        exitItem.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_E,InputEvent.CTRL_MASK)); 
        
        //���˵���saveAsItem���뵽�˵���fileMenu��
        fileMenu.add (saveAsItem); 
        //�ڲ˵�������Ӹ���
        fileMenu.addSeparator (); 
        fileMenu.add (exitItem);
        
        backItem.setMnemonic ('B'); 
        backItem.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_Z,InputEvent.CTRL_MASK)); 
        forwardItem.setMnemonic('D'); 
        forwardItem.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_P,InputEvent.CTRL_MASK)); 
        
        editMenu.setMnemonic('E'); 
        editMenu.add (backItem); 
        editMenu.add (forwardItem);  
        
        viewMenu.setMnemonic('V');
        
        fullscreenItem.setMnemonic('U');
        fullscreenItem.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_U,InputEvent.CTRL_MASK)); 
        sourceItem.setMnemonic('C');
        sourceItem.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_C,InputEvent.CTRL_MASK)); 
        reloadItem.setMnemonic('R');
        reloadItem.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_R,InputEvent.CTRL_MASK)); 
        
        Container contentPane=getContentPane (); 
            
        //���ô�С
        scrollPane.setPreferredSize(new Dimension(100,500));
        contentPane.add (scrollPane, BorderLayout.SOUTH);
        
        //�ڹ���������Ӱ�ť���
        toolBar.add(picSave);
        toolBar.addSeparator();
        toolBar.add(picBack);
        toolBar.add(picForward);
        toolBar.addSeparator();
        toolBar.add(picView);
        toolBar.addSeparator();
        toolBar.add(picExit);
        
        contentPane.add (bar,BorderLayout.CENTER); 
        contentPane.add(toolBar,BorderLayout.NORTH);
        
        viewMenu.add (fullscreenItem); 
        viewMenu.add (sourceItem); 
        viewMenu.addSeparator (); 
        viewMenu.add (reloadItem); 
        
        jMenuBar1.add (fileMenu); 
        jMenuBar1.add (editMenu); 
        jMenuBar1.add (viewMenu); 

        setJMenuBar (jMenuBar1); 
    
        adress.add (label); 
        adress.add (jurl); 
        adress.add (button); 
        bar.add (adress); 
        
        //Ϊ�������¼�����
        saveAsItem.addActionListener(this);
        picSave.addActionListener(this);
        exitItem.addActionListener(this);
        picExit.addActionListener(this);
        backItem.addActionListener(this);
        picBack.addActionListener(this);
        forwardItem.addActionListener(this);
        picForward.addActionListener(this);
        fullscreenItem.addActionListener(this);
        sourceItem.addActionListener(this);
        picView.addActionListener(this);
        reloadItem.addActionListener(this);
        button.addActionListener(this);
        jurl.addActionListener(this);
    }
    
    /**
    **ʵ�ּ������ӿڵ�actionPerformed����
    */
    public void actionPerformed(ActionEvent e) {
        String url = "";
        //���ת��ť
        if (e.getSource() == button){
            //��õ�ַ��������
            url=jurl.getText (); 
            //url��Ϊ�����������ԡ�http://����ͷ
            if(url.length ()>0&&url.startsWith ("http://")){ 
                try { 
                    //JEditorPane�����ʾurl����������
                    jEditorPane1.setPage (url); 
                    //��url��������ӵ�ArrayList����history��
                    history.add(url); 
                    //historyIndex����ֵ��Ϊhistory����ĳ���-1
                    historyIndex=history.size()-1;
                    //���²���
		    jEditorPane1.setEditable(false); //add by copy editor :)
                    jEditorPane1.revalidate (); 
                } 
                catch(Exception ex) { 
                    //���������ʾʧ�ܣ��򵯳�ѡ��Ի����޷��򿪸�����ҳ��
                    JOptionPane.showMessageDialog (WebBrowser.this,"�޷��򿪸�����ҳ","��ҳ�����",JOptionPane.ERROR_MESSAGE);
                } 
            } 
            //url��Ϊ���������Ҳ��ԡ�http://����ͷ
            else if(url.length ()>0&&!url.startsWith ("http://")) { 
                //��urlǰ����ӡ�http://��
                url="http://"+url; 
                try { 
                    jEditorPane1.setPage (url ); 
                    history.add(url); 
                    historyIndex=history.size()-1;
		    jEditorPane1.setEditable(false); //add by copy editor :)
                    jEditorPane1.revalidate (); 
                } 
                catch(Exception ex) {
                    JOptionPane.showMessageDialog (WebBrowser.this,"�޷��򿪸�����ҳ","��ҳ�����",JOptionPane.ERROR_MESSAGE);
                } 
            }
            //û������url����urlΪ��
            else if(url.length ()==0){
                JOptionPane.showMessageDialog (WebBrowser.this,"���������ӵ�ַ","��ҳ�����",JOptionPane.ERROR_MESSAGE);
            }
        
        }
        //�����ַ�����س�
        else if (e.getSource() == jurl){
            url=jurl.getText (); 
            if(url.length ()>0&&url.startsWith ("http://")) { 
                try { 
                    jEditorPane1.setPage (url); 
                    history.add(url); 
                    historyIndex=history.size()-1;
		    jEditorPane1.setEditable(false); //add by copy editor :)
                    jEditorPane1.revalidate (); 
                    jurl.setMaximumSize (jurl.getPreferredSize ()); 
                } 
                catch(Exception ex) { 
                    JOptionPane.showMessageDialog (WebBrowser.this,"�޷��򿪸�����ҳ","��ҳ�����",JOptionPane.ERROR_MESSAGE);
                } 
            } 
            else if(url.length ()>0&&!url.startsWith ("http://")) { 
                url="http://"+url; 
                try { 
                    jEditorPane1.setPage (url );
                    history.add(url); 
                    historyIndex=history.size()-1;
		    jEditorPane1.setEditable(false); //add by copy editor :)
                    jEditorPane1.revalidate (); 
                } 
                catch(Exception ex) { 
                    JOptionPane.showMessageDialog (WebBrowser.this,"�޷��򿪸�����ҳ","��ҳ�����",JOptionPane.ERROR_MESSAGE);
                } 
            }
            else if(url.length ()==0){
                JOptionPane.showMessageDialog (WebBrowser.this,"���������ӵ�ַ","��ҳ�����",JOptionPane.ERROR_MESSAGE);
            }
        }
        //���Ϊ...
        else if (e.getSource() == picSave||e.getSource() == saveAsItem){
            url = jurl.getText ().toString ().trim(); 
            if(url.length ()>0&&!url.startsWith ("http://")) { 
                url="http://"+url; 
            } 
            if(!url.equals ("")) { 
                //�����ļ�
                saveFile(url); 
            } 
            else { 
                JOptionPane.showMessageDialog (WebBrowser.this,"���������ӵ�ַ","��ҳ�����",JOptionPane.ERROR_MESSAGE); 
            } 
        }
        //�˳�
        else if (e.getSource() == exitItem ||e.getSource() == picExit){
            System.exit(0);
        }
        //����
        else if (e.getSource() == backItem ||e.getSource() == picBack){
            historyIndex--; 
            if(historyIndex < 0) 
                historyIndex = 0; 
            url = jurl.getText();
            try{
                //���history�����б���ַ֮ǰ���ʵĵ�ַ
                url = (String)history.get(historyIndex);
                jEditorPane1.setPage(url); 
                jurl.setText(url.toString());
		jEditorPane1.setEditable(false); //add by copy editor :)
                jEditorPane1.revalidate (); 
            }
            catch(Exception ex){
            }
        }
        //ǰ��
        else if (e.getSource() == forwardItem ||e.getSource() == picForward){
            historyIndex++; 
            if(historyIndex >= history.size()) 
                    historyIndex = history.size()-1; 
            url = jurl.getText();
            try{
                //���history�����б���ַ֮����ʵĵ�ַ
                url = (String)history.get(historyIndex);
                jEditorPane1.setPage(url); 
                jurl.setText(url.toString());
		jEditorPane1.setEditable(false); //add by copy editor :)
                jEditorPane1.revalidate (); 
            }
            catch(Exception ex){
            }
        }
        //ȫ��
        else if (e.getSource() == fullscreenItem){
            boolean add_button2=true; 
            //�����Ļ��С
            Dimension size = Toolkit.getDefaultToolkit().getScreenSize(); 

            Container content = window.getContentPane(); 
            content.add (bar,"North"); 
            content.add (scrollPane,"Center"); 
                
            //button2Ϊ�����ȫ������Ļ�ԭ��ť
            if(add_button2==true) { 
                bar.add (button2); 
            } 
            //Ϊbutton2����¼�
            button2.addActionListener (new ActionListener () { 
                public void actionPerformed (ActionEvent evt) { 
                    WebBrowser.this.setEnabled (true); 
                    window.remove (bar);
                    window.remove (toolBar);
                    window.remove (scrollPane); 
                    window.setVisible (false); 
                    
                    scrollPane.setPreferredSize (new Dimension (100,500)); 
                    getContentPane ().add (scrollPane,BorderLayout.SOUTH); 
                    getContentPane ().add (bar,BorderLayout.CENTER); 
                    getContentPane ().add (toolBar,BorderLayout.NORTH); 
                    bar.remove (button2);
                    pack(); 
                } 
            });
            window.setSize (size); 
            window.setVisible (true); 
        }
        //�鿴Դ�ļ�
        else if (e.getSource() == sourceItem ||e.getSource() == picView){
            url = jurl.getText ().toString ().trim (); 
                if(url.length ()>0&&!url.startsWith ("http://")) { 
                    url="http://"+url; 
                } 
                if( !url.equals ("")) {
                    //����url�����Դ����
                    getHtmlSource (url); 
                    //������ʾԴ����Ŀ�ܶ���
                    ViewSourceFrame vsframe = new ViewSourceFrame (htmlSource); 
                    vsframe.setBounds (0,0,800,500); 
                    vsframe.setVisible(true); 
                } 
                else { 
                    JOptionPane.showMessageDialog (WebBrowser.this,"���������ӵ�ַ","��ҳ�����",JOptionPane.ERROR_MESSAGE); 
                } 
        }
        //ˢ��
        else if (e.getSource() == reloadItem){
            url=jurl.getText (); 
            if(url.length ()>0&&url.startsWith ("http://")) { 
                try { 
                    jEditorPane1.setPage (url); 
		    jEditorPane1.setEditable(false); //add by copy editor :)
                    jEditorPane1.revalidate (); 
                } 
                catch(Exception ex) { 
                } 
            } 
            else if(url.length ()>0&&!url.startsWith ("http://")) { 
                url="http://"+url; 
                try { 
                    jEditorPane1.setPage (url ); 
		    jEditorPane1.setEditable(false); //add by copy editor :)
                    jEditorPane1.revalidate (); 
                } 
                catch(Exception ex) { 
                } 
            } 
        }
    }
    
    /*
    **�����ļ�
    */
    void saveFile (final String url) { 
        final String linesep = System.getProperty ("line.separator"); 
        chooser1.setCurrentDirectory (new File (".")); 
        chooser1.setDialogType (JFileChooser.SAVE_DIALOG); 
        chooser1.setDialogTitle ("���Ϊ..."); 
        if(chooser1.showSaveDialog (this) != JFileChooser.APPROVE_OPTION) 
            return; 
        this.repaint (); 
        Thread thread = new Thread () { 
            public void run () { 
                try { 
                    java.net.URL source = new URL (url); 
                    InputStream in = new BufferedInputStream (source.openStream ()); 
                    BufferedReader br=new BufferedReader (new InputStreamReader (in)); 
                    File fileName = chooser1.getSelectedFile (); 
                    FileWriter out = new FileWriter (fileName);
                    BufferedWriter bw = new BufferedWriter (out); 
                    String line; 
                    while((line = br.readLine ()) != null) { 
                        bw.write (line); 
                        bw.newLine (); 
                    } 
                    bw.flush (); 
                    bw.close (); 
                    out.close (); 
                    String dMessage = url + " �Ѿ���������"+ linesep +fileName.getAbsolutePath (); 
                    String dTitle = "���Ϊ"; 
                    int dType = JOptionPane.INFORMATION_MESSAGE; 
                    JOptionPane.showMessageDialog ((Component) null,dMessage,dTitle,dType); 
                } 
                catch(java.net.MalformedURLException muex) { 
                    JOptionPane.showMessageDialog ((Component)null,muex.toString (),"��ҳ�����",JOptionPane.ERROR_MESSAGE); 
                } 
                catch(Exception ex) { 
                    JOptionPane.showMessageDialog ((Component) null,ex.toString (),"��ҳ�����",JOptionPane.ERROR_MESSAGE); 
                } 
            } 
        }; 
        thread.start (); 
    } 

    /*
    **���Դ����
    */
    void getHtmlSource (String url) { 
        String linesep,htmlLine; 
        linesep = System.getProperty ("line.separator"); 
        htmlSource =""; 
        try { 
            java.net.URL source = new URL (url); 
            InputStream in = new BufferedInputStream (source.openStream ()); 
            BufferedReader br = new BufferedReader ( new InputStreamReader (in)); 
            while((htmlLine = br.readLine ())!=null) { 
                htmlSource = htmlSource +htmlLine+linesep; 
            } 
        } 
        catch(java.net.MalformedURLException muex) { 
            JOptionPane.showMessageDialog (WebBrowser.this,muex.toString (),"��ҳ�����",JOptionPane.ERROR_MESSAGE); 
        } 
        catch(Exception e) { 
            JOptionPane.showMessageDialog (WebBrowser.this,e.toString (),"��ҳ�����",JOptionPane.ERROR_MESSAGE); 
        } 
    } 
    
    /**
    **ʵ�ּ������ӿڵ�hyperlinkUpdate����
    */
    public void hyperlinkUpdate (HyperlinkEvent e) { 
     /*   if(e.getEventType () == HyperlinkEvent.EventType.ACTIVATED) { 
            String url = jurl.getText();
            if(url.length ()>0&&url.startsWith ("http://")) { 
                try { 
                    jEditorPane1.setPage (url); 
                    jEditorPane1.revalidate (); 
                } 
                catch(Exception ex) { 
                } 
            } 
            else if(url.length ()>0&&!url.startsWith ("http://")) { 
                url="http://"+url; 
                try { 
                    jEditorPane1.setPage (url ); 
                    jEditorPane1.revalidate (); 
                } 
                catch(Exception ex) { 
                } 
            } 
        } 
    */ 
    // Revised by copy editor :) Now with new code to help hyperlink
	try {
 		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
 			jEditorPane1.setPage(e.getURL());
 		} catch (Exception ex) {
 			ex.printStackTrace(System.err);
 		}
    } 
    
    /*����һ��IE����*/
    public static void main (String [] args){ 
        try{
            UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName() 
            );
        }
        catch(Exception e){
        }

        WebBrowser webBrowser = new WebBrowser ();
        webBrowser.pack();
        webBrowser.setVisible(true);
    } 
}















/*
import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;
import java.io.*;
import java.net.*;
import java.util.*;

public class WebBrowser extends JFrame implements HyperlinkListener, ActionListener{
	JToolBar bar= new JToolBar();
	//������ʾ��ַ��
	
	JTextField jurl = new JTextField(60);
	JEditorPane jEditorPanel = new JEditorPane();
	JScrollPane scrollPane = new JScrollPane(jEditorPanel);
	
	JFileChooser chooser = new JFileChooser();
	JFileChooser chooser1 = new JFileChooser();
	String htmlSource;
	JWindow window = new JWindow(WebBrowser.this);
	
	JButton button2 = new JButton("���ڻ�ԭ");
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	//�����˵���
	JMenuBar jMenuBar1 = new JMenuBar();
	//�����˵���
	JMenu fileMenu = new JMenu("�ļ�");
	JMenuItem saveAsItem = new JMenuItem("���Ϊ(A)");
	JMenuItem exitItem = new JMenuItem("�˳�(I)");
	
	JMenu editMenu = new JMenu("�༭(E)");
	JMenuItem backItem = new JMenuItem("����");
	JMenuItem forwardItem = new JMenuItem("ǰ��");
	
	JMenu viewMenu = new JMenu("��ͼ");
	JMenuItem fullscreenItem = new JMenuItem("ȫƵ");
	JMenuItem sourceItem = new JMenuItem("�鿴Դ��");
	JMenuItem renewItem = new JMenuItem("ˢ��");
	
	//����������
	JToolBar toolBar = new JToolBar();
	
	JButton picSave = new JButton("���Ϊ");
	JButton picBack = new JButton("���� ");
	JButton picForward = new JButton("ǰ��");
	JButton picView = new JButton("�鿴Դ����");
	JButton picExit = new JButton("�˳�");
	
	JLabel label = new JLabel("��ַ");
	JButton button = new JButton("ת��");
	
	Box adress = Box.createHorizontalBox();
	
	//�����ʷ��ַ
	private ArrayList history = new ArrayList();
	
	private int historyIndex;
	
	//��ʼ��ͼ���û�����
	
	public WebBrowser(){
		
		setTitle("��ҳ�����");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jEditorPanel.addHyperlinkListener(this);
		
		fileMenu.setMnemonic('S');
		
		saveAsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
		
		exitItem.setMnemonic('Q');
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.CTRL_MASK));
	
		//���˵���saveAsItem�������fileMenu��
		fileMenu.add(saveAsItem);
		
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		
		backItem.setMnemonic('B');
		backItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_MASK));
	
		forwardItem.setMnemonic('D');
		forwardItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,InputEvent.CTRL_MASK));
		
		editMenu.setMnemonic('e');
		editMenu.add(backItem);
		editMenu.add(forwardItem);
		
		viewMenu.setMnemonic('v');
		
		fullscreenItem.setMnemonic('u');
		fullscreenItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,InputEvent.CTRL_MASK));
		
		sourceItem.setMnemonic('c');
		sourceItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_MASK));
		
		renewItem.setMnemonic('r');
		renewItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,InputEvent.CTRL_MASK));
		
		Container contentPane = getContentPane();
		
		scrollPane.setPreferredSize(new Dimension(100,500));
		contentPane.add(scrollPane, BorderLayout.SOUTH);
		
		//����������Ӱ�ť��
		toolBar.add(picSave);
		toolBar.addSeparator();
		toolBar.add(picBack);
		toolBar.add(picForward);
		toolBar.addSeparator();
		toolBar.add(picView);
		toolBar.addSeparator();
		toolBar.add(picExit);
		
		contentPane.add(bar,BorderLayout.CENTER);
		contentPane.add(toolBar,BorderLayout.NORTH);
		
		viewMenu.add(fullscreenItem);
		viewMenu.add(sourceItem);
		viewMenu.addSeparator();
		viewMenu.add(renewItem);
		
		jMenuBar1.add(fileMenu);
		jMenuBar1.add(editMenu);
		jMenuBar1.add(viewMenu);
		
		setJMenuBar(jMenuBar1);
		
		adress.add(label);
		adress.add(jurl);
		adress.add(button);
		bar.add(adress);
		
		//Ϊ�齨����¼�������
		saveAsItem.addActionListener(this);
		picSave.addActionListener(this);
		exitItem.addActionListener(this);
		picExit.addActionListener(this);
		backItem.addActionListener(this);
		picBack.addActionListener(this);
		forwardItem.addActionListener(this);
		picForward.addActionListener(this);
		fullscreenItem.addActionListener(this);
		sourceItem.addActionListener(this);
		renewItem.addActionListener(this);
		button.addActionListener(this);
		jurl.addActionListener(this);
	
		
	}
	
	
	public void actionPerformed(ActionEvent e){
		String url = "";
		//����ת��ť
		if(e.getSource() == button){
			url = jurl.getText();
			if(url.length() > 0 && url.startsWith("http://")){
				try{
					jEditorPanel.setPage(url);
					history.add(url);
					historyIndex= history.size()-1;
					jEditorPanel.revalidate();
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(WebBrowser.this,"�޷��򿪸�������ҳ","��ҳ �����",JOptionPane.ERROR_MESSAGE);
				
				}
			}
			
			else if (url.length() > 0 && !url.startsWith("http://")){
				url= "http://"+url;
				try{
					jEditorPanel.setPage(url);
					history.add(url);
					historyIndex= history.size()-1;
					jEditorPanel.revalidate();
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(WebBrowser.this,"�޷��򿪸�������ҳ","��ҳ�����",JOptionPane.ERROR_MESSAGE);						
				}
				
			}
			else if (url.length() == 0){
				JOptionPane.showMessageDialog(WebBrowser.this,"���������ӵ�ַ","��ҳ����� ",JOptionPane.ERROR_MESSAGE);
				
			}
			
		}
		
		else if(e.getSource() == jurl){
			url = jurl.getText();
			if(url.length() > 0 && url.startsWith("http://")){
				try{
					jEditorPanel.setPage(url);
					history.add(url);
					historyIndex= history.size()-1;
					jEditorPanel.revalidate();
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(WebBrowser.this,"�޷��򿪸�������ҳ","��ҳ �����",JOptionPane.ERROR_MESSAGE);
				
				}
			}
			
			else if (url.length() > 0 && !url.startsWith("http://")){
				url= "http://"+url;
				try{
					jEditorPanel.setPage(url);
					history.add(url);
					historyIndex= history.size()-1;
					jEditorPanel.revalidate();
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(WebBrowser.this,"�޷��򿪸�������ҳ","��ҳ�����",JOptionPane.ERROR_MESSAGE);						
				}
				
			}
			else if (url.length() == 0){
				JOptionPane.showMessageDialog(WebBrowser.this,"���������ӵ�ַ","��ҳ����� ",JOptionPane.ERROR_MESSAGE);
				
			}
			
		}
		
		else if(e.getSource() == picSave||e.getSource()==saveAsItem){
			url = jurl.getText().toString().trim();
			if (url.length()>0 && !url.startsWith("http://")){
				url= url + "http://";				
			}
			else {
				JOptionPane.showMessageDialog(WebBrowser.this,"���������ӵ�ַ","��ҳ�����",JOptionPane.ERROR_MESSAGE);
				
			}
		}
		
		else if (e.getSource() == exitItem || e.getSource() == picExit){
			System.exit(0);
			
		}
		
		else if (e.getSource() == backItem || e.getSource() == picBack){
			historyIndex--;
			if(historyIndex < 0){
				historyIndex = 0;
			}
			url = jurl.getText();
			try{
				url = (String)history.get(historyIndex);
				jEditorPanel.setPage(url);
				jurl.setText(url.toString());
				jEditorPanel.revalidate();
				
			}
			catch(Exception ex){
				
			}
		}
		
		else if(e.getSource() == forwardItem || e.getSource() == picForward) {
			historyIndex++;
			if(historyIndex >= history.size())
				historyIndex = history.size()-1;
			url = jurl.getText();
			try{
				url = (String)history.get(historyIndex);
				jEditorPanel.setPage(url);
				jurl.setText(url.toString());
				jEditorPanel.revalidate();
			}
			catch(Exception ex){
				
			}
		}
		
		else if (e.getSource() == fullscreenItem){
			boolean add_button2 = true;
			Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
			
			Container content = window.getContentPane();
			content.add(bar,"North");
			content.add(scrollPane,"Center");
			if (add_button2 == true){
				bar.add(button2);
			}
			
			button2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt){
					WebBrowser.this.setEnabled(true);
					window.remove(bar);
					window.remove(toolBar);
					window.remove(scrollPane);
					window.setVisible(false);
					
					scrollPane.setPreferredSize(new Dimension(100,500));
					getContentPane().add(scrollPane,BorderLayout.CENTER);
					getContentPane().add(bar,BorderLayout.NORTH);
					bar.remove(button2);
					pack();
				}
			});
			window.setSize(size);
			window.setVisible(true);
		}
		
		else if (e.getSource() == sourceItem || e.getSource() == picView){
		
		}
		
		else if (e.getSource() == renewItem){
			
		}		
	}
	
	//ʵ�ּ������ӿڵ�hyperlinkUpdate����
	public void hyperlinkUpdate(HyperlinkEvent e){
		try{
			if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
				jEditorPanel.setPage(e.getURL());
		}
		catch (Exception ex){
			ex.printStackTrace(System.err);				
		}
	}
	
	public static void main (String [] args){
		try{
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		}
		catch(Exception e){}
		
		WebBrowser webBrowser = new WebBrowser();
		webBrowser.pack();
		webBrowser.setVisible(true);
	}
}
		
	
	
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

