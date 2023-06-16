import java.awt.EventQueue;
import login.LoginSWFrame;
import login.LoginView;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginView view = new LoginView();
                LoginSWFrame controller = new LoginSWFrame(view);
                // hiển thị màn hình login
                controller.showLoginView();
            }
        });
    }
}
