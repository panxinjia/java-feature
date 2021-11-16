import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class TestSwing {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TestSwing::login);
    }

    private static void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame jFrame = new JFrame("窗口标题");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setBounds(100, 100, 600, 500);

        JLabel label = new JLabel("Hello World Label");
        jFrame.getContentPane().add(label);
        //jFrame.pack();
        jFrame.setVisible(true);
    }


    private static void login() {
        JFrame frame = new JFrame("login");
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);

        placeComponents(panel);

        // 渲染完成之后显示
        frame.setVisible(true);


    }

    public static void placeComponents(JPanel panel) {
        panel.setLayout(null);

//        JLabel un  = new JLabel("用户名");
//        un.setBounds(10, 10, 100, 30);
//        panel.add(un);
//
//        JTextField username = new JTextField(20);
//        username.setBounds(110, 10, 200, 30);
//        panel.add(username);
//
//
//        JLabel pwd = new JLabel("密码");
//        pwd.setBounds(10, 40, 100, 30);
//        panel.add(pwd);
//
//
//        JPasswordField password = new JPasswordField(20);
//        username.setBounds(110, 40, 200, 30);
//        panel.add(password);


        JButton login = new JButton("click me");
        login.addActionListener(event -> {
            Object source = event.getSource();
            if (source instanceof JButton) {
                String paramString = event.paramString();
                System.out.println(paramString);
                long when = event.getWhen();
                Date date = new Date(when);
                System.out.println(date.toLocaleString());
            }

            System.out.println(source);
        });
        login.setBounds(100, 100, 100, 30);
        panel.add(login);
    }
}
