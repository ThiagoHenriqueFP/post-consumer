package uol.compass.postconsumer.domain.Status;

public enum Status {
    CREATED("Created"),
    POST_FIND("Post_find"),
    POST_OK("Post_ok"),
    COMMENT_FIND("Comment_find"),
    COMMENT_OK("Comment_ok"),
    ENABLED("Enabled"),
    UPDATING("Updating"),
    DISABLED("Disabled"),
    FAILED("Failed");

    private final String name;


    Status(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }
}
