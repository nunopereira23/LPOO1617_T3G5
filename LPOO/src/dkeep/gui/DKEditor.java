package dkeep.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;

public class DKEditor extends JFrame {

	private JPanel contentPane;
	
	DKGraphics img_map;
	
	private DKGraphics img_selected;
	
	private DKGraphics img_hero;
	private JButton bttnHero;
	private JCheckBox chckbxHeroArmed;
	
	private DKGraphics img_guard;
	private JButton bttnGuard;
	
	private DKGraphics img_ogre;
	private JButton bttnOgre;
	
	private DKGraphics img_wall;
	private JButton bttnWall;
	
	private DKGraphics img_door;
	private JButton bttnDoor;
	private JTextField txtfldDoorID;
	
	private DKGraphics img_key;
	private JButton bttnKey;
	private DKGraphics img_lever;
	private JButton bttnLever;
	private JTextField txtfldKeyID;
	
	private JButton bttnErase;
	
	private JTextField txtfldMapXSize;
	private JTextField txtfldMapYSize;
	
	// Missing buttons
	private JButton bttnGenerate;

	private DKEditor this_ = this;
	private DKEditorSettings settings;
	
	private int door_id = 0;
	private int key_id = 0;
	
	private int[] hero_pos = {};
	private boolean hero_armed = false;
	
	int[][] guard_pos = {};
	int[][] guard_move = {};
	
	int[][] ogre_pos = {};
	int[][] club_pos = {};
	
	char[][] map = {};
	private int[][] map_doors = {};
	private int[][] map_keys = {};
	private JButton btnLoadMap;
	private JButton btnSaveMap;
	private JButton btnTestLevel;
	
