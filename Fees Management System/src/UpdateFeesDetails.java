
import static java.lang.ScopedValue.where;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;





/**
 *
 * @author S FARNAS
 */
public final class UpdateFeesDetails extends javax.swing.JFrame {

    /**
     * Creates new form Addfees
     */
    public UpdateFeesDetails() {
        initComponents();
        displayCashFirst();
        fillComboBox();
        
        int receiptNo= getReceiptNo();
        int receipt_no = 1;
        txt_receiptNo.setText(Integer.toString(receipt_no));
        setRecords();
        
    }
    public void displayCashFirst()
    {
        lbl_DDno.setVisible(false);
        lbl_chequeNo.setVisible(false);
        lbl_bankName.setVisible(false);
        
        
        txt_DDNo.setVisible(false);
        txt_ChequeNo.setVisible(false);
        txt_bankName.setVisible(false);
    }
    public boolean validation(){
        if(txt_receivedFrom.getText().equals(" ")){
            JOptionPane.showMessageDialog(this, "please enter user name");
            return false;
        } 
        if(dateChooser.getDate()== null){
            JOptionPane.showMessageDialog(this, "please select a date");
            return false;
        } 
        if(txt_amount.getText().equals(" ")){
            JOptionPane.showMessageDialog(this, "please enter amount(in numbers)");
            return false;
        } 
        if(combo_PaymentMode.getSelectedItem().toString().equalsIgnoreCase("CHEQUE")){
            if(txt_ChequeNo.getText().equals(""))
            {
                 JOptionPane.showMessageDialog(this, "please enter cheque number");
                 return false;
            }
            if(txt_bankName.getText().equals(""))
            {
                 JOptionPane.showMessageDialog(this, "please enter bank name");
                 return false;
            }
        } 
        
        if(combo_PaymentMode.getSelectedItem().toString().equalsIgnoreCase("DD")){
            if(txt_DDNo.getText().equals(""))
            {
                 JOptionPane.showMessageDialog(this, "please enter dd number");
                 return false;
            }
            if(txt_bankName.getText().equals(""))
            {
                 JOptionPane.showMessageDialog(this, "please enter bank name");
                 return false;
            }
        } 
         if(combo_PaymentMode.getSelectedItem().toString().equalsIgnoreCase("SBI COLLECT")){
           
            if(txt_bankName.getText().equals(""))
            {
                 JOptionPane.showMessageDialog(this, "please enter bank name");
                 return false;
            }
        } 
        
        
        return true;
    }
    public void fillComboBox()
    {
        try{
            Class.forName("org.apache.derby.iapi.jdbc.AutoloadedDriver");
            Connection con=DriverManager.getConnection("jdbc:derby://localhost:1527/fees_management","root","W7301@jqir#");
            PreparedStatement pst=(PreparedStatement) con.prepareStatement("select cname from course");
            ResultSet rs= pst.executeQuery();
            while(rs.next())
            {
                comboCourse.addItem(rs.getString("cname"));
            }
        }
        catch(ClassNotFoundException | SQLException e)
                {
                    
                }
    }
    
