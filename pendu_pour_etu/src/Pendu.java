import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ButtonBar.ButtonData ;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import java.io.File;
import java.util.ArrayList;


/**
 * Vue du jeu du pendu
 */
public class Pendu extends Application {
    /**
     * modèle du jeu
     **/
    private MotMystere modelePendu;
    /**
     * Liste qui contient les images du jeu
     */
    private ArrayList<Image> lesImages;
    /**
     * Liste qui contient les noms des niveaux
     */    
    public List<String> niveaux;

    // les différents contrôles qui seront mis à jour ou consultés pour l'affichage
    /**
     * le dessin du pendu
     */
    private ImageView dessin;
    /**
     * le mot à trouver avec les lettres déjà trouvé
     */
    private Text motCrypte;
    /**
     * la barre de progression qui indique le nombre de tentatives
     */
    private ProgressBar pg;
    /**
     * le clavier qui sera géré par une classe à implémenter
     */
    private Clavier clavier;
    /**
     * le text qui indique le niveau de difficulté
     */
    private Text leNiveau;
    /**
     * le chronomètre qui sera géré par une clasee à implémenter
     */
    private Chronometre chrono;
    /**
     * le panel Central qui pourra être modifié selon le mode (accueil ou jeu)
     */
    private BorderPane panelCentral;
    /**
     * le bouton Paramètre / Engrenage
     */
    private Button boutonParametres;
    /**
     * le bouton Accueil / Maison
     */    
    private Button boutonMaison;
    /**
     * le bouton qui permet de (lancer ou relancer une partie
     */ 
    private Button bJouer;

    private Label titre;

    private Button boutonInfo;

    private Stage primaryStage;

