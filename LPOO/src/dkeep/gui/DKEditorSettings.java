package dkeep.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dkeep.logic.DungeonKeep;

public class DKEditorSettings extends JFrame {

	private JPanel contentPane;
	private JTextField txtfldGuardMove;
	private JComboBox cmbbxClubPos;
	private JButton bttnOK;
	
	public int[] guard_move = {};
	public int[] club_pos = {};
	
	/**
	 * Create the frame.
	 */
	public DKEditorSettings(DKEditor window, int panel) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("Editor Settings");
		setResizable(false);
		setBounds(100, 100, 320, 160);
		contentPane = new JPanel();
		
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent mos_evn) {
				if (txtfldGuardMove.getText() != "" || cmbbxClubPos.getSelectedIndex() != -1)
				{
					bttnOK.setEnabled(true);
				}
			}
		});
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		bttnOK = new JButton("OK");
		bttnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				char[][] map_copy = new char[window.map.length][];
				for (int i = 0; i < window.map.length; ++i)
				{
					map_copy[i] = new char[window.map[0].length];
					for (int j = 0; j < window.map[0].length; ++j)
					{
						map_copy[i][j] = window.map[i][j];
					}
				}
				
				if (panel == 0)
				{
					int n_num = 0, w_num = 0, e_num = 0, s_num = 0;
					for (int i = 0; i < txtfldGuardMove.getText().length(); ++i)
					{						
						switch (txtfldGuardMove.getText().charAt(i))
						{
							case 'N':
							case 'n':
								++n_num;								
								break;
							case 'W':
							case 'w':
								++w_num;
								break;
							case 'E':
							case 'e':
								++e_num;
								break;
							case 'S':
							case 's':
								++s_num;
								break;
						}
					}
					if (n_num == s_num && w_num == e_num)
					{
						guard_move = new int[txtfldGuardMove.getText().length()];
						for (int i = 0; i < txtfldGuardMove.getText().length(); ++i)
						{						
							switch (txtfldGuardMove.getText().charAt(i))
							{
								case 'N':
								case 'n':
									guard_move[i] = 0;								
									break;
								case 'W':
								case 'w':
									guard_move[i] = 1;
									break;
								case 'E':
								case 'e':
									guard_move[i] = 3;
									break;
								case 'S':
								case 's':
									guard_move[i] = 2;
									break;
							}
						}						
						int[] guard_pos_copy = new int[] {window.guard_pos[window.guard_pos.length - 1][0], window.guard_pos[window.guard_pos.length - 1][1]};
						for (int i = 0; i < guard_move.length; ++i)							
						{
							switch (guard_move[i])
							{
								case 0:
									--guard_pos_copy[1];
									break;
								case 1:
									--guard_pos_copy[0];
									break;
								case 2:
									++guard_pos_copy[1];
									break;
								case 3:
									++guard_pos_copy[0];
									break;
							}
							if (map_copy[guard_pos_copy[1]][guard_pos_copy[0]] == 'X' || map_copy[guard_pos_copy[1]][guard_pos_copy[0]] == 'I')
							{
								guard_move = new int[]{};
								bttnOK.setEnabled(false);
								txtfldGuardMove.requestFocusInWindow();
								// Mostrar msg de erro?
								return;
							}
						}
						window.guard_move[window.guard_move.length - 1] = guard_move;
					}
					else
					{
						bttnOK.setEnabled(false);
						txtfldGuardMove.requestFocusInWindow();
						// Mostrar msg de erro?
						return;
					}
				}
				else
				{
					club_pos = new int [] {window.club_pos[window.club_pos.length - 1][0], window.club_pos[window.club_pos.length - 1][1]}; 
					switch (cmbbxClubPos.getSelectedIndex())
					{
						case 0:
							--club_pos[1];
							break;
						case 1:
							--club_pos[0];
							break;
						case 2:
							++club_pos[0];
							break;
						case 3:
							++club_pos[1];							
							break;
					}
					if (map_copy[club_pos[1]][club_pos[0]] == 'X' || map_copy[club_pos[1]][club_pos[0]] == 'I')
					{
						club_pos = new int[]{};
						bttnOK.setEnabled(false);
						cmbbxClubPos.requestFocusInWindow();
						// Mostrar msg de erro?
						return;
					}
					window.club_pos[window.club_pos.length - 1] = club_pos;
					
					window.map[club_pos[1]][club_pos[0]] = '*';
					window.update();
				}
				
				window.setEnabled(true);
				window.requestFocusInWindow();
				setVisible(false);
			}
		});
		bttnOK.setEnabled(false);
		bttnOK.setFont(new Font("Consolas", Font.PLAIN, 12));
		bttnOK.setBounds(210, 50, 60, 40);
		contentPane.add(bttnOK);
		
		JPanel pnlGuard = new JPanel();
		pnlGuard.setVisible(panel == 0 ? true : false);
		pnlGuard.setBounds(0, 0, 314, 131);		
		pnlGuard.setLayout(null);
		contentPane.add(pnlGuard);
		
		JPanel pnlOgre = new JPanel();
		pnlOgre.setVisible(panel == 1 ? true : false);
		pnlOgre.setBounds(0, 0, 314, 131);		
		pnlOgre.setLayout(null);
		contentPane.add(pnlOgre);
		
		JLabel lblGuardMove = new JLabel("Please indicate the guard's path:");
		lblGuardMove.setFocusable(false);		
		lblGuardMove.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblGuardMove.setBounds(40, 20, 240, 20);
		pnlGuard.add(lblGuardMove);
		
		txtfldGuardMove = new JTextField("");
		txtfldGuardMove.setFont(new Font("Consolas", Font.PLAIN, 12));
		txtfldGuardMove.setBounds(40, 50, 160, 40);
		pnlGuard.add(txtfldGuardMove);
		
		JLabel lblClubPos = new JLabel("Please indicate the club's position:");
		lblClubPos.setFocusable(false);
		lblClubPos.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblClubPos.setBounds(30, 20, 260, 20);
		pnlOgre.add(lblClubPos);
		
		cmbbxClubPos = new JComboBox(new String[]{"North", "West", "East", "South"});
		cmbbxClubPos.setSelectedIndex(-1);
		cmbbxClubPos.setFont(new Font("Consolas", Font.PLAIN, 12));
		cmbbxClubPos.setBounds(40, 50, 160, 40);
		pnlOgre.add(cmbbxClubPos);
	}
}
