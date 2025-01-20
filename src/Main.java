import java.sql.*;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "39467105";
        Artist artist1 = new Artist("Abylkhan Kasteev");
        Gallery gallery1 = new Gallery("Kasteev State Museum of Arts");
        Artwork artwork1 = new Artwork("Kazakh Girl", artist1, gallery1);

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Database connection established!");


            int artistId = addArtist(connection, artist1);
            int galleryId = addGallery(connection, gallery1);


            addArtwork(connection, artwork1, artistId, galleryId);


            readAllArtwork(connection);


            updateArtworkTitle(connection, 1, "Updated Kazakh Girl");


            deleteArtwork(connection, 1);

            System.out.println("All operations completed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static int addArtist(Connection connection, Artist artist) throws SQLException {
        String insertSQL = "INSERT INTO artist (name) VALUES (?) RETURNING id";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, artist.getName());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                System.out.println("Artist added with ID: " + id);
                return id;
            }
        }
        throw new SQLException("Failed to insert artist.");
    }


    public static int addGallery(Connection connection, Gallery gallery) throws SQLException {
        String insertSQL = "INSERT INTO gallery (name) VALUES (?) RETURNING id";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, gallery.getName());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                System.out.println("Gallery added with ID: " + id);
                return id;
            }
        }
        throw new SQLException("Failed to insert gallery.");
    }


    public static void addArtwork(Connection connection, Artwork artwork, int artistId, int galleryId) throws SQLException {
        String insertSQL = "INSERT INTO artwork (title, artist_id, description) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, artwork.getTitle());
            pstmt.setInt(2, artistId);
            pstmt.setString(3, artwork.getDescription());
            pstmt.executeUpdate();
            System.out.println("Artwork successfully added.");
        }
    }


    public static void readAllArtwork(Connection connection) throws SQLException {
        String selectSQL = "SELECT a.id, a.title, ar.name AS artist, g.name AS gallery " +
                "FROM artwork a " +
                "JOIN artist ar ON a.artist_id = ar.id " +
                "JOIN artwork_gallery ag ON a.id = ag.artwork_id " +
                "JOIN gallery g ON ag.gallery_id = g.id";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(selectSQL)) {
            System.out.println("List of artworks:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String artist = rs.getString("artist");
                String gallery = rs.getString("gallery");
                System.out.printf("ID: %d, Title: %s, Artist: %s, Gallery: %s%n", id, title, artist, gallery);
            }
        }
    }


    public static void updateArtworkTitle(Connection connection, int id, String newTitle) throws SQLException {
        String updateSQL = "UPDATE artwork SET title = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setString(1, newTitle);
            pstmt.setInt(2, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Title successfully updated.");
            } else {
                System.out.println("No artwork found with the specified ID.");
            }
        }
    }


    public static void deleteArtwork(Connection connection, int id) throws SQLException {
        String deleteSQL = "DELETE FROM artwork WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Artwork successfully deleted.");
            } else {
                System.out.println("No artwork found with the specified ID.");
            }
        }
    }
}
