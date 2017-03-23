package dkeep.gui;

import dkeep.logic.*;

import java.io.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class DKInterface
{
	private ByteArrayOutputStream stos;
	private PrintStream stream;
	
	private DungeonKeep.State game_state = DungeonKeep.State.LEVEL_PLAYING; // Acts as null
	private int guard_type = 0;
	private int ogre_number = 0;
	private int current_guard_type = 0;
	private int current_ogre_number = 0;
	private DungeonKeep level;
	private int level_number = 1;
	
	private JFrame frmDungeonKeep = new JFrame();
	private JTextArea txtrMap = new JTextArea();
	private JLabel lbl1GameStatus = new JLabel("Please select game settings");
	private JLabel lbl2GameStatus = new JLabel("");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DKInterface window = new DKInterface();
					window.frmDungeonKeep.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DKInterface() {
		stos = new ByteArrayOutputStream();
		stream = new PrintStream(stos);
		
		initialize();
	}
	
	public void update()
	{
		level.display(stream);
		txtrMap.setText("");
		// Because it prints 50 newlines before the map, treated as \r\n characters
		txtrMap.setText(stos.toString().substring(100));
		stos.reset();
		
		switch (game_state)
		{
			case LEVEL_COMPLETED:
				lbl1GameStatus.setText("Level cleared. Good job!");
				lbl2GameStatus.setText("On to level " + ((Integer) level_number).toString());
				level = new DungeonKeep(++level_number, current_guard_type, current_ogre_number);
				update();
				break;
			case GAME_OVER:
				break;
			default:
				lbl1GameStatus.setText("");
				lbl2GameStatus.setText("");
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDungeonKeep.setTitle("Dungeon Keep");
		frmDungeonKeep.setResizable(false);
		frmDungeonKeep.setBounds(0, 0, 640, 360);
		frmDungeonKeep.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDungeonKeep.getContentPane().setLayout(null);
		
		txtrMap.setTabSize(4);
		txtrMap.setEditable(false);
		txtrMap.setFont(new Font("Consolas", Font.PLAIN, 12));
		txtrMap.setBounds(300, 70, 320, 240);
		frmDungeonKeep.getContentPane().add(txtrMap);
		
		lbl1GameStatus.setFont(new Font("Consolas", Font.PLAIN, 12));
		lbl1GameStatus.setBounds(300, 25, 320, 20);
		frmDungeonKeep.getContentPane().add(lbl1GameStatus);
		
		lbl2GameStatus.setFont(new Font("Consolas", Font.PLAIN, 12));
		lbl2GameStatus.setBounds(300, 45, 320, 20);
		frmDungeonKeep.getContentPane().add(lbl2GameStatus);
		
		// ==
		
		JButton bttnNewGame = new JButton("New Game");
		JButton bttnRestart = new JButton("Restart");
		JButton bttnDirectionUp = new JButton("Up");
		JButton bttnDirectionLeft = new JButton("Left");
		JButton bttnDirectionRight = new JButton("Right");
		JButton bttnDirectionDown = new JButton("Down");
		JButton bttnExitGame = new JButton("Exit Game");
		
		JTextField txtfldNumberOfOgres = new JTextField();
		JComboBox cmbbxGuardPersonality = new JComboBox(new String[]{"Rookie", "Drunken", "Suspicious"});
		
		// ==
		
		JLabel lblNumberOfOgres = new JLabel("Number of Ogres:");
		lblNumberOfOgres.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblNumberOfOgres.setBounds(20, 20, 120, 20);
		frmDungeonKeep.getContentPane().add(lblNumberOfOgres);
		
		txtfldNumberOfOgres.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent foc_evn) {
				lbl1GameStatus.setText("Please select game settings");
				lbl2GameStatus.setText("");
			}
			@Override
			public void focusLost(FocusEvent foc_evn) {
				if (!txtfldNumberOfOgres.getText().equals("")) {
					try {
						ogre_number = Integer.parseInt(txtfldNumberOfOgres.getText());
						if (ogre_number > 5 || ogre_number < 1) {
							ogre_number = 0;
							throw new NumberFormatException();
						}
					}
					catch (NumberFormatException except) {
						txtfldNumberOfOgres.setText("");
						lbl1GameStatus.setText("Invalid input!");
						lbl2GameStatus.setText("Must be a number between 1 and 5.");
					}
				}
				if (guard_type * ogre_number == 0) {
					lbl1GameStatus.setText("Please select game settings");
					lbl2GameStatus.setText("");
				}
				else {
					lbl1GameStatus.setText("You can now start a new game");
					lbl2GameStatus.setText("");
				}
			}			
		});
		txtfldNumberOfOgres.setColumns(5);
		txtfldNumberOfOgres.setFont(new Font("Consolas", Font.PLAIN, 12));
		txtfldNumberOfOgres.setBounds(160, 20, 40, 20);
		frmDungeonKeep.getContentPane().add(txtfldNumberOfOgres);
		
		JLabel lblGuardPersonality = new JLabel("Guard personality:");
		lblGuardPersonality.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblGuardPersonality.setBounds(20, 50, 140, 20);
		frmDungeonKeep.getContentPane().add(lblGuardPersonality);
		
		cmbbxGuardPersonality.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent foc_evn) {
				lbl1GameStatus.setText("Please select game settings");
				lbl2GameStatus.setText("");
			}
			@Override
			public void focusLost(FocusEvent foc_evn) {
				guard_type = cmbbxGuardPersonality.getSelectedIndex() + 1;
				if (guard_type * ogre_number == 0) {
					lbl1GameStatus.setText("Please select game settings");
					lbl2GameStatus.setText("");
				}
				else {
					lbl1GameStatus.setText("You can now start a new game");
					lbl2GameStatus.setText("");
				}
			}
		});
		cmbbxGuardPersonality.setSelectedIndex(-1);
		cmbbxGuardPersonality.setFont(new Font("Consolas", Font.PLAIN, 12));
		cmbbxGuardPersonality.setBounds(160, 50, 100, 20);
		frmDungeonKeep.getContentPane().add(cmbbxGuardPersonality);
		
		bttnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				if (guard_type * ogre_number != 0) {
					level = new DungeonKeep(level_number = 1, current_guard_type = guard_type, current_ogre_number = ogre_number);
					bttnRestart.setEnabled(true);
					bttnDirectionUp.setEnabled(true);
					bttnDirectionLeft.setEnabled(true);
					bttnDirectionRight.setEnabled(true);
					bttnDirectionDown.setEnabled(true);
					update();
				}
				else {
					lbl1GameStatus.setText("Invalid settings!");
					lbl2GameStatus.setText("Please select game settings");
				}
				
			}
		});
		bttnNewGame.setFont(new Font("Consolas", Font.PLAIN, 12));
		bttnNewGame.setBounds(30, 110, 100, 20);
		frmDungeonKeep.getContentPane().add(bttnNewGame);
		
		bttnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				level = new DungeonKeep(level_number, current_guard_type, current_ogre_number);
				bttnDirectionUp.setEnabled(true);
				bttnDirectionLeft.setEnabled(true);
				bttnDirectionRight.setEnabled(true);
				bttnDirectionDown.setEnabled(true);
				update();
			}
		});
		bttnRestart.setEnabled(false);
		bttnRestart.setFont(new Font("Consolas", Font.PLAIN, 12));
		bttnRestart.setBounds(150, 110, 100, 20);
		frmDungeonKeep.getContentPane().add(bttnRestart);
		
		bttnDirectionUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				game_state = level.update("w");
				update();
			}
		});
		bttnDirectionUp.setEnabled(false);
		bttnDirectionUp.setFont(new Font("Consolas", Font.PLAIN, 12));
		bttnDirectionUp.setBounds(100, 170, 80, 20);
		frmDungeonKeep.getContentPane().add(bttnDirectionUp);
		
		bttnDirectionLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				game_state = level.update("a");
				update();
			}
		});
		bttnDirectionLeft.setEnabled(false);
		bttnDirectionLeft.setFont(new Font("Consolas", Font.PLAIN, 12));
		bttnDirectionLeft.setBounds(50, 200, 80, 20);
		frmDungeonKeep.getContentPane().add(bttnDirectionLeft);
		
		bttnDirectionRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				game_state = level.update("d");
				update();
			}
		});
		bttnDirectionRight.setEnabled(false);
		bttnDirectionRight.setFont(new Font("Consolas", Font.PLAIN, 12));
		bttnDirectionRight.setBounds(150, 200, 80, 20);
		frmDungeonKeep.getContentPane().add(bttnDirectionRight);
		
		bttnDirectionDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				game_state = level.update("s");
				update();
			}
		});
		bttnDirectionDown.setEnabled(false);
		bttnDirectionDown.setFont(new Font("Consolas", Font.PLAIN, 12));
		bttnDirectionDown.setBounds(100, 230, 80, 20);
		frmDungeonKeep.getContentPane().add(bttnDirectionDown);
		
		bttnExitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				System.exit(0);
			}
		});
		bttnExitGame.setFont(new Font("Consolas", Font.PLAIN, 12));
		bttnExitGame.setBounds(90, 290, 100, 20);
		frmDungeonKeep.getContentPane().add(bttnExitGame);
	}
}
