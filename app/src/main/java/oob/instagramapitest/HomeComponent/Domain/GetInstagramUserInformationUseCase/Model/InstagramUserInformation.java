package oob.instagramapitest.HomeComponent.Domain.GetInstagramUserInformationUseCase.Model;

public class InstagramUserInformation {
    private String nick;
    private String following;
    private String followers;

    public InstagramUserInformation(String nick, String following, String followers) {
        this.nick = nick;
        this.following = following;
        this.followers = followers;
    }

    public String getNick() {
        return nick;
    }

    public String getFollowing() {
        return following;
    }

    public String getFollowers() {
        return followers;
    }
}
