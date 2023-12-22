package zombiesurvival;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import zombiesurvival.config.Config;
import zombiesurvival.config.Images;
import zombiesurvival.frames.MainFrame;
import zombiesurvival.frames.StartMenu;

public class ZombieSurvival {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ZombieSurvival.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ZombieSurvival.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ZombieSurvival.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ZombieSurvival.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Images.initImages();
        Config.initFonts();
        
        run();
//        try {
//            showConsole();
//        } catch (IOException ex) {
//            Logger.getLogger(ZombieSurvival.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
    }
    
    public static void showConsole() throws IOException {

        // 1. create the pipes
        PipedInputStream inPipe = new PipedInputStream();
        PipedInputStream errPipe = new PipedInputStream();
        PipedInputStream outPipe = new PipedInputStream();

        // 2. set the System.in and System.out streams
        System.setIn(inPipe);
        System.setErr(new PrintStream(new PipedOutputStream(errPipe)));
        System.setOut(new PrintStream(new PipedOutputStream(outPipe), true));

        PrintWriter inWriter = new PrintWriter(new PipedOutputStream(inPipe), true);

        // 3. create the gui 
        JFrame frame = new JFrame("\"Console\"");
        JScrollPane p = new JScrollPane();
        p.setViewportView(console(outPipe, errPipe, inWriter));
        frame.add(p);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // 4. write some output (to JTextArea)
        System.out.println("Hello World!");
        System.out.println("Test");
        System.err.println("Test");
        System.out.println("Test");

        // 5. get some input (from JTextArea)
        Scanner s = new Scanner(System.in);
        System.out.printf("got from input: \"%s\"%n", s.nextLine());
    }

    public static JTextArea console(final InputStream out, final InputStream err, final PrintWriter in) {
        final JTextArea area = new JTextArea();

        // handle "System.out"
        new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                Scanner s = new Scanner(out);
//            Scanner ss = new Scanner(err);
                while (s.hasNextLine()) {
                    publish(s.nextLine() + "\n");
                }
//            while (ss.hasNextLine()) publish(ss.nextLine() + "\n");
                return null;
            }

            protected void process(List<String> chunks) {
                for (String line : chunks) {
                    area.append(line);
                }
            }
        }.execute();

        // handle "System.in"
        area.addKeyListener(new KeyAdapter() {
            private StringBuffer line = new StringBuffer();

            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (c == KeyEvent.VK_ENTER) {
                    in.println(line);
                    line.setLength(0);
                } else if (c == KeyEvent.VK_BACK_SPACE) {
                    line.setLength(line.length() - 1);
                } else if (!Character.isISOControl(c)) {
                    line.append(e.getKeyChar());
                }
            }
        });

        return area;
    }
    
    public static void run(){
        StartMenu panel = Config.menu = new StartMenu();
        MainFrame frame = Config.frame = new MainFrame(panel);
        frame.setTitle(Config.TITLE);
        panel.setPreferredSize(new Dimension(1200, 800));
//        frame.setPreferredSize(new Dimension(1200, 800));
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
}
