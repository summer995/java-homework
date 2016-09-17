package ������;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
 
import javax.swing.JFrame;
import javax.swing.JOptionPane;
 
public class FiveChessFrame extends JFrame implements MouseListener, Runnable {
    // ��Ϸ����Ĵ�С
    int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    // ����ͼƬ
    //BufferedImage bgImage = null;
    // �����������
    int x, y;
    // ��������,0��ʾ���̸õ������ӣ�1��ʾ���ӣ�2��ʾ����
    int[][] allChess = new int[15][15];
    // ���浱ǰ�����Ǻ��ӻ��ǰ��֣�true�Ǻ��ӣ�false�ǰ���
    Boolean isBlack = true;
    // ��ʶ��ǰ��Ϸ�Ƿ����
    Boolean canPlay = true;
    // ������Ϸ��Ϣ
    String message = "�ڷ�����";
    // ��������
    int[] chessX = new int[255];
    int[] chessY = new int[255];
    int countX, countY;
    // �������ʱ��
    int maxTime = 0;
    // ��Ϸʱ�����õ���Ϣ
    String blackMessage = "������";
    String whiteMessage = "������";
    // ����ڰ׷���ʣ���ʱ��
    int blackTime = 0;
    int whiteTime = 0;
    // ��Ϸ����ʱ�߳�
    Thread timer = new Thread(this);
 
    public FiveChessFrame() {
        this.setTitle("��������Ϸ");
        this.setSize(500, 500);
        this.setLocation((width - 500) / 2, (height - 500) / 2);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.repaint();
        this.addMouseListener(this);
        timer.start();
        timer.suspend();
    }
 
    public void paint(Graphics g) {
        /*try {
            bgImage = ImageIO.read(new File("src/wzq/����������.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
 
        // ˫����
        BufferedImage bi = new BufferedImage(500, 500,
                BufferedImage.TYPE_INT_RGB);
        Graphics g2 = bi.createGraphics();
 
        //g2.drawImage(bgImage, 43, 60, 375, 375, this);
        g2.setColor(new Color(0, 169, 158));
        g2.fill3DRect(43, 60, 375, 375, true);
         
        for(int i = 0; i <= 15; i++){
            g2.setColor(Color.WHITE);
            g2.drawLine(43, i * 25 + 60, 375 + 43, 60 + i * 25);
            g2.drawLine(i * 25 + 43, 60, 43 + i * 25, 375 + 60);
        }
 
        g2.setFont(new Font("����", Font.BOLD, 20));
        g2.drawString("��Ϸ��Ϣ��" + message, 50, 50);
        g2.drawRect(30, 440, 180, 40);
        g2.drawRect(250, 440, 180, 40);
        g2.setFont(new Font("����", 0, 12));
        g2.drawString("�ڷ�ʱ�䣺" + blackMessage, 40, 465);
        g2.drawString("�׷�ʱ�䣺" + whiteMessage, 260, 465);
        // ���¿�ʼ��ť
        g2.drawRect(428, 66, 54, 20);
        g2.drawString("���¿�ʼ", 432, 80);
 
        // ��Ϸ���ð�ť
        g2.drawRect(428, 106, 54, 20);
        g2.drawString("��Ϸ����", 432, 120);
 
        // ��Ϸ˵����ť
        g2.drawRect(428, 146, 54, 20);
        g2.drawString("��Ϸ˵��", 432, 160);
 
        // �˳���Ϸ��ť
        g2.drawRect(428, 186, 54, 20);
        g2.drawString("�˳���Ϸ", 432, 200);
 
        // ����
        g2.drawRect(428, 246, 54, 20);
        g2.drawString("����", 442, 260);
 
        // ����
        g2.drawRect(428, 286, 54, 20);
        g2.drawString("����", 442, 300);
 
         
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                // ����
                if (allChess[i][j] == 1) {
                    int tempX = i * 25 + 55;
                    int tempY = j * 25 + 72;
                    g2.setColor(Color.BLACK);
                    g2.fillOval(tempX - 8, tempY - 8, 16, 16);
                }
                // ����
                if (allChess[i][j] == 2) {
                    int tempX = i * 25 + 55;
                    int tempY = j * 25 + 72;
                    g2.setColor(Color.WHITE);
                    g2.fillOval(tempX - 8, tempY - 8, 16, 16);
                    g2.setColor(Color.BLACK);
                    g2.drawOval(tempX - 8, tempY - 8, 16, 16);
                }
            }
        }
        g.drawImage(bi, 0, 0, this);
    }
 
    @Override
    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub
 
    }
 
    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub
 
    }
 
    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub
 
    }
 
    @Override
    public void mousePressed(MouseEvent e) {
        Boolean checkWin = false;
 
        if (canPlay) {
            x = e.getX();
            y = e.getY();
            /*
             * System.out.println(x); System.out.println(y);
             */
            if (x >= 55 && x <= 405 && y >= 72 && y <= 420) {
                if ((x - 55) % 25 > 12)
                    x = (x - 55) / 25 + 1;
                else
                    x = (x - 55) / 25;
                if ((y - 72) % 25 > 12)
                    y = (y - 72) / 25 + 1;
                else
                    y = (y - 72) / 25;
                if (allChess[x][y] == 0) {
 
                    chessX[countX++] = x;
                    chessY[countY++] = y;
                     
            //      System.out.println(countX + "  " + countY);
                    if (isBlack) {
                        allChess[x][y] = 1;
                        isBlack = false;
                        message = "�׷�����";
                    } else {
                        allChess[x][y] = 2;
                        isBlack = true;
                        message = "�ڷ�����";
                    }
                    this.repaint();
                    checkWin = isWin();
                    if (checkWin) {
                        if (allChess[x][y] == 1)
                            JOptionPane.showMessageDialog(this, "��Ϸ�������ڷ�ʤ��");
                        else
                            JOptionPane.showMessageDialog(this, "��Ϸ�������׷�ʤ��");
                        canPlay = false;
                    }
                }
            }
        }
 