    public int getReceiptNo()
    {
          int receiptNo=0;
        try{
              Connection con=DBConnection.getConnection();
            PreparedStatement pst;
            pst = (PreparedStatement)con.prepareStatement("select max(receipt_no) from fee_details");
            ResultSet rs=pst.executeQuery();
            if(rs.next() == true)
            {
               receiptNo= rs.getInt(1);
            }
        }
        catch(SQLException e){
        }
        return receiptNo+1;
    }
    public String updateData()
    {
        String status=" ";
        int receiptNo=Integer.parseInt(txt_receiptNo.getText());
        String studentName = txt_receivedFrom.getText();
        String rollNo=txt_rollNo.getText();
        String paymentMode=combo_PaymentMode.getSelectedItem().toString();
        String chequeNo=txt_ChequeNo.getText();
        String bankName=txt_bankName.getText();
        String ddNo=txt_DDNo.getText();
        String courseName=txt_CourseName.getText();
        String gstin=txt_GSTNo.getText();
        float totalAmount=Float.parseFloat(txt_total.getText());
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String date=dateFormat.format(dateChooser.getDate());
        float initialAmount=Float.parseFloat(txt_amount.getText());
        float cgst=Float.parseFloat(txt_cgst.getText());
        float sgst=Float.parseFloat(txt_sgst.getText());
        String totalInWords=txt_total_words.getText();
        String remark=txt_remark.getText();
        int year1=Integer.parseInt(txt_year1.getText());
        int year2=Integer.parseInt(txt_year2.getText());
        
        try{
             //Class.forName("org.apache.derby.iapi.jdbc.AutoloadedDriver");
             Connection con=DBConnection.getConnection();
            PreparedStatement pst=con.prepareStatement("update fees_details set student_name=?, roll_no=?, "+"payment_mode=?, cheque_no=?, bank_name=?, dd_no=?, course_name=?, gstin=?, total_amount=?, date=?,"+"amount=?,cgst=?, sgst=?, total_in_words=?, remark=?,year1=?, year2=? where receipt_no = ?");
          
    //String sql = "UPDATE fees_details SET student_name=?, roll_no=?, payment_mode=?, cheque_no=?, bank_name=?, dd_no=?, course_name=?, gstin=?, total_amount=?, date=?, year1=?, amount=?, cgst=?, sgst=?, total_in_words=?, remark=?, year2=? WHERE receipt_no=?";
    //PreparedStatement pst = con.prepareStatement(sql);
        
        pst.setString(1, studentName);
        pst.setString(2, rollNo);
        pst.setString(3, paymentMode);
        pst.setString(4, chequeNo);
        pst.setString(5, bankName);
        pst.setString(6, ddNo);
        pst.setString(7, courseName);
        pst.setString(8, gstin);
        pst.setFloat(9, totalAmount);
        pst.setString(10,date);
        pst.setFloat(11, initialAmount);
        pst.setFloat(12, cgst);
        pst.setFloat(13, sgst);
        pst.setString(14, totalInWords);
        pst.setString(15, remark);
        pst.setInt(16, year1);
        pst.setInt(17, year2);
        pst.setInt(18, receiptNo);
        int rowCount= pst.executeUpdate();
       if(rowCount == 1){
           
           status="success";
       }
       else
       {
           status="Failed";
       }          
      }   
        catch (SQLException ex) {
            Logger.getLogger(Addfees.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
}
    public void setRecords()
    {
        try{
            Class.forName("org.apache.derby.iapi.jdbc.AutoloadedDriver");
            Connection con=DriverManager.getConnection("jdbc:derby://localhost:1527/fees_management","root","W7301@jqir#");
            PreparedStatement pst;
            pst = (PreparedStatement) con.prepareStatement("select * from fees_details order by receipt_no desc fetch first 1 rows only");
            ResultSet rs= pst.executeQuery();
            rs.next();
            //int receipt_no = 0;
            txt_receiptNo.setText(rs.getString("receipt_no"));
            combo_PaymentMode.setSelectedItem(rs.getString("payment_mode"));
            txt_ChequeNo.setText(rs.getString("cheque_no"));
            txt_DDNo.setText(rs.getString("dd_no"));
            txt_bankName.setText(rs.getString("bank_name"));
            txt_receivedFrom.setText(rs.getString("student_name"));
            dateChooser.setDate(rs.getDate("date"));
            txt_year1.setText(rs.getString("year1"));
            txt_year2.setText(rs.getString("year2"));
            txt_rollNo.setText(rs.getString("roll_no"));
            comboCourse.setSelectedItem(rs.getString("course_name"));
            txt_amount.setText(rs.getString("amount"));
            txt_sgst.setText(rs.getString("sgst"));
            txt_cgst.setText(rs.getString("cgst"));
            txt_total.setText(rs.getString("total_amount"));
            txt_total_words.setText(rs.getString("total_in_words"));
            txt_remark.setText(rs.getString("remark"));
                 
            }
        catch(ClassNotFoundException | SQLException e)
                {
                    
                }
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        panelsideBar = new javax.swing.JPanel();
        panelhome = new javax.swing.JPanel();
        btnhome = new javax.swing.JLabel();
        panelSearch = new javax.swing.JPanel();
        btnSearch = new javax.swing.JLabel();
        panelEdit = new javax.swing.JPanel();
        btnEdit = new javax.swing.JLabel();
        panelCourse = new javax.swing.JPanel();
        btnCourse = new javax.swing.JLabel();
        panelViewAllRecord = new javax.swing.JPanel();
        btnViewAllRecord = new javax.swing.JLabel();
        panelBack = new javax.swing.JPanel();
        btnBack = new javax.swing.JLabel();
        panelLogout = new javax.swing.JPanel();
        btnLogout = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbl_DDno = new javax.swing.JLabel();
        lbl_chequeNo = new javax.swing.JLabel();
        lbl_bankName = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_receiptNo = new javax.swing.JTextField();
        txt_DDNo = new javax.swing.JTextField();
        txt_ChequeNo = new javax.swing.JTextField();
        txt_bankName = new javax.swing.JTextField();
        combo_PaymentMode = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        dateChooser = new com.toedter.calendar.JDateChooser();
        jPanel9 = new javax.swing.JPanel();
        panelChild = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_total_words = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        comboCourse = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txt_rollNo = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txt_receivedFrom = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        txt_year2 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txt_amount = new javax.swing.JTextField();
        txt_cgst = new javax.swing.JTextField();
        txt_sgst = new javax.swing.JTextField();
        txt_total = new javax.swing.JTextField();
        txt_CourseName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btn_print = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txt_remark = new javax.swing.JTextField();
        txt_year1 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        txt_GSTNo = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelsideBar.setBackground(new java.awt.Color(0, 102, 102));

        panelhome.setBackground(new java.awt.Color(0, 102, 102));
        panelhome.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelhome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelhomeMouseEntered(evt);
            }
        });

