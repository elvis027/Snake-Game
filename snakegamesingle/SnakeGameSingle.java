package snakegamesingle;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakeGameSingle {
		
		//UIFrame
		public JFrame f;
		public JPanel cardPane;
		public CardLayout card;
		public UIStartPane SP;
		public UIPlayPane PP;
		public UIEndPane EP;
		
		//Panel size
		public int PANEL_WIDTH = 1200;
		public int PANEL_HEIGHT = 800;
		
		//Listener
		public BtnListener btls;
		public KbListener kbls;
		
		public HitWallListener hwls;
		public HitSnakeListener hsls;
		public HoleListener hls;
		public EggListener els;
		public SnakeListener sls;
		
		//Timer
		public Timer hitWallTimer;
		public Timer hitSnakeTimer;
		public Timer holeTimer;
		public Timer eggTimer;
		public Timer snakeTimer;
		
		//UIStartPane
		public JButton btnPlay, btnSet, btnExit;
		
		//UIPlayPane
		public Font font;
		public JLabel P1Score, P2Score;
		public JLabel P1Life1, P1Life2, P1Life3, P2Life1, P2Life2, P2Life3;
		public JButton btnPause;
		
		public int scoreP1 = 0, scoreP2 = 0;
		public boolean isP1win;
		public Wall wall;
		public Hole hole;
		public Egg egg;
		public SnakeBody1 snakeP1 = null;
		public SnakeBody2 snakeP2 = null;
		
		public Random ran = new Random();
		public int SnakeVelocity = 300;
		public boolean isGoingIntoHoleP1 = false;
		public boolean isGoingIntoHoleP2 = false;
		public boolean isInHoleP1 = false;
		public boolean isInHoleP2 = false;
		
		public static void main(String[] args) {
			new SnakeGameSingle();
		}
		
		public SnakeGameSingle() {
			
			f = new JFrame();
			f.setTitle("Snake");
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			f.setContentPane(getCardPane());
			
			f.pack();
			f.setLocationRelativeTo(null);
			f.setVisible(true);
			
		}
		
		public JPanel getCardPane() {
			
			cardPane = new JPanel();
			card = new CardLayout();
			
			cardPane.setLayout(card);
			
			SP = new UIStartPane();
			
			cardPane.add(SP);
			
			return cardPane;
			
		}
		
		public class UIStartPane extends JPanel {
			
			UIStartPane() {
				
				setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
				setLayout(null);
				
				btls = new BtnListener();
				
				btnPlay = new JButton(new ImageIcon("SPlayBtn.png"));
				btnSet = new JButton(new ImageIcon("SSetBtn.png"));
				btnExit = new JButton(new ImageIcon("SExitBtn.png"));
		        
				btnPlay.setContentAreaFilled(false);
				btnPlay.setFocusPainted(false);
				btnPlay.setBorderPainted(false);
		        btnSet.setContentAreaFilled(false);
				btnSet.setFocusPainted(false);
				btnSet.setBorderPainted(false);
				btnExit.setContentAreaFilled(false);
				btnExit.setFocusPainted(false);
				btnExit.setBorderPainted(false);
		        
				btnPlay.setCursor(new Cursor(Cursor.HAND_CURSOR));
				btnSet.setCursor(new Cursor(Cursor.HAND_CURSOR));
				btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		        
				btnPlay.addActionListener(btls);
				btnSet.addActionListener(btls);
				btnExit.addActionListener(btls);
		        
				btnPlay.setBounds(this.getSize().width*5/12, this.getSize().height*4/8, this.getSize().width*2/12, this.getSize().height/10);
		        btnSet.setBounds(this.getSize().width*5/12, this.getSize().height*5/8, this.getSize().width*2/12, this.getSize().height/10);
		        btnExit.setBounds(this.getSize().width*5/12, this.getSize().height*6/8, this.getSize().width*2/12, this.getSize().height/10);
		        
		        add(btnPlay);
		        add(btnSet);
		        add(btnExit);
		        
			}
			
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				g.drawImage(new ImageIcon("SStartPane.png").getImage(), 0, 0, this.getSize().width, this.getSize().height, null);
				
				btnPlay.setBounds(this.getSize().width*5/12, this.getSize().height*4/8, this.getSize().width*2/12, this.getSize().height/10);
		        btnSet.setBounds(this.getSize().width*5/12, this.getSize().height*5/8, this.getSize().width*2/12, this.getSize().height/10);
		        btnExit.setBounds(this.getSize().width*5/12, this.getSize().height*6/8, this.getSize().width*2/12, this.getSize().height/10);
				
			}
			
		}
		
		public class UIPlayPane extends JPanel {
			
			UIPlayPane() {
				
				setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
				setLayout(null);
				
				kbls = new KbListener();
				
		        font = new Font(Font.DIALOG, Font.BOLD, 50);
		        
				//setScore
				P1Score = new JLabel();
				P1Score.setBounds(this.getSize().width*283/1200, this.getSize().height*23/800, this.getSize().width*230/1200, this.getSize().height*55/800);
				P1Score.setFont(font);
				P1Score.setHorizontalAlignment(JLabel.RIGHT);
				
				P2Score = new JLabel();
				P2Score.setBounds(this.getSize().width*588/1200, this.getSize().height*23/800, this.getSize().width*230/1200, this.getSize().height*55/800);
				P2Score.setFont(font);
				
				//setBtn
				btnPause = new JButton(new ImageIcon("SPauseBtn.png"));
				btnPause.setContentAreaFilled(false);
		        btnPause.setFocusPainted(false);
		        btnPause.setBorderPainted(false);
		        btnPause.setCursor(new Cursor(Cursor.HAND_CURSOR));
		        btnPause.addActionListener(btls);
		        btnPause.setBounds(this.getSize().width*1103/1200, this.getSize().height*6/800, this.getSize().width*91/1200, this.getSize().height*86/800);
		        
		        //addObj
				add(P1Score);
				add(P2Score);
				
		        add(btnPause);
				
		        addKeyListener(kbls);
		        
		        //Dynamic Object
		        wall = new Wall();
		        hole = new Hole();
		        egg = new Egg();
		        snakeP1 = new SnakeBody1();
		        snakeP2 = new SnakeBody2();
		        
			}
			
			public void connectListener() {
				
				hwls = new HitWallListener();
				hitWallTimer = new Timer(300, hwls);
				hitWallTimer.start();
				
				hsls = new HitSnakeListener();
				hitSnakeTimer = new Timer(300, hsls);
				hitSnakeTimer.start();
				
				hls = new HoleListener();
				holeTimer = new Timer(300, hls);
				holeTimer.start();
				
				els = new EggListener();
				eggTimer = new Timer(2000, els);
				eggTimer.start();
				
				sls = new SnakeListener();
				snakeTimer = new Timer(SnakeVelocity, sls);
				snakeTimer.start();
				
			}
			
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				g.drawImage(new ImageIcon("SPlayPane.png").getImage(), 0, 0, this.getSize().width, this.getSize().height, null);
				
		        P1Score.setBounds(this.getSize().width*283/1200, this.getSize().height*23/800, this.getSize().width*230/1200, this.getSize().height*55/800);
		        P2Score.setBounds(this.getSize().width*588/1200, this.getSize().height*23/800, this.getSize().width*230/1200, this.getSize().height*55/800);
		        btnPause.setBounds(this.getSize().width*1103/1200, this.getSize().height*6/800, this.getSize().width*91/1200, this.getSize().height*86/800);
				
				P1Score.setText(String.valueOf(scoreP1));
				P2Score.setText(String.valueOf(scoreP2));
				
				g.setColor(Color.BLACK);
				for(int i = 0; i < wall.wallPoints; i++) {
					g.fillRect(this.getSize().width*wall.wallx[i]/1200, this.getSize().height*wall.wally[i]/800, this.getSize().width*25/1200, this.getSize().height*25/800);
				}
				
				g.setColor(new Color(139, 69, 19));
				g.drawOval(this.getSize().width*hole.hole1x/1200, this.getSize().height*hole.hole1y/800, this.getSize().width*25/1200, this.getSize().height*25/800);
				g.drawOval(this.getSize().width*hole.hole2x/1200, this.getSize().height*hole.hole2y/800, this.getSize().width*25/1200, this.getSize().height*25/800);
				
				g.setColor(Color.YELLOW);
				if(egg.egg1x != -1 && egg.egg2x != -1) {
					g.fillOval(this.getSize().width*egg.egg1x/1200, this.getSize().height*egg.egg1y/800, this.getSize().width*25/1200, this.getSize().height*25/800);
					g.fillOval(this.getSize().width*egg.egg2x/1200, this.getSize().height*egg.egg2y/800, this.getSize().width*25/1200, this.getSize().height*25/800);
				}else if(egg.egg1x == -1 && egg.egg2x == -1) {
				}else if(egg.egg1x == -1) {
					g.fillOval(this.getSize().width*egg.egg2x/1200, this.getSize().height*egg.egg2y/800, this.getSize().width*25/1200, this.getSize().height*25/800);
				}else if(egg.egg2x == -1) {
					g.fillOval(this.getSize().width*egg.egg1x/1200, this.getSize().height*egg.egg1y/800, this.getSize().width*25/1200, this.getSize().height*25/800);
				}
				
				g.setColor(Color.RED);
				for(Body bdy : snakeP1.snakeBody) {
					if(bdy.isOutOfHole) {
						g.fillOval(this.getSize().width*bdy.x/1200, this.getSize().height*bdy.y/800, this.getSize().width*25/1200, this.getSize().height*25/800);
					}
				}
				
				g.setColor(Color.BLUE);
				for(Body bdy : snakeP2.snakeBody) {
					if(bdy.isOutOfHole) {
						g.fillOval(this.getSize().width*bdy.x/1200, this.getSize().height*bdy.y/800, this.getSize().width*25/1200, this.getSize().height*25/800);
					}
				}
				
				if(isInHoleP1) {
					g.drawImage(new ImageIcon("SinHoleP1.png").getImage(), this.getSize().width*300/1200, this.getSize().height*120/800, this.getSize().width*607/1200, this.getSize().height*83/800, null);
				}
				if(isInHoleP2) {
					g.drawImage(new ImageIcon("SinHoleP2.png").getImage(), this.getSize().width*300/1200, this.getSize().height*200/800, this.getSize().width*607/1200, this.getSize().height*83/800, null);
				}
				
			}
		}
		
		public class UIEndPane extends JPanel {
			
			UIEndPane() {
				
				setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
				setLayout(null);
				
				kbls = new KbListener();
				
		        font = new Font(Font.DIALOG, Font.BOLD, 50);
		        
				//setScore
				P1Score = new JLabel();
				P1Score.setBounds(this.getSize().width*283/1200, this.getSize().height*23/800, this.getSize().width*230/1200, this.getSize().height*55/800);
				P1Score.setFont(font);
				P1Score.setHorizontalAlignment(JLabel.RIGHT);
				
				P2Score = new JLabel();
				P2Score.setBounds(this.getSize().width*588/1200, this.getSize().height*23/800, this.getSize().width*230/1200, this.getSize().height*55/800);
				P2Score.setFont(font);
				
				//setBtn
				btnPause = new JButton(new ImageIcon("SPauseBtn.png"));
				btnPause.setContentAreaFilled(false);
		        btnPause.setFocusPainted(false);
		        btnPause.setBorderPainted(false);
		        btnPause.setCursor(new Cursor(Cursor.HAND_CURSOR));
		        btnPause.addActionListener(btls);
		        btnPause.setBounds(this.getSize().width*1103/1200, this.getSize().height*6/800, this.getSize().width*91/1200, this.getSize().height*86/800);

		        //addObj
				add(P1Score);
				add(P2Score);
				
		        add(btnPause);
				
		        
			}
			
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				g.drawImage(new ImageIcon("SPlayPane.png").getImage(), 0, 0, this.getSize().width, this.getSize().height, null);
				
		        P1Score.setBounds(this.getSize().width*283/1200, this.getSize().height*23/800, this.getSize().width*230/1200, this.getSize().height*55/800);
		        P2Score.setBounds(this.getSize().width*588/1200, this.getSize().height*23/800, this.getSize().width*230/1200, this.getSize().height*55/800);
		        btnPause.setBounds(this.getSize().width*1103/1200, this.getSize().height*6/800, this.getSize().width*91/1200, this.getSize().height*86/800);
				
				P1Score.setText(String.valueOf(scoreP1));
				P2Score.setText(String.valueOf(scoreP2));
				
				g.setColor(Color.BLACK);
				for(int i = 0; i < wall.wallPoints; i++) {
					g.fillRect(this.getSize().width*wall.wallx[i]/1200, this.getSize().height*wall.wally[i]/800, this.getSize().width*25/1200, this.getSize().height*25/800);
				}
				
				g.setColor(new Color(139, 69, 19));
				g.drawOval(this.getSize().width*hole.hole1x/1200, this.getSize().height*hole.hole1y/800, this.getSize().width*25/1200, this.getSize().height*25/800);
				g.drawOval(this.getSize().width*hole.hole2x/1200, this.getSize().height*hole.hole2y/800, this.getSize().width*25/1200, this.getSize().height*25/800);
				
				g.setColor(Color.YELLOW);
				if(egg.egg1x != -1 && egg.egg2x != -1) {
					g.fillOval(this.getSize().width*egg.egg1x/1200, this.getSize().height*egg.egg1y/800, this.getSize().width*25/1200, this.getSize().height*25/800);
					g.fillOval(this.getSize().width*egg.egg2x/1200, this.getSize().height*egg.egg2y/800, this.getSize().width*25/1200, this.getSize().height*25/800);
				}else if(egg.egg1x == -1 && egg.egg2x == -1) {
				}else if(egg.egg1x == -1) {
					g.fillOval(this.getSize().width*egg.egg2x/1200, this.getSize().height*egg.egg2y/800, this.getSize().width*25/1200, this.getSize().height*25/800);
				}else if(egg.egg2x == -1) {
					g.fillOval(this.getSize().width*egg.egg1x/1200, this.getSize().height*egg.egg1y/800, this.getSize().width*25/1200, this.getSize().height*25/800);
				}
				
				g.setColor(Color.RED);
				for(Body bdy : snakeP1.snakeBody) {
					if(bdy.isOutOfHole) {
						g.fillOval(this.getSize().width*bdy.x/1200, this.getSize().height*bdy.y/800, this.getSize().width*25/1200, this.getSize().height*25/800);
					}
				}
				
				g.setColor(Color.BLUE);
				for(Body bdy : snakeP2.snakeBody) {
					if(bdy.isOutOfHole) {
						g.fillOval(this.getSize().width*bdy.x/1200, this.getSize().height*bdy.y/800, this.getSize().width*25/1200, this.getSize().height*25/800);
					}
				}
				
				if(isP1win) {
		        	g.drawImage(new ImageIcon("Swin.png").getImage(), this.getSize().width*20/1200, this.getSize().height*40/800, this.getSize().width*100/1200, this.getSize().height*30/800, null);
		        	g.drawImage(new ImageIcon("Slose.png").getImage(), this.getSize().width*990/1200, this.getSize().height*40/800, this.getSize().width*100/1200, this.getSize().height*30/800, null);
		        }else {
		        	g.drawImage(new ImageIcon("SWin.png").getImage(), this.getSize().width*990/1200, this.getSize().height*40/800, this.getSize().width*100/1200, this.getSize().height*30/800, null);
		        	g.drawImage(new ImageIcon("SLose.png").getImage(), this.getSize().width*20/1200, this.getSize().height*40/800, this.getSize().width*100/1200, this.getSize().height*30/800, null);
		        }
				
			}
			
		}
		
		public class BtnListener implements ActionListener {
			
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnPlay) {
					
					PP = new UIPlayPane();
					cardPane.add(PP);
					card.next(cardPane);
					PP.connectListener();
					PP.setFocusable(true);
					PP.requestFocus();
					
				}else if(e.getSource() == btnSet) {
					String input=JOptionPane.showInputDialog("Please enter the speed of snake(1 ~ 10)");
					SnakeVelocity = 1000 / Integer.parseInt(input);
				}else if(e.getSource() == btnExit) {
					System.exit(0);
				}else if(e.getSource() == btnPause) {
					hitWallTimer.stop();
					hitSnakeTimer.stop();
					holeTimer.stop();
					eggTimer.stop();
					snakeTimer.stop();
					
				    int mType=JOptionPane.INFORMATION_MESSAGE;
				    int oType=JOptionPane.YES_NO_CANCEL_OPTION;
				    String[] options={"Continue","Setting","Cancel"};
				    int opt=JOptionPane.showOptionDialog(new JFrame(),"Pause",
				      "Pause",oType,mType,null,options,"Continue");
				    if(opt==JOptionPane.YES_OPTION){
				    	hitWallTimer.start();
						hitSnakeTimer.start();
						holeTimer.start();
						eggTimer.start();
						snakeTimer.start();
				    }
				    if(opt==JOptionPane.NO_OPTION){
				    	String input=JOptionPane.showInputDialog("Please enter the speed of snake(1 ~ 10)");
						SnakeVelocity = 1000 / Integer.parseInt(input);
				    } 
				    if(opt==JOptionPane.CANCEL_OPTION){
				    	hitWallTimer.start();
						hitSnakeTimer.start();
						holeTimer.start();
						eggTimer.start();
						snakeTimer.start();
				    }
				    
				}
			}
			
		}
		
		public class KbListener extends KeyAdapter {
			
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				
				switch(key) {
				case KeyEvent.VK_W:
					if(snakeP1.direction_x != 0 && snakeP1.direction_y != -25) {
						snakeP1.direction_x = 0;
						snakeP1.direction_y = -25;
					}
					break;
				case KeyEvent.VK_S:
					if(snakeP1.direction_x != 0 && snakeP1.direction_y != 25) {
						snakeP1.direction_x = 0;
						snakeP1.direction_y = 25;
					}
					break;
				case KeyEvent.VK_A:
					if(snakeP1.direction_x != -25 && snakeP1.direction_y != 0) {
						snakeP1.direction_x = -25;
						snakeP1.direction_y = 0;
					}
					break;
				case KeyEvent.VK_D:
					if(snakeP1.direction_x != 25 && snakeP1.direction_y != 0) {
						snakeP1.direction_x = 25;
						snakeP1.direction_y = 0;
					}
					break;
					
				case KeyEvent.VK_UP:
					if(snakeP2.direction_x != 0 && snakeP2.direction_y != -25) {
						snakeP2.direction_x = 0;
						snakeP2.direction_y = -25;
					}
					break;
				case KeyEvent.VK_DOWN:
					if(snakeP2.direction_x != 0 && snakeP2.direction_y != 25) {
						snakeP2.direction_x = 0;
						snakeP2.direction_y = 25;
					}
					break;
				case KeyEvent.VK_LEFT:
					if(snakeP2.direction_x != -25 && snakeP2.direction_y != 0) {
						snakeP2.direction_x = -25;
						snakeP2.direction_y = 0;
					}
					break;
				case KeyEvent.VK_RIGHT:
					if(snakeP2.direction_x != 25 && snakeP2.direction_y != 0) {
						snakeP2.direction_x = 25;
						snakeP2.direction_y = 0;
					}
					break;
				default:
					break;
				}
				
			}
			
		}
				
		public class Wall {
			
			public int[] wallx, wally;
			public int wallPoints;
			public boolean isDraw = false;
			
			public Wall() {
				
				int wallNum = (int) (Math.random() * 3 + 1);
				
				switch(wallNum) {
				case 1:
					wall1();
					break;
				case 2:
					wall2();
					break;
				case 3:
					wall3();
					break;
				default:
					break;
					
				}
				
			}
			
			public void wall1() {            //°Ñ¼Æ¤Æ
				wallx = new int[] {16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26,
						32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 31, 30, 29, 28, 27, 26, 25, 24};
				wally = new int[] {27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
						0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 18, 18, 18, 18, 18, 18, 18, 18};
				wallPoints = 56;
				for(int i = 0; i < wallPoints; i++) {
					wallx[i] = wallx[i] * 25 + 12;
					wally[i] = wally[i] * 25 + 109;
				}
			}
			
			public void wall2() {
				wallx = new int[] {12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,
						36, 36, 36, 36, 36, 36, 36, 36, 36, 35, 34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24};
				wally = new int[] {14, 13, 12, 11, 10, 9, 8, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
						10, 11, 12, 13, 14, 15, 16, 17, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18};
				wallPoints = 42;
				for(int i = 0; i < wallPoints; i++) {
					wallx[i] = wallx[i] * 25 + 12;
					wally[i] = wally[i] * 25 + 109;
				}
			}
			
			public void wall3() {
				wallx = new int[] {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26,
						20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36};
				wally = new int[] {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
						21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21};
				wallPoints = 34;
				for(int i = 0; i < wallPoints; i++) {
					wallx[i] = wallx[i] * 25 + 12;
					wally[i] = wally[i] * 25 + 109;
				}
			}
			
		}
		
		public class Hole {
			
			public int hole1x, hole1y;
			public int hole2x, hole2y;
			public boolean isDraw = false;
			
			public Hole() {
				
				do {
					hole1x = (ran.nextInt(46) + 1) * 25 + 12;
					hole1y = (ran.nextInt(26) + 1) * 25 + 109;
				}while(!judge1());
				
				do {
					hole2x = (ran.nextInt(46) + 1) * 25 + 12;
					hole2y = (ran.nextInt(26) + 1) * 25 + 109;
				}while(!judge2());
				
			}
			
			boolean judge1() {
				
				for(int i = 0; i < wall.wallPoints; i++) {
					if(hole1x == wall.wallx[i] && hole1y == wall.wally[i]) return false;
				}
				return true;
				
			}
			
			boolean judge2() {
				
				if(hole2x == hole1x && hole2y == hole1y) return false;
				
				for(int i = 0; i < wall.wallPoints; i++) {
					if(hole2x == wall.wallx[i] && hole2y == wall.wally[i]) return false;
				}
				return true;
				
			}
			
		}
		
		public class Egg {
			
			public int egg1x, egg1y;
			public int egg2x, egg2y;
			
			public Egg() {
				
				do {
					egg1x = ((int) (Math.random() * 47)) * 25 + 12;
					egg1y = ((int) (Math.random() * 27)) * 25 + 109;
				}while(!judge1());
				
				do {
					egg2x = ((int) (Math.random() * 47)) * 25 + 12;
					egg2y = ((int) (Math.random() * 27)) * 25 + 109;
				}while(!judge2());
				
			}
			
			boolean judge1() {
				
				if(egg1x == hole.hole1x && egg1y == hole.hole1y) return false;
				if(egg1x == hole.hole2x && egg1y == hole.hole2y) return false;
				
				for(int i = 0; i < wall.wallPoints; i++) {
					if(egg1x == wall.wallx[i] && egg1y == wall.wally[i]) return false;
				}
				
				if(snakeP1 != null) {
					for(Body bdy : snakeP1.snakeBody) {
						if(egg1x == bdy.x && egg1y == bdy.y) {
							return false;
						}
					}
				}
				
				if(snakeP2 != null) {
					for(Body bdy : snakeP2.snakeBody) {
						if(egg1x == bdy.x && egg1y == bdy.y) {
							return false;
						}
					}
				}
				
				return true;
				
			}
			
			boolean judge2() {
				if(egg2x == egg1x && egg2y == egg1y) return false;
				if(egg2x == hole.hole1x && egg2y == hole.hole1y) return false;
				if(egg2x == hole.hole2x && egg2y == hole.hole2y) return false;
				
				for(int i = 0; i < wall.wallPoints; i++) {
					if(egg2x == wall.wallx[i] && egg2y == wall.wally[i]) return false;
				}
				
				if(snakeP1 != null) {
					for(Body bdy : snakeP1.snakeBody) {
						if(egg2x == bdy.x && egg2y == bdy.y) {
							return false;
						}
					}
				}
				
				if(snakeP2 != null) {
					for(Body bdy : snakeP2.snakeBody) {
						if(egg2x == bdy.x && egg2y == bdy.y) {
							return false;
						}
					}
				}
				
				return true;
				
			}
			
		}
		
		public class SnakeBody1 {
			
			public List<Body> snakeBody;
			
			public int direction_x;
			public int direction_y;
			/*
			 * direction_x = 25 mean east
			 * direction_x = -25 mean west
			 * direction_y = 25 mean south
			 * direction_y = -25 mean north
			 * direction_x or y = 0 mean static in that direction
			 */
			
			public SnakeBody1() {
				
				snakeBody = new ArrayList<Body>();
				
				do {
					direction_x = (ran.nextInt(2) - 1) * 25;
					direction_y = (ran.nextInt(2) - 1) * 25;
				} while((direction_x == 0 && direction_y == 0) || (direction_x == 25 && direction_y == 25) || (direction_x == -25 && direction_y == -25));
				
				Body body = new Body(hole.hole1x + direction_x, hole.hole1y + direction_y);
				body.isOutOfHole=true;
				snakeBody.add(body);
				snakeBody.add(new Body(hole.hole1x, hole.hole1y));
				
			}
			
		}
		
		public class SnakeBody2 {
			
			public List<Body> snakeBody;
			
			public int direction_x;
			public int direction_y;
			/*
			 * direction_x = 25 mean east
			 * direction_x = -25 mean west
			 * direction_y = 25 mean south
			 * direction_y = -25 mean north
			 * direction_x or y = 0 mean static in that direction
			 */
			
			public SnakeBody2() {
				
				snakeBody = new ArrayList<Body>();
				
				do {
					direction_x = (ran.nextInt(2) - 1) * 25;
					direction_y = (ran.nextInt(2) - 1) * 25;
				} while((direction_x == 0 && direction_y == 0) || (direction_x == 25 && direction_y == 25) || (direction_x == -25 && direction_y == -25));
				
				Body body = new Body(hole.hole2x + direction_x, hole.hole2y + direction_y);
				body.isOutOfHole=true;
				snakeBody.add(body);
				snakeBody.add(new Body(hole.hole2x, hole.hole2y));
				
			}
			
		}
		
		public class Body {
			public int x;
			public int y;
			public boolean isOutOfHole = false;
			
			public Body(int x, int y) {
				this.x = x;
				this.y = y;
			}
			
		}
		
		public class HitWallListener implements ActionListener {    //hit the wall
			
			public void actionPerformed(ActionEvent e) {
				
				for(int i = 0; i < wall.wallPoints; i++) {
					if(snakeP1.snakeBody.get(0).x == wall.wallx[i] && snakeP1.snakeBody.get(0).y == wall.wally[i] && !isGoingIntoHoleP1) {
				        isP1win = false;
				        
				        hitWallTimer.stop();
						hitSnakeTimer.stop();
						holeTimer.stop();
						eggTimer.stop();
						snakeTimer.stop();
						
						EP = new UIEndPane();
						cardPane.add(EP);
						card.next(cardPane);
						break;
					}else if(snakeP2.snakeBody.get(0).x == wall.wallx[i] && snakeP2.snakeBody.get(0).y == wall.wally[i] && !isGoingIntoHoleP2) {
				        isP1win = true;
				        
				        hitWallTimer.stop();
						hitSnakeTimer.stop();
						holeTimer.stop();
						eggTimer.stop();
						snakeTimer.stop();
						
						EP = new UIEndPane();
						cardPane.add(EP);
						card.next(cardPane);
						break;
						
					}
				}
				
			}
			
		}
		
		public class HitSnakeListener implements ActionListener {    //hit the snake
			
			public void actionPerformed(ActionEvent e) {
				
				for(int i = 1; i < snakeP1.snakeBody.size(); i++) {
					if(snakeP1.snakeBody.get(i).x == snakeP1.snakeBody.get(0).x && snakeP1.snakeBody.get(i).y == snakeP1.snakeBody.get(0).y && !isGoingIntoHoleP1) {
				        isP1win = false;
				        
				        hitWallTimer.stop();
						hitSnakeTimer.stop();
						holeTimer.stop();
						eggTimer.stop();
						snakeTimer.stop();
						
						EP = new UIEndPane();
						cardPane.add(EP);
						card.next(cardPane);
						break;
					}
				}
				
				for(int i = 0; i < snakeP2.snakeBody.size(); i++) {
					if(snakeP2.snakeBody.get(i).x == snakeP1.snakeBody.get(0).x && snakeP2.snakeBody.get(i).y == snakeP1.snakeBody.get(0).y && !isGoingIntoHoleP1) {
				        isP1win = false;
				        
				        hitWallTimer.stop();
						hitSnakeTimer.stop();
						holeTimer.stop();
						eggTimer.stop();
						snakeTimer.stop();
						
						EP = new UIEndPane();
						cardPane.add(EP);
						card.next(cardPane);
						break;
					}
				}
				
				for(int i = 1; i < snakeP2.snakeBody.size(); i++) {
					if(snakeP2.snakeBody.get(i).x == snakeP2.snakeBody.get(0).x && snakeP2.snakeBody.get(i).y == snakeP2.snakeBody.get(0).y && !isGoingIntoHoleP2) {
				        isP1win = true;
				        
				        hitWallTimer.stop();
						hitSnakeTimer.stop();
						holeTimer.stop();
						eggTimer.stop();
						snakeTimer.stop();
						
						EP = new UIEndPane();
						cardPane.add(EP);
						card.next(cardPane);
						break;
					}
				}
				
				for(int i = 0; i < snakeP1.snakeBody.size(); i++) {
					if(snakeP1.snakeBody.get(i).x == snakeP2.snakeBody.get(0).x && snakeP1.snakeBody.get(i).y == snakeP2.snakeBody.get(0).y && !isGoingIntoHoleP2) {
				        isP1win = true;
				        
				        hitWallTimer.stop();
						hitSnakeTimer.stop();
						holeTimer.stop();
						eggTimer.stop();
						snakeTimer.stop();
						
						EP = new UIEndPane();
						cardPane.add(EP);
						card.next(cardPane);
						break;
					}
				}
				
			}
			
		}
		
		public class HoleListener implements ActionListener {   //go in and come out the hole
			
			public void actionPerformed(ActionEvent e) {
				
				if(!isGoingIntoHoleP1) {
					if(snakeP1.snakeBody.get(0).x == hole.hole1x && snakeP1.snakeBody.get(0).y == hole.hole1y) {
						if(!snakeP1.snakeBody.get(0).isOutOfHole) {    //snakeP1 come out the hole1
							synchronized(snakeP1.snakeBody) {
								java.util.Timer timer = new java.util.Timer();
								
						        timer.schedule(new TimerTask(){
						            public void run() {
						            	isInHoleP1 = false;
						            	PP.repaint();
						            	Body body = new Body(snakeP1.snakeBody.get(0).x + snakeP1.direction_x, snakeP1.snakeBody.get(0).y + snakeP1.direction_y);
										body.isOutOfHole = true;
										snakeP1.snakeBody.add(0, judge(body));
										snakeP1.snakeBody.remove(snakeP1.snakeBody.size() - 1);
										PP.repaint();
						            }},2000);
							}
						}else if(snakeP1.snakeBody.get(0).isOutOfHole) {    //snakeP1 go into the hole1
							isGoingIntoHoleP1 = true;
						}
					}
				}else if(snakeP1.snakeBody.get(snakeP1.snakeBody.size() - 1).x == hole.hole1x &&
						snakeP1.snakeBody.get(snakeP1.snakeBody.size() - 1).y == hole.hole1y) {    //snakeP1 go into the hole1
					synchronized(snakeP1.snakeBody) {
						isInHoleP1 = true;
						isGoingIntoHoleP1 = false;
						snakeP1.snakeBody.get(0).x = hole.hole2x;
						snakeP1.snakeBody.get(0).y = hole.hole2y;
						PP.repaint();
					}
				}
				
				if(!isGoingIntoHoleP1) {
					if(snakeP1.snakeBody.get(0).x == hole.hole2x && snakeP1.snakeBody.get(0).y == hole.hole2y) {
						if(!snakeP1.snakeBody.get(0).isOutOfHole) {    //snakeP1 come out the hole2
							synchronized(snakeP1.snakeBody) {
								java.util.Timer timer = new java.util.Timer();
								
						        timer.schedule(new TimerTask(){
						            public void run() {
						            	isInHoleP1 = false;
						            	PP.repaint();
						            	Body body = new Body(snakeP1.snakeBody.get(0).x + snakeP1.direction_x, snakeP1.snakeBody.get(0).y + snakeP1.direction_y);
										body.isOutOfHole = true;
										snakeP1.snakeBody.add(0, judge(body));
										snakeP1.snakeBody.remove(snakeP1.snakeBody.size() - 1);
										PP.repaint();
						            }},2000);
							}
						}else if(snakeP1.snakeBody.get(0).isOutOfHole) {    //snakeP1 go into the hole2
							isGoingIntoHoleP1 = true;
						}
					}
				}else if(snakeP1.snakeBody.get(snakeP1.snakeBody.size() - 1).x == hole.hole2x &&
						snakeP1.snakeBody.get(snakeP1.snakeBody.size() - 1).y == hole.hole2y) {    //snakeP1 go into the hole2
					synchronized(snakeP1.snakeBody) {
						isInHoleP1 = true;
						isGoingIntoHoleP1 = false;
						snakeP1.snakeBody.get(0).x = hole.hole1x;
						snakeP1.snakeBody.get(0).y = hole.hole1y;
						PP.repaint();
					}
				}
				
				if(!isGoingIntoHoleP2) {
					if(snakeP2.snakeBody.get(0).x == hole.hole1x && snakeP2.snakeBody.get(0).y == hole.hole1y) {
						if(!snakeP2.snakeBody.get(0).isOutOfHole) {    //snakeP2 come out the hole1
							synchronized(snakeP2.snakeBody) {
								java.util.Timer timer = new java.util.Timer();
								
						        timer.schedule(new TimerTask(){
						            public void run() {
						            	isInHoleP2 = false;
						            	PP.repaint();
						            	Body body = new Body(snakeP2.snakeBody.get(0).x + snakeP2.direction_x, snakeP2.snakeBody.get(0).y + snakeP2.direction_y);
										body.isOutOfHole = true;
										snakeP2.snakeBody.add(0, judge(body));
										snakeP2.snakeBody.remove(snakeP2.snakeBody.size() - 1);
										PP.repaint();
						            }},2000);
							}
						}else if(snakeP2.snakeBody.get(0).isOutOfHole) {    //snakeP2 go into the hole1
							isGoingIntoHoleP2 = true;
						}
					}
				}else if(snakeP2.snakeBody.get(snakeP2.snakeBody.size() - 1).x == hole.hole1x &&
						snakeP2.snakeBody.get(snakeP2.snakeBody.size() - 1).y == hole.hole1y) {    //snakeP2 go into the hole1
					synchronized(snakeP2.snakeBody) {
						isInHoleP2 = true;
						isGoingIntoHoleP2 = false;
						snakeP2.snakeBody.get(0).x = hole.hole2x;
						snakeP2.snakeBody.get(0).y = hole.hole2y;
						PP.repaint();
					}
				}
				
				if(!isGoingIntoHoleP2) {
					if(snakeP2.snakeBody.get(0).x == hole.hole2x && snakeP2.snakeBody.get(0).y == hole.hole2y) {
						if(!snakeP2.snakeBody.get(0).isOutOfHole) {    //snakeP2 come out the hole2
							synchronized(snakeP2.snakeBody) {
								java.util.Timer timer = new java.util.Timer();
								
						        timer.schedule(new TimerTask(){
						            public void run() {
						            	isInHoleP2 = false;
						            	PP.repaint();
						            	Body body = new Body(snakeP2.snakeBody.get(0).x + snakeP2.direction_x, snakeP2.snakeBody.get(0).y + snakeP2.direction_y);
										body.isOutOfHole = true;
										snakeP2.snakeBody.add(0, judge(body));
										snakeP2.snakeBody.remove(snakeP2.snakeBody.size() - 1);
										PP.repaint();
						            }},2000);
							}
						}else if(snakeP2.snakeBody.get(0).isOutOfHole) {    //snakeP2 go into the hole2
							isGoingIntoHoleP2 = true;
						}
					}
				}else if(snakeP2.snakeBody.get(snakeP2.snakeBody.size() - 1).x == hole.hole2x &&
						snakeP2.snakeBody.get(snakeP2.snakeBody.size() - 1).y == hole.hole2y) {    //snakeP2 go into the hole2
					synchronized(snakeP2.snakeBody) {
						isInHoleP2 = true;
						isGoingIntoHoleP2 = false;
						snakeP2.snakeBody.get(0).x = hole.hole1x;
						snakeP2.snakeBody.get(0).y = hole.hole1y;
						PP.repaint();
					}
				}
				
			}
			
			Body judge(Body body) {
				if(body.x == 47*25+12) body.x = 0*25+12;
				if(body.x == (-1)*25+12) body.x = 46*25+12;
				
				if(body.y == 27*25+109) body.y = 0*25+109;
				if(body.y == (-1)*25+109) body.y = 26*25+109;
				return body;
			}
			
		}
		
		public class EggListener implements ActionListener {
			
			public void actionPerformed(ActionEvent e) {
				
				if(egg.egg1x == -1 && egg.egg2x == -1) {
					
					java.util.Timer timer = new java.util.Timer();
					
			        timer.schedule(new TimerTask(){
			            public void run() {
			            	egg = new Egg();
			            }},2000);
					
				}
				
			}
			
		}
		
		public class SnakeListener implements ActionListener {
			
			public void actionPerformed(ActionEvent e) {
				
				if(isGoingIntoHoleP1) {
					synchronized(snakeP1.snakeBody) {
						Body body = new Body(snakeP1.snakeBody.get(0).x + snakeP1.direction_x, snakeP1.snakeBody.get(0).y + snakeP1.direction_y);
						body.isOutOfHole = false;
						snakeP1.snakeBody.add(0, body);
						snakeP1.snakeBody.remove(snakeP1.snakeBody.size() - 1);
						PP.repaint();
					}
				}else if(snakeP1.snakeBody.get(0).isOutOfHole) {
					if(snakeP1.snakeBody.get(0).x + snakeP1.direction_x == egg.egg1x && snakeP1.snakeBody.get(0).y +snakeP1.direction_y == egg.egg1y) {
						synchronized(snakeP1.snakeBody) {
							Body body = new Body(snakeP1.snakeBody.get(0).x + snakeP1.direction_x, snakeP1.snakeBody.get(0).y + snakeP1.direction_y);
							body.isOutOfHole = true;
							snakeP1.snakeBody.add(0, judge(body));
							
							egg.egg1x = -1;
							scoreP1++;
							PP.repaint();
						}
					}else if(snakeP1.snakeBody.get(0).x + snakeP1.direction_x == egg.egg2x && snakeP1.snakeBody.get(0).y + snakeP1.direction_y == egg.egg2y) {
						synchronized(snakeP1.snakeBody) {
							Body body = new Body(snakeP1.snakeBody.get(0).x + snakeP1.direction_x, snakeP1.snakeBody.get(0).y + snakeP1.direction_y);
							body.isOutOfHole = true;
							snakeP1.snakeBody.add(0, judge(body));
							
							egg.egg2x = -1;
							scoreP1++;
							PP.repaint();
						}
					}else {
						synchronized(snakeP1.snakeBody) {
							Body body = new Body(snakeP1.snakeBody.get(0).x + snakeP1.direction_x, snakeP1.snakeBody.get(0).y + snakeP1.direction_y);
							body.isOutOfHole = true;
							snakeP1.snakeBody.add(0, judge(body));
							snakeP1.snakeBody.remove(snakeP1.snakeBody.size() - 1);
							PP.repaint();
						}
					}
				}
				
				if(isGoingIntoHoleP2) {
					synchronized(snakeP2.snakeBody) {
						Body body = new Body(snakeP2.snakeBody.get(0).x + snakeP2.direction_x, snakeP2.snakeBody.get(0).y + snakeP2.direction_y);
						body.isOutOfHole = false;
						snakeP2.snakeBody.add(0, body);
						snakeP2.snakeBody.remove(snakeP2.snakeBody.size() - 1);
						PP.repaint();
					}
				}else if(snakeP2.snakeBody.get(0).isOutOfHole) {
					if(snakeP2.snakeBody.get(0).x + snakeP2.direction_x == egg.egg1x && snakeP2.snakeBody.get(0).y + snakeP2.direction_y == egg.egg1y) {
						synchronized(snakeP2.snakeBody) {
							Body body = new Body(snakeP2.snakeBody.get(0).x + snakeP2.direction_x, snakeP2.snakeBody.get(0).y + snakeP2.direction_y);
							body.isOutOfHole = true;
							snakeP2.snakeBody.add(0, judge(body));
							
							egg.egg1x = -1;
							scoreP2++;
							PP.repaint();
						}
					}else if(snakeP2.snakeBody.get(0).x + snakeP2.direction_x == egg.egg2x && snakeP2.snakeBody.get(0).y + snakeP2.direction_y == egg.egg2y) {
						synchronized(snakeP2.snakeBody) {
							Body body = new Body(snakeP2.snakeBody.get(0).x + snakeP2.direction_x, snakeP2.snakeBody.get(0).y + snakeP2.direction_y);
							body.isOutOfHole = true;
							snakeP2.snakeBody.add(0, judge(body));
							
							egg.egg2x = -1;
							scoreP2++;
							PP.repaint();
						}
					}else {
						synchronized(snakeP2.snakeBody) {
							Body body = new Body(snakeP2.snakeBody.get(0).x + snakeP2.direction_x, snakeP2.snakeBody.get(0).y + snakeP2.direction_y);
							body.isOutOfHole = true;
							snakeP2.snakeBody.add(0, judge(body));
							snakeP2.snakeBody.remove(snakeP2.snakeBody.size() - 1);
							PP.repaint();
						}
					}
				}
				
			}
			
			Body judge(Body body) {
				if(body.x == 47*25+12) body.x = 0*25+12;
				if(body.x == (-1)*25+12) body.x = 46*25+12;
				
				if(body.y == 27*25+109) body.y = 0*25+109;
				if(body.y == (-1)*25+109) body.y = 26*25+109;
				return body;
			}
			
		}
		
}
