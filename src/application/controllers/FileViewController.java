package application.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import application.AppController;
import helpers.ViewController;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import javafx.util.StringConverter;
import examination.core.*;
import examination.reader.*;

public class FileViewController extends ViewController<AppController> {
	
	private String path = null;
	private boolean menuOpen = false;
	private ReaderResult res = null;
	private Examination ex = null;
	
	NumberAxis xa, ya;
	
	@FXML private LineChart<Number, Number> datChart;
	@FXML private HBox sidenav;
	@FXML private VBox navContent;
	@FXML private Pane content;
	
	@FXML public void initialize () {
		AppController.register("View", this);
		
		AppController.registerFileView(this);
		
		sidenav.setTranslateX(330f);
		
		datChart.setCreateSymbols(false);

		xa = (NumberAxis) datChart.getXAxis();
		ya = (NumberAxis) datChart.getYAxis();
		
		xa.setLabel("Time");
		ya.setLabel("Values");
		
		xa.setAutoRanging(false);
		xa.setTickUnit(3600); // 1 hour
		
		xa.setTickLabelFormatter(new StringConverter<Number>() {

			@Override
			public String toString(Number num) {
				Long n = num.longValue();
				
				DateFormat dateFormat = new SimpleDateFormat("HH:mm");
				
				Date d = new Date(n * 1000L);
				
				return dateFormat.format(d);				
			}

			@Override
			public Number fromString(String string) {
				// TODO Auto-generated method stub
				return null;
			}
			
		});
		
	}
	
	private VBox genListItem (Measurement m) {
		VBox item = new VBox();
		HBox head = new HBox(10), cont = new HBox(10);
		
		item.getChildren().addAll(head, cont);

		Label date = new Label(m.getDate());
		HBox.setHgrow(date, Priority.ALWAYS);
		date.setMaxWidth(Double.MAX_VALUE);
		
		Label time = new Label(m.getTime() + " Uhr");
		
		head.getChildren().addAll(date, time);
		
		
		head.setPrefHeight(10);
		VBox.setVgrow(head, Priority.ALWAYS);
		VBox.setVgrow(cont, Priority.ALWAYS);
		cont.setPrefHeight(10);
		head.setPrefWidth(290);
		
		
		Label systole = new Label(String.valueOf(m.getSystole()));
		HBox.setHgrow(systole, Priority.ALWAYS);
		systole.setMaxWidth(Double.MAX_VALUE);
		systole.setTextAlignment(TextAlignment.RIGHT);
		systole.setAlignment(Pos.CENTER);
		
		cont.getChildren().addAll(
				new Label(String.valueOf(m.getDiastole())), 
				systole, 
				new Label(String.valueOf(m.getPulse()))
			);
		
		item.setPadding(new Insets(0, 0, 0, 0));
		item.setSpacing(0);
		
		
		return item;
	}
	
	@SuppressWarnings("unchecked")
	public String setPath (String p) {
		path = p;
		
		res = ExamReader.read(path);
		
		if (res.success()) {
			ex = res.getResult();
			
		} else {
			return res.getError();
		}
		
		datChart.setTitle(
				ex.getType() + 
				" from " + 
				ex.getPatient().getFname() + 
				" " + ex.getPatient().getLname()
			);
		
		datChart.getData().clear();
		
		XYChart.Series<Number, Number> diastole = new XYChart.Series<Number, Number>();
		diastole.setName("Diastole");
		
		XYChart.Series<Number, Number> systole = new XYChart.Series<Number, Number>();
		systole.setName("Systole");
		
		XYChart.Series<Number, Number> pulse = new XYChart.Series<Number, Number>();
		pulse.setName("Pulse");
		
		ObservableList<Data<Number, Number>> dia = diastole.getData(), sys = systole.getData(), pul = pulse.getData();
		
		long min = Long.MAX_VALUE, max = Long.MIN_VALUE;
		
		ArrayList<Measurement> list = ex.getMeasurements();
		
		navContent.getChildren().clear();
		
		Patient pat = ex.getPatient();
		
		navContent.getChildren().addAll(
				new Label("ID: " + pat.getPid()),
				new Label("Name: " + pat.getFname() + " " + pat.getLname()),
				new Label("Insurance: " + pat.getInsurance()),
				new Label("Station: " + pat.getStation())
			);
		
		HBox header = new HBox(4);
		Label systo = new Label("Systole");
		
		header.getStyleClass().add("nav-header");
		HBox.setHgrow(systo, Priority.ALWAYS);
		systo.setPrefWidth(190);
		systo.setTextAlignment(TextAlignment.CENTER);
		systo.setAlignment(Pos.CENTER);
		
		header.getChildren().addAll(
				new Label("Diastole"),
				systo,
				new Label("Pulse")
			);
		
		navContent.getChildren().add(header);
		


		int offset = 60*5;
		
		for (int i = 0; i < list.size(); i++) {
			Measurement m = list.get(i);
			
			navContent.getChildren().add(
						genListItem(m)
					);

			max = (max > m.getUnixTime())? max : m.getUnixTime();
			min = (min < m.getUnixTime())? min : m.getUnixTime();
			
			dia.add(new XYChart.Data<Number, Number>(m.getUnixTime() + offset, 0));
			dia.add(new XYChart.Data<Number, Number>(m.getUnixTime() + offset, m.getDiastole()));
			dia.add(new XYChart.Data<Number, Number>(m.getUnixTime() + offset, 0));
			sys.add(new XYChart.Data<Number, Number>(m.getUnixTime() - offset, 0));
			sys.add(new XYChart.Data<Number, Number>(m.getUnixTime() - offset, m.getSystole()));
			sys.add(new XYChart.Data<Number, Number>(m.getUnixTime() - offset, 0));
			pul.add(new XYChart.Data<Number, Number>(m.getUnixTime(), 0));
			pul.add(new XYChart.Data<Number, Number>(m.getUnixTime(), m.getPulse()));
			pul.add(new XYChart.Data<Number, Number>(m.getUnixTime(), 0));
		}
		
		xa.setLowerBound(min - 900);
		xa.setUpperBound(max + 900);
		
		datChart.getData().addAll(diastole, systole, pulse);
		
		if (res.getLinesSkipped() > 0) {
			return "Warn: " + String.valueOf(res.getLinesSkipped()) + " lines were skipped due to formatting errors.";
		}
		
		return null;
	}
	
	@FXML
	protected void close () {
		mother.go("Drop");
	}
	
	@FXML
	protected void burgerClick() {
		menuOpen = !menuOpen;
		
		TranslateTransition tt = new TranslateTransition(Duration.millis(150), sidenav);
		
		tt.setByX(330f * (menuOpen ? -1 : 1));
		tt.setCycleCount(1);
		
		tt.play();		
	}

	@Override
	public void beforeRemove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeRender() {
		// TODO Auto-generated method stub
		
	}

}