        // ���¿�ʼ��Ϸ
        if (e.getX() >= 428 && e.getX() <= 482 && e.getY() >= 66 && e.getY() <= 86) {
            int result = JOptionPane.showConfirmDialog(this, "�Ƿ����¿�ʼ��Ϸ��");
            if (result == 0) {
                for (int i = 0; i < 15; i++)
                    for (int j = 0; j < 15; j++)
                        allChess[i][j] = 0;
                for (int i = 0; i < 15; i++) {
                    chessX[i] = 0;
                    chessY[i] = 0;
                }
                countX = 0;
                countY = 0;
                message = "�ڷ�����";
                blackMessage = "������";
                whiteMessage = "������";
                blackTime = maxTime;
                whiteTime = maxTime;
                isBlack = true;
                canPlay = true;
                this.repaint();
            }
        }
        // ��Ϸ����ʱ����
        if (e.getX() >= 428 && e.getX() <= 482 && e.getY() >= 106 && e.getY() <= 126) {
            String input = JOptionPane
                    .showInputDialog("��������Ϸ�����ʱ��(��λ:����),�������0,��ʾû��ʱ������:");
            maxTime = Integer.parseInt(input) * 60;
            System.out.println(maxTime);
            if (maxTime < 0) {
                JOptionPane.showMessageDialog(this, "�������Ϸʱ���������������ã�");
            } else if (maxTime == 0) {
                int result = JOptionPane.showConfirmDialog(this,
                        "��Ϸʱ�����óɹ����Ƿ�ʼ��Ϸ��");
                if (result == 0) {
                    for (int i = 0; i < 15; i++)
                        for (int j = 0; j < 15; j++)
                            allChess[i][j] = 0;
                    for (int i = 0; i < 15; i++) {
                        chessX[i] = 0;
                        chessY[i] = 0;
                    }
                    countX = 0;
                    countY = 0;
                    message = "�ڷ�����";
                    blackMessage = "������";
                    whiteMessage = "������";
                    blackTime = maxTime;
                    whiteTime = maxTime;
                    isBlack = true;
                    canPlay = true;
                    this.repaint();
                }
            } else if (maxTime > 0) {
                int result = JOptionPane.showConfirmDialog(this,
                        "��Ϸʱ�����óɹ����Ƿ����¿�ʼ��Ϸ��");
                if (result == 0) {
                    for (int i = 0; i < 15; i++)
                        for (int j = 0; j < 15; j++)
                            allChess[i][j] = 0;
                    for (int i = 0; i < 15; i++) {
                        chessX[i] = -1;
                        chessY[i] = -1;
                    }
                    countX = 0;
                    countY = 0;
                    message = "�ڷ�����";
                    isBlack = true;
                    blackMessage = maxTime / 3600 + ":"
                            + (maxTime / 60 - maxTime / 3600 * 60) + ":"
                            + (maxTime - maxTime / 60 * 60);
                    whiteMessage = maxTime / 3600 + ":"
                            + (maxTime / 60 - maxTime / 3600 * 60) + ":"
                            + (maxTime - maxTime / 60 * 60);
                     
                    blackTime = maxTime;
                    whiteTime = maxTime;
                    System.out.println(blackMessage + " - -" + whiteMessage);
                    timer.resume();
                    this.canPlay = true;
                    this.repaint();
                }
            }
        }
        // ��Ϸ˵��
        if (e.getX() >= 428 && e.getX() <= 482 && e.getY() >= 146 && e.getY() <= 166) {
            JOptionPane.showMessageDialog(this, "��һ�䣺����б�����������߻�ʤ��");
        }
        // �˳���Ϸ
        if (e.getX() >= 428 && e.getX() <= 482 && e.getY() >= 186 && e.getY() <= 206) {
            int result = JOptionPane.showConfirmDialog(this, "�Ƿ��˳���Ϸ��");
            if (result == 0) {
                System.exit(0);
            }
        }
        // ����
        if (e.getX() >= 428 && e.getX() <= 482 && e.getY() >= 246 && e.getY() <= 266) {
            int result = JOptionPane.showConfirmDialog(this,
                    (isBlack == true ? "�׷����壬�ڷ��Ƿ�ͬ�⣿" : "�ڷ����壬�׷��Ƿ�ͬ�⣿"));
            if (result == 0) {
                allChess[chessX[--countX]][chessY[--countY]] = 0;
                isBlack = (isBlack == true) ? false : true;
                this.repaint();
            }
        }
        // ����
        if (e.getX() >= 428 && e.getX() <= 482 && e.getY() >= 286 && e.getY() <= 306) {
            int result = JOptionPane.showConfirmDialog(this, "�Ƿ����䣿");
            if (result == 0) {
                JOptionPane.showMessageDialog(this, "��Ϸ������"
                        + (isBlack == true ? "�ڷ����䣬�׷���ʤ!" : "�׷����䣬�ڷ���ʤ!"));
            }
        }
    }
 
    @Override
    public void mouseReleased(MouseEvent arg0) {
 
    }
 
    private Boolean isWin() {
        boolean flag = false;
        // ���湲����ͬ��ɫ������������
        int count = 1;
        // �жϺ����Ƿ���5�������������ص� ������ ����ͬ�� ��allChess[x][y]��yֵ����ͬ
        int color = allChess[x][y];
        // �жϺ���
        count = this.checkCount(1, 0, color);
        if (count >= 5) {
            flag = true;
        } else {
            // �ж�����
            count = this.checkCount(0, 1, color);
            if (count >= 5) {
                flag = true;
            } else {
                // �ж����ϡ�����
                count = this.checkCount(1, -1, color);
                if (count >= 5) {
                    flag = true;
                } else {
                    // �ж����¡�����
                    count = this.checkCount(1, 1, color);
                    if (count >= 5) {
                        flag = true;
                    }
                }
            }
        }
 
        return flag;
    }
 
    // �ж��������ӵ�����
    private int checkCount(int xChange, int yChange, int color) {
        int count = 1;
        int tempX = xChange;
        int tempY = yChange;
        while (x + xChange >= 0 && x + xChange <= 14 && y + yChange >= 0
                && y + yChange <= 14
                && color == allChess[x + xChange][y + yChange]) {
            count++;
            if (xChange != 0)
                xChange++;
            if (yChange != 0) {
                if (yChange > 0)
                    yChange++;
                else {
                    yChange--;
                }
            }
        }
        xChange = tempX;
        yChange = tempY;
        while (x - xChange >= 0 && x - xChange <= 14 && y - yChange >= 0
                && y - yChange <= 14
                && color == allChess[x - xChange][y - yChange]) {
            count++;
            if (xChange != 0)
                xChange++;
            if (yChange != 0) {
                if (yChange > 0)
                    yChange++;
                else {
                    yChange--;
                }
            }
        }
        return count;
    }
 
    @Override
    public void run() {
        if (maxTime > 0) {
            while (true) {
                if (isBlack) {
                    blackTime--;
                    if (blackTime == 0) {
                        JOptionPane.showMessageDialog(this, "�ڷ���ʱ,��Ϸ����!");
                    }
                } else {
                    whiteTime--;
                    if (whiteTime == 0) {
                        JOptionPane.showMessageDialog(this, "�׷���ʱ,��Ϸ����!");
                    }
                }
                blackMessage = blackTime / 3600 + ":"
                        + (blackTime / 60 - blackTime / 3600 * 60) + ":"
                        + (blackTime - blackTime / 60 * 60);
                whiteMessage = whiteTime / 3600 + ":"
                        + (whiteTime / 60 - whiteTime / 3600 * 60) + ":"
                        + (whiteTime - whiteTime / 60 * 60);
                this.repaint();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        //      System.out.println(blackTime + " -- " + whiteTime);
            }
        }
    }
     
    public static void main(String[] args) {
        new FiveChessFrame();
    }
}