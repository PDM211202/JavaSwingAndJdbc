package adcms;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class Demo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Demo frame = new Demo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Demo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
        
		JEditorPane editorPane = new JEditorPane();
		editorPane.setEditable(false);
        try {
        	URL url = new File("E://Project/2023IT6020003/2023IT6020003/src/main/webapp/WebContent/Article.html").toURI().toURL();
        	editorPane.setPage(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    
        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.setBounds(0, 90, 1008, 591);
        contentPane.add(scrollPane, BorderLayout.CENTER);
	}
}
