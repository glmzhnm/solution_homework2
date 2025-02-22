

import java.util.Scanner;
public class MUDController {
    private final Player player;
    private boolean running;

    public MUDController(Player player) {
        this.player = player;
        this.running = true;
    }
    public void runGameLoop() {
        Scanner scanner = new Scanner(System.in);
        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            handleInput(input);
        }
    }

    public void handleInput(String input) {
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        String argument = (parts.length > 1) ? parts[1] : null;

        switch (command) {
            case "look":
                lookAround();
                break;
            case "move":
                if (argument != null) move(argument);
                else System.out.println("Specify a direction (forward, back, left, right)");
                break;
            case "pick":
                if (argument != null) pickUp(argument);
                else System.out.println("Specify an item to pick up.");
                break;
            case "inventory":
                checkInventory();
                break;
            case "help":
                showHelp();
                break;
            case "quit":
            case "exit":
                quitGame();
                break;
            default:
                System.out.println("Unknown");
        }
    }

    private void lookAround() {
        Room currentRoom = player.getCurrentRoom();
        System.out.println("Room: " + currentRoom.getName());
        System.out.println(currentRoom.getDescription());
        System.out.println("Items: " + currentRoom.getItems());
    }

    private void move(String direction) {
        Room nextRoom = player.getCurrentRoom().getConnectedRoom(direction);
        if (nextRoom != null) {
            player.setCurrentRoom(nextRoom);
            System.out.println("move " + direction + ".");
            lookAround();
        } else {
            System.out.println("another way");
        }
    }

    private void pickUp(String itemName) {
        Room currentRoom = player.getCurrentRoom();
        Item item = currentRoom.removeItem(itemName);
        if (item != null) {
            player.addItemToInventory(item);
            System.out.println("player has " + item.getName() + ".");
        } else {
            System.out.println("palyer hasn't " + itemName );
        }
    }

    private void checkInventory() {
        if (player.getInventory().isEmpty()) {
            System.out.println("0");
        } else {
            System.out.println("1");
            for (Item item : player.getInventory()) {
                System.out.println("- " + item.getName());
            }
        }
    }

    private void showHelp() {
        System.out.println("quit/exit - Exit the game.");
    }

    private void quitGame() {
        running = false;
        System.out.println("GAME OVER");
    }
}
