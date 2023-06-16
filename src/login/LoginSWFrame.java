package login;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import shows.MenuSWFrame;

public class LoginSWFrame {
    private UserDao userDao;
    private LoginView loginView;
    private MenuSWFrame Menuview;

    public LoginSWFrame(LoginView view) {
        this.loginView = view;
        this.userDao = new UserDao();
        view.addLoginListener(new LoginListener());
    }

    public void showLoginView() {
        loginView.setVisible(true);
    }

    /**
     * Lớp LoginListener
     * chứa cài đặt cho sự kiện click button "Login"
     *
     * @author Steve
     */
    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            User user = loginView.getUser();
            if (userDao.checkUser(user)) {
                // nếu đăng nhập thành công, mở màn hình quản lý sinh viên
                new MenuSWFrame();
                loginView.setVisible(false);
            } else {
                loginView.showMessage("username hoặc password không đúng.");
            }
        }
    }
}
