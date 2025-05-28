import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.*;


/**
 * Permet de gérer un Text associé à une Timeline pour afficher un temps écoulé
 */
public class Chronometre extends Text{
    /**
     * timeline qui va gérer le temps
     */
    private Timeline timeline;
    /**
     * la fenêtre de temps
     */
    private KeyFrame keyFrame;
    /**
     * le contrôleur associé au chronomètre
     */
    private ControleurChronometre actionTemps;

    private Label temps;

    /**
     * Constructeur permettant de créer le chronomètre
     * avec un label initialisé à "0:0:0"
     * Ce constructeur créer la Timeline, la KeyFrame et le contrôleur
     */
    public Chronometre(ControleurChronometre actionTemps){
        // A implémenter
        // this.actionTemps = actionTemps;
        // this.keyFrame = new KeyFrame(null, null);
        // this.timeline = new Timeline();
        // this.temps = new Label();
        // this.temps.setText(0+":"+0+":"+0);
    }

    /**
     * Permet au controleur de mettre à jour le text
     * la durée est affichée sous la forme m:s
     * @param tempsMillisec la durée depuis à afficher
     */
    public void setTime(long tempsMillisec){
        // A implémenter
        long secondes = tempsMillisec /100;
        if( secondes>=60){
            long minutes = secondes/60;
            secondes -= minutes*60;
            this.temps.setText(minutes + " min "+ secondes+ " s ");
        }
        else{
            this.temps.setText(secondes + "s");
        }
    }

    /**
     * Permet de démarrer le chronomètre
     */
    public void start(){
        this.timeline.play();
    }


    /**
     * Permet d'arrêter le chronomètre
     */
    public void stop(){
        this.timeline.stop();
    }

    /**
     * Permet de remettre le chronomètre à 0
     */
    public void resetTime(){
        this.temps.setText(0 +"s");
    }
}
