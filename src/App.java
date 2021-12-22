import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    

    @Override
    public void start(Stage primaryStage) throws SQLException {

        Table table = new Table();

        Button add = new Button("add mahasiswa");
        
        add.setOnAction(e -> add());

        

        loadData();
        VBox vbox = new VBox(20, add, table.tableView);

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
            if(mahasiswa.update(namaField.getText(), telponField.getText())) {
                loadData();
                editState.close();
            }
        });

        editState.setScene(scene);
        editState.show();
    }

    private void doDelete(TableRow<Mahasiswa> item) {
        Mahasiswa mahasiswa = item.getItem();
        if(mahasiswa.delete()) {
            loadData();
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