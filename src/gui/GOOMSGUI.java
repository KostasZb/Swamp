package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import game.GridNotification;
import game.Notification;
import game.Observer;
import game.Playable;
import game.TextNotification;
import game.TheGame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;

public class GOOMSGUI extends JFrame implements Observer {

	
	// Setting the game as a class implementing the playable interface
	private Playable game;
	// Defaulting to showing notifications
	private boolean showNotifications = true;
	private JPanel contentPane;
	private JPanel panel;
	private JButton btnNewButton;
	private Border border = BorderFactory.createLineBorder(Color.black, 1);
	// Background image for the gui
	//private  Image imageBackground = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("background.png"));
	// Image for the hero of the game
	//private  Image imageHero = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("HeroImage.gif"));
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	private JButton btnNewButton_5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GOOMSGUI frame = new GOOMSGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GOOMSGUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 800);
		contentPane = new JPanel() {
			

			// Drawing the background
			@Override
			public void paintComponent(Graphics g) {
				Image imageBackground = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("background.png"));
				g.drawImage(imageBackground, 0, 0, this);
			}
		};

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getPanel());
		contentPane.add(getBtnNewButton());
		contentPane.add(getBtnNewButton_1());
		contentPane.add(getBtnNewButton_2());
		contentPane.add(getBtnNewButton_3());
		contentPane.add(getBtnNewButton_4());
		contentPane.add(getBtnNewButton_5());

	}

	/*
	 * Receiving updates from the observable and updating the grid or showing a text
	 * notification, according to the type of the update
	 */
	@Override
	public void update(Notification notification) {
		if (notification instanceof TextNotification && this.showNotifications == true) {
			JOptionPane.showMessageDialog(this, notification.getContains());
		} else if (notification instanceof GridNotification) {
			GridNotification gridNotification = (GridNotification) notification;
			this.updateGrid(gridNotification.getRowNumber(), gridNotification.getSquareNumber(),
					gridNotification.getContains());
		}
	}

	/*
	 * Updating the grid contents
	 */
	public void updateGrid(int rowNum, int squareNum, String contains) {
		// Going through all the panels of the grid and checking if the panel's position
		// matches the position of the square that the update was received for
		for (int counter = 0; counter < this.panel.getComponentCount(); counter++) {
			CustomPanel customPanel = (CustomPanel) this.panel.getComponent(counter);
			// Checking if the the hero is located in this panel and updating the image
			// shown
			String hero = "HERO";
			// The monsterCounter variable holds the amount of monsters in the square
			int monsterCounter = 0;
			if ((customPanel.getRowNumber() == rowNum) && (customPanel.getSquareNumber() == squareNum)) {
				customPanel.removeAll();
				customPanel.revalidate();
				customPanel.repaint();
				monsterCounter = 0;
				if (contains != "") {
					if (game.checkInput(contains, hero) > 0) {
						JLabel label = new JLabel();
						customPanel.add(label);
						label.setIcon(new ImageIcon(getClass().getClassLoader().getResource("HeroImage.gif")));
					}
					// Going through all the available monsters that are available for the gui and
					// checking if any of them are contained in the current square
					for (Monsters tempMonsterName : Monsters.values()) {
						if (game.checkInput(contains, tempMonsterName.toString()) > 0) {
							// The sameMonsterOccurencies holds the amount of monsters of the same type in
							// the square
							int sameMonsterOccurecies = game.checkInput(contains, tempMonsterName.toString());
							// Adding the amount of same monsters in the square to the total of monsters
							monsterCounter = monsterCounter + sameMonsterOccurecies;
							// Showing the image of only the first two elements of the game(Hero or
							// monsters)
							for (int counterTwo = 0; counterTwo < sameMonsterOccurecies; counterTwo++) {
								if (customPanel.getComponentCount() < 2) {
									JLabel label = new JLabel();
									customPanel.add(label);
									String imageSource = tempMonsterName.imageSource();
									label.setIcon((new ImageIcon(getClass().getClassLoader().getResource(imageSource))));
									// If the there are more than 2 elements, the total number of monsters is shown
									// on top of the second element
								} else {
									JLabel label = (JLabel) customPanel.getComponent(1);
									label.setText("" + monsterCounter);
									label.setVerticalTextPosition(SwingConstants.BOTTOM);
									label.setIconTextGap(-30);
									label.setForeground(Color.WHITE);
									label.setFont(new Font("Serif", Font.BOLD, 20));
								}
							}
						}
					}
				}
			}
		}
	}

	// creating a new game
	public void registerNewGame() {
		panel.removeAll();
		Playable newGame = new TheGame();
		this.game = newGame;
		setGrid();
		game.registerObserver(this);
		this.game.startGame();
	}

	// Getting the panel that holds the grid
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBounds(106, 101, 600, 400);
			panel.setLayout(new GridLayout(4, 4, 0, 0));
			panel.setOpaque(false);
		}
		return panel;
	}

	// Setting the grid for the panel
	public void setGrid() {
		for (int counterRow = 0; counterRow < game.getGridSize(); counterRow++) {
			for (int counterSquare = 0; counterSquare < game.getGridSize(); counterSquare++) {
				JPanel tempLabel = new CustomPanel(counterRow, counterSquare);
				tempLabel.setBorder(border);
				this.panel.add(tempLabel);
			}
		}
	}

	// Button to take turns
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Next Round");
			btnNewButton.setVisible(false);
			btnNewButton.setForeground(new Color(51, 102, 0));
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					game.takingTurns();
				}
			});
			btnNewButton.setBounds(357, 530, 120, 23);
		}
		return btnNewButton;
	}

	// Button to start a new game
	private JButton getBtnNewButton_1() {
		if (btnNewButton_1 == null) {
			btnNewButton_1 = new JButton("New Game");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnNewButton_2.setVisible(true);
					registerNewGame();
					btnNewButton.setVisible(true);
					btnNewButton_4.setVisible(true);
				}
			});
			btnNewButton_1.setBounds(106, 67, 113, 23);
		}
		return btnNewButton_1;
	}

	// button to save the game
	private JButton getBtnNewButton_2() {
		if (btnNewButton_2 == null) {
			btnNewButton_2 = new JButton("Save Game");
			Observer gui = this;
			btnNewButton_2.setVisible(false);
			btnNewButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					game.removeObserver(gui);
					game.serialize();
					game.registerObserver(gui);
				}
			});
			btnNewButton_2.setBounds(247, 67, 113, 23);
		}
		return btnNewButton_2;
	}

	// button to load the last saved game
	private JButton getBtnNewButton_3() {
		if (btnNewButton_3 == null) {
			btnNewButton_3 = new JButton("Load Game");
			Observer gui = this;
			btnNewButton_3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					registerNewGame();
					btnNewButton_2.setVisible(true);
					game.removeObserver(gui);
					panel.removeAll();
					game = game.deserialize();
					setGrid();
					game.registerObserver(gui);
					game.notifyForAllGrid();
					btnNewButton.setVisible(true);
					btnNewButton_4.setVisible(true);

				}
			});
			btnNewButton_3.setBounds(388, 67, 113, 23);
		}
		return btnNewButton_3;
	}

	// Button to change the hero's mood
	private JButton getBtnNewButton_4() {
		if (btnNewButton_4 == null) {
			btnNewButton_4 = new JButton("Change Mood");
			btnNewButton_4.setVisible(false);
			btnNewButton_4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					game.changeHeroMood();
				}
			});
			btnNewButton_4.setBounds(106, 530, 113, 23);
		}
		return btnNewButton_4;
	}

	// Button to switch between showing messages
	private JButton getBtnNewButton_5() {
		if (btnNewButton_5 == null) {
			btnNewButton_5 = new JButton("Toggle messages");
			btnNewButton_5.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showNotifications = !(showNotifications);
				}
			});
			btnNewButton_5.setBounds(525, 67, 146, 23);
		}
		return btnNewButton_5;
	}
}
