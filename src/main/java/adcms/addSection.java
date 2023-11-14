package adcms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import IT6020003.objects.*;
import IT6020003.process.*;

public class addSection extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Section s = new Section();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					addSection frame = new addSection(s);
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
	public addSection(Section s) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(74, 32, 76, 29);
		contentPane.add(lblName);
		
		txtName = new JTextField();
		txtName.setBounds(167, 32, 198, 29);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				
				if (!"".equalsIgnoreCase(name)) {
					SectionObject nsec = new SectionObject();
					nsec.setSection_name(name);
					s.addSection(nsec);
				}
			}
		});
		btnCreate.setBounds(114, 100, 125, 40);
		contentPane.add(btnCreate);
	}
}