    private BorderPane root;


    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono ...)
     */
    @Override
    public void init() {
        this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, MotMystere.FACILE, 10);
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("./pendu_pour_etu/img");

        this.boutonInfo = new Button();
        this.boutonParametres = new Button();
        this.boutonMaison = new Button();


        ImageView accueil = new ImageView(new Image("file:./pendu_pour_etu/img/home.png"));
        accueil.setFitHeight(40);
        accueil.setFitWidth(40);
        ImageView param = new ImageView(new Image("file:./pendu_pour_etu/img/parametres.png"));
        param.setFitHeight(40);
        param.setFitWidth(40);
        ImageView info = new ImageView(new Image("file:./pendu_pour_etu/img/info.png"));
        info.setFitHeight(40);
        info.setFitWidth(40);


        this.boutonMaison.setGraphic(accueil);
        this.boutonParametres.setGraphic(param);
        this.boutonInfo.setGraphic(info);


        this.leNiveau = new Text();
        // this.chrono = new Chronometre(null);
        this.niveaux = new ArrayList<>();
        this.dessin = new ImageView();
        this.motCrypte = new Text();
        this.pg = new ProgressBar();
        this.titre = new Label("Jeu du Pendu");
        this.titre.setFont(Font.font("Arial", FontWeight.BLACK, 30));
        this.panelCentral = fenetreAccueil();
        this.clavier = new Clavier("ABCDEFGHIJKLMNOPQRSTUVWXYZ-", new ControleurLettres(this.modelePendu, this), 7);       
        this.chrono = new Chronometre();
        
        
    }


    /**
     * @return le panel contenant le titre du jeu
     */
    private BorderPane titre(){
        BorderPane banniere = new BorderPane();
        banniere.setLeft(this.titre);
        HBox boutons = new HBox();
        boutons.getChildren().addAll(this.boutonMaison, this.boutonParametres, this.boutonInfo);
        banniere.setRight(boutons);
        banniere.setStyle("-fx-background-color:rgb(218, 230, 243);");
        banniere.setPadding(new Insets(30));
        return banniere;
    }

    // /**
     // * @return le panel du chronomètre
     // */
    // private TitledPane leChrono(){
        // A implementer
        // TitledPane res = new TitledPane();
        // return res;
    // }

    // /**
     // * @return la fenêtre de jeu avec le mot crypté, l'image, la barre
     // *         de progression et le clavier
     // */
    private BorderPane fenetreJeu() {
    this.boutonParametres.setDisable(true);
    this.dessin.setImage(this.lesImages.get(0)); 
    this.dessin.setFitHeight(500);
    this.dessin.setPreserveRatio(true);
    RetourAccueil accueil = new RetourAccueil(modelePendu, this);
    this.boutonMaison.setOnAction(accueil);
    this.boutonMaison.setDisable(false);

    BorderPane res = new BorderPane();
    
    VBox center = new VBox(15);
    this.clavier = new Clavier("ABCDEFGHIJKLMNOPQRSTUVWXYZ-", new ControleurLettres(this.modelePendu, this), 7);
    clavier.setHgap(10);
    clavier.setVgap(10);
    
    this.motCrypte.setText(this.modelePendu.getMotCrypte());
    this.motCrypte.setFont(Font.font("Arial", FontWeight.BOLD, 36));
    this.motCrypte.setTextAlignment(TextAlignment.CENTER);
    
    center.setAlignment(Pos.TOP_CENTER);
    center.getChildren().addAll(this.motCrypte, this.dessin, this.pg, this.clavier);
    center.setPadding(new Insets(30, 0, 0, 0));
    res.setCenter(center);

    VBox right = new VBox(15);
    right.setAlignment(Pos.TOP_CENTER);
    right.setMaxHeight(Double.MAX_VALUE);
    right.setPrefWidth(275);
    right.setPadding(new Insets(30, 0, 0, 0));
    
    int niveau = this.modelePendu.getNiveau();
    switch (niveau) {
        case 0 : this.leNiveau.setText("Niveau Facile"); break;
        case 1 : this.leNiveau.setText("Niveau Medium"); break;
        case 2 : this.leNiveau.setText("Niveau Difficile"); break;
        case 3 : this.leNiveau.setText("Niveau Expert"); break;
    }
    this.leNiveau.setFont(Font.font("Arial", FontWeight.BLACK, 20));
    ControleurLancerPartie reLancerPartie = new ControleurLancerPartie(modelePendu, this);
    Button nouveauMot = new Button("Nouveau mot");
    nouveauMot.setOnAction(reLancerPartie);
    right.getChildren().addAll(this.leNiveau, this.chrono, nouveauMot);
    res.setRight(right);

    return res;
}


    // /**
     // * @return la fenêtre d'accueil sur laquelle on peut choisir les paramètres de jeu
     // */
    private BorderPane fenetreAccueil(){
        this.bJouer = new Button("Lancer la partie");
        BorderPane res = new BorderPane();
        RadioButton facile = new RadioButton("facile");
        RadioButton medium = new RadioButton("medium");
        RadioButton difficile = new RadioButton("difficile");
        RadioButton expert = new RadioButton("expert");
        ToggleGroup indiv = new ToggleGroup();

        
        facile.setToggleGroup(indiv);
        medium.setToggleGroup(indiv);
        difficile.setToggleGroup(indiv);
        expert.setToggleGroup(indiv);
        VBox niveaux = new VBox(10);
        niveaux.getChildren().addAll(facile, medium, difficile, expert);
        TitledPane titreRadio = new TitledPane("Niveau de difficulté", niveaux);

        ControleurLancerPartie lancerPartie = new ControleurLancerPartie(modelePendu, this);
        this.bJouer.setOnAction(lancerPartie);
        ControleurInfos infoPartie = new ControleurInfos(this);
        this.boutonInfo.setOnAction(infoPartie);

        VBox accueil = new VBox();
        titreRadio.setPadding(new Insets(20,40,20,0));
        accueil.getChildren().addAll(this.bJouer, titreRadio);
        accueil.setPadding(new Insets(20,40,20,40));
        res.setCenter(accueil);
        this.boutonMaison.setDisable(true);
        this.boutonParametres.setDisable(false);
        return res;
    }

    /**
     * charge les images à afficher en fonction des erreurs
     * @param repertoire répertoire où se trouvent les images
     */
    private void chargerImages(String repertoire){
        for (int i=0; i<this.modelePendu.getNbErreursMax()+1; i++){
            File file = new File(repertoire+"/pendu"+i+".png");
            System.out.println(file.toURI().toString());
            this.lesImages.add(new Image(file.toURI().toString()));
        }
    }

    public void modeAccueil(){
        this.panelCentral = fenetreAccueil();
        this.root.setCenter(this.panelCentral);
    }
    
    public void modeJeu(){
        this.panelCentral = fenetreJeu();
        this.root.setCenter(this.panelCentral);
    }
    
    public void modeParametres(){
        // A implémenter
    }

    /** lance une partie */
    public void lancePartie(){
        this.modelePendu.relancerPartie();
        modeJeu();
    }

    public void mettreAJourImage() {
        int indiceImage = this.modelePendu.getNbErreursMax() - this.modelePendu.getNbErreursRestants();
        if (indiceImage >= 0 && indiceImage < this.lesImages.size()) {
            this.dessin.setImage(this.lesImages.get(indiceImage));
        }
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage(){
        this.motCrypte.setText(this.modelePendu.getMotCrypte());
        mettreAJourImage();    
        double progression = (double) this.modelePendu.getNbEssais() / this.modelePendu.getNbErreursMax();
        this.pg.setProgress(progression);
    }

    /**
     * accesseur du chronomètre (pour les controleur du jeu)
     * @return le chronomètre du jeu
     */
    public Chronometre getChrono(){
        return this.chrono;
    }

    public Clavier getClavier(){
        return this.clavier;
    }

    public Alert popUpPartieEnCours(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"La partie est en cours!\n Etes-vous sûr de l'interrompre ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        return alert;
    }
        
    public Alert popUpReglesDuJeu(){
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Le jeu du pendu consiste à deviner un mot en tentant de le trouver lettre par lettre.\n Attention, chaque lettre essayé qui n'est pas bonne complète l'image du pendu, lorsqu'on l'image arrive à son terme, vous avez perdu !");
        return alert;
    }
    
    public Alert popUpMessageGagne(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Bravo ! Vous avez gagné !");        
        return alert;
    }
    
    public Alert popUpMessagePerdu(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Vous avez perdu\n le mot a trouvé était "+this.modelePendu.getMotATrouve());
        return alert;
    }

    /**
     * créer le graphe de scène et lance le jeu
     * @param stage la fenêtre principale
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("IUTEAM'S - La plateforme de jeux de l'IUTO");
        this.primaryStage = stage;
        this.root = new BorderPane();
        this.root.setTop(titre());
        this.panelCentral = fenetreAccueil();
        this.root.setCenter(this.panelCentral);

        Scene scene = new Scene(this.root, 800, 1000);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Programme principal
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
