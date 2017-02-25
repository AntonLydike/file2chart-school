package helpers;

import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

// T = class of AppController
public class ViewsHandler<T> {
	private Node curr;
	private double transition = .5;	
	private boolean init = false;

	private AdvancedList<Node> views = new AdvancedList<Node>();
	private AdvancedList<ViewController<T>> controllers = new AdvancedList<>();
	private ArrayList<Label> labels = new ArrayList<Label>();

	private T mother;

	public ViewsHandler () {}

	public void init (StackPane yield, double trans, T mother) {
		if (init) return;
		
		init = true;
		transition = trans;
		this.mother = mother;

		ObservableList<Node> arr = yield.getChildren();

		for (Node n: arr) {			
			if (n.getId() != "") {
				System.out.println("Found View with ID: \"" + n.getId() + "\"");

				views.add(n.getId().trim(), n);

				n.setMouseTransparent(true);
				n.setOpacity(0);
			}
		}
		
		for (ListItem<ViewController<T>> c: controllers) {
			c.getContent().setMother(mother);
			System.out.println("Found Controller with ID: \"" + c.getId() + "\"");
		}
	}

	public void switchTo (String id) {
		// nur wechseln, wenn die View existiert
		if (!views.contains(id)) {
			System.out.println("View \"" + id + "\" not found");
			return;
		}

		// n = Neue View
		Node n = views.getById(id).getContent();
		

		// Nur auf neue Views wechseln
		if (n == curr) return;
		
		
		// o = Alte View (Wenn keine existiert, benutze die neue)
		Node o = curr == null ? n : curr;

		// gegebene Labels mit dem Namen der neuen View versehen
		for (Label l: labels) {
			l.setText(id);
		}

		// Wenn vorhanden, die entsprechenden Funktionen der ViewController aufrufen
		ListItem<ViewController<T>> ctrl;
		if ((ctrl = controllers.getById(id)) != null) {
			ctrl.getContent().beforeRender();
		}
		if ((ctrl = controllers.getById(o.getId())) != null) {
			ctrl.getContent().beforeRemove();
		}

		// ot = Transition der Alten View
		FadeTransition ot = new FadeTransition(Duration.seconds(transition), o);
		ot.setFromValue(1);
		ot.setToValue(0);
		ot.setCycleCount(1);		
		ot.play();

		// nt = Transition der neuen View (Fade-in)
		FadeTransition nt = new FadeTransition(Duration.seconds(transition), n);
		nt.setFromValue(0);
		nt.setToValue(1);
		nt.setCycleCount(1);
		// Fade-In erst animieren, wenn die alte view weg ist
		nt.setDelay(Duration.seconds(transition));
		nt.play();

		// Alte View soll nicht mehr "click-bar" sein
		o.setMouseTransparent(true);
		// Dafür die neue View
		n.setMouseTransparent(false);

		// Neue aktuelle View setzen
		curr = n;
	}

	public void setDefault (String id) {
		if (!views.contains(id)) {
			System.out.println("View \"" + id + "\" not found");

			return;
		}
		curr = views.getById(id).getContent();
	}

	public void addLabel (Label l) {
		labels.add(l);
	}

	public void registerController (String id, ViewController<T> ctrl) {
		controllers.add(id, ctrl);

		ctrl.setMother(mother);
	}
}
