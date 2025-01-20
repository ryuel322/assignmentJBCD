class Gallery extends Object {
    private String name;

    public Gallery(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Gallery: " + name;
    }
}