        btnhome.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnhome.setForeground(java.awt.Color.white);
        btnhome.setText("Home");
        btnhome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnhomeMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout panelhomeLayout = new javax.swing.GroupLayout(panelhome);
        panelhome.setLayout(panelhomeLayout);
        panelhomeLayout.setHorizontalGroup(
            panelhomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelhomeLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(btnhome, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelhomeLayout.setVerticalGroup(
            panelhomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelhomeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnhome)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        panelSearch.setBackground(new java.awt.Color(0, 102, 102));
        panelSearch.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));

        btnSearch.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnSearch.setForeground(java.awt.Color.white);
        btnSearch.setText("Search Record");
        btnSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSearchMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout panelSearchLayout = new javax.swing.GroupLayout(panelSearch);
        panelSearch.setLayout(panelSearchLayout);
        panelSearchLayout.setHorizontalGroup(
            panelSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSearchLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSearch)
                .addGap(26, 26, 26))
        );
        panelSearchLayout.setVerticalGroup(
            panelSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSearchLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelEdit.setBackground(new java.awt.Color(0, 102, 102));
        panelEdit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));

        btnEdit.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnEdit.setForeground(java.awt.Color.white);
        btnEdit.setText("Edit Course");
        btnEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout panelEditLayout = new javax.swing.GroupLayout(panelEdit);
        panelEdit.setLayout(panelEditLayout);
        panelEditLayout.setHorizontalGroup(
            panelEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelEditLayout.setVerticalGroup(
            panelEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEditLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(btnEdit)
                .addContainerGap())
        );

        panelCourse.setBackground(new java.awt.Color(0, 102, 102));
        panelCourse.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));

        btnCourse.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnCourse.setForeground(java.awt.Color.white);
        btnCourse.setText("Course List");
        btnCourse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCourseMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout panelCourseLayout = new javax.swing.GroupLayout(panelCourse);
        panelCourse.setLayout(panelCourseLayout);
        panelCourseLayout.setHorizontalGroup(
            panelCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCourseLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(btnCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelCourseLayout.setVerticalGroup(
            panelCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCourseLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(btnCourse)
                .addContainerGap())
        );

        panelViewAllRecord.setBackground(new java.awt.Color(0, 102, 102));
        panelViewAllRecord.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));

        btnViewAllRecord.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnViewAllRecord.setForeground(java.awt.Color.white);
        btnViewAllRecord.setText("View All Record");
        btnViewAllRecord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnViewAllRecordMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout panelViewAllRecordLayout = new javax.swing.GroupLayout(panelViewAllRecord);
        panelViewAllRecord.setLayout(panelViewAllRecordLayout);
        panelViewAllRecordLayout.setHorizontalGroup(
            panelViewAllRecordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelViewAllRecordLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(btnViewAllRecord)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        panelViewAllRecordLayout.setVerticalGroup(
            panelViewAllRecordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelViewAllRecordLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnViewAllRecord)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        panelBack.setBackground(new java.awt.Color(0, 102, 102));
        panelBack.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));

        btnBack.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnBack.setForeground(java.awt.Color.white);
        btnBack.setText("Back");
        btnBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBackMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout panelBackLayout = new javax.swing.GroupLayout(panelBack);
        panelBack.setLayout(panelBackLayout);
        panelBackLayout.setHorizontalGroup(
            panelBackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBackLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        panelBackLayout.setVerticalGroup(
            panelBackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBackLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBack)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        panelLogout.setBackground(new java.awt.Color(0, 102, 102));
        panelLogout.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));

        btnLogout.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnLogout.setForeground(java.awt.Color.white);
        btnLogout.setText("Log Out");
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogoutMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout panelLogoutLayout = new javax.swing.GroupLayout(panelLogout);
        panelLogout.setLayout(panelLogoutLayout);
        panelLogoutLayout.setHorizontalGroup(
            panelLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLogoutLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelLogoutLayout.setVerticalGroup(
            panelLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLogoutLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(btnLogout)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelsideBarLayout = new javax.swing.GroupLayout(panelsideBar);
        panelsideBar.setLayout(panelsideBarLayout);
        panelsideBarLayout.setHorizontalGroup(
            panelsideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelsideBarLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(panelsideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panelBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelViewAllRecord, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelCourse, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelEdit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelSearch, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelhome, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        panelsideBarLayout.setVerticalGroup(
            panelsideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelsideBarLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(panelhome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(panelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(panelEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(panelCourse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(panelViewAllRecord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(panelBack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(panelLogout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(2015, Short.MAX_VALUE))
        );

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("GSTIN: ");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 90, 60, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Mode Of Payment:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 170, 50));

        lbl_DDno.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_DDno.setText("DD no:");
        jPanel2.add(lbl_DDno, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 120, 50));

        lbl_chequeNo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_chequeNo.setText("Cheque no:");
        jPanel2.add(lbl_chequeNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 120, 50));

        lbl_bankName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_bankName.setText("Bank Name:");
        jPanel2.add(lbl_bankName, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 120, 50));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Receipt No: SOC-");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 160, 50));

        txt_receiptNo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txt_receiptNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_receiptNoActionPerformed(evt);
            }
        });
        jPanel2.add(txt_receiptNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, 300, -1));

        txt_DDNo.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_DDNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_DDNoActionPerformed(evt);
            }
        });
        jPanel2.add(txt_DDNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, 310, 30));

        txt_ChequeNo.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_ChequeNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ChequeNoActionPerformed(evt);
            }
        });
        jPanel2.add(txt_ChequeNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, 310, 30));

        txt_bankName.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jPanel2.add(txt_bankName, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 200, 310, -1));

        combo_PaymentMode.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        combo_PaymentMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DD", "CHEQUE", "CASH", "SBI COLLECT", " " }));
        combo_PaymentMode.setSelectedIndex(2);
        combo_PaymentMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_PaymentModeActionPerformed(evt);
            }
        });
        jPanel2.add(combo_PaymentMode, new org.netbeans.lib.awtextra.AbsoluteConstraints(172, 72, 300, 30));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setText("Date:");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 40, 80, -1));
        jPanel2.add(dateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 40, 210, 30));

        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 330, -1, -1));

        panelChild.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Received From:");

        txt_total_words.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_total_words.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_total_wordsActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setText("Course:");

        comboCourse.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        comboCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCourseActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setText("Rollno:");

        txt_rollNo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel16.setText("Total In Words:");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setText("Course:");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel19.setText("The Following Payments in the college office for the year:");

        txt_receivedFrom.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jTextField11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField11.setText("to");

        txt_year2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_year2ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Sr.No");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setText("Head");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel18.setText("Amount");

        txt_amount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_amountActionPerformed(evt);
            }
        });

        txt_sgst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_sgstActionPerformed(evt);
            }
        });

        txt_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalActionPerformed(evt);
            }
        });

        txt_CourseName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_CourseNameActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("CGST       9%");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("SGST      9%");

        btn_print.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btn_print.setText("Print");
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Remark:");

        txt_remark.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txt_remark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_remarkActionPerformed(evt);
            }
        });

        txt_year1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout panelChildLayout = new javax.swing.GroupLayout(panelChild);
        panelChild.setLayout(panelChildLayout);
        panelChildLayout.setHorizontalGroup(
            panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChildLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelChildLayout.createSequentialGroup()
                        .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelChildLayout.createSequentialGroup()
                                .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelChildLayout.createSequentialGroup()
                                        .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(panelChildLayout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(txt_receivedFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(panelChildLayout.createSequentialGroup()
                                                        .addComponent(comboCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(147, 147, 147)
                                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txt_rollNo, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(panelChildLayout.createSequentialGroup()
                                                .addGap(344, 344, 344)
                                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelChildLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(panelChildLayout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_remark, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(panelChildLayout.createSequentialGroup()
                                                .addComponent(txt_total_words, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(53, 53, 53)
                                                .addComponent(btn_print)))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panelChildLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(panelChildLayout.createSequentialGroup()
                                        .addComponent(txt_CourseName, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(161, 161, 161))
                                    .addGroup(panelChildLayout.createSequentialGroup()
                                        .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(221, 221, 221)))))
                        .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_cgst, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_sgst, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_amount, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(81, Short.MAX_VALUE))
                    .addGroup(panelChildLayout.createSequentialGroup()
                        .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelChildLayout.createSequentialGroup()
                                .addGap(491, 491, 491)
                                .addComponent(txt_year1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(63, 63, 63)
                                .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_year2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(194, 194, 194))))
            .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelChildLayout.createSequentialGroup()
                    .addGap(48, 48, 48)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(973, Short.MAX_VALUE)))
        );
        panelChildLayout.setVerticalGroup(
            panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChildLayout.createSequentialGroup()
                .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelChildLayout.createSequentialGroup()
                        .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_receivedFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txt_year1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelChildLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(txt_year2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(comboCourse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_rollNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelChildLayout.createSequentialGroup()
                        .addGap(293, 293, 293)
                        .addComponent(btn_print))
                    .addGroup(panelChildLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_amount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_CourseName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_cgst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_sgst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_total_words, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_remark, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(898, Short.MAX_VALUE))
            .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelChildLayout.createSequentialGroup()
                    .addGap(225, 225, 225)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(1135, Short.MAX_VALUE)))
        );

        jPanel2.add(panelChild, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 1120, 1360));

        txt_GSTNo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txt_GSTNo.setText("22HVSJHSS");
        jPanel2.add(txt_GSTNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 90, 150, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelsideBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1113, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelsideBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(195, 195, 195))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnhomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhomeMouseEntered
        // TODO add your handling code here:  
    }//GEN-LAST:event_btnhomeMouseEntered

    private void btnSearchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchMouseEntered

    private void btnEditMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditMouseEntered

    private void btnBackMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBackMouseEntered

    private void btnViewAllRecordMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewAllRecordMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnViewAllRecordMouseEntered

    private void btnCourseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCourseMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCourseMouseEntered

    private void btnLogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLogoutMouseEntered

    private void txt_DDNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_DDNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_DDNoActionPerformed

    private void txt_ChequeNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ChequeNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ChequeNoActionPerformed

    private void combo_PaymentModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_PaymentModeActionPerformed
        // TODO add your handling code here:
        if(combo_PaymentMode.getSelectedIndex()==0)
        {
            lbl_DDno.setVisible(true);
            txt_DDNo.setVisible(true);
            lbl_chequeNo.setVisible(false);
            txt_ChequeNo.setVisible(false);
            lbl_bankName.setVisible(true);
            txt_bankName.setVisible(true);
            
        }
        if(combo_PaymentMode.getSelectedIndex()==1)
        {
            lbl_DDno.setVisible(false);
            txt_DDNo.setVisible(false);
            lbl_chequeNo.setVisible(true);
            txt_ChequeNo.setVisible(true);
            lbl_bankName.setVisible(true);
            txt_bankName.setVisible(true);
            
        }
         if(combo_PaymentMode.getSelectedIndex()==2)
        {
            lbl_DDno.setVisible(false);
            txt_DDNo.setVisible(false);
            lbl_chequeNo.setVisible(false);
            txt_ChequeNo.setVisible(false);
            lbl_bankName.setVisible(true);
            txt_bankName.setVisible(true);
            
        }
          if(combo_PaymentMode.getSelectedItem().equals("SBI COLLECT"))
        {
            lbl_DDno.setVisible(false);
            txt_DDNo.setVisible(false);
            lbl_chequeNo.setVisible(false);
            txt_ChequeNo.setVisible(false);
            lbl_bankName.setVisible(true);
            txt_bankName.setVisible(true);
            
        }
    }//GEN-LAST:event_combo_PaymentModeActionPerformed

    private void txt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalActionPerformed

    private void txt_sgstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_sgstActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_sgstActionPerformed

    private void txt_year2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_year2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_year2ActionPerformed

    private void txt_CourseNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_CourseNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_CourseNameActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        // TODO add your handling code here:
       if( validation() == true){
           String result = updateData();
           if(result.equals("success"))
           {
               JOptionPane.showMessageDialog(this,"record updated succcessfully");
                PrintReciept p=new PrintReciept();
               p.setVisible(true);
               this.dispose();
           }
           else{
               JOptionPane.showMessageDialog(this,"record updation failed");
           }
       }
    }//GEN-LAST:event_btn_printActionPerformed

    private void txt_amountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_amountActionPerformed
        // TODO add your handling code here:
        Float amnt=Float.valueOf(txt_amount.getText());
        Float cgst= (float)(amnt*0.09);
        Float sgst=(float)(amnt*0.09);
        txt_cgst.setText(cgst.toString());
        txt_sgst.setText(sgst.toString());
        float total=amnt+cgst+sgst;
        txt_total.setText(Float.toString(total));
        txt_total_words.setText(NumberToWordsconverter.convert((int)total)+" only");
    }//GEN-LAST:event_txt_amountActionPerformed

    private void txt_remarkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_remarkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_remarkActionPerformed

    private void comboCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCourseActionPerformed
        // TODO add your handling code here:
        txt_CourseName.setText(comboCourse.getSelectedItem().toString());
    }//GEN-LAST:event_comboCourseActionPerformed

    private void txt_total_wordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_total_wordsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_total_wordsActionPerformed

    private void panelhomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelhomeMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelhomeMouseEntered

    private void txt_receiptNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_receiptNoActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txt_receiptNoActionPerformed

        
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        Addfees a=new Addfees();
        a.displayCashFirst();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Addfees.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Addfees.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Addfees.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Addfees.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Addfees().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnBack;
    private javax.swing.JLabel btnCourse;
    private javax.swing.JLabel btnEdit;
    private javax.swing.JLabel btnLogout;
    private javax.swing.JLabel btnSearch;
    private javax.swing.JLabel btnViewAllRecord;
    private javax.swing.JButton btn_print;
    private javax.swing.JLabel btnhome;
    private javax.swing.JComboBox<String> comboCourse;
    private javax.swing.JComboBox<String> combo_PaymentMode;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel lbl_DDno;
    private javax.swing.JLabel lbl_bankName;
    private javax.swing.JLabel lbl_chequeNo;
    private javax.swing.JPanel panelBack;
    private javax.swing.JPanel panelChild;
    private javax.swing.JPanel panelCourse;
    private javax.swing.JPanel panelEdit;
    private javax.swing.JPanel panelLogout;
    private javax.swing.JPanel panelSearch;
    private javax.swing.JPanel panelViewAllRecord;
    private javax.swing.JPanel panelhome;
    private javax.swing.JPanel panelsideBar;
    private javax.swing.JTextField txt_ChequeNo;
    private javax.swing.JTextField txt_CourseName;
    private javax.swing.JTextField txt_DDNo;
    private javax.swing.JLabel txt_GSTNo;
    private javax.swing.JTextField txt_amount;
    private javax.swing.JTextField txt_bankName;
    private javax.swing.JTextField txt_cgst;
    private javax.swing.JTextField txt_receiptNo;
    private javax.swing.JTextField txt_receivedFrom;
    private javax.swing.JTextField txt_remark;
    private javax.swing.JTextField txt_rollNo;
    private javax.swing.JTextField txt_sgst;
    private javax.swing.JTextField txt_total;
    private javax.swing.JTextField txt_total_words;
    private javax.swing.JTextField txt_year1;
    private javax.swing.JTextField txt_year2;
    // End of variables declaration//GEN-END:variables

}
