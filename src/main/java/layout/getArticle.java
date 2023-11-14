package layout;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.*;
import java.sql.*;
import func.*;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class getArticle extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private ArticleObject selectArticle;
	private ArrayList<ArticleObject> data;
	int ID = 0;
	int i = 0;

	/**
	 * Create the panel.
	 */
	public getArticle(Article a, updateArticle layoutUpdate, ArrayList<ArticleObject> items) {
		// Đặt kích thước và màu nền cho giao diện
	    setBounds(10, 111, 789, 579);
	    setBackground(new Color(245, 247, 255));

	    // Tạo mô hình cho bảng hiển thị dữ liệu
	    DefaultTableModel model = new DefaultTableModel();
	    table = new JTable(model);
	    model.addColumn("Article id");
	    model.addColumn("Article title");
	    model.addColumn("Article content");
	    model.addColumn("Article summary");
	    model.addColumn("Article create date");

	    // Gán dữ liệu từ danh sách bài viết vào bảng
	    data = items;

	    for (ArticleObject item : data) {
	        decode dc = new decode();
	        int id = item.getArticle_id();
	        String title = dc.Decode(item.getArticle_title());
	        String content = dc.Decode(item.getArticle_content());
	        String summary = dc.Decode(item.getArticle_summary());
	        String created_date = item.getArticle_created_date();
	        model.addRow(new Object[] { id, title, content, summary, created_date });
	        i++;
	    }

	    // Tạo nút "Refresh" để làm mới dữ liệu
	    JButton reloadButton = new JButton("Refresh");
	    reloadButton.setBounds(699, 0, 90, 25);
	    reloadButton.addActionListener(e -> {
	        // Xóa dữ liệu hiện tại của bảng
	        model.setRowCount(0);

	        // Thêm dữ liệu mới từ danh sách bài viết
	        for (ArticleObject item : data) {
	            decode dc = new decode();
	            int id = item.getArticle_id();
	            String title = dc.Decode(item.getArticle_title());
	            String content = dc.Decode(item.getArticle_content());
	            String summary = dc.Decode(item.getArticle_summary());
	            String created_date = item.getArticle_created_date();
	            model.addRow(new Object[] { id, title, content, summary, created_date });
	            i++;
	        }

	        // Thông báo cho mô hình rằng dữ liệu đã thay đổi
	        model.fireTableDataChanged();
	    });
	    add(reloadButton);

	    // Xử lý sự kiện khi người dùng chọn một hàng trong bảng
	    table.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            // Lấy hàng được chọn
	            int selectRow = table.getSelectedRow();
	            selectArticle = items.get(selectRow);
	            
	            // Gửi dữ liệu của hàng được chọn đến giao diện chính
	            layoutUpdate.getSelectRow(selectArticle);
	        }
	    });

	    // Đặt bố cục cho giao diện
	    setLayout(null);
	    table.setAutoCreateRowSorter(true);
	    JScrollPane scrollPaneView = new JScrollPane(table);
	    scrollPaneView.setBounds(0, 25, 789, 554);
	    add(scrollPaneView);
	}

	// Phương thức trả về bài viết được chọn
	public ArticleObject getSelectRow() {
	    return selectArticle;
	}

	// Phương thức cập nhật dữ liệu cho giao diện
	public void updateData(ArrayList<ArticleObject> items) {
	    this.data = items;
	}

}
