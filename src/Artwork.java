class Artwork extends Object {
    private String title;
    private Artist artist;
    private Gallery gallery;

    public Artwork(String title, Artist artist, Gallery gallery) {
        this.title = title;
        this.artist = artist;
        this.gallery = gallery;
    }

    public String getTitle() {
        return title;
    }

    public Artist getArtist() {
        return artist;
    }

    public Gallery getGallery() {
        return gallery;
    }

    @Override
    public String getDescription() {
        return "Artwork: " + title + ", Artist: " + artist.getName() + ", Gallery: " + gallery.getName();
    }
}
