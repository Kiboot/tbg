package ms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private String[] clans = {"Tiger Sect", "Serpent Clan", "Crane School"};
    private String[] clanLore = {
        "Tiger Sect: Masters of strength and discipline. Their warriors train relentlessly to dominate in combat.",
        "Serpent Clan: Specialists in cunning and agility. They strike with precision and manipulate the battlefield.",
        "Crane School: Seekers of wisdom and balance. Their approach harmonizes intelligence and technique."
    };
    private int currentClan = 0;

    public GameGUI() {
        setTitle("Wuxia RPG");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createMainMenu(), "MainMenu");
        mainPanel.add(createClanSelectionScreen(), "ClanSelection");
        mainPanel.add(createCharacterCustomizationScreen(), "CharacterCustomization");
        mainPanel.add(createGameScreen(), "GameScreen");

        add(mainPanel);
        cardLayout.show(mainPanel, "MainMenu");
    }

    private JPanel createMainMenu() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Set window size
        setSize(1280, 720);
        
        // Title Label
        JLabel titleLabel = new JLabel("Martial Story", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 40));
        
        // Spacer to push the button panel to the bottom
        //JPanel spacerPanel = new JPanel(); 
        //spacerPanel.setPreferredSize(new Dimension(1280, 500)); // Creates vertical space
        
        // Buttons Panel (Horizontal Layout)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Space between buttons
        
        // Main Content Panel with GridBagLayout for fine control
        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1; // Pushes everything else downward
        contentPanel.add(titleLabel, gbc);
        
        
        //buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Add padding
        String[] buttons = {"New Game", "Save Game", "Load Game", "Credits", "Exit"};
        panel.setLayout(new GridLayout(5, 1));

        for (String label : buttons) {
            JButton btn = new JButton(label);
            btn.setPreferredSize(new Dimension(170, 60)); // Set fixed button size
            btn.setMaximumSize(new Dimension(170, 60)); // Ensure size consistency
            btn.setFont(new Font("SansSerif", Font.BOLD, 22)); // Bigger text for readability
            btn.setAlignmentX(Component.CENTER_ALIGNMENT); // Keep centered
            buttonPanel.add(btn);
            buttonPanel.add(Box.createVerticalStrut(15)); // Adds proper spacing between buttons
        	

            btn.addActionListener(e -> {
                if (label.equals("New Game")) {
                    cardLayout.show(mainPanel, "ClanSelection");
                    mainPanel.getComponent(1).requestFocusInWindow();
                } else if (label.equals("Exit")) {
                    System.exit(0);
                }
            });
        }
        // Set constraints for button panel at the bottom
        gbc.gridy = 1;
        gbc.weighty = 0; // Prevents buttons from taking extra space
        contentPanel.add(buttonPanel, gbc);
        // Position elements
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH); // Aligns buttons to bottom
        return panel;
    }

    private JPanel createClanSelectionScreen() {
        JPanel panel = new JPanel(new BorderLayout());

        // Labels for clan selection
        JLabel clanLabel = new JLabel("Clan: " + clans[currentClan], SwingConstants.CENTER);
        clanLabel.setFont(new Font("Serif", Font.BOLD, 24));

        JLabel loreLabel = new JLabel("<html>" + clanLore[currentClan] + "</html>", SwingConstants.CENTER);
        loreLabel.setFont(new Font("Serif", Font.ITALIC, 16));

        // Buttons for mouse interaction
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton prevButton = new JButton("Previous");
        JButton nextButton = new JButton("Next");
        JButton selectButton = new JButton("Select");

        // Button Actions
        prevButton.addActionListener(e -> {
            currentClan = (currentClan - 1 + clans.length) % clans.length;
            clanLabel.setText("Clan: " + clans[currentClan]);
            loreLabel.setText("<html>" + clanLore[currentClan] + "</html>");
        });

        nextButton.addActionListener(e -> {
            currentClan = (currentClan + 1) % clans.length;
            clanLabel.setText("Clan: " + clans[currentClan]);
            loreLabel.setText("<html>" + clanLore[currentClan] + "</html>");
        });

        selectButton.addActionListener(e -> cardLayout.show(mainPanel, "CharacterCustomization"));

        // Add buttons to panel
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(selectButton);

        // KeyListener for keyboard controls
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    prevButton.doClick(); // Simulate button click
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    nextButton.doClick(); // Simulate button click
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    selectButton.doClick(); // Simulate button click
                }
            }
        });

        panel.add(clanLabel, BorderLayout.NORTH);
        panel.add(loreLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        panel.setFocusable(true);
        panel.requestFocusInWindow();

        return panel;
    }


    private JPanel createCharacterCustomizationScreen() {
        JPanel panel = new JPanel(new GridLayout(8, 2));
        panel.add(new JLabel("First Name:"));
        panel.add(new JTextField());

        panel.add(new JLabel("Last Name:"));
        panel.add(new JTextField());

        String[] attributes = {"Constitution", "Strength", "Intelligence", "Agility", "Dexterity"};
        for (String attr : attributes) {
            panel.add(new JLabel(attr + ":"));
            panel.add(new JLabel("0", SwingConstants.CENTER));
        }

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> cardLayout.show(mainPanel, "GameScreen"));
        panel.add(confirmButton);

        return panel;
    }

    private JPanel createGameScreen() {
        return new JPanel(new BorderLayout()) {{
            add(new JLabel("Welcome to the game world!", SwingConstants.CENTER), BorderLayout.CENTER);
        }};
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameGUI game = new GameGUI();
            game.setVisible(true);
            game.requestFocus(); // Ensure key listener works
        });
    }
}