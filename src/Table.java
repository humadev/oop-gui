import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Table {
    public TableView<Mahasiswa> tableView = new TableView<Mahasiswa>();

    Table() {
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
            System.out.println("test");
            return row;
        });
    }
}
