package oob.instaminder.HomeComponent.Domain.GetInstagramUserInformationUseCase.Model;

public class InstagramUserInformation {
    private String nick;
    private String following;
    private String followers;
    private String profilePicUrl;

    public InstagramUserInformation(String nick, String following, String followers, String profilePicUrl) {
        this.nick = nick;
        this.following = following;
        this.followers = followers;
        this.profilePicUrl = profilePicUrl;
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

    public String getProfilePicUrl() {
        return profilePicUrl;
    }
}
