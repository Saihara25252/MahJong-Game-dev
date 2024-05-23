package Game;

import Item.*;
import Player.*;
import Dice.*;
import GameInterface.*;
import Initialization.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.*;


public class Game extends JFrame {
    private InitializeMahJong initializeMahjong;
    private InitializePlayer initializePlayer;
    private JLabel statusLabel;
    private static Game instance;
    private List<Player> players;

    private int currentPlayerIndex = 1;

    private Game() {}

    public static synchronized Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    private void initializeGame() {
        List<String> playerList = new ArrayList<>();
        playerList.add("NORTH");
        playerList.add("Me");
        playerList.add("WEST");
        playerList.add("EAST");
        initializeMahjong = new InitializeMahJong();
        initializePlayer = new InitializePlayer(initializeMahjong);
    }

    public void initInterface() {

        setTitle("Mahjong Game");
        setSize(1200, 1200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); // Use the border layout as the primary layout

        statusLabel = new JLabel("Ready...");
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Serif", Font.BOLD, 16));

        //Structural mahjong layout
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(0, 100, 0));
        centerPanel.add(statusLabel);
        getContentPane().setBackground(new Color(0, 100, 0));


        add(createPlayerPanel(players.get(0), BorderLayout.NORTH), BorderLayout.NORTH);
        add(createPlayerPanel(players.get(1), BorderLayout.SOUTH), BorderLayout.SOUTH);
        add(createPlayerPanel(players.get(2), BorderLayout.WEST), BorderLayout.WEST);
        add(createPlayerPanel(players.get(3), BorderLayout.EAST), BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }
    // Update status bar information
    private void updateStatus(String message) {
        statusLabel.setText(message);
    }

