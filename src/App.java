import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {

    TableView<Mahasiswa> tableView = new TableView<Mahasiswa>();

    @Override
    public void start(Stage primaryStage) throws SQLException {

        Button add = new Button("add mahasiswa");
        
        add.setOnAction(e -> add());

        TableColumn<Mahasiswa, String> columnID = new TableColumn<>("id");
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));


        TableColumn<Mahasiswa, String> columnNama = new TableColumn<>("Nama");
        columnNama.setCellValueFactory(new PropertyValueFactory<>("nama"));

        TableColumn<Mahasiswa, String> columnTelpon = new TableColumn<>("Telpon");
        columnTelpon.setCellValueFactory(new PropertyValueFactory<>("telpon"));

        tableView.getColumns().add(columnID);
        tableView.getColumns().add(columnNama);
        tableView.getColumns().add(columnTelpon);

        tableView.setRowFactory(tv -> {
            TableRow<Mahasiswa> row = new TableRow<Mahasiswa>();
            
            ContextMenu contextMenu = new ContextMenu();

            MenuItem edit = new MenuItem("Edit");
            MenuItem delete = new MenuItem("Delete");

            edit.setOnAction(e -> doEdit(row));
            delete.setOnAction(e -> doDelete(row));

            contextMenu.getItems().addAll(edit, delete);
            row.setContextMenu(contextMenu);
            return row;
        });

        loadData();
        VBox vbox = new VBox(20, add, tableView);

        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch();
    }

    private void loadData() {
        Statement stmt;
        try {
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from mahasiswa");
            tableView.getItems().clear();
            // tampilkan hasil query
            while(rs.next()){
                tableView.getItems().add(new Mahasiswa(rs.getInt("id"), rs.getString("nama"), rs.getString("telpon")));
            }
            
            stmt.close();
            db.conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void doEdit(TableRow<Mahasiswa> item) {
        Mahasiswa mahasiswa = item.getItem();
        Stage editState = new Stage();
        Button save = new Button("simpan");

        editState.setTitle("edit data mahasiswa");
        
        TextField namaField = new TextField();
        TextField telponField = new TextField();
        Label labelNama = new Label("nama");
        Label labelTelpon = new Label("telpon");

        namaField.setText(mahasiswa.getNama());
        telponField.setText(mahasiswa.getTelpon());
        

        VBox hbox1 = new VBox(5, labelNama, namaField);
        VBox hbox2 = new VBox(5, labelTelpon, telponField);
        VBox vbox = new VBox(20, hbox1, hbox2, save);

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "update mahasiswa SET nama='%s', telpon='%s' WHERE id=%d";
                sql = String.format(sql, namaField.getText(), telponField.getText(), mahasiswa.getId());
                state.execute(sql);
                loadData();
                editState.close();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

        editState.setScene(scene);
        editState.show();
    }

    private void doDelete(TableRow<Mahasiswa> item) {
        Mahasiswa mahasiswa = item.getItem();
        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "delete from mahasiswa where id=%d";
            sql = String.format(sql, mahasiswa.getId());
            state.execute(sql);
            loadData();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    private void add() {
        Stage addStage = new Stage();
        Button save = new Button("simpan");

        addStage.setTitle("add data mahasiswa");
        
        TextField namaField = new TextField();
        TextField telponField = new TextField();
        Label labelNama = new Label("nama");
        Label labelTelpon = new Label("telpon");

        VBox hbox1 = new VBox(5, labelNama, namaField);
        VBox hbox2 = new VBox(5, labelTelpon, telponField);
        VBox vbox = new VBox(20, hbox1, hbox2, save);

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "insert into mahasiswa SET nama='%s', telpon='%s'";
                sql = String.format(sql, namaField.getText(), telponField.getText());
                state.execute(sql);
                loadData();
                addStage.close();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

}