	/**
	 * Create the frame.
	 */
	public DKEditor(DKInterfaceV2 window) {
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent mos_evn) {
				img_selected.setVisible(false);
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Level Editor");
		setResizable(false);
		setBounds(100, 100, 1280, 720);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		// =====
		
		img_selected = new DKGraphics();
		img_selected.setDraw(' ');
		img_selected.setSize(64, 64);
		img_selected.setVisible(false);
		contentPane.add(img_selected);
		
		// =====
		
		img_map = new DKGraphics();
		img_map.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent mos_evn) {
				if (map.length > 0 && img_selected.getDraw() != ' ')
				{
					img_selected.setVisible(true);
					img_selected.setBounds(mos_evn.getX() + 140 - (1024 / (2 * map[0].length)), mos_evn.getY() + 20 - (640 / (2 * map.length)), 1024 / map[0].length, 640 / map.length);
				}
			}
		});
		img_map.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mos_evn) {
				if (map.length > 0 && img_selected.getDraw() != ' ')
				{
					int x_mouse = (int) (map[0].length * ((double) mos_evn.getX() / 1024.0));
					int y_mouse = (int) (map.length * ((double) mos_evn.getY() / 640.0));
					
					if (img_selected.getDraw() != '?')
					{
						switch (img_selected.getDraw())
						{
							case 'H':
							case 'A':
								if (x_mouse != 0 && x_mouse != map[0].length - 1 && y_mouse != 0 && y_mouse != map.length - 1) {									
									map[y_mouse][x_mouse] = img_selected.getDraw();
									hero_pos = new int[]{x_mouse, y_mouse};
									hero_armed = (img_selected.getDraw() == 'A');
									bttnHero.setEnabled(false);
									img_selected.setDraw(' ');
								}
								break;
							case 'G':
								if (x_mouse != 0 && x_mouse != map[0].length - 1 && y_mouse != 0 && y_mouse != map.length - 1) {
									map[y_mouse][x_mouse] = 'G';
									
									int[][] new_guard_pos = new int[guard_pos.length + 1][];
									int[][] new_guard_move = new int[guard_move.length + 1][];
									for (int i = 0; i < guard_pos.length; ++i)
									{
										new_guard_pos[i] = guard_pos[i];
										new_guard_move[i] = guard_move[i];										
									}
									new_guard_pos[guard_pos.length] = new int[]{x_mouse, y_mouse};
									guard_pos = new_guard_pos;
									guard_move = new_guard_move;
									
									settings = new DKEditorSettings(this_, 0);
									this_.setEnabled(false);
									settings.setVisible(true);
								}
								break;
							case 'O':
								if (x_mouse != 0 && x_mouse != map[0].length - 1 && y_mouse != 0 && y_mouse != map.length - 1) {
									map[y_mouse][x_mouse] = 'O';

									int[][] new_ogre_pos = new int[ogre_pos.length + 1][];
									int[][] new_club_pos = new int[club_pos.length + 1][];
									for (int i = 0; i < ogre_pos.length; ++i)
									{
										new_ogre_pos[i] = ogre_pos[i];
										new_club_pos[i] = club_pos[i];										
									}
									new_ogre_pos[ogre_pos.length] = new int[]{x_mouse, y_mouse};
									new_club_pos[club_pos.length] = new int[]{x_mouse, y_mouse};
									ogre_pos = new_ogre_pos;
									club_pos = new_club_pos;
									
									settings = new DKEditorSettings(this_, 1);
									this_.setEnabled(false);
									settings.setVisible(true);
								}
								break;
							case 'X':
								if (x_mouse != 0 && x_mouse != map[0].length - 1 && y_mouse != 0 && y_mouse != map.length - 1) {
									map[y_mouse][x_mouse] = 'X';
								}
								break;
							case 'I':
								if ((x_mouse == 0 || x_mouse == map[0].length - 1 || y_mouse == 0 || y_mouse == map.length - 1) && door_id == 0) {
									img_selected.setDraw(' ');
									txtfldDoorID.setText("");
									txtfldDoorID.requestFocusInWindow();									
								}
								else {
									map[y_mouse][x_mouse] = 'I';
									map_doors[y_mouse][x_mouse] = door_id;
								}
								break;
							case 'k':
							case 'l':
								if (x_mouse != 0 && x_mouse != map[0].length - 1 && y_mouse != 0 && y_mouse != map.length - 1) {
									map[y_mouse][x_mouse] = img_selected.getDraw();
									map_keys[y_mouse][x_mouse] = ((img_selected.getDraw() == 'k') ? key_id : -key_id);
									img_selected.setDraw(' ');
								}
								break;							
						}
					}
					else
					{
						switch(map[y_mouse][x_mouse])
						{
							case 'H':
							case 'A':
								bttnHero.setEnabled(true);
								break;
							case 'G':
								for (int i = 0; i < guard_pos.length; ++i)
								{
									if (guard_pos[i][0] == x_mouse && guard_pos[i][1] == y_mouse)
									{
										int[][] new_guard_pos = new int[guard_pos.length - 1][];
										int[][] new_guard_move = new int[guard_move.length - 1][];
										for (int j = 0, k = 0; j < guard_pos.length; ++j)
										{
											if (i != j)
											{
												new_guard_pos[k] = guard_pos[j];
												new_guard_move[k] = guard_move[j];
												++k;
											}
										}
										guard_pos = new_guard_pos;
										guard_move = new_guard_move;
									}
								}
								break;
							case 'O':
								for (int i = 0; i < ogre_pos.length; ++i)
								{
									if (ogre_pos[i][0] == x_mouse && ogre_pos[i][1] == y_mouse)
									{
										map[club_pos[i][1]][club_pos[i][0]] = ' ';
										
										int[][] new_ogre_pos = new int[ogre_pos.length - 1][];
										int[][] new_club_pos = new int[club_pos.length - 1][];
										for (int j = 0, k = 0; j < ogre_pos.length; ++j)
										{
											if (i != j)
											{
												new_ogre_pos[k] = ogre_pos[j];
												new_club_pos[k] = club_pos[j];
												++k;
											}
										}
										ogre_pos = new_ogre_pos;
										club_pos = new_club_pos;
										
										break;
									}
								}
								break;
							case '*':
								for (int i = 0; i < club_pos.length; ++i)
								{
									if (club_pos[i][0] == x_mouse && club_pos[i][1] == y_mouse)
									{
										map[ogre_pos[i][1]][ogre_pos[i][0]] = ' ';
										
										int[][] new_ogre_pos = new int[ogre_pos.length - 1][];
										int[][] new_club_pos = new int[club_pos.length - 1][];
										for (int j = 0, k = 0; j < ogre_pos.length; ++j)
										{
											if (i != j)
											{
												new_ogre_pos[k] = ogre_pos[j];
												new_club_pos[k] = club_pos[j];
												++k;
											}
										}
										ogre_pos = new_ogre_pos;
										club_pos = new_club_pos;
										
										break;
									}
								}
								break;
							case 'X':
								if (x_mouse == 0 || x_mouse == map[0].length - 1 || y_mouse == 0 || y_mouse == map.length - 1) {									
									return;
								}
								break;
							case 'I':
								map_doors[y_mouse][x_mouse] = 0;
								if (x_mouse == 0 || x_mouse == map[0].length - 1 || y_mouse == 0 || y_mouse == map.length - 1) {
									map[y_mouse][x_mouse] = 'X';
									update();
									return;
								}								
								break;
							case 'k':
							case 'l':
								map_keys[y_mouse][x_mouse] = 0;							
								break;
						}
						map[y_mouse][x_mouse] = ' ';
					}
					update();
				}
			}
		});
		img_map.setBounds(140, 20, 1024, 640);
		contentPane.add(img_map);
		
		// =====
		
		img_hero = new DKGraphics();
		img_hero.setDraw('H');
		img_hero.setBounds(1200, 10, 64, 64);
		img_hero.setLayout(null);		
		contentPane.add(img_hero);
		
		bttnHero = new JButton("");
		bttnHero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {	
				if (img_selected.getDraw() != 'H' && img_selected.getDraw() != 'A')
				{
					img_selected.setDraw(hero_armed ? 'A' : 'H');
				}
				else
				{
					img_selected.setDraw(' ');					
				}
				
			}
		});
		bttnHero.setContentAreaFilled(false);
		bttnHero.setBounds(0, 0, 64, 64);
		img_hero.add(bttnHero);
		
		chckbxHeroArmed = new JCheckBox("Armed");
		chckbxHeroArmed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				img_hero.setDraw((hero_armed = !hero_armed) ? 'A' : 'H');
				img_selected.setDraw(' ');
			}
		});
		chckbxHeroArmed.setFont(new Font("Consolas", Font.PLAIN, 12));
		chckbxHeroArmed.setBounds(1200, 79, 64, 20);
		contentPane.add(chckbxHeroArmed);
		
		// =====
		
		img_guard = new DKGraphics();
		img_guard.setDraw('G');
		img_guard.setLayout(null);
		img_guard.setBounds(1200, 114, 64, 64);
		contentPane.add(img_guard);
		
		bttnGuard = new JButton("");
		bttnGuard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				if (img_selected.getDraw() != 'G')
				{
					img_selected.setDraw('G');
				}
				else
				{
					img_selected.setDraw(' ');
				}
			}
		});
		bttnGuard.setContentAreaFilled(false);
		bttnGuard.setBounds(0, 0, 64, 64);
		img_guard.add(bttnGuard);
		
		// =====
		
		img_ogre = new DKGraphics();
		img_ogre.setDraw('O');
		img_ogre.setLayout(null);
		img_ogre.setBounds(1200, 188, 64, 64);
		contentPane.add(img_ogre);
		
		bttnOgre = new JButton("");
		bttnOgre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				if (img_selected.getDraw() != 'O')
				{
					img_selected.setDraw('O');
				}
				else
				{
					img_selected.setDraw(' ');
				}
			}
		});
		bttnOgre.setContentAreaFilled(false);
		bttnOgre.setBounds(0, 0, 64, 64);
		img_ogre.add(bttnOgre);
		
		// =====
		
		img_wall = new DKGraphics();
		img_wall.setDraw('X');
		img_wall.setLayout(null);
		img_wall.setBounds(1200, 262, 64, 64);
		contentPane.add(img_wall);
		
		bttnWall = new JButton("");
		bttnWall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				if (img_selected.getDraw() != 'X')
				{
					img_selected.setDraw('X');
				}
				else
				{
					img_selected.setDraw(' ');
				}
			}
		});
		bttnWall.setContentAreaFilled(false);
		bttnWall.setBounds(0, 0, 64, 64);
		img_wall.add(bttnWall);
		
		// =====
		
		JLabel lblDoorID = new JLabel("ID:");
		lblDoorID.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblDoorID.setBounds(1200, 583, 24, 20);
		contentPane.add(lblDoorID);
		
		img_door = new DKGraphics();
		img_door.setDraw('I');
		img_door.setLayout(null);
		img_door.setBounds(1200, 336, 64, 64);
		contentPane.add(img_door);
		
		bttnDoor = new JButton("");
		bttnDoor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				try {
					door_id = Integer.parseInt(txtfldDoorID.getText());
				}
				catch (NumberFormatException exc) {
					txtfldDoorID.setText("0");
				}
				
				if (img_selected.getDraw() != 'I')
				{
					img_selected.setDraw('I');
				}
				else
				{
					img_selected.setDraw(' ');
				}
			}
		});
		bttnDoor.setContentAreaFilled(false);
		bttnDoor.setBounds(0, 0, 64, 64);
		img_door.add(bttnDoor);
		
		txtfldDoorID = new JTextField();
		txtfldDoorID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent foc_evn) {
				img_selected.setDraw(' ');
			}
		});
		txtfldDoorID.setText("0");
		txtfldDoorID.setColumns(10);
		txtfldDoorID.setFont(new Font("Consolas", Font.PLAIN, 12));
		txtfldDoorID.setBounds(1224, 405, 40, 20);
		contentPane.add(txtfldDoorID);		
		
		// =====
		
		JLabel lblKeyID = new JLabel("ID:");
		lblKeyID.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblKeyID.setBounds(1200, 405, 24, 20);
		contentPane.add(lblKeyID);
		
		img_key = new DKGraphics();
		img_key.setDraw('k');
		img_key.setLayout(null);
		img_key.setBounds(1200, 440, 64, 64);
		contentPane.add(img_key);
		
		bttnKey = new JButton("");
		bttnKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				boolean noexcept = true;
				try {
					key_id = Integer.parseInt(txtfldKeyID.getText());
					if (key_id == 0) {
						throw new NumberFormatException(); 
					}
				}
				catch (NumberFormatException exc) {
					noexcept = false;
					txtfldKeyID.setText("");
				}
				
				if (noexcept)
				{
					key_id = Math.abs(key_id);
					if (img_selected.getDraw() != 'k')
					{
						img_selected.setDraw('k');
					}
					else
					{
						img_selected.setDraw(' ');
					}
				}
				else
				{
					txtfldKeyID.requestFocusInWindow();
				}
			}
		});
		bttnKey.setContentAreaFilled(false);
		bttnKey.setBounds(0, 0, 64, 64);
		img_key.add(bttnKey);
		
		img_lever = new DKGraphics();
		img_lever.setDraw('l');
		img_lever.setLayout(null);
		img_lever.setBounds(1200, 514, 64, 64);
		contentPane.add(img_lever);
		
		bttnLever = new JButton("");
		bttnLever.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				boolean noexcept = true;
				try {
					key_id = Integer.parseInt(txtfldKeyID.getText());
					if (key_id == 0) {
						throw new NumberFormatException(); 
					}
				}
				catch (NumberFormatException exc) {
					noexcept = false;
					txtfldKeyID.setText("");
				}
				
				if (noexcept)
				{
					key_id = -Math.abs(key_id);
					if (img_selected.getDraw() != 'l')
					{
						img_selected.setDraw('l');
					}
					else
					{
						img_selected.setDraw(' ');
					}
				}
				else
				{
					txtfldKeyID.requestFocusInWindow();
				}
			}
		});
		bttnLever.setContentAreaFilled(false);
		bttnLever.setBounds(0, 0, 64, 64);
		img_lever.add(bttnLever);
		
		txtfldKeyID = new JTextField();
		txtfldKeyID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent foc_evn) {
				img_selected.setDraw(' ');
			}
		});
		txtfldKeyID.setColumns(10);
		txtfldKeyID.setFont(new Font("Consolas", Font.PLAIN, 12));		
		txtfldKeyID.setBounds(1224, 583, 40, 20);
		contentPane.add(txtfldKeyID);
		
		// =====

		JLabel lblMapXSize = new JLabel("Width:");
		lblMapXSize.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblMapXSize.setBounds(10, 625, 48, 20);
		contentPane.add(lblMapXSize);
		
		txtfldMapXSize = new JTextField();
		txtfldMapXSize.setText("5");
		txtfldMapXSize.setColumns(10);
		txtfldMapXSize.setFont(new Font("Consolas", Font.PLAIN, 12));
		txtfldMapXSize.setBounds(74, 625, 32, 20);
		contentPane.add(txtfldMapXSize);

		JLabel lblMapYSize = new JLabel("Height:");
		lblMapYSize.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblMapYSize.setBounds(10, 655, 56, 20);
		contentPane.add(lblMapYSize);
		
		txtfldMapYSize = new JTextField();
		txtfldMapYSize.setText("5");
		txtfldMapYSize.setColumns(10);
		txtfldMapYSize.setFont(new Font("Consolas", Font.PLAIN, 12));
		txtfldMapYSize.setBounds(74, 655, 32, 20);
		contentPane.add(txtfldMapYSize);
		
		bttnGenerate = new JButton("Generate");		
		bttnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				boolean noexcept = true;
				try {
					Integer.parseInt(txtfldMapXSize.getText());
				}
				catch (NumberFormatException exc) {
					noexcept = false;
					txtfldMapXSize.setText("");
				}
				try {
					Integer.parseInt(txtfldMapYSize.getText());
				}
				catch (NumberFormatException exc) {
					noexcept = false;
					txtfldMapYSize.setText("");
				}
				if (noexcept)
				{
					map = new char[Integer.parseInt(txtfldMapYSize.getText())][Integer.parseInt(txtfldMapXSize.getText())];
					map_doors = new int[Integer.parseInt(txtfldMapYSize.getText())][Integer.parseInt(txtfldMapXSize.getText())];
					map_keys = new int[Integer.parseInt(txtfldMapYSize.getText())][Integer.parseInt(txtfldMapXSize.getText())];
					for (int y = 0; y < Integer.parseInt(txtfldMapYSize.getText()); ++y)
					{
						for (int x = 0; x < Integer.parseInt(txtfldMapXSize.getText()); ++x)
						{
							if (y == 0 || y == Integer.parseInt(txtfldMapYSize.getText()) - 1 || x == 0 || x == Integer.parseInt(txtfldMapXSize.getText()) - 1)
							{
								map[y][x] = 'X';
							}
							else
							{
								map[y][x] = ' ';
							}
						}
					}
					img_map.setDraw(map);
					reset();					
					update();
				}
			}
		});
		bttnGenerate.setFont(new Font("Consolas", Font.PLAIN, 12));
		bttnGenerate.setBounds(10, 551, 96, 64);
		contentPane.add(bttnGenerate);
		
		bttnErase = new JButton("Erase");
		bttnErase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				if (img_selected.getDraw() != '?')
				{
					img_selected.setDraw('?');
				}
				else
				{
					img_selected.setDraw(' ');
				}					
			}
		});
		bttnErase.setContentAreaFilled(false);
		bttnErase.setFont(new Font("Consolas", Font.PLAIN, 10));
		bttnErase.setBounds(1200, 618, 64, 64);
		contentPane.add(bttnErase);
		
		btnLoadMap = new JButton("Load Map");
		btnLoadMap.setFont(new Font("Consolas", Font.PLAIN, 12));
		btnLoadMap.setBounds(10, 188, 96, 64);
		contentPane.add(btnLoadMap);
		
		btnSaveMap = new JButton("Save Map");
		btnSaveMap.setFont(new Font("Consolas", Font.PLAIN, 12));
		btnSaveMap.setBounds(10, 114, 96, 64);
		contentPane.add(btnSaveMap);
		
		btnTestLevel = new JButton("Test Map");
		btnTestLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				
			}
		});
		btnTestLevel.setFont(new Font("Consolas", Font.PLAIN, 12));
		btnTestLevel.setBounds(10, 10, 96, 64);
		contentPane.add(btnTestLevel);
	}
	
	void reset() {		
		door_id = 0;
		key_id = 0;
		
		hero_pos = new int[]{};
		hero_armed = false;
		guard_pos = new int[][]{};
		guard_move = new int[][]{};
		ogre_pos = new int[][]{};
		club_pos = new int[][]{};
		
		img_selected.setVisible(false);
		img_selected.setDraw(' ');
		
		bttnHero.setEnabled(true);					
		bttnGuard.setEnabled(true);
		bttnWall.setEnabled(true);
		bttnDoor.setEnabled(true);
		bttnKey.setEnabled(true);
		bttnLever.setEnabled(true);
		bttnErase.setEnabled(true);
		chckbxHeroArmed.setSelected(false);
		txtfldDoorID.setEnabled(true);
		txtfldKeyID.setEnabled(true);
		txtfldDoorID.setText("0");
		txtfldKeyID.setText("");
	}
	
	void update() {
		for (int i = 0; i < guard_pos.length; ++i)
		{
			map[guard_pos[i][1]][guard_pos[i][0]] = 'G';
		}
		for (int i = 0; i < club_pos.length; ++i)
		{
			map[club_pos[i][1]][club_pos[i][0]] = '*';
		}
		for (int i = 0; i < ogre_pos.length; ++i)
		{
			map[ogre_pos[i][1]][ogre_pos[i][0]] = 'O';
		}
		img_map.repaint();
	}
}
