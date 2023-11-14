package adcms;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import IT6020003.objects.*;
import IT6020003.process.*;
import java.awt.Font;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import layout.*;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.MouseAdapter;

public class ArticleScr extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	ArrayList<ArticleObject> items;
	private JTextField txtGetId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		Article a = new Article();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ArticleScr frame = new ArticleScr(a);
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
	public ArticleScr(Article a) {
		// Cài đặt thuộc tính không thể thay đổi kích thước
	    setResizable(false);
	    // Đóng ứng dụng khi cửa sổ được đóng
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    // Đặt kích thước và vị trí của cửa sổ
	    setBounds(100, 100, 1024, 800);

	    // Tạo và cấu hình nội dung chính
	    contentPane = new JPanel();
	    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	    setContentPane(contentPane);
	    contentPane.setLayout(null);

	    // Lấy danh sách tất cả các bài viết
	    items = a.getAllArticleObjects(null);

	    // Panel menu
	    JPanel menuPanel = new JPanel();
	    menuPanel.setBackground(Color.WHITE);
	    menuPanel.setBounds(0, 0, 200, 761);
	    contentPane.add(menuPanel);

	    // Panel chính cho bảng điều khiển nội dung
	    JPanel contentDashboardPanel = new JPanel();
	    contentDashboardPanel.setBackground(Color.RED);
	    contentDashboardPanel.setBounds(199, 60, 809, 701);

	    // Panel cho quản lý bài viết
	    JPanel contentArticlePanel = new JPanel();
	    contentArticlePanel.setBackground(new Color(245, 247, 255));
	    contentArticlePanel.setBounds(199, 0, 809, 761);
	    contentArticlePanel.setVisible(false);
	    contentPane.add(contentArticlePanel);
	    contentArticlePanel.setLayout(null);

	    // Ô văn bản để nhập ID cho việc tìm kiếm
	    txtGetId = new JTextField();
	    txtGetId.setBounds(523, 50, 117, 30);
	    contentArticlePanel.add(txtGetId);
	    txtGetId.setColumns(10);

	    // Nhãn "Data Article"
	    JLabel lblDataArticle = new JLabel("Data Article");
	    lblDataArticle.setFont(new Font("Tahoma", Font.BOLD, 18));
	    lblDataArticle.setBounds(10, 11, 117, 35);
	    contentArticlePanel.add(lblDataArticle);

	    // Layout cập nhật bài viết
	    updateArticle layoutUpdate = new updateArticle(a);
	    layoutUpdate.setBounds(0, 0, 789, 590);
	    JPanel updateArticlePane = new JPanel();
	    updateArticlePane.setBounds(10, 111, 789, 590);
	    updateArticlePane.setLayout(null);
	    updateArticlePane.add(layoutUpdate);
	    updateArticlePane.setVisible(false);
	    contentArticlePanel.add(updateArticlePane);

	    // Layout hiển thị bài viết
	    getArticle layoutGetArticle = new getArticle(a, layoutUpdate, items);
	    layoutGetArticle.setBounds(10, 111, 789, 639);
	    layoutGetArticle.setVisible(true);
	    contentArticlePanel.add(layoutGetArticle);

	    // Layout thêm bài viết
	    addArticle layoutAddArticle = new addArticle(a);
	    layoutAddArticle.setBounds(0, 0, 789, 590);
	    JPanel addArticlePane = new JPanel();
	    addArticlePane.setBounds(10, 111, 789, 590);
	    addArticlePane.setVisible(false);
	    addArticlePane.setLayout(null);
	    addArticlePane.add(layoutAddArticle);
	    contentArticlePanel.add(addArticlePane);

	    // Nút "Add"
	    JButton btnAdd = new JButton("Add");
	    btnAdd.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            contentDashboardPanel.setVisible(false);
	            addArticlePane.setVisible(true);
	            contentArticlePanel.setVisible(true);
	            layoutGetArticle.setVisible(false);
	            updateArticlePane.setVisible(false);
	        }
	    });
	    btnAdd.setBounds(131, 50, 90, 30);
	    contentArticlePanel.add(btnAdd);

	    // Nút "Update"
	    JButton btnUpdate = new JButton("Update");
	    btnUpdate.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            contentDashboardPanel.setVisible(false);
	            addArticlePane.setVisible(false);
	            contentArticlePanel.setVisible(true);
	            layoutGetArticle.setVisible(false);
	            updateArticlePane.setVisible(true);
	        }
	    });
	    btnUpdate.setBounds(250, 50, 90, 30);
	    contentArticlePanel.add(btnUpdate);

	    // Nút "Delete"
	    JButton btnDelete = new JButton("Delete");
	    btnDelete.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            if (a.deleteArticleById(layoutGetArticle.getSelectRow())) {
	                items = a.getAllArticleObjects(null);
	                layoutGetArticle.updateData(items);
	            } else {
	                System.out.println("Xóa không thành công");
	            }
	        }
	    });
	    btnDelete.setBounds(367, 50, 90, 30);
	    contentArticlePanel.add(btnDelete);

	    // Nút "Search"
	    JButton btnSearch = new JButton("Search");
	    btnSearch.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            int getID = Integer.parseInt(txtGetId.getText());
	            items = a.getArticleObjectsById(null, getID);
	            layoutGetArticle.updateData(items);
	        }
	    });
	    btnSearch.setBounds(639, 50, 90, 30);
	    contentArticlePanel.add(btnSearch);

	    // Nút "View"
	    JButton btnView = new JButton("View");
	    btnView.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            contentDashboardPanel.setVisible(false);
	            addArticlePane.setVisible(false);
	            contentArticlePanel.setVisible(true);
	            layoutGetArticle.setVisible(true);
	            updateArticlePane.setVisible(false);
	            items = a.getAllArticleObjects(null);
	            layoutGetArticle.updateData(items);
	        }
	    });
	    btnView.setBounds(10, 50, 90, 30);
	    contentArticlePanel.add(btnView);

	    // Nút "Dashboard" trong menu
	    JButton btnDashboard = new JButton("Dashboard");
	    btnDashboard.setFont(new Font("Tahoma", Font.BOLD, 14));
	    btnDashboard.setBackground(Color.WHITE);
	    btnDashboard.setBorder(null);
	    btnDashboard.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseEntered(MouseEvent e) {
	            btnDashboard.setBackground(new Color(75, 73, 172));
	            btnDashboard.setForeground(Color.WHITE);
	        }

	        @Override
	        public void mouseExited(MouseEvent e) {
	            btnDashboard.setBackground(Color.WHITE);
	            btnDashboard.setForeground(Color.BLACK);
	        }
	    });
	    btnDashboard.setBounds(10, 5, 180, 40);
	    btnDashboard.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            contentDashboardPanel.setVisible(true);
	            contentArticlePanel.setVisible(false);
	        }
	    });
	    menuPanel.setLayout(null);
	    menuPanel.add(btnDashboard);

	    // Nút "Article" trong menu
	    JButton btnArticle = new JButton("Article");
	    btnArticle.setBackground(Color.WHITE);
	    btnArticle.setBorder(null);
	    btnArticle.setFont(new Font("Tahoma", Font.BOLD, 14));
	    btnArticle.setBounds(10, 54, 180, 40);
	    btnArticle.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            contentDashboardPanel.setVisible(false);
	            addArticlePane.setVisible(false);
	            contentArticlePanel.setVisible(true);
	            layoutGetArticle.setVisible(true);
	        }
	    });
	    menuPanel.add(btnArticle);

	    // Nút "Section" trong menu
	    JButton btnSection = new JButton("Section");
	    btnSection.setBackground(Color.WHITE);
	    btnSection.setBorder(null);
	    btnSection.setFont(new Font("Tahoma", Font.BOLD, 14));
	    btnSection.setBounds(10, 105, 180, 40);
	    menuPanel.add(btnSection);

	    // Nút "Category" trong menu
	    JButton btnCategory = new JButton("Category");
	    btnCategory.setFont(new Font("Tahoma", Font.BOLD, 14));
	    btnCategory.setBorder(null);
	    btnCategory.setBackground(Color.WHITE);
	    btnCategory.setBounds(10, 160, 180, 40);
	    menuPanel.add(btnCategory);

	    // Nút "Pc" trong menu
	    JButton btnPc = new JButton("Pc");
	    btnPc.setFont(new Font("Tahoma", Font.BOLD, 14));
	    btnPc.setBorder(null);
	    btnPc.setBackground(Color.WHITE);
	    btnPc.setBounds(10, 212, 180, 40);
	    menuPanel.add(btnPc);

	    // Nút "Ps" trong menu
	    JButton btnPs = new JButton("Ps");
	    btnPs.setFont(new Font("Tahoma", Font.BOLD, 14));
	    btnPs.setBorder(null);
	    btnPs.setBackground(Color.WHITE);
	    btnPs.setBounds(10, 263, 180, 40);
	    menuPanel.add(btnPs);

	    // Nút "User" trong menu
	    JButton btnUser = new JButton("User");
	    btnUser.setFont(new Font("Tahoma", Font.BOLD, 14));
	    btnUser.setBorder(null);
	    btnUser.setBackground(Color.WHITE);
	    btnUser.setBounds(10, 314, 180, 40);
	    menuPanel.add(btnUser);
	}
}
