import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class ListaTowarowController extends ViewController implements Initializable {

    private ZamowieniaRepository pozycjaZamRepo;

    @FXML
    Button btnX;

    @FXML
    TextField tfNazwaSzukana;

    @FXML
    GridPane gpListaTowarow;

    private LinkedList<Button> buttons = new LinkedList<>();
    private ArrayList<Towar> towaryList;
    private TowaryRepository towaryRepository;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        closeButton = btnX;
    }

    public void updateView()
    {
        fillGridView();
        setGridViewConstraints();
    }
    public void setZamowieniaRepository(ZamowieniaRepository pozycjaZamRepo) {
        this.pozycjaZamRepo = pozycjaZamRepo;
    }


    private void fillGridView()
    {
        fillGridView(null);
    }

    private void wybierzTowar(int i, ActionEvent e) {
        towaryRepository.setTowar(towaryRepository.towary.remove(i));
        zamknijOkno(e);
    }

    private void setGridViewConstraints()
    {
        gpListaTowarow.getColumnConstraints().get(0).setMinWidth(270);

        gpListaTowarow.getColumnConstraints().get(1).setMinWidth(80);
        gpListaTowarow.getColumnConstraints().get(1).setHalignment(HPos.CENTER);

        gpListaTowarow.getColumnConstraints().get(2).setMinWidth(80);
        gpListaTowarow.getColumnConstraints().get(2).setHalignment(HPos.CENTER);

        gpListaTowarow.getColumnConstraints().get(3).setMinWidth(70);
        gpListaTowarow.getColumnConstraints().get(3).setHalignment(HPos.RIGHT);


    }
    @FXML
    private void powrot(javafx.event.ActionEvent e)
    {
        zamknijOkno(e);
    }

    public void setTowaryRepository(TowaryRepository towaryRepository) {
        this.towaryRepository = towaryRepository;
        towaryList = (ArrayList<Towar>) this.towaryRepository.towary;
    }

    public void szukaj(ActionEvent event) {
     fillGridView(tfNazwaSzukana.getText());
    }

   
    private void fillGridView(String nazwaSzukana)
    {
        gpListaTowarow.getChildren().clear();
        buttons.clear();


        int rowNumber = 1;
        gpListaTowarow.addRow(0, new Label("Nazwa"), new Label("Ilość:"), new Label("Cena j."));
        for(int i =0; i < towaryList.size(); i++)
        {

            //USTALANIE TESTOWYCH WARTOSCI
            Towar towar = towaryList.get(i);
            String nazwa = towar.getNazwa();
            if(nazwa.matches(".*"+nazwaSzukana+".*") || nazwaSzukana == null) {
                String ilosc = String.valueOf(towar.getIlosc());
                String cena = Formater.formatujCene(towar.getCenaJn());

                Button wybierz = new Button("Wybierz");

                buttons.add(wybierz);
                wybierz.setOnAction(new EventHandler<ActionEvent>() {
                    private Button bRef;

                    @Override
                    public void handle(ActionEvent e) {
                        wybierzTowar(buttons.indexOf(bRef), e);
                    }

                    private EventHandler<ActionEvent> init(Button b) {
                        bRef = b;
                        return this;
                    }
                }.init(wybierz));


                gpListaTowarow.addRow(rowNumber, new Label(nazwa), new Label(ilosc), new Label(cena), wybierz);
                gpListaTowarow.getRowConstraints().add(new RowConstraints(50));

                rowNumber++;
            }
        }
    }
}