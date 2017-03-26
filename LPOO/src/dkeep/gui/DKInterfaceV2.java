package dkeep.gui;

import dkeep.logic.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Dimension;
import javax.swing.UIManager;

public class DKInterfaceV2
{
	private static final int x_border_size_ = 0x10; 
	private static final int y_border_size_ = 0x27; 
	
	private DungeonKeep.State game_state = DungeonKeep.State.GAME_START; // Acts as null
	private DungeonKeep level;
	
	private int level_number = 0;
	private int new_guard_type = 0;
	private int new_ogre_number = 0;
	private int guard_type = 0;
	private int ogre_number = 0;
	
	DKInterfaceV2 this_ = this;
	DKGraphics graphics;
	DKSettings settings;
	DKEditor editor;
	
	private JFrame frmDungeonKeep = new JFrame();
	JButton bttnNewGame = new JButton("New Game");
	JButton bttnSettings = new JButton("Settings");
	JButton bttnRestart = new JButton("Restart");
	JButton bttnNext = new JButton("Next");
	JButton bttnMenu = new JButton("Menu");
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DKInterfaceV2 window = new DKInterfaceV2();
					window.getFrame().setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DKInterfaceV2() { 
		initialize();
	}
	
	public void update()
	{
		graphics.repaint();
		
		switch (game_state)
		{
			case LEVEL_PLAYING:
				graphics.requestFocusInWindow();
				break;
			case LEVEL_COMPLETED:
			case GAME_COMPLETED:
				graphics.setSize(getWidth(), getHeight());
				bttnRestart.setVisible(false);
				bttnMenu.setVisible(false);
				bttnNext.setVisible(true);
				break;
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		graphics = new DKGraphics(this);		
		settings = new DKSettings(this, false);
		editor = new DKEditor(this);
		editor.setVisible(true);
		
		graphics.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent key_evn) {
				if (game_state == DungeonKeep.State.LEVEL_PLAYING)
				{
					switch (key_evn.getKeyCode())
					{
						case KeyEvent.VK_UP:
							game_state = level.update("w");
							break;
						case KeyEvent.VK_LEFT:
							game_state = level.update("a");
							break;
						case KeyEvent.VK_RIGHT:
							game_state = level.update("d");
							break;
						case KeyEvent.VK_DOWN:
							game_state = level.update("s");
							break;
						default:
							return;
					}
					update();
				}
			}
			@Override
			public void keyTyped(KeyEvent key_evn) {
			}
			@Override
			public void keyReleased(KeyEvent key_evn) {
			}
		});
		
		getFrame().addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent foc_evn) {
				if (settings.available)
				{
					new_ogre_number = settings.ogre_number;
					new_guard_type = settings.guard_type;
				}
			}
		});
		getFrame().setTitle("Dungeon Keep");
		getFrame().setMinimumSize(new Dimension(640, 360));
		getFrame().setBounds(0, 0, 640, 360);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().getContentPane().setLayout(null);
		
		bttnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				if (new_guard_type * new_ogre_number == 0) {
					settings = new DKSettings(this_, true);
					getFrame().setEnabled(false);
					settings.setVisible(true);
				}
				else {
					game_state = DungeonKeep.State.LEVEL_PLAYING;
					level = new DungeonKeep(level_number = 0, guard_type = new_guard_type, ogre_number = new_ogre_number);
					new_guard_type = new_ogre_number = 0;
					graphics.setSize(getWidth(), getHeight() - 30);
					bttnNewGame.setVisible(false);
					bttnSettings.setVisible(false);
					bttnRestart.setVisible(true);
					bttnMenu.setVisible(true);
					graphics.reset();
					update();
				}
			}
		});
		bttnNewGame.setFocusable(false);
		bttnNewGame.setBackground(UIManager.getColor("Button.background"));
		bttnNewGame.setFont(new Font("Consolas", Font.PLAIN, 12));
		bttnNewGame.setBounds(10, 271, 200, 40);
		getFrame().getContentPane().add(bttnNewGame);
		
		bttnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				settings = new DKSettings(this_, false);
				getFrame().setEnabled(false);
				settings.setVisible(true);
			}
		});
		bttnSettings.setFocusable(false);
		bttnSettings.setBackground(UIManager.getColor("Button.background"));
		bttnSettings.setFont(new Font("Consolas", Font.PLAIN, 12));
		bttnSettings.setBounds(414, 271, 200, 40);
		getFrame().getContentPane().add(bttnSettings);
		
		bttnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				game_state = DungeonKeep.State.LEVEL_PLAYING;
				level = new DungeonKeep(level_number, guard_type, ogre_number);
				graphics.reset();
				update();
			}
		});
		bttnRestart.setVisible(false);
		bttnRestart.setFocusable(false);
		bttnRestart.setBackground(UIManager.getColor("Button.background"));
		bttnRestart.setFont(new Font("Consolas", Font.PLAIN, 12));
		bttnRestart.setBounds(260, 296, 100, 20);
		getFrame().getContentPane().add(bttnRestart);
		
		bttnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				switch(game_state)
				{
					case LEVEL_COMPLETED:
						game_state = DungeonKeep.State.LEVEL_PLAYING;
						level = new DungeonKeep(++level_number, guard_type, ogre_number);
						graphics.setSize(getWidth(), getHeight() - 30);
						bttnRestart.setVisible(true);
						bttnMenu.setVisible(true);
						bttnNext.setVisible(false);
						graphics.reset();
						update();
						break;
					case GAME_COMPLETED:
						game_state = DungeonKeep.State.GAME_START;						
						bttnNewGame.setVisible(true);
						bttnSettings.setVisible(true);
						bttnNext.setVisible(false);
						graphics.reset();
						graphics.repaint();
						break;
				}
			}
		});
		bttnNext.setVisible(false);
		bttnNext.setFocusable(false);
		bttnNext.setBackground(UIManager.getColor("Button.background"));
		bttnNext.setFont(new Font("Consolas", Font.PLAIN, 12));
		bttnNext.setBounds(414, 271, 200, 40);
		getFrame().getContentPane().add(bttnNext);
		
		bttnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act_evn) {
				game_state = DungeonKeep.State.GAME_START;
				graphics.setSize(getWidth(), getHeight());
				bttnNewGame.setVisible(true);
				bttnSettings.setVisible(true);
				bttnRestart.setVisible(false);
				bttnMenu.setVisible(false);
				graphics.repaint();
			}
		});		
		bttnMenu.setVisible(false);
		bttnMenu.setFocusable(false);
		bttnMenu.setBackground(UIManager.getColor("Button.background"));
		bttnMenu.setFont(new Font("Consolas", Font.PLAIN, 12));
		bttnMenu.setBounds(519, 296, 100, 20);
		getFrame().getContentPane().add(bttnMenu);
		
		getFrame().addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent com_evn) {
				switch (game_state)
				{
					case GAME_START:
					case LEVEL_COMPLETED:
					case GAME_COMPLETED:
						graphics.setSize(getWidth(), getHeight());
						break;
					case GAME_OVER:
					case LEVEL_PLAYING:					
						graphics.setSize(getWidth(), getHeight() - 30);
						break;
				}
				bttnNewGame.setBounds(20, getHeight() - 60, 200, 40);
				bttnSettings.setBounds(getWidth() - 220, getHeight() - 60, 200, 40);
				bttnRestart.setBounds(getWidth() / 2 - 50, getHeight() - 25, 100, 20);
				bttnNext.setBounds(getWidth() - 220, getHeight() - 60, 200, 40);
				bttnMenu.setBounds(getWidth() - 105, getHeight() - 25, 100, 20);
				graphics.repaint();
			}
		});		
		getFrame().getContentPane().add(graphics);
	}

	int getWidth() {
		return getFrame().getWidth() - x_border_size_;
	}

	int getHeight() {
		return getFrame().getHeight() - y_border_size_;
	}
	
	JFrame getFrame() {
		return frmDungeonKeep;
	}

	DungeonKeep.State getState() {
		return game_state;
	}
	
	void setState(DungeonKeep.State state) {
		game_state = state;
	}
	
	DungeonKeep getLevel() {
		return level;
	}
	
	void setLevel(int level_number, int guard_type, int ogre_number) {
		level = new DungeonKeep(this.level_number = level_number, this.guard_type = guard_type, this.ogre_number = ogre_number);
	}
}
