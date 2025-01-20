class Artist extends Object {
    private String name;

    public Artist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Artist: " + name;
    }
}
