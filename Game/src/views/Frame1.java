package views;

import engine.Game;
import engine.Player;
import exceptions.*;
import model.abilities.AreaOfEffect;
import model.world.Champion;
import model.world.Cover;
import model.world.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class Frame1 extends JFrame implements ActionListener{
	JButton button1, confirmP1, confirmP2,Confirm,Start,resume;
	JButton move,attack,cast,ULability,endturn,namewin;
	JLabel p1Select,p2Select,CurrentP,p1Leader,p2Leader,CurrentPLeader,test,backl;
	JTextField P1name; JTextField P2name;
	JPanel panel1, panel2, panel3, panel4, panel5, panel6, panel7,panel8,panel9,panel10,panelB,panelB1,panelB2,panelB3,panelB4,panelB5,panelBB;
	JPanel panelP,panelwin;
	Player Player1;Player Player2;
	Game game,game1;
	boolean flag,flagL1,flagL2,CastS;
	JLayeredPane Board,welcome;
	int count =0;
	public Frame1(){
		//welcome
		ImageIcon back=new ImageIcon("marvel.jpg");
		backl=new JLabel();
		backl.setBounds(0,0,1600,1000);
		backl.setIcon(back);
		welcome=new JLayeredPane();
		welcome.setBounds(0, 0, 1600, 880);	
		welcome.setBackground(new Color(0,0,0));
		welcome.setVisible(true);
		welcome.add(backl);
		
		//panel 1
		ImageIcon Background= new ImageIcon("Background.jpg");
		
		button1=new JButton();
		button1.setBounds(0, 0, 1600, 880);	
		button1.setText("Click To Start");
		button1.setFont(new Font("MV Boli", Font.PLAIN, 25));
		button1.setHorizontalTextPosition(JButton.CENTER);
		button1.setVerticalTextPosition(JButton.BOTTOM);
		button1.setBackground(new Color(0,0,0));
		button1.setForeground(new Color(123,106,174));
		button1.addActionListener(this);
		button1.setFocusable(false);
		button1.setBorderPainted(false);
		
		panel1=new JPanel();
		panel1.setBounds(0, 0, 1600, 880);	
		panel1.setBackground(new Color(0,0,0));
		panel1.setOpaque(true);
		panel1.setVisible(true);
		panel1.add(button1);
		
		this.setLayout(null);
		this.setTitle("Marvel Wars");
		this.setCursor(new Cursor(1));
		this.setIconImage(Background.getImage());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(1600,880);
		this.setVisible(true);
		welcome.add(panel1);
		this.add(welcome);
		//panel 2
		P1name = new JTextField();
		P2name = new JTextField();
		P1name.setBounds(100, 200, 300, 75);
		P2name.setBounds(1200, 200, 300, 75);
		P1name.setFont(new Font("MV Boli", Font.PLAIN, 30));
		P2name.setFont(new Font("MV Boli", Font.PLAIN, 30));
		P1name.setForeground(new Color(0,0,255));
		P2name.setForeground(new Color(255,0,0));
		
		confirmP1=new JButton(imageGet("confirm1"));
		confirmP1.setBackground(new Color(0,0,0));
		confirmP1.setBorderPainted(false);
		confirmP2=new JButton(imageGet("confirm2"));
		confirmP2.setBackground(new Color(0,0,0));
		confirmP2.setBorderPainted(false);
		confirmP1.setFocusable(false);
		confirmP2.setFocusable(false);
		confirmP1.setBounds(100, 300, 300, 100);
		confirmP2.setBounds(1200, 300, 300, 100);
		confirmP1.addActionListener(this);
		confirmP2.addActionListener(this);
		
		panel2=new JPanel();
		panel2.setLayout(null);
		panel2.setBounds(0, 0, 1600, 880);	
		panel2.setBackground(new Color(0,0,0,0));
		panel2.add(P1name);
		panel2.add(P2name);
		panel2.add(confirmP1);
		panel2.add(confirmP2);

		//panel 3
		Confirm=new JButton();
		Confirm.addActionListener(this);
		Confirm.setBounds(0, 0, 1000, 880);
		
		CurrentP=new JLabel();
		CurrentP.setPreferredSize(new Dimension(1600,80));
		
		panel3=new JPanel();
		panel3.setLayout(new BorderLayout());
		panel3.setBounds(0, 0, 1600, 880);	
		panel3.setBackground(new Color(123,106,174));
		
		//panel 4 part of panel 3 
		panel4=new JPanel();
		panel4.setLayout(new GridLayout(3,5,5,5));	
		panel4.setBounds(0, 0, 1000, 880);	
		panel4.setBackground(new Color(123,106,174));
		panel3.add(panel4,BorderLayout.CENTER);
		
		//panel 5 part of panel 3
		p1Select=new JLabel();
		p1Select.setPreferredSize(new Dimension(300,200));
		
		panel5=new JPanel();
		panel5.setLayout(new GridLayout(4,1));
		panel5.setBounds(0, 0, 300, 880);	
		panel5.setBackground(new Color(123,106,174));
		panel5.add(p1Select,3,0);
		panel3.add(panel5,BorderLayout.WEST);
		
		//panel 6 part of panel 3
		p2Select=new JLabel();
		p2Select.setPreferredSize(new Dimension(300,200));
		
		panel6=new JPanel();
		panel6.setLayout(new GridLayout(4,1));
		panel6.setBounds(0, 0, 300, 880);	
		panel6.setBackground(new Color(123,106,174));
		panel6.add(p2Select,0,0);
		panel3.add(panel6,BorderLayout.EAST);
		
		//panel 7 
		CurrentPLeader=new JLabel();
		CurrentPLeader.setPreferredSize(new Dimension(1600,80));
		
		Start=new JButton();
		Start.addActionListener(this);
		
		panel7=new JPanel();
		panel7.setLayout(new BorderLayout());
		panel7.setBounds(0, 0, 1600, 880);	
		panel7.setBackground(new Color(123,106,174));
		//panel7.add(CurrentPLeader,BorderLayout.NORTH);
		
		//panel 8 part of panel 7
		p1Leader=new JLabel();
		p1Leader.setPreferredSize(new Dimension(300,200));
		
		panel8=new JPanel();
		panel8.setLayout(new GridLayout(2,1));
		panel8.setBounds(0, 0, 300, 880);	
		panel8.setBackground(new Color(123,106,174));
		panel8.add(p1Leader,2,0);
		panel7.add(panel8,BorderLayout.WEST);
		
		//panel 9 part of panel 7
				p2Leader=new JLabel();
				p2Leader.setPreferredSize(new Dimension(300,200));
				
				panel9=new JPanel();
				panel9.setLayout(new GridLayout(2,1));
				panel9.setBounds(0, 0, 300, 880);	
				panel9.setBackground(new Color(123,106,174));
				panel9.add(p2Leader,2,0);
				panel7.add(panel9,BorderLayout.EAST);		
				
		//panel 10 part of panel 7		
				panel10=new JPanel();
				panel10.setLayout(new GridLayout(3,2,10,10));
				panel10.setBackground(new Color(123,106,174));
				panel10.setBounds(0, 0, 1000, 800);	
				panel7.add(panel10,BorderLayout.CENTER);
				
				
		//panelB
				panelB=new JPanel();
				panelB.setBounds(0, 0, 1600, 880);
				panelB.setLayout(new BorderLayout());
				panelB.setBackground(new Color(123,106,174));
		
		//panelB1 part of panelB
				panelB1=new JPanel();
				panelB1.setBounds(0, 0, 1280, 750);
				panelB1.setLayout(new BorderLayout());
				panelB1.setBackground(new Color(0,0,255));
				panelB.add(panelB1,BorderLayout.CENTER);
				
		//panelB2 part of panelB
				panelB2=new JPanel();
				panelB2.setBounds(0, 0, 1290, 730);
				panelB2.setLayout(new GridLayout(5,5,10,10));
				panelB2.setOpaque(true);
				panelB2.setBackground(new Color(0,0,0,0));
				
		//panelB3 part of panelB
				panelB3=new JPanel();
				panelB3.setBounds(0, 0, 300, 750);
				panelB3.setLayout(new GridLayout(0,1));
				panelB3.setBackground(new Color(123,106,174));
				panelB.add(panelB3,BorderLayout.WEST);
				
		//panelB4 part of panelB
				panelB4=new JPanel();
				panelB4.setBounds(0, 0, 300, 750);
				panelB4.setLayout(new GridLayout(0,1));
				panelB4.setBackground(new Color(123,106,174));
				panelB.add(panelB4,BorderLayout.EAST);
		
		//panelB5 part of panelB
				move =new JButton(imageGet("Move"));
				move.setFont(new Font("MV Boli", Font.PLAIN, 30));	
				move.setOpaque(false);
				move.setContentAreaFilled(false);
				move.setBorderPainted(false);
				move.setFocusable(false);
				move.addActionListener(this);
				attack=new JButton(imageGet("Attack"));
				attack.setFont(new Font("MV Boli", Font.PLAIN, 30));
				attack.setOpaque(false);
				attack.setContentAreaFilled(false);
				attack.setBorderPainted(false);
				attack.setFocusable(false);
				attack.addActionListener(this);
				cast=new JButton(imageGet("CastAbility"));
				cast.setFont(new Font("MV Boli", Font.PLAIN, 30));	
				cast.setOpaque(false);
				cast.setContentAreaFilled(false);
				cast.setBorderPainted(false);
				cast.setFocusable(false);
				cast.addActionListener(this);
				ULability=new JButton(imageGet("UseLeaderAbility"));
				ULability.setFont(new Font("MV Boli", Font.PLAIN, 30));	
				ULability.setOpaque(false);
				ULability.setContentAreaFilled(false);
				ULability.setBorderPainted(false);
				ULability.setFocusable(false);
				ULability.addActionListener(this);
				endturn=new JButton(imageGet("EndTurn"));
				endturn.setFont(new Font("MV Boli", Font.PLAIN, 30));
				endturn.setOpaque(false);
				endturn.setContentAreaFilled(false);
				endturn.setBorderPainted(false);
				endturn.setFocusable(false);
				endturn.addActionListener(this);

				
				panelB5=new JPanel();
				panelB5.setBounds(0, 0, 1600, 50);
				panelB5.setLayout(new GridLayout(1,6));
				panelB5.setBackground(new Color(123,106,174));
				panelB5.add(move);
				panelB5.add(attack);
				panelB5.add(cast);
				panelB5.add(ULability);
				panelB5.add(endturn);
				panelB.add(panelB5,BorderLayout.SOUTH);
				
		//Jpane
				Board=new JLayeredPane();
				Board.setLayout(new BorderLayout());
				Board.setBounds(0, 0, 1280, 750);
				Board.add(panelB2,BorderLayout.CENTER,1);
				
		//panelBB
				JLabel l= new JLabel();
				panelBB=new JPanel();
				panelBB.setBounds(0, 0,10000, 10000);
				panelBB.setLayout(new BorderLayout());
				ImageIcon i=new ImageIcon("BackgroundB.png");
				l.setIcon(i);
				panelBB.add(l,BorderLayout.CENTER);
				Board.add(panelBB,2);
				panelB1.add(Board,BorderLayout.CENTER);
				
		//panelP
				resume =new JButton();
				resume.setBounds(0,0,100,100);
				resume.addActionListener(this);
				
				panelP=new JPanel();
				panelP.setBackground(new Color(100,100,100));
				panelP.setBounds(0, 0, 1600, 880);
				panelP.add(resume,BorderLayout.CENTER);
				
		//panelwin
				JButton close=new JButton();
				close.setBackground(new Color(0,0,0));
				
				ImageIcon win=new ImageIcon("marvel.jpg");
				JLabel winn=new JLabel();
				winn.setIcon(win);
				panelwin =new JPanel(new BorderLayout());
				panelwin.setBackground(new Color(0,0,0));
				panelwin.add(winn,BorderLayout.CENTER);
				panelwin.add(close,BorderLayout.SOUTH);
				panelwin.setBounds(0, 0, 1600, 750);
						
	}
	
	public void win() {
		if(game1.checkGameOver()!=null){
			panelB.setVisible(false);
			namewin=new JButton();
			namewin.setBounds(0, 0, 1600, 300);
			namewin.setFont(new Font("MV Boli", Font.PLAIN, 40));
			namewin.setText(game1.checkGameOver().getName());
			namewin.setFocusable(false);
			namewin.addActionListener(this);
			if(namewin.getText().equals(game1.getFirstPlayer().getName()))
				namewin.setForeground(new Color(0,0,255));	
			if(namewin.getText().equals(game1.getSecondPlayer().getName()))
				namewin.setForeground(new Color(255,0,0));
			namewin.setText("Winner : "+game1.checkGameOver().getName());
			namewin.setBackground(new Color(0,0,0));
			panelwin.add(namewin,BorderLayout.NORTH);
			this.add(panelwin);
		}	
	}
	
	public Champion ChampChoose(String s) {
		for(int i=0;i<Game.getAvailableChampions().size();i++)
			if(Game.getAvailableChampions().get(i).getName().equals(s))
				return Game.getAvailableChampions().get(i);
		return null;
	}
	
	public void turn() {
		ArrayList<Champion> temp = new ArrayList<>();
		int count=0;
		panelB4.removeAll();
		while (game1.getTurnOrder().size()!=0) {
			JLabel turn=new JLabel();
			temp.add((Champion) game1.getTurnOrder().remove());
			turn.setIcon(imageGet(temp.get(count).getName()));
			panelB4.add(turn);
			count++;
			}
		while (!temp.isEmpty()){
			game1.getTurnOrder().insert(temp.remove(0));
			}
		
	}
	
	public void leftP() {
		panelB3.removeAll();
		JButton firstP=new JButton();
		firstP.setOpaque(false);
		firstP.setContentAreaFilled(false);
		firstP.setBorderPainted(false);
		firstP.setFocusable(false);
		firstP.setText(game1.getFirstPlayer().getName());
		firstP.setFont(new Font("MV Boli", Font.PLAIN, 25));
		firstP.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(game1.isFirstLeaderAbilityUsed())
				JOptionPane.showOptionDialog(null,"Leader : "+game1.getFirstPlayer().getLeader().getName()+"\n"+"Leader Ability Used","First Player" , JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, 0);
				else
					JOptionPane.showOptionDialog(null,"Leader : "+game1.getFirstPlayer().getLeader().getName()+"\n"+"Leader Ability Not Used","First Player" , JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, 0);
			}
		});
		panelB3.add(firstP);
		
		for(int i=0;i<game1.getFirstPlayer().getTeam().size();i++) {
			JButton firstPChamp=new JButton(imageGet(game1.getFirstPlayer().getTeam().get(i).getName()));
			String name=game1.getFirstPlayer().getTeam().get(i).getName();
			firstPChamp.setOpaque(false);
			firstPChamp.setContentAreaFilled(false);
			firstPChamp.setBorderPainted(false);
			firstPChamp.setFocusable(false);
			firstPChamp.addActionListener(new ActionListener() {		
				public void actionPerformed(ActionEvent e) {
					String []x= {"Applied Effects"};
					
					int y=JOptionPane.showOptionDialog(null,game1.Championdet1(name),name, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, x, 0);					
					if(y==0)
						JOptionPane.showOptionDialog(null,game1.ChampAEffects(name),name, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, 0);	
					
				}
			});
			panelB3.add(firstPChamp);
			
			
		}
		
		JButton secondP=new JButton();
		secondP.setText(game1.getSecondPlayer().getName());
		secondP.setOpaque(false);
		secondP.setContentAreaFilled(false);
		secondP.setBorderPainted(false);
		secondP.setFocusable(false);
		secondP.setFont(new Font("MV Boli", Font.PLAIN, 25));
		secondP.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(game1.isSecondLeaderAbilityUsed())
					JOptionPane.showOptionDialog(null,"Leader : "+game1.getSecondPlayer().getLeader().getName()+"\n"+"Leader Ability Used","Second Player" , JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, 0);
					else
						JOptionPane.showOptionDialog(null,"Leader : "+game1.getSecondPlayer().getLeader().getName()+"\n"+"Leader Ability Not Used","Second Player" , JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, 0);
				
			}
		});
		panelB3.add(secondP);
		
		for(int i=0;i<game1.getSecondPlayer().getTeam().size();i++) {
			JButton secondPChamp=new JButton(imageGet(game1.getSecondPlayer().getTeam().get(i).getName()));
			String name=game1.getSecondPlayer().getTeam().get(i).getName();
			secondPChamp.setOpaque(false);
			secondPChamp.setContentAreaFilled(false);
			secondPChamp.setBorderPainted(false);
			secondPChamp.setFocusable(false);
			secondPChamp.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					String []x= {"Applied Effects"};
					int y=JOptionPane.showOptionDialog(null,game1.Championdet1(name),name, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, x, 0);					
					if(y==0)
						JOptionPane.showOptionDialog(null,game1.ChampAEffects(name),name, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, 0);	
				}
			});
			panelB3.add(secondPChamp);
			
			
		}
		
	}
	
	public void Boardupdate() {
		Object [][] b=new Object[5][5];
		b=game1.getBoard();
		panelB2.removeAll();
		panelB1.removeAll();
		Board.removeAll();
		for(int i=0;i<5;i++)
			for(int j=0;j<5;j++) {
				if(b[i][j] instanceof Champion) {
				JLabel board=new JLabel();
				board.setIcon(imageGet(((Champion)b[i][j]).getName()));
				board.setBounds(0, 0, 300, 300);
				String hp=((Champion)b[i][j]).getCurrentHP()+"/"+((Champion)b[i][j]).getMaxHP();
				board.setText(hp);
				board.setHorizontalTextPosition(JButton.CENTER);
				board.setVerticalTextPosition(JButton.BOTTOM);
				board.setIconTextGap(-18);
				board.setFont(new Font("MV Boli", Font.PLAIN, 25));	
				board.setOpaque(false);
				if(game1.getFirstPlayer().getTeam().contains((Champion)b[i][j])) {
				board.setBorder(BorderFactory.createLineBorder(new Color(0,0,255),3));
				board.setForeground(new Color(0,0,255));
				if(game1.getFirstPlayer().getLeader().equals((Champion)b[i][j]))
					board.setForeground(new Color(255,215,0));}
				if(game1.getSecondPlayer().getTeam().contains((Champion)b[i][j])) {
				board.setBorder(BorderFactory.createLineBorder(new Color(255,0,0),3));
				board.setForeground(new Color(255,0,0));
				if(game1.getSecondPlayer().getLeader().equals((Champion)b[i][j]))
					board.setForeground(new Color(255,215,0));}
				panelB2.add(board,i,j);
				}
				if(b[i][j] instanceof Cover) {
					JLabel board=new JLabel();
					board.setBounds(0, 0, 300, 300);
					board.setIcon(imageGet("cover"));
					String hp=((Cover)b[i][j]).getCurrentHP()+"/"+"1000";
					board.setText(hp);
					board.setForeground(new Color(255,255,255));
					board.setHorizontalTextPosition(JButton.CENTER);
					board.setVerticalTextPosition(JButton.BOTTOM);
					board.setIconTextGap(-18);
					board.setFont(new Font("MV Boli", Font.PLAIN, 25));
					board.setOpaque(false);
					board.setBorder(BorderFactory.createLineBorder(new Color(255,255,255),3));
					panelB2.add(board,i,j);
					}
				if(b[i][j]== null) {
					JLabel board=new JLabel();
					board.setBounds(0, 0, 300, 300);
					board.setOpaque(false);
					board.setBorder(BorderFactory.createLineBorder(new Color(0,0,0),3));
					panelB2.add(board,i,j);
				}
			}
		Board.add(panelB2,BorderLayout.CENTER,0);
		Board.add(panelBB,BorderLayout.CENTER,1);
		panelB1.add(Board,BorderLayout.CENTER);
		validate();
		revalidate();
		repaint();
	}
	
	public ImageIcon imageGet(String name){
		String x=name+".png";
		ImageIcon i=new ImageIcon(x);
		return i;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button1) {
			welcome.removeAll();
			backl.setBounds(0,0,1600,880);
			welcome.add(backl,1);
			welcome.add(panel2,0);
			validate();
			repaint();
			revalidate();
			}
		
		if(!P1name.getText().equals("")) {
		if(e.getSource()==confirmP1) {
			confirmP1.setEnabled(false);
			P1name.setEnabled(false);
			Player1 = new Player(P1name.getText());
			if(flag) {
				Player2 = new Player(P2name.getText());
				game =new Game(Player1,Player2);
				welcome.setVisible(false);	
				this.add(panel3);
				try {
					Game.loadAbilities("Abilities.csv");
					Game.loadChampions("Champions.csv");
					p1Select.setText(game.getFirstPlayer().getName());
					p1Select.setFont(new Font("MV Boli", Font.PLAIN, 30));
					p2Select.setText(game.getSecondPlayer().getName());
					p2Select.setFont(new Font("MV Boli", Font.PLAIN, 30));
					CurrentP.setText(game.getFirstPlayer().getName());
					int x=0;
					for(int i=0;i<3;i++)
						for(int j=0;j<5;j++) {
							JButton z=new JButton(imageGet(Game.getAvailableChampions().get(x).getName()));
							z.setText(Game.getAvailableChampions().get(x).getName());
							z.setHorizontalTextPosition(JButton.CENTER);
							z.setVerticalTextPosition(JButton.TOP);
							z.setIconTextGap(-10);
							z.setFont(new Font("MV Boli", Font.PLAIN, 20));
							z.setFocusable(false);
							z.setOpaque(false);
							z.setContentAreaFilled(false);
							z.setBorder(BorderFactory.createLineBorder(new Color(0,0,0),3));
							z.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									String [] selection=new  String[5];
									selection[0]= "Confirm";
									selection[1]= "Cancel";
									for(int i=2;i<5;i++) {
										selection[i]=ChampChoose(z.getText()).getAbilities().get(i-2).getName();
									}
									int x=JOptionPane.showOptionDialog(null,Game.Championdet(z.getText()),"Confirm Selection" , JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, selection, 0);
									if(x==2) {
									JOptionPane.showOptionDialog(null,Game.abilitiedet(ChampChoose(z.getText()).getAbilities().get(0).getName()),"Confirm Selection" , JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, 0);
									}
									if(x==3) {
									JOptionPane.showOptionDialog(null,Game.abilitiedet(ChampChoose(z.getText()).getAbilities().get(1).getName()),"Confirm Selection" , JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, 0);
									}
									if(x==4) {
										JOptionPane.showOptionDialog(null,Game.abilitiedet(ChampChoose(z.getText()).getAbilities().get(2).getName()),"Confirm Selection" , JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, 0);
									}
									if(count <6 &&count>=3 &&x==0) {
										z.setVisible(false);
										game.getSecondPlayer().getTeam().add(ChampChoose(z.getText()));
										test = new JLabel();
										test.setText(z.getText());
										test.setIcon(imageGet(z.getText()));
										test.setHorizontalTextPosition(SwingConstants.LEFT);
										panel6.add(test);
										validate();
										z.setEnabled(false);
										count++;
									}
									if(count <3 &&x==0) {
										z.setVisible(false);
										game.getFirstPlayer().getTeam().add(ChampChoose(z.getText()));
										test = new JLabel();
										test.setText(z.getText());
										test.setIcon(imageGet(z.getText()));
										panel5.add(test);
										validate();
										z.setEnabled(false);
										count++;
										if(count==3)
											CurrentP.setText(game.getSecondPlayer().getName());
									}
									if(count==6) {
										z.setVisible(false);
										panel4.setVisible(false);
										Confirm.setText("Choose Leader");
										Confirm.setFont(new Font("MV Boli", Font.PLAIN, 50));
										Confirm.setFocusable(false);
										Confirm.setOpaque(false);
										Confirm.setContentAreaFilled(false);
										Confirm.setBorder(BorderFactory.createLineBorder(new Color(0,0,0),3));
										panel3.add(Confirm,BorderLayout.CENTER);
										CurrentP.setText("Choose ur leader");
										CurrentP.setBounds(700, 50, 100, 100);
										validate();
										}
								}
							});
							panel4.add(z,i,j);
							x++;
							}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
					}
			flag=true;
			}
		}
		
		if(!P2name.getText().equals("")) {
		if(e.getSource()==confirmP2) {
			confirmP2.setEnabled(false);
			P2name.setEnabled(false);
			Player2 = new Player(P2name.getText());
			if(flag) {
				Player1 = new Player(P1name.getText());
				game =new Game(Player1,Player2);
				welcome.setVisible(false);
				this.add(panel3);
				try {
					Game.loadAbilities("Abilities.csv");
					Game.loadChampions("Champions.csv");
					p1Select.setText(game.getFirstPlayer().getName());
					p1Select.setFont(new Font("MV Boli", Font.PLAIN, 30));
					p2Select.setText(game.getSecondPlayer().getName());
					p2Select.setFont(new Font("MV Boli", Font.PLAIN, 30));
					CurrentP.setText(game.getFirstPlayer().getName());
					int x=0;
					for(int i=0;i<3;i++)
						for(int j=0;j<5;j++) {
							JButton z=new JButton(imageGet(Game.getAvailableChampions().get(x).getName()));
							z.setText(Game.getAvailableChampions().get(x).getName());
							z.setHorizontalTextPosition(JButton.CENTER);
							z.setVerticalTextPosition(JButton.TOP);
							z.setIconTextGap(-10);
							z.setFont(new Font("MV Boli", Font.PLAIN, 20));
							z.setFocusable(false);
							z.setOpaque(false);
							z.setContentAreaFilled(false);
							z.setBorder(BorderFactory.createLineBorder(new Color(0,0,0),3));
							z.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									String [] selection=new  String[5];
									selection[0]= "Confirm";
									selection[1]= "Cancel";
									for(int i=2;i<5;i++) {
										selection[i]=ChampChoose(z.getText()).getAbilities().get(i-2).getName();
									}
									int x=JOptionPane.showOptionDialog(null,Game.Championdet(z.getText()),"Confirm Selection" , JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, selection, 0);
									if(x==2) {
									JOptionPane.showOptionDialog(null,Game.abilitiedet(ChampChoose(z.getText()).getAbilities().get(0).getName()),"Confirm Selection" , JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, 0);
									}
									if(x==3) {
									JOptionPane.showOptionDialog(null,Game.abilitiedet(ChampChoose(z.getText()).getAbilities().get(1).getName()),"Confirm Selection" , JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, 0);
									}
									if(x==4) {
										JOptionPane.showOptionDialog(null,Game.abilitiedet(ChampChoose(z.getText()).getAbilities().get(2).getName()),"Confirm Selection" , JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, 0);
									}
									if(count <6 &&count>=3 &&x==0) {
										z.setVisible(false);
										game.getSecondPlayer().getTeam().add(ChampChoose(z.getText()));
										test = new JLabel();
										test.setText(z.getText());
										test.setIcon(imageGet(z.getText()));
										test.setHorizontalTextPosition(SwingConstants.LEFT);
										panel6.add(test);
										validate();
										z.setEnabled(false);
										count++;
									}
									if(count <3 &&x==0) {
										z.setVisible(false);
										game.getFirstPlayer().getTeam().add(ChampChoose(z.getText()));
										test = new JLabel();
										test.setText(z.getText());
										test.setIcon(imageGet(z.getText()));
										panel5.add(test);
										validate();
										z.setEnabled(false);
										count++;
										if(count==3)
											CurrentP.setText(game.getSecondPlayer().getName());
									}
									if(count==6) {
										z.setVisible(false);
										panel4.setVisible(false);
										Confirm.setText("Choose Leader");
										Confirm.setFont(new Font("MV Boli", Font.PLAIN, 50));
										Confirm.setFocusable(false);
										Confirm.setOpaque(false);
										Confirm.setContentAreaFilled(false);
										Confirm.setBorder(BorderFactory.createLineBorder(new Color(0,0,0),3));
										panel3.add(Confirm,BorderLayout.CENTER);
										CurrentP.setText("Choose ur leader");
										CurrentP.setBounds(700, 50, 100, 100);
										validate();
										}
								}
							});
							panel4.add(z,i,j);
							x++;
							}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
					}
					
			flag=true;
				}
		}	
		if(e.getSource()==Confirm) {
			panel3.setVisible(false);
			CurrentPLeader.setText(game.getFirstPlayer().getName());
			p1Leader.setText(game.getFirstPlayer().getName());
			p2Leader.setText(game.getSecondPlayer().getName());
			this.add(panel7);	
			for(int i=0;i<3;i++) {
				JButton n=new JButton(imageGet(game.getFirstPlayer().getTeam().get(i).getName()));
				n.setText(game.getFirstPlayer().getTeam().get(i).getName());
				n.setHorizontalTextPosition(JButton.CENTER);
				n.setVerticalTextPosition(JButton.TOP);
				n.setIconTextGap(-10);
				n.setFont(new Font("MV Boli", Font.PLAIN, 40));
				n.setFocusable(false);
				n.setOpaque(false);
				n.setContentAreaFilled(false);
				n.setBorder(BorderFactory.createLineBorder(new Color(0,0,0),3));
				n.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(!flagL1 &&e.getSource()==n) {
							String [] selection= {"Confirm","Cancel"};
							int x=JOptionPane.showOptionDialog(null,Game.Leaderdet(n.getText()),"Confirm Selection" , JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, selection, 0);
							if(x==0) {
								n.setVisible(false);
								n.setEnabled(false);
								flagL1=true;
								game.getFirstPlayer().setLeader(ChampChoose(n.getText()));
								JLabel l=new JLabel();
								l.setText(n.getText());
								l.setIcon(imageGet(n.getText()));
								panel8.add(l);
							}
						}
					}
				});
					panel10.add(n,i,0);
					JButton m=new JButton(imageGet(game.getSecondPlayer().getTeam().get(i).getName()));
					m.setText(game.getSecondPlayer().getTeam().get(i).getName());
					m.setHorizontalTextPosition(JButton.CENTER);
					m.setVerticalTextPosition(JButton.TOP);
					m.setIconTextGap(-10);
					m.setFont(new Font("MV Boli", Font.PLAIN, 40));
					m.setFocusable(false);
					m.setOpaque(false);
					m.setContentAreaFilled(false);
					m.setBorder(BorderFactory.createLineBorder(new Color(0,0,0),3));
					m.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							if(e.getSource()==m&&(!flagL2 &&flagL1) ) {
								String [] selection= {"Confirm","Cancel"};
								int x=JOptionPane.showOptionDialog(null,Game.Leaderdet(m.getText()),"Confirm Selection" , JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, selection, 0);
								if(x==0) {
									m.setVisible(false);
									m.setEnabled(false);
									flagL2=true;
									game.getSecondPlayer().setLeader(ChampChoose(m.getText()));
									JLabel l=new JLabel();
									l.setText(m.getText());
									l.setIcon(imageGet(m.getText()));
									l.setHorizontalTextPosition(SwingConstants.LEFT);
									panel9.add(l);
									panel10.setVisible(false);
									Start.setText("Start Game");
									Start.setFont(new Font("MV Boli", Font.PLAIN, 50));
									Start.setFocusable(false);
									Start.setOpaque(false);
									Start.setContentAreaFilled(false);
									Start.setBorder(BorderFactory.createLineBorder(new Color(0,0,0),3));
									Start.setBounds(0, 0, 1000, 800);
									
									panel7.add(Start,BorderLayout.CENTER);
									}
							}
						}
					});
						panel10.add(m,i,1);
				}
			}
		
		if(e.getSource()==Start) {
			panel7.setVisible(false);
			this.add(panelB);
			game1 =new Game(game.getFirstPlayer(),game.getSecondPlayer());
			Boardupdate();
			leftP();
			turn();  			
		}
		
		if(e.getSource()==move) {
			String [] directions= {"Up","Down","Left","Right"};
			int x=JOptionPane.showOptionDialog(null,"Move" , "Pick direction", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, directions, 0);
				try {
					if(x==0)
						game1.move(Direction.UP);
					if(x==1)
						game1.move(Direction.DOWN);
					if(x==2)
						game1.move(Direction.LEFT);
					if(x==3)
						game1.move(Direction.RIGHT);
					
				} catch (UnallowedMovementException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"Error",JOptionPane.PLAIN_MESSAGE);
				} catch (NotEnoughResourcesException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"Error",JOptionPane.PLAIN_MESSAGE);
				}
				Boardupdate();
				leftP();
				turn();
				validate();
				win();
		}
		if(e.getSource()==attack) {
			String [] directions= {"Up","Down","Left","Right"};
			int x=JOptionPane.showOptionDialog(null,"Attack" , "Pick direction", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, directions, 0);
				try {
					if(x==0)
						game1.attack(Direction.UP);
					if(x==1)
						game1.attack(Direction.DOWN);	
					if(x==2)
						game1.attack(Direction.LEFT);	
					if(x==3)
						game1.attack(Direction.RIGHT);	
				} catch (NotEnoughResourcesException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"Error",JOptionPane.PLAIN_MESSAGE);
				} catch (ChampionDisarmedException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"Error",JOptionPane.PLAIN_MESSAGE);
				}
				Boardupdate();
				leftP();
				turn();
				validate();
				win();
		}
		if(e.getSource()==cast) {
			String []x=new String[game1.getCurrentChampion().getAbilities().size()];
			for(int i=0;i<game1.getCurrentChampion().getAbilities().size();i++) {
				x[i]=game1.getCurrentChampion().getAbilities().get(i).getName();		
			}
			int y=JOptionPane.showOptionDialog(null,"Choose ur Ability","Available abilities", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, x, 0);
			if(y!=-1) {
			String []r={"Confirm","Cancel"};
			int m=JOptionPane.showOptionDialog(null,game1.AbilityChamp(x[y]),"Confirm", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, r, 0);	
				if(m==0) {
					if(game1.getCurrentChampion().getAbilities().get(y).getCastArea().equals(AreaOfEffect.SINGLETARGET)){
						try {
							String [] Coordinates= {"1,1","1,2","1,3","1,4","1,5",
													"2,1","2,2","2,3","2,4","2,5",
													"3,1","3,2","3,3","3,4","3,5",
													"4,1","4,2","4,3","4,4","4,5",
													"5,1","5,2","5,3","5,4","5,5"};
							JLabel koko=new JLabel();
							koko.setText(Coordinates[1]);
						int k=JOptionPane.showOptionDialog(null,koko, "Pick Location", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, Coordinates, 0);
						if(k!=-1) {
							String py=""+Coordinates[k].charAt(0);
							String px=""+Coordinates[k].charAt(2);
						game1.castAbility(game1.getCurrentChampion().getAbilities().get(y),Integer.parseInt(px)-1,Integer.parseInt(py)-1);}
						} catch (NotEnoughResourcesException e1) {
							JOptionPane.showMessageDialog(null,e1.getMessage(),"Error",JOptionPane.PLAIN_MESSAGE);
						} catch (AbilityUseException e1) {
							JOptionPane.showMessageDialog(null,e1.getMessage(),"Error",JOptionPane.PLAIN_MESSAGE);
						} catch (InvalidTargetException e1) {
							JOptionPane.showMessageDialog(null,e1.getMessage(),"Error",JOptionPane.PLAIN_MESSAGE);
						} catch (CloneNotSupportedException e1) {
							JOptionPane.showMessageDialog(null,e1.getMessage(),"Error",JOptionPane.PLAIN_MESSAGE);
						}
					}
					if(game1.getCurrentChampion().getAbilities().get(y).getCastArea().equals(AreaOfEffect.DIRECTIONAL)){
						String [] directions= {"Up","Down","Left","Right"};
						int d=JOptionPane.showOptionDialog(null,"Cast" , "Pick direction", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, directions, 0);	
									try {
										if(d==0)
											game1.castAbility(game1.getCurrentChampion().getAbilities().get(y),Direction.UP);
										if(d==1)
											game1.castAbility(game1.getCurrentChampion().getAbilities().get(y),Direction.DOWN);	
										if(d==2)
											game1.castAbility(game1.getCurrentChampion().getAbilities().get(y),Direction.LEFT);	
										if(d==3)
											game1.castAbility(game1.getCurrentChampion().getAbilities().get(y),Direction.RIGHT);	
									} catch (NotEnoughResourcesException e1) {
										JOptionPane.showMessageDialog(null,e1.getMessage(),"Error",JOptionPane.PLAIN_MESSAGE);
									} catch (AbilityUseException e1) {
										JOptionPane.showMessageDialog(null,e1.getMessage(),"Error",JOptionPane.PLAIN_MESSAGE);
									} catch (CloneNotSupportedException e1) {
										JOptionPane.showMessageDialog(null,e1.getMessage(),"Error",JOptionPane.PLAIN_MESSAGE);
									}
					} 
					if(game1.getCurrentChampion().getAbilities().get(y).getCastArea().equals(AreaOfEffect.SELFTARGET)||
							game1.getCurrentChampion().getAbilities().get(y).getCastArea().equals(AreaOfEffect.SURROUND)||
							game1.getCurrentChampion().getAbilities().get(y).getCastArea().equals(AreaOfEffect.TEAMTARGET))
						try {
							game1.castAbility(game1.getCurrentChampion().getAbilities().get(y));
						} catch (NotEnoughResourcesException e1) {
							JOptionPane.showMessageDialog(null,e1.getMessage(),"Error",JOptionPane.PLAIN_MESSAGE);
						} catch (AbilityUseException e1) {
							JOptionPane.showMessageDialog(null,e1.getMessage(),"Error",JOptionPane.PLAIN_MESSAGE);
						} catch (CloneNotSupportedException e1) {
							JOptionPane.showMessageDialog(null,e1.getMessage(),"Error",JOptionPane.PLAIN_MESSAGE);
						}
					
					
				}
			}
			Boardupdate();
			leftP();
			turn();
			validate();
			win();
			
		}
		if(e.getSource()==ULability) {
			try {
				game1.useLeaderAbility();
			} catch (LeaderNotCurrentException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Error",JOptionPane.PLAIN_MESSAGE);
			} catch (LeaderAbilityAlreadyUsedException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Error",JOptionPane.PLAIN_MESSAGE);
			}
			Boardupdate();
			leftP();
			turn();
			validate();
			win();
			
		}
		if(e.getSource()==endturn){
			game1.endTurn();
			turn();
			validate();
		}

		if(e.getSource()==resume) {
			panelP.setVisible(false);
			panelB.setVisible(true);
		}
		if(e.getSource()==namewin){
			this.dispose();
		}
	}
	public static void main(String [] args) {
		new Frame1();
		
		
	}

}