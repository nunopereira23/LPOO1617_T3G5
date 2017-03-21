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

public class DKInterface
{
	private ByteArrayOutputStream stos;
	private PrintStream stream;
	
	private DungeonKeep.State state;
	private DungeonKeep level;
	private int level_number = 1;
	
	private JFrame frmDungeonKeep;
	private JTextField txtfldNumberOfOgres;
	private JComboBox cmbbxGuardPersonality;
	private JTextArea txtrMap;
	private JLabel lblGameStatus;

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
		// Because it prints 50 newlines before the map, treated as \r\n characters
		txtrMap.setText(stos.toString().substring(100));
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDungeonKeep = new JFrame();
		frmDungeonKeep.setTitle("Dungeon Keep");
		frmDungeonKeep.setResizable(false);
		frmDungeonKeep.setBounds(0, 0, 640, 360);
		frmDungeonKeep.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDungeonKeep.getContentPane().setLayout(null);
		
		JLabel lblNumberOfOgres = new JLabel("Number of Ogres:");
		lblNumberOfOgres.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblNumberOfOgres.setBounds(20, 20, 120, 20);
		frmDungeonKeep.getContentPane().add(lblNumberOfOgres);
		
		txtfldNumberOfOgres = new JTextField();
		txtfldNumberOfOgres.setFont(new Font("Consolas", Font.PLAIN, 12));
		txtfldNumberOfOgres.setBounds(160, 20, 40, 20);
		frmDungeonKeep.getContentPane().add(txtfldNumberOfOgres);
		txtfldNumberOfOgres.setColumns(5);
		
		JLabel lblGuardPersonality = new JLabel("Guard personality:");
		lblGuardPersonality.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblGuardPersonality.setBounds(20, 50, 140, 20);
		frmDungeonKeep.getContentPane().add(lblGuardPersonality);
		
		String[] cmbbxGuardPersonalityContent = {"Rookie", "Drunken", "Suspicious"};
		cmbbxGuardPersonality = new JComboBox(cmbbxGuardPersonalityContent);
		cmbbxGuardPersonality.setFont(new Font("Consolas", Font.PLAIN, 12));
		cmbbxGuardPersonality.setBounds(160, 50, 100, 20);
		frmDungeonKeep.getContentPane().add(cmbbxGuardPersonality);

		txtrMap = new JTextArea();
		txtrMap.setTabSize(4);
		txtrMap.setEditable(false);
		txtrMap.setFont(new Font("Consolas", Font.PLAIN, 12));
		txtrMap.setBounds(300, 70, 320, 240);
		frmDungeonKeep.getContentPane().add(txtrMap);
		
		lblGameStatus = new JLabel("");
		lblGameStatus.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblGameStatus.setBounds(300, 30, 320, 20);
		frmDungeonKeep.getContentPane().add(lblGameStatus);
		
		JButton bttnNewGame = new JButton("New Game");
		bttnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				level = new DungeonKeep(level_number = 1);
				update();
			}
		});
		bttnNewGame.setFont(new Font("Consolas", Font.PLAIN, 12));
		bttnNewGame.setBounds(30, 110, 100, 20);
		frmDungeonKeep.getContentPane().add(bttnNewGame);
		
		JButton bttnRestart = new JButton("Restart");
		bttnRestart.setEnabled(false);
		bttnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				level = new DungeonKeep(level_number);
			}
		});
		bttnRestart.setFont(new Font("Consolas", Font.PLAIN, 12));
		bttnRestart.setBounds(150, 110, 100, 20);
		frmDungeonKeep.getContentPane().add(bttnRestart);
		
		JButton bttnDirectionUp = new JButton("Up");
		bttnDirectionUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				state = level.update("w");
				update();
			}
		});
		bttnDirectionUp.setEnabled(false);
		bttnDirectionUp.setFont(new Font("Consolas", Font.PLAIN, 12));
		bttnDirectionUp.setBounds(100, 170, 80, 20);
		frmDungeonKeep.getContentPane().add(bttnDirectionUp);
		
		JButton bttnDirectionLeft = new JButton("Left");
		bttnDirectionLeft.setEnabled(false);
		bttnDirectionLeft.setFont(new Font("Consolas", Font.PLAIN, 12));
		bttnDirectionLeft.setBounds(50, 200, 80, 20);
		frmDungeonKeep.getContentPane().add(bttnDirectionLeft);
		
		JButton bttnDirectionRight = new JButton("Right");
		bttnDirectionRight.setEnabled(false);
		bttnDirectionRight.setFont(new Font("Consolas", Font.PLAIN, 12));
		bttnDirectionRight.setBounds(150, 200, 80, 20);
		frmDungeonKeep.getContentPane().add(bttnDirectionRight);
		
		JButton bttnDirectionDown = new JButton("Down");
		bttnDirectionDown.setEnabled(false);
		bttnDirectionDown.setFont(new Font("Consolas", Font.PLAIN, 12));
		bttnDirectionDown.setBounds(100, 230, 80, 20);
		frmDungeonKeep.getContentPane().add(bttnDirectionDown);
		
		JButton bttnExitGame = new JButton("Exit Game");
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
