package oob.instaminder.HomeComponent.Domain.GetInstagramUserInformationUseCase.Model;

public class InstagramUserInformation {
    private String nick;
    private int followers;
    private String profilePicUrl;

    public InstagramUserInformation(String nick, int followers, String profilePicUrl) {
        this.nick = nick;
        this.followers = followers;
        this.profilePicUrl = profilePicUrl;
    }

    public String getNick() {
        return nick;
    }

    public int getFollowers() {
        return followers;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }
}
