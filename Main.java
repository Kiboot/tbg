import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class Main extends JFrame implements java.awt.event.KeyListener {
    Faction[] factions = {
        new Faction("Iron Vanguard", "Disciplined warriors with resilience and strength.", 5, 0, 2),
        new Faction("Arcane Syndicate", "Mystical scholars wielding powerful magic.", 1, 6, 3),
        new Faction("Shadow Stalkers", "Stealthy assassins specializing in agility.", 2, 2, 5)
    };

    int selectedIndex = 0;
    JLabel factionLabel;
    JButton confirmButton;

    public Main() {
        setTitle("Faction Selection");
        setSize(400, 300);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        factionLabel = new JLabel(factions[selectedIndex].getFormattedStats(), SwingConstants.CENTER);
        factionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(factionLabel, BorderLayout.CENTER);

        // Confirmation Button
        confirmButton = new JButton("Select Starting Clan");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
        confirmButton.addActionListener(e -> showConfirmationWindow());
        add(confirmButton, BorderLayout.SOUTH);

        //addKeyListener(this);
        setupKeyBindings();
        setVisible(true);;
    }

    private void setupKeyBindings() {
        JRootPane rootPane = getRootPane();
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = rootPane.getActionMap();

        // Left Arrow Key: Move left in faction selection
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedIndex = (selectedIndex - 1 + factions.length) % factions.length;
                updateFactionDisplay();
            }
        });

        // Right Arrow Key: Move right in faction selection
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedIndex = (selectedIndex + 1) % factions.length;
                updateFactionDisplay();
            }
        });

        // Enter Key: Confirm faction selection
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "confirmSelection");
        actionMap.put("confirmSelection", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showConfirmationWindow();
            }
        });
    }

    private void updateFactionDisplay() {
        factionLabel.setText(factions[selectedIndex].getFormattedStats());
        revalidate();
        repaint();
        this.requestFocusInWindow();

    }

    private void showConfirmationWindow() {
        JOptionPane.showMessageDialog(this, "You have chosen " + factions[selectedIndex].name + "!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
    }

    public void displayFaction() {
        factions[selectedIndex].displayInfo();
        System.out.println("Use LEFT/RIGHT arrows to navigate. Press ENTER to select.");

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            selectedIndex = (selectedIndex - 1 + factions.length) % factions.length; // Loop backward
            displayFaction();
            updateFactionDisplay();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            selectedIndex = (selectedIndex + 1) % factions.length; // Loop forward
            displayFaction();
            updateFactionDisplay();
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println("You have chosen " + factions[selectedIndex].name + "!");
            System.exit(0); // Close selection
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        new Main();
    }

    public static void combat(Character player, LinkedList<Character> enemies ){
        Collections.shuffle(enemies); //shuffle enemy order

        LinkedList<Character> actors = new LinkedList<>(); //creating a new list for battle participants
        actors.add(player); //adding the player to the battle participant list
        actors.addAll(enemies); //adding the enemies as well

        //Sort battle participants based on speed in descending order
        actors.sort((a, b) -> Double.compare(b.getBaseSpeed(), a.getBaseSpeed()));

        //while the player is alive and enemies list is not empty
        while(player.isAlive() && !enemies.isEmpty()){

            //for loop used to process actor turn processing
            for(Character current: actors){
                if(!current.isAlive()){ continue;} //if the actor with the current turn is dead, then skip


            for (Character enemy : enemies) {
                System.out.printf("%40s HP: %-5d MP: %-5d\n", enemy.name, enemy.health, enemy.mana); // Enemy HP/MP
            }
                //if its player's turn
                if(current == player){

                    System.out.println("\n" + "=".repeat(40));
                    System.out.printf("%-20s %20s\n", "PLAYER", "ENEMIES");
                    System.out.printf("%-20s %20s\n", "--------------------", "--------------------");
                    System.out.printf("%-10s HP: %-5d MP: %-5d %20s\n", player.name, player.health, player.mana, ""); // Player HP/MP

                    System.out.println("Choose an action:");
                    System.out.println("1. Attack");
                    System.out.println("2. Defend");
                    System.out.println("3. Heal");
                    System.out.print("Enter your choice: ");

                    Scanner scanner = new Scanner(System.in);
                    String choice = scanner.nextLine();
                    handlePlayerAction(player, enemies, choice);
                    scanner.close();
                }

            }
            
            // Display Combat Status

        System.out.println("=".repeat(40));
        }

    }

    public static void handlePlayerAction(Character player, List<Character> enemies, String choice) {
        Scanner scanner = new Scanner(System.in);
        //boolean defending = false;

        switch (choice.toLowerCase()) {
            case "1": // Attack
            case "attack":
                System.out.println("\nChoose a target to attack:");
                for (int i = 0; i < enemies.size(); i++) {
                    System.out.printf("%d. %s HP: %d MP: %d\n", i + 1, enemies.get(i).name, enemies.get(i).health, enemies.get(i).mana);
                }
                System.out.print("Enter the number: ");
                try {
                    int targetIndex = Integer.parseInt(scanner.nextLine()) - 1;
                    if (targetIndex >= 0 && targetIndex < enemies.size()) {
                        Character target = enemies.get(targetIndex);
                        player.attack(target);
                        if (!target.isAlive()) {
                            System.out.println(target.name + " has been defeated!");
                            enemies.remove(target);
                        }
                    } else {
                        System.out.println("Invalid target number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
                break;

            case "2": // Defend
            case "defend":
                //defending = true;
                System.out.println(player.name + " takes a defensive stance!");
                //player.defensePower += 10; // Temporary defense boost
                break;

            case "3": // Heal
            case "heal":
                //player.heal();
                break;

            default:
                System.out.println("Invalid input. You lose a turn!");
                break;
        }

        // If defending, reset defense power after enemy turns
        //if (defending) {
        //    player.defensePower -= 10;
        //}
    }

    
}

