package 五子棋;
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
    // 游戏界面的大小
    int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    // 背景图片
    //BufferedImage bgImage = null;
    // 保存鼠标坐标
    int x, y;
    // 保存棋盘,0表示棋盘该点无棋子，1表示黑子，2表示白子
    int[][] allChess = new int[15][15];
    // 保存当前下子是黑子还是白字，true是黑子，false是白子
    Boolean isBlack = true;
    // 标识当前游戏是否结束
    Boolean canPlay = true;
    // 保存游戏信息
    String message = "黑方先行";
    // 保存棋谱
    int[] chessX = new int[255];
    int[] chessY = new int[255];
    int countX, countY;
    // 保存最大时间
    int maxTime = 0;
    // 游戏时间设置的信息
    String blackMessage = "无限制";
    String whiteMessage = "无限制";
    // 保存黑白方所剩余的时间
    int blackTime = 0;
    int whiteTime = 0;
    // 游戏倒计时线程
    Thread timer = new Thread(this);
 
    public FiveChessFrame() {
        this.setTitle("五子棋游戏");
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
            bgImage = ImageIO.read(new File("src/wzq/五子棋棋盘.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
 
        // 双缓冲
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
 
        g2.setFont(new Font("黑体", Font.BOLD, 20));
        g2.drawString("游戏信息：" + message, 50, 50);
        g2.drawRect(30, 440, 180, 40);
        g2.drawRect(250, 440, 180, 40);
        g2.setFont(new Font("宋体", 0, 12));
        g2.drawString("黑方时间：" + blackMessage, 40, 465);
        g2.drawString("白方时间：" + whiteMessage, 260, 465);
        // 重新开始按钮
        g2.drawRect(428, 66, 54, 20);
        g2.drawString("重新开始", 432, 80);
 
        // 游戏设置按钮
        g2.drawRect(428, 106, 54, 20);
        g2.drawString("游戏设置", 432, 120);
 
        // 游戏说明按钮
        g2.drawRect(428, 146, 54, 20);
        g2.drawString("游戏说明", 432, 160);
 
        // 退出游戏按钮
        g2.drawRect(428, 186, 54, 20);
        g2.drawString("退出游戏", 432, 200);
 
        // 悔棋
        g2.drawRect(428, 246, 54, 20);
        g2.drawString("悔棋", 442, 260);
 
        // 认输
        g2.drawRect(428, 286, 54, 20);
        g2.drawString("认输", 442, 300);
 
         
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                // 黑子
                if (allChess[i][j] == 1) {
                    int tempX = i * 25 + 55;
                    int tempY = j * 25 + 72;
                    g2.setColor(Color.BLACK);
                    g2.fillOval(tempX - 8, tempY - 8, 16, 16);
                }
                // 白子
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
                        message = "白方下子";
                    } else {
                        allChess[x][y] = 2;
                        isBlack = true;
                        message = "黑方下子";
                    }
                    this.repaint();
                    checkWin = isWin();
                    if (checkWin) {
                        if (allChess[x][y] == 1)
                            JOptionPane.showMessageDialog(this, "游戏结束，黑方胜利");
                        else
                            JOptionPane.showMessageDialog(this, "游戏结束，白方胜利");
                        canPlay = false;
                    }
                }
            }
        }
 
        // 重新开始游戏
        if (e.getX() >= 428 && e.getX() <= 482 && e.getY() >= 66 && e.getY() <= 86) {
            int result = JOptionPane.showConfirmDialog(this, "是否重新开始游戏？");
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
                message = "黑方先行";
                blackMessage = "无限制";
                whiteMessage = "无限制";
                blackTime = maxTime;
                whiteTime = maxTime;
                isBlack = true;
                canPlay = true;
                this.repaint();
            }
        }
        // 游戏倒计时设置
        if (e.getX() >= 428 && e.getX() <= 482 && e.getY() >= 106 && e.getY() <= 126) {
            String input = JOptionPane
                    .showInputDialog("请输入游戏的最大时间(单位:分钟),如果输入0,表示没有时间限制:");
            maxTime = Integer.parseInt(input) * 60;
            System.out.println(maxTime);
            if (maxTime < 0) {
                JOptionPane.showMessageDialog(this, "输入的游戏时间有误，请重新设置！");
            } else if (maxTime == 0) {
                int result = JOptionPane.showConfirmDialog(this,
                        "游戏时间设置成功，是否开始游戏？");
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
                    message = "黑方先行";
                    blackMessage = "无限制";
                    whiteMessage = "无限制";
                    blackTime = maxTime;
                    whiteTime = maxTime;
                    isBlack = true;
                    canPlay = true;
                    this.repaint();
                }
            } else if (maxTime > 0) {
                int result = JOptionPane.showConfirmDialog(this,
                        "游戏时间设置成功，是否重新开始游戏？");
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
                    message = "黑方先行";
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
        // 游戏说明
        if (e.getX() >= 428 && e.getX() <= 482 && e.getY() >= 146 && e.getY() <= 166) {
            JOptionPane.showMessageDialog(this, "简单一句：横竖斜先连成五子者获胜！");
        }
        // 退出游戏
        if (e.getX() >= 428 && e.getX() <= 482 && e.getY() >= 186 && e.getY() <= 206) {
            int result = JOptionPane.showConfirmDialog(this, "是否退出游戏？");
            if (result == 0) {
                System.exit(0);
            }
        }
        // 悔棋
        if (e.getX() >= 428 && e.getX() <= 482 && e.getY() >= 246 && e.getY() <= 266) {
            int result = JOptionPane.showConfirmDialog(this,
                    (isBlack == true ? "白方悔棋，黑方是否同意？" : "黑方悔棋，白方是否同意？"));
            if (result == 0) {
                allChess[chessX[--countX]][chessY[--countY]] = 0;
                isBlack = (isBlack == true) ? false : true;
                this.repaint();
            }
        }
        // 认输
        if (e.getX() >= 428 && e.getX() <= 482 && e.getY() >= 286 && e.getY() <= 306) {
            int result = JOptionPane.showConfirmDialog(this, "是否认输？");
            if (result == 0) {
                JOptionPane.showMessageDialog(this, "游戏结束，"
                        + (isBlack == true ? "黑方认输，白方获胜!" : "白方认输，黑方获胜!"));
            }
        }
    }
 
    @Override
    public void mouseReleased(MouseEvent arg0) {
 
    }
 
    private Boolean isWin() {
        boolean flag = false;
        // 保存共有相同颜色多少棋子相连
        int count = 1;
        // 判断横向是否有5个棋子相连，特点 纵坐标 是相同， 即allChess[x][y]中y值是相同
        int color = allChess[x][y];
        // 判断横向
        count = this.checkCount(1, 0, color);
        if (count >= 5) {
            flag = true;
        } else {
            // 判断纵向
            count = this.checkCount(0, 1, color);
            if (count >= 5) {
                flag = true;
            } else {
                // 判断右上、左下
                count = this.checkCount(1, -1, color);
                if (count >= 5) {
                    flag = true;
                } else {
                    // 判断右下、左上
                    count = this.checkCount(1, 1, color);
                    if (count >= 5) {
                        flag = true;
                    }
                }
            }
        }
 
        return flag;
    }
 
    // 判断棋子连接的数量
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
                        JOptionPane.showMessageDialog(this, "黑方超时,游戏结束!");
                    }
                } else {
                    whiteTime--;
                    if (whiteTime == 0) {
                        JOptionPane.showMessageDialog(this, "白方超时,游戏结束!");
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