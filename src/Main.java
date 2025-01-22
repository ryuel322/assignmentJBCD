import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "39467105";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to the database.");
            while (true) {
                System.out.println("\nChoose an action:");
                System.out.println("1 - Add artist");
                System.out.println("2 - Add gallery");
                System.out.println("3 - Add artwork");
                System.out.println("4 - Show all artworks");
                System.out.println("5 - Update artwork title");
                System.out.println("6 - Delete artwork");
                System.out.println("0 - Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter artist name: ");
                        String artistName = scanner.nextLine();
                        addArtist(connection, artistName);
                        break;
                    case 2:
                        System.out.print("Enter gallery name: ");
                        String galleryName = scanner.nextLine();
                        addGallery(connection, galleryName);
                        break;
                    case 3:
                        System.out.print("Enter artwork title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter artist ID: ");
                        int artistId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter artwork description: ");
                        String description = scanner.nextLine();
                        addArtwork(connection, title, artistId, description);
                        break;
                    case 4:
                        readAllArtwork(connection);
                        break;
                    case 5:
                        System.out.print("Enter artwork ID to update: ");
                        int artworkId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter new title: ");
                        String newTitle = scanner.nextLine();
                        updateArtworkTitle(connection, artworkId, newTitle);
                        break;
                    case 6:
                        System.out.print("Enter artwork ID to delete: ");
                        int deleteId = scanner.nextInt();
                        deleteArtwork(connection, deleteId);
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice, try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database connection error!");
            e.printStackTrace();
        }
    }

    public static void addArtist(Connection connection, String name) {
        String sql = "INSERT INTO artist (name) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("Artist added: " + name);
        } catch (SQLException e) {
            System.out.println("Error adding artist.");
            e.printStackTrace();
        }
    }

    public static void addGallery(Connection connection, String name) {
        String sql = "INSERT INTO gallery (name) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("Gallery added: " + name);
        } catch (SQLException e) {
            System.out.println("Error adding gallery.");
            e.printStackTrace();
        }
    }

    public static void addArtwork(Connection connection, String title, int artistId, String description) {
        String sql = "INSERT INTO artwork (title, artist_id, description) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setInt(2, artistId);
            pstmt.setString(3, description);
            pstmt.executeUpdate();
            System.out.println("Artwork added: " + title);
        } catch (SQLException e) {
            System.out.println("Error adding artwork.");
            e.printStackTrace();
        }
    }

    public static void readAllArtwork(Connection connection) {
        String sql = "SELECT id, title FROM artwork";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("List of artworks:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Title: " + rs.getString("title"));
            }
        } catch (SQLException e) {
            System.out.println("Error reading artworks.");
            e.printStackTrace();
        }
    }

    public static void updateArtworkTitle(Connection connection, int id, String newTitle) {
        String sql = "UPDATE artwork SET title = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, newTitle);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("Artwork title updated.");
        } catch (SQLException e) {
            System.out.println("Error updating artwork title.");
            e.printStackTrace();
        }
    }

    public static void deleteArtwork(Connection connection, int id) {
        String sql = "DELETE FROM artwork WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Artwork deleted.");
        } catch (SQLException e) {
            System.out.println("Error deleting artwork.");
            e.printStackTrace();
        }
    }
}