    private JPanel createPlayerPanel(Player player, String position) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        if (position.equals(BorderLayout.WEST) || position.equals(BorderLayout.EAST)) {

            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(position.equals(BorderLayout.WEST) ?
                    BorderFactory.createEmptyBorder(0, 0, 0, 600) :
                    BorderFactory.createEmptyBorder(0, 600, 0, 0));
            for (Item item : player.getPlayerMahjong()) {
                JLabel label = createCardLabel(item,player,null);
                panel.add(label);
                panel.add(Box.createVerticalStrut(5));
            }
        } else {
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            for (Item item : player.getPlayerMahjong()) {
                JLabel label = createCardLabel(item,player,null);
                panel.add(label);
            }
        }
        return panel;
    }

    private JPanel createPlayerPanel(Player player, String position, Item newCard) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        if (position.equals(BorderLayout.WEST) || position.equals(BorderLayout.EAST)) {
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        } else {
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        }

        for (Item item : player.getPlayerMahjong()) {
            JLabel label = createCardLabel(item, player, newCard);
            panel.add(label);
        }

        return panel;
    }

    private  JPanel createDiscardLabel(Player player,String position) {

        JPanel panel = new JPanel();
        panel.setOpaque(false);

        if (position.equals(BorderLayout.WEST) || position.equals(BorderLayout.EAST)) {
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(position.equals(BorderLayout.WEST) ?
                    BorderFactory.createEmptyBorder(0, 100, 0,0 ) :
                    BorderFactory.createEmptyBorder(0, 0, 0, 100));






        } else {
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));







        }
        return panel;
    }

    private  JLabel createCardLabel(Item item, Player player, Item newCard) {
        ImageIcon icon;
        if(!player.getName().equals("Me")){
            icon = new ImageIcon(getClass().getResource("/static/bj.jpg"));
        }else {
            icon = new ImageIcon(getClass().getResource("/static/" + item.getImgurl()));
        }
        Image image = icon.getImage().getScaledInstance(24, 48, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(image));

        if(newCard!=null){
            if (item.equals(newCard)) {
                label.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                label.setOpaque(true);
                label.setBackground(new Color(255, 255, 204));
            }
        }

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playCard(player, item);
            }
        });
        return label;
    }


    private void updateCenterPanel() {
        Container contentPane = getContentPane();
        BorderLayout layout = (BorderLayout) contentPane.getLayout();
        java.awt.Component oldCenter = layout.getLayoutComponent(BorderLayout.CENTER);
        if (oldCenter != null) {
            contentPane.remove(oldCenter);
        }

        JPanel allDiscardedTilesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 1));
        allDiscardedTilesPanel.setBackground(new Color(0, 100, 0));
        allDiscardedTilesPanel.setOpaque(true);






        allDiscardedTilesPanel.setPreferredSize(new Dimension(getContentPane().getWidth(), allDiscardedTilesPanel.getPreferredSize().height));

        JScrollPane scrollPane = new JScrollPane(allDiscardedTilesPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.validate();
        contentPane.repaint();
    }

    private void playCard(Player player, Item card) {
        //Remove the playing hand
        player.removeMahjong(card);
        updateStatus(player.getName() + " play a hand: " + card.getName());
        refreshPlayerHandUI(player,null);

        proceedToNextTurn(player,card);
    }

    public static boolean chakpeng(List<Item> items) {
        HashMap<Item, Integer> itemCounts = new HashMap<>();
        for (Item item : items) {
            itemCounts.put(item, itemCounts.getOrDefault(item, 0) + 1);
            if (itemCounts.get(item) >= 3) {
                return true;
            }
        }
        return false;
    }


    private void proceedToNextTurn(Player player, Item card) {
        
        List<Item> temp = new ArrayList<>(players.get(currentPlayerIndex).getPlayerMahjong());
        temp.add(card);
//        if(chakpeng(temp)){
//
//        }

        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        SwingUtilities.invokeLater(this::nextTurn);
    }


    private void refreshUI(Player player) {
        String borderLayoutPosition = determinePlayerPosition(player);
        java.awt.Component comp = ((BorderLayout)getContentPane().getLayout()).getLayoutComponent(borderLayoutPosition);
        if (comp != null) {
            getContentPane().remove(comp);
        }
        JPanel DiscardLabel;
        DiscardLabel = createDiscardLabel(player, borderLayoutPosition);
        getContentPane().add(DiscardLabel, borderLayoutPosition);
        getContentPane().validate();
        getContentPane().repaint();
    }
    private void refreshPlayerHandUI(Player player, Item newCard) {
        String borderLayoutPosition = determinePlayerPosition(player);
        java.awt.Component comp = ((BorderLayout)getContentPane().getLayout()).getLayoutComponent(borderLayoutPosition);
        if (comp != null) {
            getContentPane().remove(comp);
        }
        JPanel playerPanel;
        if(newCard==null){
            playerPanel = createPlayerPanel(player, borderLayoutPosition);
        }else {
            playerPanel = createPlayerPanel(player, borderLayoutPosition, newCard);
        }
        getContentPane().add(playerPanel, borderLayoutPosition);
        getContentPane().validate();
        getContentPane().repaint();
    }

    private String determinePlayerPosition(Player player) {
        // Return the correct BorderLayout position based on player's name
        if (player.getName().equals("NORTH")) {
            return BorderLayout.NORTH;
        } else if (player.getName().equals("Me")) {
            return BorderLayout.SOUTH;
        } else if (player.getName().equals("WEST")) {
            return BorderLayout.WEST;
        } else {
            return BorderLayout.EAST;
        }
    }

    private void proceedToNextAction() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    //Player turn processing
    private void handleHumanPlayer(Player player) {
        updateStatus(player.getName() + "Playing for Cards...");
        SwingUtilities.invokeLater(() -> {

            updateStatus("Click on the card you want to play");
        });
    }

    private void handleAIPlayer(Player player) {
        updateStatus(player.getName() + "Playing for Cards...");
        SwingUtilities.invokeLater(() -> {
            Random random = new Random();
            Item cardToPlay = player.getPlayerMahjong().get(random.nextInt(player.getPlayerMahjong().size()));
            playCard(player, cardToPlay);

        });
    }

    private boolean isGameOver() {
        return false;
    }

    public void startGame() {
        initializeGame();
        initInterface();
        nextTurn();
    }

    private void nextTurn() {
        if (isGameOver()) {
            return;
        }
        updateStatus("Player " + currentPlayerIndex + " round");
        Player currentPlayer = players.get(currentPlayerIndex);
        if (currentPlayer.getName().equals("Me")) {
            handleHumanPlayer(currentPlayer);
            Collections.sort(currentPlayer.getPlayerMahjong());
        } else {
            handleAIPlayer(currentPlayer);
            Collections.sort(currentPlayer.getPlayerMahjong());
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> Game.getInstance().startGame());
    }
}
