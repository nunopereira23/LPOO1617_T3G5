package dkeep.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dkeep.logic.DungeonKeep;

public class DKSettings extends JFrame {

	private JPanel contentPane;
	private JComboBox cmbbxNumberOfOgres;
	private JComboBox cmbbxGuardPersonality;
	public boolean available = false;
	public int ogre_number = 0;
	public int guard_type = 0;
	
	/**
	 * Create the frame.
	 */
	public DKSettings(DKInterfaceV2 window, boolean start) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 320, 160);
		contentPane = new JPanel();
		
		JButton bttnOK = new JButton("OK");
		
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent mos_evn) {
				ogre_number = cmbbxNumberOfOgres.getSelectedIndex() + 1;
				guard_type = cmbbxGuardPersonality.getSelectedIndex() + 1;
				
				if (ogre_number != 0 && guard_type != 0)
				{
					bttnOK.setEnabled(true);
				}
			}
		});
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBounds(100, 100, 320, 160);
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblNumberOfOgres = new JLabel("Number of Ogres:");
		lblNumberOfOgres.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblNumberOfOgres.setFocusable(false);
		lblNumberOfOgres.setBounds(30, 20, 120, 20);
		contentPane.add(lblNumberOfOgres);
		
		JLabel lblGuardPersonality = new JLabel("Guard personality:");
		lblGuardPersonality.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblGuardPersonality.setFocusable(false);
		lblGuardPersonality.setBounds(30, 50, 140, 20);
		contentPane.add(lblGuardPersonality);
		
		cmbbxNumberOfOgres = new JComboBox(new String[]{"1", "2", "3", "4", "5"});
		cmbbxNumberOfOgres.setSelectedIndex(-1);
		cmbbxNumberOfOgres.setFont(new Font("Consolas", Font.PLAIN, 12));
		cmbbxNumberOfOgres.setBounds(170, 20, 40, 20);
		contentPane.add(cmbbxNumberOfOgres);
		
		cmbbxGuardPersonality = new JComboBox(new String[]{"Rookie", "Drunken", "Suspicious"});
		cmbbxGuardPersonality.setSelectedIndex(-1);
		cmbbxGuardPersonality.setFont(new Font("Consolas", Font.PLAIN, 12));
		cmbbxGuardPersonality.setBounds(170, 50, 100, 20);
		contentPane.add(cmbbxGuardPersonality);
		
		bttnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				available = true;
				window.getFrame().setEnabled(true);
				window.getFrame().requestFocusInWindow();
				setVisible(false);
				
				if (start)
				{
					window.setState(DungeonKeep.State.LEVEL_PLAYING);
					window.setLevel(0, guard_type, ogre_number);
					window.graphics.setSize(window.getWidth(), window.getHeight() - 30);
					window.bttnNewGame.setVisible(false);
					window.bttnSettings.setVisible(false);
					window.bttnRestart.setVisible(true);
					window.bttnMenu.setVisible(true);
					window.update();
				}
			}
		});
		bttnOK.setFont(new Font("Consolas", Font.PLAIN, 12));
		bttnOK.setEnabled(false);
		bttnOK.setBounds(240, 90, 60, 30);
		contentPane.add(bttnOK);
	}
}
