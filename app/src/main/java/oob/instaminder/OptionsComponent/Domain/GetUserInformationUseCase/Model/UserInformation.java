package oob.instaminder.OptionsComponent.Domain.GetUserInformationUseCase.Model;

public class UserInformation {
    private String nick;
    private String password;

    public UserInformation(String nick, String password) {
        this.nick = nick;
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public String getPassword() {
        return password;
    }
}
